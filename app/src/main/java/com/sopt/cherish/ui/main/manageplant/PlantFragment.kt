package com.sopt.cherish.ui.main.manageplant

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sopt.cherish.databinding.FragmentPlantBinding
import com.sopt.cherish.remote.api.MyPageCherishData
import com.sopt.cherish.remote.api.MyPageUserRes
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import com.sopt.cherish.ui.adapter.MyPageBottomSheetAdapter
import com.sopt.cherish.ui.detail.DetailPlantActivity
import com.sopt.cherish.ui.main.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Create on 01-08 by Yejin
 * bottom sheet에서 보여지는 recyclerview fragment
 */

class PlantFragment(private var data:MutableList<MyPageCherishData>) : Fragment() {

    private var _binding: FragmentPlantBinding? = null
    private val binding get() = _binding!!
    private lateinit var cherishAdapter: MyPageBottomSheetAdapter
    private val requestData = RetrofitBuilder
    private val viewModel: MainViewModel by activityViewModels()

    lateinit var list: MutableList<MyPageCherishData>
    var searchText = ""
    var sortText = "asc"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPlantBinding.inflate(inflater, container, false)

        setAdapterData()
        startProcess()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        //initialRecyclerView(binding, cherishAdapter)
        setAdapterData()
        //cherishAdapter.notifyDataSetChanged()
    }
/*
    private fun setAdapterData() {
        Log.d("viewmodeluserid", viewModel.cherishuserId.value.toString())
        requestData.myPageAPI.fetchUserPage(viewModel.cherishuserId.value!!)
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
                                Log.d("list", it.myPageUserData.result.toString())
                                cherishAdapter =
                                    MyPageBottomSheetAdapter(
                                        it.myPageUserData.result as MutableList<MyPageCherishData>
                                    )
                                binding.mypageCherryList.adapter=cherishAdapter
                                binding.mypageCherryList.layoutManager = LinearLayoutManager(context)
                                //initialRecyclerView(binding, cherishAdapter)
                                cherishAdapter.notifyDataSetChanged()
                                cherishAdapter.setItemClickListener(
                                    object : MyPageBottomSheetAdapter.ItemClickListener {
                                        override fun onClick(view: View, position: Int) {
                                            Log.d("onclick", "success")
                                            val intent =
                                                Intent(context, DetailPlantActivity::class.java)
                                            intent.putExtra(
                                                "plantId",
                                                it.myPageUserData.result[position].plantId
                                            )
                                            intent.putExtra(
                                                "Id",
                                                it.myPageUserData.result[position].id
                                            )
                                            Log.d(
                                                "Id",
                                                it.myPageUserData.result[position].id.toString()
                                            )
                                            //startActivityForResult(intent, 100)
                                            startActivity(intent)
                                        }
                                    }
                                )
                            }
                    }
                })
    } */

    private fun setAdapterData(){
        cherishAdapter= MyPageBottomSheetAdapter(data)

        binding.mypageCherryList.adapter=cherishAdapter
        binding.mypageCherryList.layoutManager = LinearLayoutManager(context)

        //initialRecyclerView(binding, cherishAdapter)
        cherishAdapter.notifyDataSetChanged()


        cherishAdapter.setItemClickListener(
            object : MyPageBottomSheetAdapter.ItemClickListener {
                override fun onClick(view: View, position: Int) {
                    Log.d("onclick", "success")

                    val intent =
                        Intent(context, DetailPlantActivity::class.java)

                    intent.putExtra(
                        "plantId",
                        data[position].plantId
                    )
                    intent.putExtra(
                        "Id",
                        data[position].id
                    )
                    Log.d(
                        "Id",
                        data[position].id.toString()
                    )
                    //startActivityForResult(intent, 100)
                    startActivity(intent)
                }
            }
        )
    }
    fun startProcess() {
        setAdapterData()
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
                    changeList(searchText)
                }
            })
    }
    fun changeList(searchText:String) {
        requestData.myPageAPI.fetchUserPage(viewModel.cherishuserId.value!!)
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
                                Log.d("식물검색하는중", it.myPageUserData.result.toString())
                                val mulist: MutableList<MyPageCherishData> =
                                    mutableListOf<MyPageCherishData>()
                                for(i in 0..it.myPageUserData.result.size-1) {

                                    if (it.myPageUserData.result[i].nickName.contains(searchText)) {

                                        //var list2: MutableList<MyPageCherishData>
                                        mulist.add(it.myPageUserData.result[i])

                                        Log.d("검색나옴", "검색나옴")


                                    }
                                }
                                cherishAdapter =
                                    MyPageBottomSheetAdapter(
                                        mulist as MutableList<MyPageCherishData>
                                    )
                                //initialRecyclerView(binding, cherishAdapter)
                                binding.mypageCherryList.adapter=cherishAdapter
                                binding.mypageCherryList.layoutManager = LinearLayoutManager(context)
                                cherishAdapter.notifyDataSetChanged()


                            }


                    }
                })




    }

/*
    private fun initialRecyclerView(
        binding: FragmentPlantBinding,
        mainAdapter: MyPageBottomSheetAdapter
    ) {
        binding.mypageCherryList.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(context)
        }
    } */
}