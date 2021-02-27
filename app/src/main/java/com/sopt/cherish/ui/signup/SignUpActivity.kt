package com.sopt.cherish.ui.signup

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySignUpBinding
    lateinit var email: String
    lateinit var password: String
    lateinit var phone: String
    var sex:Boolean=true
    lateinit var birth:String
    lateinit var nickname:String
    lateinit var mBundle:Bundle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivitySignUpBinding.inflate(layoutInflater)

        initializeFragment()

        setContentView(R.layout.activity_sign_up)
    }

    private fun initializeFragment(){
        supportFragmentManager.beginTransaction().add(R.id.sign_up_fragment_container,SignUpFirstFragment()).commit()
    }

    fun setActionBarTitle(title: String?) {
        setSupportActionBar(binding.signUpToolBar)
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.icon_gnb_back)
            actionBar.setDisplayShowTitleEnabled(false)

            binding.signUpText.text = title
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    fun replaceFragment(index:Int){
        val transAction = supportFragmentManager.beginTransaction()

        when(index){
            0->
                transAction.replace(R.id.sign_up_fragment_container,SignUpFirstFragment()).commit()
            1->
                transAction.replace(R.id.sign_up_fragment_container,SignUpSecondFragment()).commit()
            2->
                transAction.replace(R.id.sign_up_fragment_container,SignUpThirdFragment()).commit()
            3->
                transAction.replace(R.id.sign_up_fragment_container,SignUpFourthFragment()).commit()
        }
    }
/*
    fun setData(fragment:Fragment,email:String,password:String,phone:String,sex:Boolean,birth:String,nickname:String){
        this.email=email
        this.password=password
        this.phone=phone
        this.sex=sex
        this.birth=birth
        this.nickname=nickname

        Log.d("activity",email)

        postData(fragment)
    }

    fun postData(fragment: Fragment){
        var bundle=Bundle()
        bundle.putString("email",email)
        bundle.putString("password",password)
        bundle.putString("phone",phone)
        bundle.putBoolean("sex",sex)
        bundle.putString("birth",birth)
        bundle.putString("nickname",nickname)

        Log.d("postdata",email)
        fragment.arguments=bundle
    }  */

    fun postData(bundle:Bundle){
        this.mBundle=bundle
    }
}