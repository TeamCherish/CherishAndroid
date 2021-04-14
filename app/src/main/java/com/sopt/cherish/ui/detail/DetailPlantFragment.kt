package com.sopt.cherish.ui.detail

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentDetailPlantBinding
import com.sopt.cherish.remote.api.ResponsePlantCardDatas
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import com.sopt.cherish.ui.detail.calendar.CalendarFragment
import com.sopt.cherish.ui.dialog.plantpopup.AlertPlantDialogFragment
import com.sopt.cherish.ui.dialog.wateringdialog.DetailWateringDialogFragment
import com.sopt.cherish.ui.domain.MemoListDataclass
import com.sopt.cherish.ui.main.onboarding.OnBoardingActivity
import com.sopt.cherish.ui.main.onboarding.onboardingFragment
import com.sopt.cherish.util.DateUtil
import com.sopt.cherish.util.extension.longToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Math.abs
import java.util.*


class DetailPlantFragment : Fragment() {

    //private lateinit var circleProgressbar: CircleProgressbar


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

    var statusmessagebig = ""
    var statusmessagesmall = ""
    var touchimage = false

    companion object {
        private val TAG = "DetailPlantFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailserver()


        binding.imageViewDetailUrl.setOnClickListener {

            if (!touchimage) {
                binding.textViewStatusMessage.text = statusmessagebig
                binding.textViewStatus.text = statusmessagesmall
                binding.imageViewDetailDim.isVisible = true
                binding.textViewStatusMessage.isVisible = true
                binding.textViewStatus.isVisible = true
                touchimage = true
            } else {

                binding.imageViewDetailDim.isVisible = false
                binding.textViewStatusMessage.isVisible = false
                binding.textViewStatus.isVisible = false
                touchimage = false
            }
        }
        //여기에 작성
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_detail_plant, container, false)
        binding.lifecycleOwner = viewLifecycleOwner





        plantId = arguments?.getInt("plantId_detail")!!

        cherishid = arguments?.getInt("cherishidmain_detail")!!
        cherishUserPhoneNumber = arguments?.getString("cherishUserPhoneNumber_detail")!!
        cherishNickname = arguments?.getString("cherishNickname_detail")!!
        userNickname = arguments?.getString("userNickname_detail")!!
        userId = arguments?.getInt("userId_detail")!!
        //reset()

        //circleProgressbar = binding.test

        //reset()
        binding.buttonWater.setOnClickListener {
            // 이거 매개변수 바꿔야 함
/*            DetailWateringDialogFragment(cherishid).show(
                parentFragmentManager,
                "DetailPlantFragment"
            )*/
            if (viewModel.dDay <= 0) {
                DetailWateringDialogFragment().show(parentFragmentManager, "DetailPlantFragment")
            } else {
                longToast(requireContext(), "물 줄수있는 날이 아니에요 ㅠ")
            }
        }

