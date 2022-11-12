package com.pr.potd.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pr.potd.R
import com.pr.potd.databinding.ActivityMainBinding
import com.pr.potd.ui.fragments.MainFragment
import com.pr.potd.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(R.id.container, supportFragmentManager, MainFragment.newInstance(), false)
    }


}