package com.sopt.cherish.ui.main.manageplant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sopt.cherish.databinding.FragmentPlantBinding
import com.sopt.cherish.ui.adapter.MyPageBottomSheetAdapter
import com.sopt.cherish.ui.domain.MyPageCherryLevelDataclass

/**
 * Create on 01-08 by Yejin
 * bottom sheet에서 보여지는 recyclerview fragment
 */

class PlantFragment : Fragment() {


    private var _binding: FragmentPlantBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MyPageBottomSheetAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPlantBinding.inflate(inflater, container, false)

        adapter = MyPageBottomSheetAdapter()
        setAdapterData(adapter)
        initialRecyclerView(binding, adapter)

        return binding.root
    }

    private fun setAdapterData(adapter: MyPageBottomSheetAdapter) {
        adapter.data = mutableListOf(
            MyPageCherryLevelDataclass("안녕", "스투키 Lv1"),
            MyPageCherryLevelDataclass("안녕", "스투키 Lv1"),
            MyPageCherryLevelDataclass("안녕", "스투키 Lv1"),
            MyPageCherryLevelDataclass("안녕", "스투키 Lv1"),
            MyPageCherryLevelDataclass("안녕", "스투키 Lv1"),
            MyPageCherryLevelDataclass("안녕", "스투키 Lv1"),
            MyPageCherryLevelDataclass("안녕", "스투키 Lv1"),
            MyPageCherryLevelDataclass("안녕", "스투키 Lv1"),
            MyPageCherryLevelDataclass("안녕", "스투키 Lv1"),
            MyPageCherryLevelDataclass("안녕", "스투키 Lv1"),
            MyPageCherryLevelDataclass("안녕", "스투키 Lv1"),
            MyPageCherryLevelDataclass("안녕", "스투키 Lv1"),
            MyPageCherryLevelDataclass("안녕", "스투키 Lv1"),
            MyPageCherryLevelDataclass("안녕", "스투키 Lv1"),
            MyPageCherryLevelDataclass("안녕", "스투키 Lv1"),
            MyPageCherryLevelDataclass("안녕", "스투키 Lv1")
        )
        adapter.notifyDataSetChanged()
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