package com.sopt.cherish.ui.pwfinding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ActivityPwFindingBinding
import com.sopt.cherish.ui.signin.SignInActivity
import com.sopt.cherish.ui.signup.SignUpFirstFragment

class PwFindingActivity : AppCompatActivity() {

    private lateinit var binding:ActivityPwFindingBinding
    lateinit var mBundle: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPwFindingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeFragment()
    }

    private fun initializeFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_finding, PwFindingFirstFragment()).commit()
    }

    fun setActionBarTitlePwFinding(title: String?) {
        setSupportActionBar(binding.toolbarPwFinding)
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {

            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.icon_gnb_back)
            actionBar.setDisplayShowTitleEnabled(false)
            binding.toolbarTitleFinding.text = title
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun replaceFragment(index: Int) {
        val transAction = supportFragmentManager.beginTransaction()

        when (index) {
            0 -> {
                transAction.replace(R.id.fragment_finding, PwFindingFirstFragment()).commit()
            }
            1 ->
                transAction.replace(R.id.fragment_finding, PwFindingSecondFragment()).commit()

            2 ->
                transAction.replace(R.id.fragment_finding, PwFindingThirdFragment()).commit()
        }
    }


    fun postData(bundle: Bundle) {
        this.mBundle = bundle
    }
}