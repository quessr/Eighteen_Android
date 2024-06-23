package com.eighteen.eighteenandroid.presentation.auth.signup.addmedias

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.common.safeLet
import com.eighteen.eighteenandroid.databinding.FragmentSignUpAddMediasBinding
import com.eighteen.eighteenandroid.presentation.auth.signup.BaseSignUpContentFragment
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpEditMediaAction
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpNextButtonModel
import com.eighteen.eighteenandroid.presentation.common.getMimeTypeFromUri
import com.eighteen.eighteenandroid.presentation.common.mediapicker.MediaPicker
import kotlinx.coroutines.launch

class SignUpAddMediasFragment :
    BaseSignUpContentFragment<FragmentSignUpAddMediasBinding>(FragmentSignUpAddMediasBinding::inflate) {
    override val onMoveNextPageAction: () -> Unit = {
        findNavController().navigate(R.id.action_fragmentSignUpAddMedias_to_fragmentSignUpCompleted)
    }
    override val progress: Int = 100
    override val signUpNextButtonModel = SignUpNextButtonModel(
        isVisible = true,
        isEnabled = true,
        size = SignUpNextButtonModel.Size.NORMAL,
        buttonText = SignUpNextButtonModel.ButtonText.PASS
    )

    private val signUpAddMediasViewModel by viewModels<SignUpAddMediasViewModel>()
    private val mediaPicker = MediaPicker(this) { uri ->
        safeLet(uri, context) { nonNullUri, context ->
            getMimeTypeFromUri(nonNullUri, context)?.let { typeString ->
                if (typeString.startsWith("image")) SignUpEditMediaAction.EditImage(nonNullUri)
                else if (typeString.startsWith("video")) SignUpEditMediaAction.EditVideo(
                    nonNullUri
                )
                else null
            }?.also { action ->
                signUpViewModelContentInterface.setEditMediaAction(action)
            }
        }
    }
    private var signUpMediasAdapter: SignUpMediasAdapter? = null

    private val clickListener = object : SignUpAddMediasClickListener {
        override fun onClickAddMedia(position: Int) {
            mediaPicker.openMediaPicker()
        }

        override fun onClickMedia() {
            Log.d("TESTLOG", "onClickMedia")
            //TODO 미디어 클릭 시
        }
    }

    override fun initView() {
        initRecyclerView()
        initStateFlow()
        initFragmentResultListener()
    }

    private fun initRecyclerView() {
        bind {
            rvMedias.adapter = SignUpMediasAdapter(clickListener = clickListener).also {
                signUpMediasAdapter = it
            }
            rvMedias.addItemDecoration(SignUpMediasItemDecoration())
        }
    }

    private fun initStateFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                signUpAddMediasViewModel.mediasStateFlow.collect {
                    (binding.rvMedias.adapter as? SignUpMediasAdapter)?.submitList(it)
                }
            }
        }
    }

    private fun initFragmentResultListener() {
//        getNavigationResult<EditImageResult>(EDIT_IMAGE_RESULT_KEY)?.observe(
//            viewLifecycleOwner
//        ) {
//            Log.d("TESTLOG", "add $it")
//        }
    }

    override fun onDestroyView() {
        signUpMediasAdapter = null
        super.onDestroyView()
    }
}