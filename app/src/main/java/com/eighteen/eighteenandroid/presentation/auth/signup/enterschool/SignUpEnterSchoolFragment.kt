package com.eighteen.eighteenandroid.presentation.auth.signup.enterschool

import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.FragmentSignUpEnterSchoolBinding
import com.eighteen.eighteenandroid.presentation.auth.signup.BaseSignUpContentFragment
import com.eighteen.eighteenandroid.presentation.auth.signup.model.SignUpNextButtonModel
import com.eighteen.eighteenandroid.presentation.common.ModelState
import com.eighteen.eighteenandroid.presentation.common.dp2Px
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//TODO 검색 결과 뷰 visibility 조절, 학교 선택하면 어떻게 되는지?
@AndroidEntryPoint
class SignUpEnterSchoolFragment :
    BaseSignUpContentFragment<FragmentSignUpEnterSchoolBinding>(FragmentSignUpEnterSchoolBinding::inflate) {
    override val onMovePrevPageAction: () -> Unit = {
        signUpViewModelContentInterface.birth = null
        super.onMovePrevPageAction.invoke()
    }
    override val onMoveNextPageAction: () -> Unit = {
        findNavController().navigate(R.id.action_fragmentSignUpEnterSchool_to_fragmentSignUpSelectTag)
    }
    override val progress: Int = 80
    override val signUpNextButtonModel = SignUpNextButtonModel(
        isVisible = true,
        isEnabled = true,
        size = SignUpNextButtonModel.Size.NORMAL,
        buttonText = SignUpNextButtonModel.ButtonText.NEXT
    )

    private val signUpEnterSchoolViewModel by viewModels<SignUpEnterSchoolViewModel>()

    private var textChangeJob: Job? = null

    override fun initView() {
        initEditText()
        initRecyclerView()
        initStateFlow()
    }

    private fun initEditText() = with(binding.etInput) {
        addTextChangedListener {
            val keyword = it?.toString() ?: ""
            if (keyword.isBlank()) return@addTextChangedListener
            textChangeJob?.cancel()
            textChangeJob = viewLifecycleOwner.lifecycleScope.launch {
                delay(SEARCH_DEBOUNCE_MIL_SEC)
                signUpEnterSchoolViewModel.requestGetSchools(schoolName = keyword)
            }
        }
    }

    private fun initRecyclerView() = with(binding.rvSearchResult) {
        adapter = SignUpEnterSchoolSearchResultAdapter()
        val dividerColorInt = ContextCompat.getColor(context, R.color.divider)
        val dividerHeightPx = context.dp2Px(DIVIDER_HEIGHT_DP)
        addItemDecoration(
            SignUpEnterSchoolSearchResultItemDecoration(
                dividerColor = dividerColorInt,
                dividerHeightPx = dividerHeightPx
            )
        )

    }

    private fun initStateFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                signUpEnterSchoolViewModel.schoolsStateFlow.collect {
                    when (it) {
                        is ModelState.Loading -> {
                            //TODO Loading 처리
                        }
                        is ModelState.Success -> {
                            bind {
                                (rvSearchResult.adapter as? SignUpEnterSchoolSearchResultAdapter)?.submitList(
                                    it.data?.schools
                                )
                            }

                        }
                        is ModelState.Error -> {
                            //TODO Error 처리
                        }
                        else -> {
                            //do nothing
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_MIL_SEC = 500L
        private const val DIVIDER_HEIGHT_DP = 1
    }
}