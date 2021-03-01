package com.sopt.cherish.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.sopt.cherish.R
import com.sopt.cherish.ui.signin.SignInActivity

/**
 * Created on 2020-01-03 by SSong-develop
 * 스플래쉬 시점이 아닌 Tutorial이 끝난 시점에 하는게 맞는거 같다.
 * 권한 허가를 어느 뷰에서 해줄 지는 다시 생각해보자
 * test방법 : Splash Activity를 처음 시작하는 activity로 둔 후 실행
 *
 * PermissionUtil 이 필요없을 수 도 있겠지만 , 생각해야할 것은 유저가 permission을 허가하지 않는 경우
 * 가 존재한다는 것을 인지하고 개발해야하는 것이 중요하다는게 생각이다.
 */
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }, 3000)

    }


}