        binding.memocons.setOnClickListener {
            if (binding.userdate.text != "_ _") {
                viewModel.selectedMemoCalendarDay.value =
                    DateUtil.convertStringToCalendarDay(binding.userdate.text.toString())
                viewModel.selectedCalendarDay.value =
                    DateUtil.convertStringToCalendarDay(binding.userdate.text.toString())
                val transaction =
                    parentFragmentManager.beginTransaction()
                transaction.replace(
                    R.id.fragment_detail,
                    CalendarFragment()
                )
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
        binding.memocons2.setOnClickListener {
            if (binding.userdate2.text != "_ _") {
                viewModel.selectedMemoCalendarDay.value =
                    DateUtil.convertStringToCalendarDay(binding.userdate2.text.toString())
                viewModel.selectedCalendarDay.value =
                    DateUtil.convertStringToCalendarDay(binding.userdate2.text.toString())
                val transaction =
                    parentFragmentManager.beginTransaction()
                transaction.replace(
                    R.id.fragment_detail,
                    CalendarFragment()
                )
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }

        Log.d("gogo", cherishid.toString())


        // memolist 어댑터 연결 부분
        binding.imageButton3detail.setOnClickListener {

            AlertPlantDialogFragment(plantId).show(parentFragmentManager, DetailPlantFragment.TAG)
           // startActivity(Intent(context,OnBoardingActivity::class.java))

            //3단계 식물 뷰 들어가는 곳
        }
        return binding.root
    }


    fun detailserver() {
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
                                viewModel.dDay = it.data.dDay
                                if (it.data.dDay > 0) {
                                    binding.textViewDday.text = "D-" + it.data.dDay.toString()

                                } else if (it.data.dDay == 0) {
                                    binding.textViewDday.text = "D-day"
                                } else {
                                    binding.textViewDday.text = "D+" + abs(it.data.dDay).toString()

                                }
                                binding.textViewDuration.text = it.data.duration.toString() + "일째"
                                if ((it.data.birth.toString()) == "Invalid Date") {
                                    binding.textViewBirth.text = "_ _"

                                } else {
                                    binding.textViewBirth.text = it.data.birth.toString()

                                }
                                binding.textView1WithName.text = it.data.nickname.toString()

                                statusmessagebig = it.data.status_message
                                statusmessagesmall = it.data.status
                                /*  binding.textViewStatusMessage.text = it.data.status_message
                                  binding.textViewStatus.text = it.data.status.toString()*/
                                Glide.with(this@DetailPlantFragment)
                                    .load(it.data.plant_thumbnail_image_url)
                                    .into(binding.imageViewDetailUrl)

                                plant_id = it.data.plantId
                                Log.d("fdfdfd", it.data.plantId.toString())
                                if (it.data.gage < 0.5) {

                                    binding.test.setProgressStartColor(Color.parseColor("#F7596C"))
                                    binding.test.setProgressEndColor(Color.parseColor("#F7596C"))

                                    binding.test.progress = (it.data.gage.toFloat() * 100).toInt()


                                } else {
                                    binding.test.progress = (it.data.gage.toFloat() * 100).toInt()

                                }

                                binding.chip.isVisible = false
                                binding.chip2.isVisible = false
                                binding.chip3.isVisible = false
                                /* binding.chip.text = it.data.keyword1
                                 binding.chip2.text = it.data.keyword2
                                 binding.chip3.text = it.data.keyword3*/
                                if (it.data.keyword1 == "" && it.data.keyword2 == "" && it.data.keyword3 == "") {
                                    binding.chip.text = "등록된 키워드가 없어요"
                                    binding.chip.isVisible = true
                                    binding.chip2.isVisible = false
                                    binding.chip3.isVisible = false

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

                            
                                if (it.data.reviews.isEmpty()) {
                                    binding.userdate.text = "_ _"
                                    binding.usermemo.text = "메모를 입력하지 않았어요!"

                                    binding.userdate2.text = "_ _"
                                    binding.usermemo2.text = "메모를 입력하지 않았어요!"

                                } else if (it.data.reviews.size == 1) {
                                    binding.userdate.text = (it.data.reviews[0].water_date)
                                    if(it.data.reviews[0].review=="") {
                                        binding.usermemo.text = "메모를 입력하지 않았어요!"
                                    }else{
                                        binding.usermemo.text = it.data.reviews[0].review
                                    }
                                    binding.userdate2.text = "_ _"
                                    binding.usermemo2.text = "메모를 입력하지 않았어요!"

                                    binding.memocons2.isVisible = true
                                } else {
                                    binding.userdate.text = (it.data.reviews[0].water_date)
                                    if(it.data.reviews[0].review=="") {
                                        binding.usermemo.text = "메모를 입력하지 않았어요!"
                                    }else{
                                        binding.usermemo.text = it.data.reviews[0].review
                                    }

                                    binding.userdate2.text = (it.data.reviews[1].water_date)
                                    if(it.data.reviews[1].review=="") {
                                        binding.usermemo2.text = "메모를 입력하지 않았어요!"
                                    }else{
                                        binding.usermemo2.text = it.data.reviews[1].review
                                    }

                                }



                            }
                    }
                })

    }

    override fun onResume() {
        super.onResume()

        val activity = activity
        if (activity != null) {
            (activity as DetailPlantActivity).setActionBarTitle("식물 카드")

        }
        // reset()
    }


}
