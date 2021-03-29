package com.sopt.cherish.ui.main.setting

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentUserModifyBinding
import com.sopt.cherish.remote.api.RequestNicknameData
import com.sopt.cherish.remote.api.ResponseNicknameChangedata
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import com.sopt.cherish.ui.main.MainViewModel
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
    val REQUEST_IMAGE_CAPTURE = 1
    val REQUEST_GALLERY_TAKE=2
    lateinit var currentPhotoPath : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_modify, container, false)

        binding = FragmentUserModifyBinding.bind(view)

        binding.settingModifyBack.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.modifyUserImg.setOnClickListener{
            binding.buttonNickchange.setBackgroundColor(Color.parseColor("#1AD287"))
            changeProfileImage()
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
            val body = RequestNicknameData(
                viewModel.cherishUserId.value!!,
                binding.settingEditNickname.text.toString()
            )
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

    private fun initializeProfile(){
        if(ImageSharedPreferences.getImageFile(requireContext()).length!=0){ //앨범일 때
            val uri=Uri.parse(ImageSharedPreferences.getImageFile(requireContext()))
            Glide.with(requireContext()).load(uri).circleCrop().into(binding.modifyUserImg)
        }
    }

    private fun changeProfileImage() {
        val popUp = PopupMenu(requireContext(), binding.modifyUserImg)
        popUp.menuInflater.inflate(R.menu.profile_menu, popUp.menu)

        popUp.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.camera -> {
                    captureCamera()
                }
                R.id.gallery ->
                    getAlbum()
            }
            true
        })
        popUp.show()
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp:String= SimpleDateFormat("yyyyMMdd__HHmmss").format(Date())
        val storageDir: File?=requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply{
            currentPhotoPath=absolutePath
        }
    }

    private fun getAlbum(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_TAKE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            val bitmap = BitmapFactory.decodeFile(currentPhotoPath)
            lateinit var exif : ExifInterface

            ImageSharedPreferences.clearImage(requireContext())
            ImageSharedPreferences.setImageFile(requireContext(),currentPhotoPath)

            try{
                exif = ExifInterface(currentPhotoPath)
                var exifOrientation = 0
                var exifDegree = 0

                exifOrientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )
                exifDegree = exifOrientationToDegress(exifOrientation)

                Glide.with(requireContext()).load(rotate(bitmap, exifDegree)).circleCrop().into(
                    binding.modifyUserImg
                )
                //binding.myPageUserImg.setImageBitmap(rotate(bitmap, exifDegree))
            }catch (e: IOException){
                e.printStackTrace()
            }

        }

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_GALLERY_TAKE){
            //binding.myPageUserImg.setImageURI(data?.data)
            ImageSharedPreferences.clearImage(requireContext())
            ImageSharedPreferences.setImageFile(requireContext(),data?.data.toString())

            Glide.with(requireContext()).load(data?.data).circleCrop().into(binding.modifyUserImg)
        }
    }


    private fun exifOrientationToDegress(exifOrientation: Int): Int {
        when(exifOrientation){
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

    private fun captureCamera(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                val photoFile: File? = try{
                    createImageFile()
                }catch (ex: IOException){
                    null
                }
                photoFile?.also{
                    val photoURI : Uri = FileProvider.getUriForFile(
                        requireContext(),
                        "com.sopt.cherish.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    private fun rotate(bitmap: Bitmap, degree: Int) : Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var usernick = arguments?.getString("settingusernickname").toString()
        binding.settingEditNickname.hint = (usernick)

        binding.settingEditEmail.hint = (arguments?.getString("settinguseremail"))
        binding.settingEditEmail.isEnabled = false
    }


}