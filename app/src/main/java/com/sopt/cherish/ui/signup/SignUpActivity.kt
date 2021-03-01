package com.sopt.cherish.ui.signup

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ActivitySignUpBinding
import com.sopt.cherish.ui.signin.SignInActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    lateinit var email: String
    lateinit var password: String
    lateinit var phone: String
    var sex: Boolean = true
    lateinit var birth: String
    lateinit var nickname: String
    lateinit var mBundle: Bundle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeFragment()
        //setFragment(SignUpFirstFragment())


    }

    fun setFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_signup, fragment)
        transaction.commit()
    }

    private fun initializeFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_signup, SignUpFirstFragment()).commit()
    }

    fun setActionBarTitlesignup(title: String?) {
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
            android.R.id.home -> startActivity(Intent(this, SignInActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    fun replaceFragment(index: Int) {
        val transAction = supportFragmentManager.beginTransaction()

        when (index) {
            0 -> {
                transAction.replace(R.id.fragment_signup, SignUpFirstFragment())
                //  transAction.addToBackStack(null)
                transAction.commit()
            }
            1 ->
                transAction.replace(R.id.fragment_signup, SignUpSecondFragment())
                    .commit()
            2 ->
                transAction.replace(R.id.fragment_signup, SignUpThirdFragment()).commit()
            3 ->
                transAction.replace(R.id.fragment_signup, SignUpFourthFragment())
                    .commit()
        }
    }


    fun postData(bundle: Bundle) {
        this.mBundle = bundle
    }
}