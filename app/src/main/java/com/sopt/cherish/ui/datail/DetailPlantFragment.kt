package com.sopt.cherish.ui.datail

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jackandphantom.circularprogressbar.CircleProgressbar
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentDetailPlantBinding
import com.sopt.cherish.remote.api.ResponsePlantCardDatas
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import com.sopt.cherish.ui.adapter.DetailMemoAdapter
import com.sopt.cherish.ui.datail.calendar.CalendarFragment
import com.sopt.cherish.ui.dialog.PlantDetailPopUpFirst
import com.sopt.cherish.ui.dialog.WateringDialogFragment
import com.sopt.cherish.ui.domain.MemoListDataclass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailPlantFragment : Fragment() {

    private lateinit var circleProgressbar: CircleProgressbar
    private val viewModel: DetailPlantViewModel by activityViewModels()
    private val requestData = RetrofitBuilder
    //lateinit var memoList:ArrayList<MemoListDataclass>

    // private lateinit var memoList: ArrayList<MemoListDataclass>

    companion object {
        private val TAG = "DetailPlantFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        val binding: FragmentDetailPlantBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_detail_plant, container, false)

        binding.imageButton3detail.setOnClickListener {

            PlantDetailPopUpFirst().show(parentFragmentManager, DetailPlantFragment.TAG)
            //3단계 식물 뷰 들어가는 곳
        }
        circleProgressbar = binding.test
        val animationDuration = 100

        binding.buttonWater.setOnClickListener {
            // 이거 매개변수 바꿔야 함
            WateringDialogFragment().show(parentFragmentManager, "DetailPlantFragment")
        }
        val cherishid = arguments?.getInt("plantidgo")
        Log.d("gogo", cherishid.toString())

        requestData.responsePlantCardData.Detailcherishcard(1)
            .enqueue(
                object : Callback<ResponsePlantCardDatas> {
                    override fun onFailure(call: Call<ResponsePlantCardDatas>, t: Throwable) {
                        Log.d("통신 실패", t.toString())
                    }

                    override fun onResponse(
                        call: Call<ResponsePlantCardDatas>,
                        response: Response<ResponsePlantCardDatas>
                    ) {
                        Log.d("success", response.body().toString())
                        response.takeIf {
                            it.isSuccessful
                        }?.body()
                            ?.let { it ->
                                binding.textViewNick.text = it.data.nickname
                                binding.textViewName.text = it.data.name
                                binding.textViewPlantname.text = it.data.plant_name
                                binding.textViewDday.text = "D-" + it.data.dDay.toString()
                                binding.textViewDuration.text = it.data.duration.toString()
                                binding.textViewBirth.text = it.data.birth.toString()
                                binding.textView1WithName.text = it.data.name.toString()


                                circleProgressbar.setProgressWithAnimation(
                                    it.data.gage.toFloat(),
                                    animationDuration
                                )

                                binding.chip.text = it.data.keyword1
                                binding.chip2.text = it.data.keyword2
                                binding.chip3.text = it.data.keyword3


                                var memoList = arrayListOf<MemoListDataclass>(

                                    MemoListDataclass(
                                        it.data.reviews[0].water_date,
                                        it.data.reviews[0].review
                                    ),
                                    MemoListDataclass(
                                        it.data.reviews[1].water_date,
                                        it.data.reviews[1].review
                                    )
                                )

                                //memoList.add(MemoListDataclass(it.data.reviews[0].water_date, it.data.reviews[0].review))
                                //memoList.add(MemoListDataclass(it.data.reviews[1].water_date, it.data.reviews[1].review))

                                Log.d("data success!", it.data.reviews[0].review.toString())


                                val mAdapter = DetailMemoAdapter(memoList)

                                binding.recyclerDetail.adapter = mAdapter

                                binding.recyclerDetail.addItemDecoration(
                                    VerticalSpaceItemDecoration(
                                        20
                                    )
                                )
                                mAdapter.setItemClickListener(object :
                                    DetailMemoAdapter.ItemClickListener {
                                    override fun onClick(view: View, position: Int) {
                                        val item = mAdapter.memolist[position]
                                        Log.d("SSS", "${position}번 리스트 선택")
                                        val transaction =
                                            parentFragmentManager.beginTransaction()
                                        transaction.replace(
                                            R.id.fragment_detail,
                                            CalendarFragment()
                                        )
                                        transaction.addToBackStack(null)
                                        transaction.commit()
                                    }
                                })

                                binding.recyclerDetail.layoutManager =
                                    LinearLayoutManager(context)
                                binding.recyclerDetail.setHasFixedSize(true)

                            }
                    }
                })

        // 유저 원형 프로그레스바 보여주는 부분


        // memolist 어댑터 연결 부분

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val activity = activity
        if (activity != null) {
            (activity as DetailPlantActivity).setActionBarTitle("식물 카드")

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

        /*when (item.itemId) {
            R.id.calendar -> {
                val transaction = childFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_detail, CalendarFragment())
                transaction.commit()
                return true
            }
            R.id.setting->{
                Log.d("click","ASDf")
                val transaction = childFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_detail, EnrollModifyPlantFragment())
                // if (transaction == null) {
                transaction.addToBackStack(null)
                // }
                transaction.commit()

                return true
            }
        }*/
        return super.onOptionsItemSelected(item)
    }


    inner class VerticalSpaceItemDecoration(private val verticalSpaceHeight: Int) :
        RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.bottom = verticalSpaceHeight
        }
    }
}