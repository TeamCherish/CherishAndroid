package com.sopt.cherish.ui.enrollment

import android.annotation.SuppressLint
import android.graphics.Color
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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentPhoneBookBinding
import com.sopt.cherish.di.Injection
import com.sopt.cherish.remote.api.RequestCheckPhoneData
import com.sopt.cherish.remote.api.ResponseCheckPhoneData
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import com.sopt.cherish.ui.adapter.Phone
import com.sopt.cherish.ui.adapter.PhoneBookAdapter
import com.sopt.cherish.ui.dialog.CheckPhoneDialogFragment
import com.sopt.cherish.ui.main.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PhoneBookFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels { Injection.provideMainViewModelFactory() }
    lateinit var madapter: PhoneBookAdapter
    var phonelist = mutableListOf<Phone>()
    var phonelistphone = mutableListOf<Phone>()
    var searchText = ""
    var searchphone = ""
    var sortText = "asc"
    var countphonebook = 0
    private lateinit var enrollToolbar: Toolbar
    private val requestData = RetrofitBuilder

    private lateinit var binding: FragmentPhoneBookBinding


    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_phone_book, container, false)

        binding = FragmentPhoneBookBinding.bind(view)
        countphonebook = arguments?.getInt("useridenroll")!!
        Log.d("useridna", countphonebook.toString())
        startProcess()

        Log.d("checkedRadioButton", madapter.checkedRadioButton?.isClickable.toString())

        madapter.setItemClickListener(object : PhoneBookAdapter.ItemClickListener {
            override fun onchange(radio: Boolean) {
                Log.d("radio", radio.toString())
                if (radio == true) {
                    binding.buttonnext.setBackgroundColor(Color.parseColor("#1AD287"))
                    binding.buttonnext.setTextColor(Color.parseColor("#ffffff"))

                }
            }

        })





        binding.buttonnext.setOnClickListener {
            if (madapter.checkedRadioButton != null) {

                Log.d("vvvv", madapter.phonename)
                val phonenumber = madapter.phonenumber
                /*madapter.phonenumber.substring(0, 3) + "-" + madapter.phonenumber.substring(
                    3,
                    7
                ) + "-" +
                        madapter.phonenumber.substring(7)*/
                Log.d("phonenumbervvvv", phonenumber)

                val body =
                    RequestCheckPhoneData(phone = phonenumber, UserId = countphonebook)
                requestData.checkphoneAPI.checkphone(body)
                    .enqueue(
                        object : Callback<ResponseCheckPhoneData> {
                            override fun onFailure(
                                call: Call<ResponseCheckPhoneData>,
                                t: Throwable
                            ) {
                                Log.d("통신 실패", t.toString())
                            }

                            override fun onResponse(
                                call: Call<ResponseCheckPhoneData>,
                                response: Response<ResponseCheckPhoneData>
                            ) {
                                Log.d("success", response.body().toString())
                                if (response.body() == null) {
                                    val deletedialog =
                                        CheckPhoneDialogFragment(
                                            R.layout.fragment_check_phone_dialog,

                                            ).show(
                                            parentFragmentManager, "asdf"
                                        )

                                }
                                response.takeIf {
                                    it.isSuccessful
                                }?.body()
                                    ?.let { it ->
                                        Log.d("중복", "중복")

                                        setFragment(EnrollPlantFragment())


                                    }
                            }
                        }
                    )
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
                putInt("useridend", countphonebook)
            }
            Log.d("phonebookfragment", countphonebook.toString())


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
        binding.recycler.layoutManager = LinearLayoutManager(context)
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

        val optionSort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " $sort"

        context?.run {


            val cursor = contentResolver.query(phonUri, projections, where, whereValues, optionSort)
            while (cursor?.moveToNext() == true) {
                val id = cursor.getString(0)
                val name = cursor.getString(1)
                val number = cursor.getString(2)

                val phone = Phone(id, name, number)

                list.add(phone)
                //list.distinct()

            }

            val cursor2 =
                contentResolver.query(phonUri, projections, where2, whereValues, optionSort)
            while (cursor2?.moveToNext() == true) {
                val id = cursor2.getString(0)
                val name = cursor2.getString(1)
                val number = cursor2.getString(2)

                val phone = Phone(id, name, number)

                list.add(phone)
                // list.distinct()
            }

        }

        // 결과목록 반환
        Log.d("listsize", list.size.toString())
        return list.distinct()
    }


}

