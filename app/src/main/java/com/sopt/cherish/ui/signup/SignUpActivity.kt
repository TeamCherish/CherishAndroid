package com.sopt.cherish.ui.signup

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ActivitySignUpBinding


class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    lateinit var email: String
    lateinit var phone: String
    lateinit var birth: String
    lateinit var nickname: String
    lateinit var mBundle: Bundle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeFragment()
    }


    private fun initializeFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_signup, SignUpFirstFragment()).commit()
    }

    fun setActionBarTitleSignUp(title: String?) {
        setSupportActionBar(binding.toolbarSignup)
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {

            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.icon_gnb_back)
            actionBar.setDisplayShowTitleEnabled(false)
            binding.toolbarTitleSignup.text = title
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    fun replaceFragment(index: Int) {
        val transAction = supportFragmentManager.beginTransaction()

        when (index) {
            0 -> {
                transAction.replace(R.id.fragment_signup, SignUpFirstFragment())
                    .addToBackStack(null)
                    .commit()
            }
            1 ->
                transAction.replace(R.id.fragment_signup, SignUpSecondFragment())
                    .addToBackStack(null)
                    .commit()

            2 ->
                transAction.replace(R.id.fragment_signup, SignUpFourthFragment())
                    .addToBackStack(null)
                    .commit()
        }
    }


    fun postData(bundle: Bundle) {
        this.mBundle = bundle
    }
}