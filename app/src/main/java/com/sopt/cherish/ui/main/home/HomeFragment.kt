package com.sopt.cherish.ui.main.home

import android.animation.ArgbEvaluator
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentHomeBinding
import com.sopt.cherish.remote.model.CherryDataclass
import com.sopt.cherish.ui.adapter.MainBottomSheetAdapter
import com.sopt.cherish.ui.enrollment.PhoneBookActivity


/**
 * 메인 홈뷰
 * 초기상태와 중간에 있는 경우 2개 다 고려해야 합니다.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var standardBottomSheetBehavior: BottomSheetBehavior<*>
    private lateinit var standardBottomSheet: ConstraintLayout
    private lateinit var homeFragmentBg: ConstraintLayout
    private lateinit var adapter: MainBottomSheetAdapter
    private lateinit var useraddbtn: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        adapter = MainBottomSheetAdapter()
        setAdapterData(adapter)
        setAdapter(binding, adapter)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeFragmentBg = binding.homeFragmentBg

        standardBottomSheet = view.findViewById(R.id.main_bottom_sheet)
        useraddbtn = view.findViewById(R.id.user_add_btn)
        useraddbtn.setOnClickListener {

            val intent = Intent(context, PhoneBookActivity::class.java)
            startActivity(intent)
        }

        standardBottomSheetBehavior = BottomSheetBehavior.from(standardBottomSheet)
        standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        standardBottomSheetBehavior.peekHeight = 160
        standardBottomSheetBehavior.expandedOffset = 158
        standardBottomSheetBehavior.isHideable = false

        initRecyclerView(homeFragmentBg, adapter)
        updateProgressBar(binding)

        standardBottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {

                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    homeFragmentBg.setBackgroundColor(
                        ContextCompat.getColor(
                            context!!,
                            R.color.cherish_purple
                        )
                    )
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                //transitionBottomSheetParentView(slideOffset)
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initRecyclerView(
        standardBottomSheet: ConstraintLayout?,
        mainAdapter: MainBottomSheetAdapter
    ) {
        val recyclerView = standardBottomSheet?.findViewById<RecyclerView>(R.id.main_list)
        recyclerView?.apply {
            adapter = mainAdapter
            layoutManager = GridLayoutManager(context, 5)
        }
    }

    private fun transitionBottomSheetParentView(slideOffset: Float) {

        val argbEvaluator = ArgbEvaluator().evaluate(slideOffset, 0x8189b3, 0x242222)
        homeFragmentBg.setBackgroundColor(argbEvaluator as Int)

    }

    private fun setAdapterData(adapter: MainBottomSheetAdapter) {
        adapter.data = mutableListOf(
            CherryDataclass("안녕"),
            CherryDataclass("반가워"),
            CherryDataclass("잘가"),
            CherryDataclass("반가워"),
            CherryDataclass("반가워"),
            CherryDataclass("반가워"),
            CherryDataclass("반가워"),
            CherryDataclass("안녕"),
            CherryDataclass("반가워"),
            CherryDataclass("잘가"),
            CherryDataclass("반가워"),
            CherryDataclass("반가워"),
            CherryDataclass("반가워"),
            CherryDataclass("반가워"),
            CherryDataclass("안녕"),
            CherryDataclass("반가워"),
            CherryDataclass("잘가"),
            CherryDataclass("반가워"),
            CherryDataclass("반가워"),
            CherryDataclass("반가워"),
            CherryDataclass("반가워"),
            CherryDataclass("안녕"),
            CherryDataclass("반가워"),
            CherryDataclass("잘가"),
            CherryDataclass("반가워"),
            CherryDataclass("반가워"),
            CherryDataclass("반가워"),
            CherryDataclass("반가워"),
            CherryDataclass("안녕"),
            CherryDataclass("안녕"),
            CherryDataclass("반가워"),
            CherryDataclass("잘가"),
            CherryDataclass("반가워"),
            CherryDataclass("반가워"),
            CherryDataclass("반가워"),
            CherryDataclass("반가워"),
            CherryDataclass("안녕"),
            CherryDataclass("반가워"),
            CherryDataclass("반가워"),
            CherryDataclass("반가워"),
        )
        adapter.notifyDataSetChanged()
    }

    private fun setAdapter(binding: FragmentHomeBinding, mainAdapter: MainBottomSheetAdapter) {
        binding.mainBottomSheet.mainList.apply {
            adapter = mainAdapter
            layoutManager = GridLayoutManager(context, 5)
        }
    }

    private fun updateProgressBar(binding: FragmentHomeBinding) {
        val rating = binding.affectionProgressbar.progress

        if (rating <= 30) {
            binding.affectionProgressbar.progressDrawable = ResourcesCompat.getDrawable(
                resources,
                R.drawable.progress_drawable_verticle_red,
                null
            )
        }
    }
}