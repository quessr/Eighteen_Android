package com.eighteen.eighteenandroid.presentation.auth.signup.addmedias

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentSignUpAddMediasBinding
import com.eighteen.eighteenandroid.presentation.auth.signup.BaseSignUpContentFragment
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpNextButtonModel
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

    private val clickListener = object : SignUpAddMediasClickListener {
        override fun onClickAddMedia(position: Int) {
            Log.d("TESTLOG", "onClickAddMedia $position")
            //TODO 미디어 선택
        }

        override fun onClickMedia() {
            Log.d("TESTLOG", "onClickMedia")
            //TODO 미디어 클릭 시
        }
    }

    override fun initView() {
        initRecyclerView()
        initStateFlow()
    }

    private fun initRecyclerView() {
        bind {
            rvMedias.adapter = SignUpMediasAdapter(clickListener = clickListener)
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


}