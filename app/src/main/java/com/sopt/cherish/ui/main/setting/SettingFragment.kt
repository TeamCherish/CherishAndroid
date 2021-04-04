package com.sopt.cherish.ui.main.setting

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.sopt.cherish.MainApplication
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentSettingBinding
import com.sopt.cherish.remote.api.*
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import com.sopt.cherish.ui.dialog.deletedialog.DeleteUserDialog
import com.sopt.cherish.ui.dialog.selectgender.SelectGenderDialogFragment
import com.sopt.cherish.ui.main.MainViewModel
import com.sopt.cherish.ui.splash.SplashActivity
import com.sopt.cherish.util.ImageSharedPreferences
import com.sopt.cherish.util.extension.shortToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

/**
 * 환경 설정 뷰
 */
class SettingFragment : Fragment() {

    private val requestData = RetrofitBuilder
    private var usernickname: String = ""
    private var useremail: String = ""
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        binding.mainViewModel = viewModel
        binding = FragmentSettingBinding.bind(view)

        setView()
        binding.constraintLayoutQuestion.setOnClickListener {
            //setFragment(SettingAlarmFragment())
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("Co.Cherishteam@gmail.com"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "체리쉬 문의")
            intent.putExtra(
                Intent.EXTRA_TEXT, "1. 문의 유형(문의, 버그 제보, 기타) :\n" +
                        "2. 회원 닉네임(필요시 기입) :\n" +
                        "3. 문의 내용 :\n" +
                        "\n" +
                        "문의하신 사항은 체리쉬팀이 신속하게 처리하겠습니다. 감사합니다 :)"
            )
            intent.type = "message/rfc822"
            startActivity(intent)
        }

        binding.constraintLayoutAboutCherish.setOnClickListener {
            val intent = Intent(context,AboutCherishActivity::class.java)
            startActivity(intent)
        }

        binding.constraintLayoutInfo.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.notion.so/Cherish-2d35c1bffa2f4d49943db302d76e3cac")
            )
            startActivity(intent)
        }

        binding.constraintLayoutService.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.notion.so/Cherish-d96f88172ffa4d80b257665849bddc65")
            )
            startActivity(intent)
        }

        binding.settingNextNickname.setOnClickListener {
            setFragment(UserModifyFragment())
        }

        binding.settingAlarmSetting.setOnCheckedChangeListener { buttonView, isChecked ->
            MainApplication.sharedPreferenceController.setAlarmKey(isChecked)
        }

        binding.quit.setOnClickListener{
            val fm = requireActivity().supportFragmentManager
            val dialogFragment = DeleteUserDialog(R.layout.fragment_delete_user_dialog)
            dialogFragment.setTargetFragment(this@SettingFragment, 101)
            dialogFragment.show(fm, "SettingFragment")
        }

        binding.friendsCons.setOnClickListener {
            shortToast(requireContext(), "로그아웃 되었습니다.")
            MainApplication.sharedPreferenceController.apply {
                deleteToken()
                deleteUserId()
                deleteUserPassword()
            }
            requireActivity().finish()
            val intent = Intent(requireActivity(), SplashActivity::class.java)
            startActivity(intent)
        }

        initializeProfile(binding)

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        initializeProfile(binding)
    }

    private fun initializeProfile(binding: FragmentSettingBinding) {
        if (ImageSharedPreferences.getGalleryFile(requireContext()).isNotEmpty()) { //앨범일 때
            val uri = Uri.parse(ImageSharedPreferences.getGalleryFile(requireContext()))
            Glide.with(requireContext()).load(uri).circleCrop().into(binding.imageView8)
        } else if (ImageSharedPreferences.getCameraFile(requireContext()).isNotEmpty()) {
            val path = ImageSharedPreferences.getCameraFile(requireContext())
            val bitmap = BitmapFactory.decodeFile(path)
            lateinit var exif: ExifInterface

            try {
                exif = ExifInterface(path)
                var exifOrientation = 0
                var exifDegree = 0

                exifOrientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )
                exifDegree = exifOrientationToDegress(exifOrientation)

                Glide.with(requireContext()).load(rotate(bitmap, exifDegree)).circleCrop().into(
                    binding.imageView8
                )
                //binding.myPageUserImg.setImageBitmap(rotate(bitmap, exifDegree))
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            binding.imageView8.setBackgroundResource(R.drawable.user_img)
        }
    }

    private fun rotate(bitmap: Bitmap, degree: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    private fun exifOrientationToDegress(exifOrientation: Int): Int {
        when (exifOrientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> {
                return 90
            }
            ExifInterface.ORIENTATION_ROTATE_180 -> {
                return 180
            }
            ExifInterface.ORIENTATION_ROTATE_270 -> {
                return 270
            }
            else -> {
                return 0
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.settingAlarmSetting.isChecked =
            MainApplication.sharedPreferenceController.getAlarmKey()
        setView()
    }

    fun setFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment_container, fragment.apply {
            arguments = Bundle().apply {
                putString("settingusernickname", usernickname)
                putString("settinguseremail", useremail)

            }
        })
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun setView() {
        requestData.myPageAPI.fetchUserPage(viewModel.cherishUserId.value!!)
            .enqueue(
                object : Callback<MyPageUserRes> {
                    override fun onFailure(call: Call<MyPageUserRes>, t: Throwable) {
                        Log.d("통신 실패", t.toString())
                    }

                    override fun onResponse(
                        call: Call<MyPageUserRes>,
                        response: Response<MyPageUserRes>
                    ) {
                        Log.d("success", response.body().toString())
                        response.takeIf {
                            it.isSuccessful
                        }?.body()
                            ?.let { it ->

                                usernickname = it.myPageUserData.user_nickname
                                useremail = it.myPageUserData.email

                                binding.settingUsernickname.text =
                                    it.myPageUserData.user_nickname
                                binding.settingUseremail.text = it.myPageUserData.email

                            }
                    }
                })
    }

}