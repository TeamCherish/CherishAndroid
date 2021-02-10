package com.sopt.cherish.ui.main.manageplant

import android.content.Intent
import android.os.Bundle
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

class PlantFragment : Fragment() {

    private var _binding: FragmentPlantBinding? = null
    private val binding get() = _binding!!
    private lateinit var cherishAdapter: MyPageBottomSheetAdapter
    private val requestData = RetrofitBuilder
    private val viewModel: MainViewModel by activityViewModels()

    lateinit var list: MutableList<MyPageCherishData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPlantBinding.inflate(inflater, container, false)

        setAdapterData()
  

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        //initialRecyclerView(binding, cherishAdapter)
        setAdapterData()
        //cherishAdapter.notifyDataSetChanged()
    }

    private fun setAdapterData() {
        Log.d("viewmodeluserid",viewModel.userId.value.toString())
        requestData.myPageAPI.fetchUserPage(viewModel.userId.value!!)
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
                                        requireContext(),
                                        it.myPageUserData.result as MutableList<MyPageCherishData>
                                    )

                                initialRecyclerView(binding, cherishAdapter)
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

                                            //startActivityForResult(intent, 100)
                                            startActivity(intent)
                                        }
                                    }
                                )
                            }


                    }
                })
    }

    private fun initialRecyclerView(
        binding: FragmentPlantBinding,
        mainAdapter: MyPageBottomSheetAdapter
    ) {

        binding.mypageCherryList.apply {
            adapter = mainAdapter

            layoutManager = LinearLayoutManager(context)
        }
    }

}