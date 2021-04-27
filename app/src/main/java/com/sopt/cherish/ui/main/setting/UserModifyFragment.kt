package com.sopt.cherish.ui.main.setting

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentUserModifyBinding
import com.sopt.cherish.remote.api.RequestNicknameData
import com.sopt.cherish.remote.api.ResponseNicknameChangedata
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import com.sopt.cherish.ui.main.MainActivity
import com.sopt.cherish.ui.main.MainViewModel
import com.sopt.cherish.util.FinalSharedPreferences
import com.sopt.cherish.util.ImageSharedPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class UserModifyFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()

    lateinit var binding: FragmentUserModifyBinding
    private val requestData = RetrofitBuilder
    val REQUEST_GALLERY_TAKE = 2
    var usernick: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_user_modify, container, false)

        binding = FragmentUserModifyBinding.bind(view)

        binding.settingModifyBack.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.clickView.setOnClickListener {
            changeProfileImage()
            binding.buttonNickchange.setBackgroundColor(Color.parseColor("#1AD287"))
        }

        binding.settingEditNickname.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                @SuppressLint("ResourceType")
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    binding.buttonNickchange.setBackgroundColor(Color.parseColor("#1AD287"))
                }
            })

        binding.settingmodifycancel.setOnClickListener {
            binding.settingEditNickname.setText("")
        }
        binding.buttonNickchange.setOnClickListener {
            if (binding.settingEditNickname.text.toString().isNotEmpty())
                usernick = binding.settingEditNickname.text.toString()

            val body = RequestNicknameData(
                viewModel.cherishUserId.value!!,
                usernick!!
            )

            (activity as MainActivity).beforeClick = false

            val galleryUri = FinalSharedPreferences.getGalleryFile(requireContext())

            if (galleryUri.isNotEmpty())
                ImageSharedPreferences.setGalleryFile(requireContext(), galleryUri)
            else if (galleryUri.isEmpty()) {
                ImageSharedPreferences.clearImage(requireContext(), "gallery")
            }

            requestData.nicknameChangeAPI.nicknamechange(body)
                .enqueue(
                    object : Callback<ResponseNicknameChangedata> {
                        override fun onFailure(
                            call: Call<ResponseNicknameChangedata>,
                            t: Throwable
                        ) {
                        }

                        override fun onResponse(
                            call: Call<ResponseNicknameChangedata>,
                            response: Response<ResponseNicknameChangedata>
                        ) {
                            response.takeIf {
                                it.isSuccessful
                            }?.body()
                                ?.let { it ->

                                    binding.settingEditNickname.text =
                                        binding.settingEditNickname.text
                                    // binding.settingEditEmail.text=binding.settingEditNickname.text

                                }
                        }
                    }
                )
            activity?.onBackPressed()

        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initializeProfile()
    }

    private fun getAlbum() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_TAKE)
    }

    private fun initializeProfile() {
        if ((activity as MainActivity).beforeClick) {
            binding.modifyUserImg.setBackgroundResource(R.drawable.user_img)
            (activity as MainActivity).beforeClick = false
        } else {
            if (ImageSharedPreferences.getGalleryFile(requireContext()).isNotEmpty()) { //앨범일 때
                val uri = Uri.parse(ImageSharedPreferences.getGalleryFile(requireContext()))
                Glide.with(requireContext()).load(uri).circleCrop().into(binding.modifyUserImg)
            } else {
                binding.modifyUserImg.setBackgroundResource(R.drawable.user_img)
            }
        }

    }

    private fun changeProfileImage() {
        val popUp = PopupMenu(requireContext(), binding.modifyUserImg)
        popUp.menuInflater.inflate(R.menu.profile_menu, popUp.menu)

        popUp.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.gallery ->
                    getAlbum()
                R.id.delete ->
                    deleteImage()

            }
            true
        })
        popUp.show()
    }


    private fun deleteImage() {
        FinalSharedPreferences.clearImage(requireContext(), "gallery")

        (activity as MainActivity).beforeClick = true
        val transaction = parentFragmentManager.beginTransaction()
        transaction.detach(this).attach(this).commit()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_GALLERY_TAKE) {
            //binding.myPageUserImg.setImageURI(data?.data)
            FinalSharedPreferences.clearImage(requireContext(), "gallery")
            FinalSharedPreferences.setGalleryFile(requireContext(), data?.data.toString())

            Glide.with(requireContext()).load(data?.data).circleCrop().into(binding.modifyUserImg)
        } else {
            binding.modifyUserImg.setBackgroundResource(R.drawable.user_img)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        usernick = arguments?.getString("settingusernickname").toString()
        binding.settingEditNickname.hint = (usernick)

        binding.settingEditEmail.hint = (arguments?.getString("settinguseremail"))
        binding.settingEditEmail.isEnabled = false
    }
}