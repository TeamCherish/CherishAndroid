package com.sopt.cherish.ui.detail

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
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
import com.sopt.cherish.ui.detail.calendar.CalendarFragment
import com.sopt.cherish.ui.dialog.AlertPlantDialogFragment
import com.sopt.cherish.ui.dialog.DetailWateringDialogFragment
import com.sopt.cherish.ui.domain.MemoListDataclass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Math.abs


class DetailPlantFragment : Fragment() {

    private lateinit var circleProgressbar: CircleProgressbar
    private val viewModel: DetailPlantViewModel by activityViewModels()
    private val requestData = RetrofitBuilder
    lateinit var memoList: ArrayList<MemoListDataclass>
    lateinit var binding: FragmentDetailPlantBinding
    val animationDuration = 2000
    var plant_id = 0

    //lateinit var memoList:ArrayList<MemoListDataclass>
    var plantId = 0
    var cherishid = 0
    lateinit var cherishUserPhoneNumber: String
    lateinit var cherishNickname: String
    lateinit var userNickname: String
    var userId = 0

    companion object {
        private val TAG = "DetailPlantFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_detail_plant, container, false)

        plantId = arguments?.getInt("plantId_detail")!!

        cherishid = arguments?.getInt("cherishidmain_detail")!!
        cherishUserPhoneNumber = arguments?.getString("cherishUserPhoneNumber_detail")!!
        cherishNickname = arguments?.getString("cherishNickname_detail")!!
        userNickname = arguments?.getString("userNickname_detail")!!
        userId = arguments?.getInt("userId_detail")!!
        //reset()

        circleProgressbar = binding.test

        //reset()
        binding.buttonWater.setOnClickListener {
            // 이거 매개변수 바꿔야 함
            DetailWateringDialogFragment(cherishid).show(
                parentFragmentManager,
                "DetailPlantFragment"
            )
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
                                //식물 아이디 받는 곳 이거를 이제 정보 아이콘 누를때 넘겨줘야함
                                plantId = it.data.plantId
                                if (it.data.dDay > 0) {
                                    binding.textViewDday.text = "D-" + it.data.dDay.toString()

                                } else if (it.data.dDay == 0) {
                                    binding.textViewDday.text = "D-day"
                                } else {
                                    binding.textViewDday.text = "D-" + abs(it.data.dDay).toString()

                                }
                                binding.textViewDuration.text = it.data.duration.toString()
                                if ((it.data.birth.toString()) == "Invalid Date") {
                                    binding.textViewBirth.text = "_ _"

                                } else {
                                    binding.textViewBirth.text = it.data.birth.toString()

                                }
                                binding.textView1WithName.text = it.data.name.toString()
                                binding.textViewStatusMessage.text = it.data.status_message
                                binding.textViewStatus.text = it.data.status.toString()
                                Glide.with(this@DetailPlantFragment)
                                    .load(it.data.plant_thumbnail_image_url)
                                    .into(binding.imageViewDetailUrl)

                                plant_id = it.data.plantId
                                Log.d("fdfdfd", it.data.plantId.toString())


                                circleProgressbar.setProgressWithAnimation(
                                    it.data.gage.toFloat() * 100,
                                    animationDuration
                                )
                                binding.chip.isVisible= false
                                binding.chip2.isVisible = false
                                binding.chip3.isVisible = false
                               /* binding.chip.text = it.data.keyword1
                                binding.chip2.text = it.data.keyword2
                                binding.chip3.text = it.data.keyword3*/
                                if(it.data.keyword1==""&& it.data.keyword2=="" && it.data.keyword3==""){
                                    binding.chip.text = "키워드를 입력하지 않았어요!"
                                    binding.chip2.isVisible=false
                                    binding.chip3.isVisible=false

                                }
                                if (it.data.keyword1.toString() != "null" && it.data.keyword1 != "") {
                                    binding.chip.text = it.data.keyword1
                                    binding.chip.isVisible = true

                                }
                                if (it.data.keyword2.toString() != "null" && it.data.keyword2 != "") {
                                    binding.chip2.text = it.data.keyword2
                                    binding.chip2.isVisible = true

                                }
                                if (it.data.keyword3.toString() != "null" && it.data.keyword3 != "") {
                                    binding.chip3.text = it.data.keyword3
                                    binding.chip3.isVisible = true

                                }

                                /*binding.chip.isVisible= true
                                binding.chip2.isVisible = false
                                binding.chip3.isVisible = false

                                if (it.data.keyword1.toString() != "null" && it.data.keyword1 != "") {
                                    binding.chip.text = it.data.keyword1
                                    binding.chip.isVisible = true

                                }
                                else{
                                    binding.chip.text = "키워드를 입력하지 않았어요!"

                                }
                                if (it.data.keyword2.toString() != "null" && it.data.keyword2 != "") {
                                    binding.chip2.text = it.data.keyword2
                                    binding.chip2.isVisible = true

                                } else{
                                    binding.chip2.text = "키워드를 입력하지 않았어요!"

                                }
                                if (it.data.keyword3.toString() != "null" && it.data.keyword3 != "") {
                                    binding.chip3.text = it.data.keyword3
                                    binding.chip3.isVisible = true

                                }
                                else{
                                    binding.chip3.text = "키워드를 입력하지 않았어요!"

                                }
*/



                                if (it.data.reviews.isEmpty()) {


                                    var memoList = arrayListOf<MemoListDataclass>(

                                        MemoListDataclass(
                                            "_ _",
                                            "메모를 입력하지 않았어요!"
                                        ),
                                        MemoListDataclass(
                                            "_ _",
                                            "메모를 입력하지 않았어요!"
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


                                    Log.d("asdfasdf", it.data.reviews.size.toString())
                                    if (it.data.reviews.size == 1) {
                                        var memoList = arrayListOf<MemoListDataclass>(

                                            MemoListDataclass(
                                                it.data.reviews[0].water_date,
                                                it.data.reviews[0].review
                                            ),
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
                                    } else if (it.data.reviews.size >= 2) {
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


                                //memoList.add(MemoListDataclass(it.data.reviews[0].water_date, it.data.reviews[0].review))
                                //memoList.add(MemoListDataclass(it.data.reviews[1].water_date, it.data.reviews[1].review))


                            }
                    }
                })


        // memolist 어댑터 연결 부분
        binding.imageButton3detail.setOnClickListener {

            AlertPlantDialogFragment(plantId).show(parentFragmentManager, DetailPlantFragment.TAG)
            //3단계 식물 뷰 들어가는 곳
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val activity = activity
        if (activity != null) {
            (activity as DetailPlantActivity).setActionBarTitle("식물 카드")

        }
        // reset()
    }

    fun getcherishid() {
        //detailactivity에서 받은 데이터

        Log.d("0cherishiddetailplant", cherishid.toString())

    }

    fun reset() {
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
                                //식물 아이디 받는 곳 이거를 이제 정보 아이콘 누를때 넘겨줘야함
                                plantId = it.data.plantId
                                if (it.data.dDay > 0) {
                                    binding.textViewDday.text = "D+" + it.data.dDay.toString()

                                } else if (it.data.dDay == 0) {
                                    binding.textViewDday.text = "D-day"
                                } else {
                                    binding.textViewDday.text = "D" + it.data.dDay.toString()

                                }
                                binding.textViewDuration.text = it.data.duration.toString()
                                if ((it.data.birth.toString()) == "Invalid Date") {
                                    binding.textViewBirth.text = "_ _"

                                } else {
                                    binding.textViewBirth.text = it.data.birth.toString()

                                }
                                binding.textView1WithName.text = it.data.name.toString()
                                binding.textViewStatusMessage.text = it.data.status_message
                                binding.textViewStatus.text = it.data.status.toString()
                                Glide.with(this@DetailPlantFragment)
                                    .load(it.data.plant_thumbnail_image_url)
                                    .into(binding.imageViewDetailUrl)

                                plant_id = it.data.plantId
                                Log.d("fdfdfd", it.data.plantId.toString())


                                circleProgressbar.setProgressWithAnimation(
                                    it.data.gage.toFloat() * 100,
                                    animationDuration
                                )
                                binding.chip.isVisible = false
                                binding.chip2.isVisible = false
                                binding.chip3.isVisible = false

                                if (it.data.keyword1.toString() != "null" && it.data.keyword1 != "") {
                                    binding.chip.text = it.data.keyword1
                                    binding.chip.isVisible = true

                                } else if (it.data.keyword2.toString() != "null" && it.data.keyword2 != "") {
                                    binding.chip2.text = it.data.keyword2
                                    binding.chip2.isVisible = true

                                } else if (it.data.keyword3.toString() != "null" && it.data.keyword3 != "") {
                                    binding.chip3.text = it.data.keyword3
                                    binding.chip3.isVisible = true

                                }



                                if (it.data.reviews.isEmpty()) {


                                    var memoList = arrayListOf<MemoListDataclass>(

                                        MemoListDataclass(
                                            "_ _",
                                            "메모를 입력하지 않았어요!"
                                        ),
                                        MemoListDataclass(
                                            "_ _",
                                            "메모를 입력하지 않았어요!"
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


                                    Log.d("asdfasdf", it.data.reviews.size.toString())
                                    if (it.data.reviews.size == 1) {
                                        var memoList = arrayListOf<MemoListDataclass>(

                                            MemoListDataclass(
                                                it.data.reviews[0].water_date,
                                                it.data.reviews[0].review
                                            ),
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
                                    } else if (it.data.reviews.size >= 2) {
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


                                //memoList.add(MemoListDataclass(it.data.reviews[0].water_date, it.data.reviews[0].review))
                                //memoList.add(MemoListDataclass(it.data.reviews[1].water_date, it.data.reviews[1].review))


                            }
                    }
                })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                activity?.finish()
                return true
            }

/*R.id.setting -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(
                    R.id.fragment_detail,
                    EnrollModifyPlantFragment().apply {
                        arguments = Bundle().apply {

                            putInt("cherishidgo_delete", cherishid_main)
                            putInt("cherishidgo_userid",userId)
                        }
                    })
                // if (transaction == null) {
                transaction.addToBackStack(null)
                // }
                transaction.commit()

                return true
            }*/
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