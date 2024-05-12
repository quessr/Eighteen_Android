package com.eighteen.eighteenandroid.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import com.eighteen.eighteenandroid.R
import com.eighteen.eighteenandroid.databinding.ActivityMainBinding
import androidx.fragment.app.commit
import com.eighteen.eighteenandroid.presentation.home.MainFragment

/**
 *
 * @file MainActivity.kt
 * @date 05/08/2024
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationBar.itemIconTintList = null

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<MainFragment>(R.id.fragment_container_view)
                add<TestFragment>(R.id.test_fragment)
            }
        }
    }
}