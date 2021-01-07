package com.sopt.cherish.ui.enrollment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.sopt.cherish.databinding.ActivityPhonebookBinding
import com.sopt.cherish.databinding.ItemLayoutBinding
import com.sopt.cherish.ui.adapter.Phone
import com.sopt.cherish.ui.adapter.PhoneBookAdapter

class PhoneBookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhonebookBinding
    private lateinit var binding_item: ItemLayoutBinding


    val permissions = arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE)
    lateinit var madapter: PhoneBookAdapter
    var phonelist = mutableListOf<Phone>()
    var searchText = ""
    var sortText = "asc"

    // todo : 퍼미션 util 쓸거고 로직들 다시한번 확인하고 왜 지워야지 주석처리 하는지 모르겠음
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhonebookBinding.inflate(layoutInflater)
        binding_item = ItemLayoutBinding.inflate(layoutInflater)

        setSupportActionBar(binding.toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        //ab.setLogo(R.drawable.icn_gnb_back)
        ab.setDisplayHomeAsUpEnabled(true)


        setContentView(binding.root)
        checkAndStart()


        // 버튼 클릭하면 라디오 버튼 선택한 데이터가 다음 식물등록 뷰에 보여지는 부분
        binding.buttonnext.setOnClickListener {
            if (madapter.checkedRadioButton != null) {
                val intent = Intent(this, EnrollPlantActicity::class.java)
                //Toast.makeText(this,madapter.phonename,Toast.LENGTH_LONG).show()
                intent.putExtra("phonename", madapter.phonename)
                intent.putExtra("phonenumber", madapter.phonenumber)
                Log.d("vvvv", madapter.phonename.toString())
                startActivity(intent)
            }
        }


        /* binding.imageButtonphone.setOnClickListener {
             val intent = Intent(this, MainActivity::class.java)
             startActivity(intent)
         }*/

    }

    // toolbar에서 뒤로가기 버튼 부분
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun startProcess() {
        setList()
        setSearchListener()

        // setContentView(R.layout.activity_main)

    }

    // 권한처리 코드
    private fun checkAndStart() {
        if (isLower23() || isPermitted()) {
            startProcess()
        } else {
            ActivityCompat.requestPermissions(this, permissions, 99)
        }
    }

    private fun isLower23(): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isPermitted(): Boolean {
        for (perm in permissions) {
            if (checkSelfPermission(perm) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 99) {
            var check = true
            for (grant in grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    check = false
                    break
                }
            }
            if (check) startProcess()
            else {
                Toast.makeText(this, "권한 승인을 하셔야지만 앱을 사용할 수 있습니다.", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    fun setSearchListener() {
        binding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchText = s.toString()
                changeList()
            }
        })
    }

    fun changeList() {
        val newList = getPhoneNumbers(sortText, searchText)
        this.phonelist.clear()
        this.phonelist.addAll(newList)
        this.madapter.notifyDataSetChanged()
    }

    fun setList() {
        phonelist.addAll(getPhoneNumbers(sortText, searchText))
        madapter = PhoneBookAdapter(phonelist)
        binding.recycler.adapter = madapter
        binding.recycler.layoutManager = LinearLayoutManager(this)
    }

    fun getPhoneNumbers(sort: String, name: String): List<Phone> {
        val list = mutableListOf<Phone>()

        val phonUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        // 2.1 전화번호에서 가져올 컬럼 정의
        val projections = arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
        // 2.2 조건 정의
        var where: String? = null
        var whereValues: Array<String>? = null
        // searchName에 값이 있을 때만 검색을 사용한다
        if (name.isNotEmpty()) {
            where = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " like ?"
            whereValues = arrayOf("%$name%")
        }
        /*if(name.isNotEmpty()){
            where= ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+"= ?"
            whereValues= arrayOf(name)
        }*/
        val optionSort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " $sort"

        this.run {
            val cursor = contentResolver.query(phonUri, projections, where, whereValues, optionSort)

            while (cursor?.moveToNext() == true) {
                val id = cursor.getString(0)
                val name = cursor.getString(1)
                val number = cursor.getString(2)

                val phone = Phone(id, name, number)

                list.add(phone)
            }
        }
        // 결과목록 반환

        return list
    }



}