package com.sopt.cherish.ui.enrollment

import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentPhoneBookBinding
import com.sopt.cherish.ui.adapter.Phone
import com.sopt.cherish.ui.adapter.PhoneBookAdapter


class PhoneBookFragment : Fragment() {

    // val permissions = arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE)
    lateinit var madapter: PhoneBookAdapter
    var phonelist = mutableListOf<Phone>()
    var phonelistphone = mutableListOf<Phone>()
    var searchText = ""
    var searchphone = ""
    var sortText = "asc"
    private lateinit var enrollToolbar: Toolbar

    private lateinit var binding: FragmentPhoneBookBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_phone_book, container, false)

        binding = FragmentPhoneBookBinding.bind(view)



        startProcess()

        binding.buttonnext.setOnClickListener {
            if (madapter.checkedRadioButton != null) {
                //  val intent = Intent(view.context, EnrollPlantActicity::class.java)
                //Toast.makeText(this,madapter.phonename,Toast.LENGTH_LONG).show()

                // intent.putExtra("phonename", madapter.phonename)
                // intent.putExtra("phonenumber", madapter.phonenumber)
                Log.d("vvvv", madapter.phonename.toString())
                // startActivity(intent)
                /*val transaction=parentFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_enroll,EnrollPlantFragment().apply {
                    arguments=Bundle().apply {
                        putString("phonename", madapter.phonename)
                        putString("phonenumber", madapter.phonenumber)

                    }
                })
                transaction.commit()*/



                setFragment(EnrollPlantFragment())


            }
        }


        return view
    }

    override fun onResume() {
        super.onResume()
        val activity = activity
        if (activity != null) {
            (activity as EnrollmentPhoneActivity).setActionBarTitle("식물 등록 친구 선택")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                activity?.finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun setFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_enroll, fragment.apply {
            arguments = Bundle().apply {
                putString("phonename", madapter.phonename)
                putString("phonenumber", madapter.phonenumber)

            }
        })
        transaction.addToBackStack(null)

        transaction.commit()
    }


    fun startProcess() {
        setList()
        setSearchListener()
        // setContentView(R.layout.activity_main)

    }


    fun setSearchListener() {
        binding.editSearch.addTextChangedListener(


            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // if((.contains(".*[0-9].*"))){

                    //    searchText = s.toString()
                    // changeList2()

                    //     }
                    //  else {
                    searchText = s.toString()
                    //searchphone=s.toString()
                    changeList()
                    // changeList2()

                    //     }

                }
            })
    }

    fun changeList() {

        val newList = getPhoneNumbers(sortText, searchText)
        //  val newListphone=getPhoneNumbers(sortText,searchphone)
        this.phonelist.clear()
        this.phonelist.addAll(newList)
        this.madapter.notifyDataSetChanged()
        /*  this.phonelistphone.clear()
          this.phonelistphone.addAll(newListphone)
          this.madapter.notifyDataSetChanged()*/
    }

    fun changeList2() {

        val newList = getPhoneNumbers2(sortText, searchText)
        //  val newListphone=getPhoneNumbers(sortText,searchphone)
        this.phonelist.clear()
        this.phonelist.addAll(newList)
        this.madapter.notifyDataSetChanged()
        /*  this.phonelistphone.clear()
          this.phonelistphone.addAll(newListphone)
          this.madapter.notifyDataSetChanged()*/
    }

    fun setList() {
        phonelist.distinct()
        phonelist.addAll(getPhoneNumbers(sortText, searchText))
        phonelist.distinct()
        madapter = PhoneBookAdapter(phonelist)
        binding.recycler.adapter = madapter
        binding.recycler.layoutManager = LinearLayoutManager(context)
    }

    fun getcount(): String {
        return phonelist.size.toString()
    }

    fun getPhoneNumbers(sort: String, search: String): List<Phone> {
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
        var where2: String? = null


        var whereValues: Array<String>? = null
        // searchName에 값이 있을 때만 검색을 사용한다
        if (search.isNotEmpty()) {


            where = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " like ? "

            where2 = ContactsContract.CommonDataKinds.Phone.NUMBER + " like ?"

            whereValues = arrayOf("%$search%")


        }
        /*if(name.isNotEmpty()){
            where= ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+"= ?"
            whereValues= arrayOf(name)
        }*/
        val optionSort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " $sort"

        context?.run {


            val cursor = contentResolver.query(phonUri, projections, where, whereValues, optionSort)
            while (cursor?.moveToNext() == true) {
                val id = cursor?.getString(0)
                val name = cursor?.getString(1)
                val number = cursor?.getString(2)

                val phone = Phone(id, name, number)

                list.add(phone)
                list.distinct()

            }


            val cursor2 =
                contentResolver.query(phonUri, projections, where2, whereValues, optionSort)
            while (cursor2?.moveToNext() == true) {
                val id = cursor2?.getString(0)
                val name = cursor2?.getString(1)
                val number = cursor2?.getString(2)

                val phone = Phone(id, name, number)

                list.add(phone)
                list.distinct()
            }


        }


        // 결과목록 반환

        return list.distinct()
    }

    fun getPhoneNumbers2(sort: String, name: String): List<Phone> {
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
        var where2: String? = null
        var whereValues: Array<String>? = null
        // searchName에 값이 있을 때만 검색을 사용한다
        if (name.isNotEmpty()) {
            where = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " like ?"
            where2 = ContactsContract.CommonDataKinds.Phone.NUMBER + " like ?"
            whereValues = arrayOf("%$name%")

        }
        /*if(name.isNotEmpty()){
            where= ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+"= ?"
            whereValues= arrayOf(name)
        }*/
        val optionSort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " $sort"
        val optionSort2 = ContactsContract.CommonDataKinds.Phone.NUMBER + " $sort"

        context?.run {
            val cursor =
                contentResolver.query(phonUri, projections, where2, whereValues, optionSort2)

            while (cursor?.moveToNext() == true) {
                val id = cursor?.getString(0)
                val name = cursor?.getString(1)
                val number = cursor?.getString(2)

                val phone = Phone(id, name, number)

                list.add(phone)
            }


        }
        // 결과목록 반환

        return list
    }
}

