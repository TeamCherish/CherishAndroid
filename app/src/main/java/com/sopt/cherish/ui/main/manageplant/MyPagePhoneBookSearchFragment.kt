package com.sopt.cherish.ui.main.manageplant

import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentMyPagePhoneBookSearchBinding
import com.sopt.cherish.ui.adapter.MypagePhoneBookAdapter
import com.sopt.cherish.ui.adapter.Phonemypage
import com.sopt.cherish.ui.main.MainActivity


class MyPagePhoneBookSearchFragment() : Fragment() {

    // val permissions = arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE)
    lateinit var madapter: MypagePhoneBookAdapter
    var phonelist = mutableListOf<Phonemypage>()
    var searchText = ""
    var sortText = "asc"
    var phonecount = 0
    private lateinit var enrollToolbar: Toolbar
    lateinit var countphone:String
    private lateinit var binding: FragmentMyPagePhoneBookSearchBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_page_phone_book_search, container, false)



        binding = FragmentMyPagePhoneBookSearchBinding.bind(view)

        startProcess()

        binding.cancelBtn.setOnClickListener{
            (activity as MainActivity).setIsSearched(false)
            (activity as MainActivity).replaceFragment(1,null,false)
        }

        return view
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
                    searchText =s.toString()
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

        madapter = MypagePhoneBookAdapter(phonelist)
        binding.recyclerMypage.adapter = madapter
        binding.recyclerMypage.layoutManager = LinearLayoutManager(context)
    }


    fun getPhoneNumbers(sort: String, name: String): List<Phonemypage> {
        val list = mutableListOf<Phonemypage>()

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

        val optionSort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " $sort"

        context?.run {


            val cursor = contentResolver.query(phonUri, projections, where, whereValues, optionSort)
            while (cursor?.moveToNext() == true) {
                val id = cursor.getString(0)
                val name = cursor.getString(1)
                val number = cursor.getString(2)

                val Phonemypage = Phonemypage(id, name, number)

                list.add(Phonemypage)
                //list.distinct()

            }

            val cursor2 =
                contentResolver.query(phonUri, projections, where2, whereValues, optionSort)
            while (cursor2?.moveToNext() == true) {
                val id = cursor2.getString(0)
                val name = cursor2.getString(1)
                val number = cursor2.getString(2)

                val Phonemypage = Phonemypage(id, name, number)

                list.add(Phonemypage)
                // list.distinct()
            }

        }

        // 결과목록 반환
        Log.d("listsize", list.size.toString())
        return list.distinct()
    }


}