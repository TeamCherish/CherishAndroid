package com.sopt.cherish.ui.main.manageplant

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentPlantBinding
import com.sopt.cherish.remote.api.MyPageCherishData
import com.sopt.cherish.ui.adapter.MyPageBottomSheetAdapter
import com.sopt.cherish.ui.detail.DetailPlantActivity
import com.sopt.cherish.ui.main.MainViewModel
import com.sopt.cherish.ui.main.home.HomeFragment

/**
 * Create on 01-08 by Yejin
 * bottom sheet에서 보여지는 recyclerview fragment
 */

class PlantFragment(private var data: List<MyPageCherishData>?) : Fragment() {

    private var _binding: FragmentPlantBinding? = null
    private val binding get() = _binding!!
    private lateinit var cherishAdapter: MyPageBottomSheetAdapter
    private val viewModel: MainViewModel by activityViewModels()

    lateinit var list: List<MyPageCherishData>

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

        setAdapterData()
    }


    private fun setAdapterData() {
        cherishAdapter = MyPageBottomSheetAdapter(data)
        binding.mypageCherryList.adapter = cherishAdapter
        binding.mypageCherryList.layoutManager = LinearLayoutManager(context)

        cherishAdapter.notifyDataSetChanged()


        cherishAdapter.setItemClickListener(
            object : MyPageBottomSheetAdapter.ItemClickListener {
                override fun onClick(view: View, position: Int) {
                    val intent =
                        Intent(context, DetailPlantActivity::class.java)

                    intent.putExtra(
                        "plantId",
                        data!![position].plantId
                    )
                    intent.putExtra(
                        "Id",
                        data!![position].id
                    )
                    intent.putExtra(
                        "mypageuserId",
                        viewModel.cherishUserId.value
                    )
                    intent.putExtra(
                        "mypageuserNickname",
                        viewModel.userNickName.value
                    )
                    startActivity(intent)
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_container, HomeFragment()).commitNow()
                }
            }
        )
    }
}