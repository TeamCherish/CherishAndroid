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
import androidx.recyclerview.widget.LinearLayoutManager
import com.sopt.cherish.databinding.FragmentPlantSearchBinding
import com.sopt.cherish.remote.api.MyPageCherishData
import com.sopt.cherish.ui.adapter.MyPageBottomSheetAdapter
import com.sopt.cherish.ui.detail.DetailPlantActivity
import com.sopt.cherish.ui.main.MainActivity

/**
 * Create on 01-08 by Yejin
 * bottom sheet에서 보여지는 recyclerview fragment
 */

class PlantSearchFragment(private var data:MutableList<MyPageCherishData>?) : Fragment() {

    private var _binding: FragmentPlantSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var cherishAdapter: MyPageBottomSheetAdapter


    lateinit var list: MutableList<MyPageCherishData>
    var searchText = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPlantSearchBinding.inflate(inflater, container, false)

        startProcess()

        binding.cancelBtn.setOnClickListener{
            (activity as MainActivity).setIsSearched(false)
            (activity as MainActivity).replaceFragment(0,data,false)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        setAdapterData()
    }


    private fun setAdapterData(){
        cherishAdapter= MyPageBottomSheetAdapter(data!!)

        binding.mypageCherryList.adapter=cherishAdapter
        binding.mypageCherryList.layoutManager = LinearLayoutManager(context)

        cherishAdapter.notifyDataSetChanged()


        cherishAdapter.setItemClickListener(
            object : MyPageBottomSheetAdapter.ItemClickListener {
                override fun onClick(view: View, position: Int) {
                    Log.d("onclick", "success")

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
                    Log.d(
                        "Id",
                        data!![position].id.toString()
                    )

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

        val mulist: MutableList<MyPageCherishData> =
            mutableListOf<MyPageCherishData>()
        for(i in 0 until data!!.size-1) {

            if (data!![i].nickName.contains(searchText)) {

                mulist.add(data!![i])

                cherishAdapter =
                    MyPageBottomSheetAdapter(
                        mulist
                    )

                binding.mypageCherryList.adapter=cherishAdapter
                binding.mypageCherryList.layoutManager = LinearLayoutManager(context)
                cherishAdapter.notifyDataSetChanged()
            }

        }
    // STOPSHIP: 2021-02-18
        cherishAdapter.setItemClickListener(
            object : MyPageBottomSheetAdapter.ItemClickListener {
                override fun onClick(view: View, position: Int) {
                    Log.d("onclick", "success")

                    val intent =
                        Intent(context, DetailPlantActivity::class.java)

                    intent.putExtra(
                        "plantId",
                        mulist[position].plantId
                    )
                    intent.putExtra(
                        "Id",
                        mulist[position].id
                    )
                    Log.d(
                        "Id",
                        mulist[position].id.toString()
                    )

                    startActivity(intent)
                }
            }
        )
    }

}