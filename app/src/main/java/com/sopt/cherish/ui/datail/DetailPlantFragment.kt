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
import com.bumptech.glide.Glide
import com.jackandphantom.circularprogressbar.CircleProgressbar
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentDetailPlantBinding
import com.sopt.cherish.remote.api.ResponsePlantCardDatas
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import com.sopt.cherish.ui.adapter.DetailMemoAdapter
import com.sopt.cherish.ui.datail.calendar.CalendarFragment
import com.sopt.cherish.ui.dialog.AlertPlantDialogFragment
import com.sopt.cherish.ui.dialog.WateringDialogFragment
import com.sopt.cherish.ui.domain.MemoListDataclass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailPlantFragment : Fragment() {

    private lateinit var circleProgressbar: CircleProgressbar
    private val viewModel: DetailPlantViewModel by activityViewModels()
    private val requestData = RetrofitBuilder
    lateinit var memoList: ArrayList<MemoListDataclass>

    //lateinit var memoList:ArrayList<MemoListDataclass>
    var cherishid = 0
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

        cherishid = arguments?.getInt("cherishidgo")!!
        Log.d("0cherishiddetailplant", cherishid.toString())


        binding.imageButton3detail.setOnClickListener {

            AlertPlantDialogFragment().show(parentFragmentManager, DetailPlantFragment.TAG)
            //3단계 식물 뷰 들어가는 곳
        }
        circleProgressbar = binding.test
        val animationDuration = 2000

        binding.buttonWater.setOnClickListener {
            // 이거 매개변수 바꿔야 함
            WateringDialogFragment(cherishid).show(parentFragmentManager, "DetailPlantFragment")
        }

        Log.d("gogo", cherishid.toString())

        requestData.responsePlantCardData.Detailcherishcard(cherishid)
            .enqueue(
                object : Callback<ResponsePlantCardDatas> {
                    override fun onFailure(call: Call<ResponsePlantCardDatas>, t: Throwable) {
                        Log.d("통신 실패", t.toString())
                    }

                    override fun onResponse(
                        call: Call<ResponsePlantCardDatas>,
                        response: Response<ResponsePlantCardDatas>
                    ) {
                        Log.d("식물카드 뷰", response.body().toString())
                        response.takeIf {
                            it.isSuccessful
                        }?.body()
                            ?.let { it ->
                                binding.textViewNick.text = it.data.nickname
                                Log.d("textViewNick", it.data.nickname.toString())
                                binding.textViewName.text = it.data.name.toString()
                                binding.textViewPlantname.text = it.data.plant_name.toString()
                                binding.textViewDday.text = "D-" + it.data.dDay.toString()
                                binding.textViewDuration.text = it.data.duration.toString()
                                if (it.data.birth.toString() == "Invalid Date") {
                                    binding.textViewBirth.text = "미입력"

                                }
                                binding.textViewBirth.text = it.data.birth.toString()
                                binding.textView1WithName.text = it.data.name.toString()
                                binding.textViewStatusMessage.text = it.data.status_message
                                binding.textViewStatus.text = it.data.status.toString()
                                Glide.with(this@DetailPlantFragment)
                                    .load(it.data.plant_thumbnail_image_url)
                                    .into(binding.imageViewDetailUrl)




                                circleProgressbar.setProgressWithAnimation(
                                    it.data.gage.toFloat() * 100,
                                    animationDuration
                                )

                                binding.chip.text = it.data.keyword1
                                binding.chip2.text = it.data.keyword2
                                binding.chip3.text = it.data.keyword3




                                if (it.data.reviews.isEmpty()) {


                                    var memoList = arrayListOf<MemoListDataclass>(

                                        MemoListDataclass(
                                            "_ _",
                                            "이 날의 기록이 없어요!"
                                        ),
                                        MemoListDataclass(
                                            "_ _",
                                            "이 날의 기록이 없어요!"
                                        )
                                    )

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

                                } else {

                                    var memoList = arrayListOf<MemoListDataclass>(

                                        MemoListDataclass(
                                            "_ _",
                                            "이 날의 기록이 없어요!"
                                        ),
                                        MemoListDataclass(
                                            "_ _",
                                            "이 날의 기록이 없어요!"
                                        )
                                    )

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


                                //memoList.add(MemoListDataclass(it.data.reviews[0].water_date, it.data.reviews[0].review))
                                //memoList.add(MemoListDataclass(it.data.reviews[1].water_date, it.data.reviews[1].review))


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
        /* when (item.itemId) {
             R.id.calendar -> {
                 val transaction = parentFragmentManager.beginTransaction()
                 transaction.replace(R.id.fragment_detail, CalendarFragment())
                 // if (transaction == null) {
                 transaction.addToBackStack(null)
                 // }
                 transaction.commit()

                 return true
             }
             R.id.setting -> {
                 val transaction = parentFragmentManager.beginTransaction()
                 transaction.replace(R.id.fragment_detail, EnrollModifyPlantFragment().apply {
                     arguments = Bundle().apply {

                         if (cherishid != null) {
                             putInt("plantidgo_delete", cherishid)
                         }

                     }
                 })
                 // if (transaction == null) {
                 transaction.addToBackStack(null)
                 // }
                 transaction.commit()

                 return true
             }
         }*/
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