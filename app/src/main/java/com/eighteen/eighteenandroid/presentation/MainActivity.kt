package com.eighteen.eighteenandroid.presentation

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.ActivityMainBinding
import com.eighteen.eighteenandroid.presentation.common.permission.PermissionManager
import com.eighteen.eighteenandroid.presentation.dialog.TwoButtonPopUpDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 *
 * @file MainActivity.kt
 * @date 05/08/2024
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val permissionManager = PermissionManager(this)

    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationBar.itemIconTintList = null
        setupNavigation()
        initStateFlow()
        initFragmentResult()
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController

        // 바텀 네비게이션과 NavController 연결
        binding.bottomNavigationBar.setupWithNavController(navController)

        // 네비게이션 목적지 변경 시 바텀 네비게이션의 가시성 조정
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val visibleNavigationIds = listOf(
                R.id.fragmentMain,
                R.id.fragmentMyProfile,
                R.id.fragmentChat,
                R.id.teenMainFragment
            )
            binding.bottomNavigationBar.isVisible = visibleNavigationIds.contains(destination.id)
        }
    }

    private fun initStateFlow() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.myProfileStateFlow.collect {
                    //TODO placeholder 추가
                    if (it.isSuccess()) {
                        Glide.with(this@MainActivity).asBitmap()
                            .placeholder(R.drawable.ic_launcher_background).circleCrop()
                            .load(it.data?.medias?.firstOrNull()?.url)
                            .into(object : CustomTarget<Bitmap>() {
                                override fun onResourceReady(
                                    resource: Bitmap,
                                    transition: Transition<in Bitmap>?
                                ) {
                                    val drawable = BitmapDrawable(resources, resource)
                                    binding.bottomNavigationBar.menu.children.find { menu -> menu.itemId == R.id.fragmentMyProfile }?.icon =
                                        drawable
                                }

                                override fun onLoadCleared(placeholder: Drawable?) {
                                }
                            })
                    }
                }
            }
        }
    }

    private fun initFragmentResult() {
        supportFragmentManager.setFragmentResultListener(
            LOGIN_DIALOG_REQUEST_KEY,
            this,
            object : TwoButtonPopUpDialogFragment.TowButtonPopUpDialogFragmentResultListener() {
                override fun onConfirm() {
                    //TODO 로그인 화면 이동
                }
            })
    }

    fun requestWithLogin(request: () -> Unit) {
        if (loginViewModel.authToken?.accessToken == null) {
            showLoginDialog()
            return
        }

    }

    private fun showLoginDialog() {
        val dialogFragment =
            TwoButtonPopUpDialogFragment.newInstance(requestKey = LOGIN_DIALOG_REQUEST_KEY)
        dialogFragment.show(supportFragmentManager, null)
    }

    companion object {
        private const val LOGIN_DIALOG_REQUEST_KEY = "login_dialog_request_key"
    }
}