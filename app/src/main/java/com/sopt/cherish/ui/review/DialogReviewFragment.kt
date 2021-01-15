package com.sopt.cherish.ui.review

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ActivityReviewBinding
import com.sopt.cherish.remote.api.ReviewWateringReq
import com.sopt.cherish.ui.datail.DetailPlantFragment
import com.sopt.cherish.ui.dialog.CustomDialogFragment
import com.sopt.cherish.ui.main.MainViewModel
import com.sopt.cherish.util.DialogUtil
import com.sopt.cherish.util.extension.FlexBoxExtension.getChip
import com.sopt.cherish.util.extension.countNumberOfCharacters
import com.sopt.cherish.util.extension.shortToast
import com.sopt.cherish.util.extension.writeReview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DialogReviewFragment : DialogFragment() {

    private val TAG = "ReviewFragment"

    private val viewModel: MainViewModel by activityViewModels()

    // todo : Review를 보내서 점수도 받았는데 서버에서 갱신이 안되는거 같음
    // todo : Server와 얘기를 해봐야함
    // todo : dDay인 아이만 갱신되게 해놨을 가능성이 있슴 그래서 내가 보내봤자 아무것도 오르지 않는거지
    // todo : Review API 다시한번 듣기
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: ActivityReviewBinding =
            DataBindingUtil.inflate(inflater, R.layout.activity_review, container, false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        initializeView(binding)
        addUserStatusWithChip(binding)
        addLimitNumberOfKeywordCharacters(binding)
        addLimitNumberOfMemoCharacters(binding)
        sendReviewToServer(binding)
        ignoreSendReviewToServer(binding)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        DialogUtil.adjustDialogSize(this, 1.0f, 0.975f)
    }

    @SuppressLint("SetTextI18n")
    private fun initializeView(binding: ActivityReviewBinding) {
        binding.reviewUser.text =
            "${viewModel.userNickName.value}님! ${viewModel.cherishUser.value?.nickName}님과의"
        binding.reviewDescription.text =
            "${viewModel.cherishUser.value?.nickName}님과의 물주기를 기록해주세요"
    }

    private fun addLimitNumberOfMemoCharacters(binding: ActivityReviewBinding) {
        binding.reviewMemo.countNumberOfCharacters { memo ->
            binding.reviewNumberOfMemo.text = memo?.length.toString()
            if (memo?.length!! > 100) {
                shortToast(requireContext(), "100자를 초과했습니다.")
            }
        }
    }

    private fun addLimitNumberOfKeywordCharacters(binding: ActivityReviewBinding) {
        binding.reviewEditKeyword.countNumberOfCharacters { keyword ->
            binding.reviewNumberOfCharacters.text = keyword?.length.toString()
            if (keyword?.length!! > 5) {
                CustomDialogFragment(R.layout.sample_wordcount_error).show(
                    parentFragmentManager,
                    TAG
                )
            }
        }
    }

    private fun showLoadingDialog() {
        // 이녀석을 호출하는게 맞는지 아닌지 확인해야함
        /*viewModel.fetchUsers()*/
        lifecycleScope.launch(Dispatchers.IO) {
            CustomDialogFragment(R.layout.dialog_loading).show(parentFragmentManager, TAG)
            delay(2000)
            // 만약에 홈 프라그먼트에서 물 주는 애니메이션을 해야한다 그러면 finishActivity로 변경해야할 수도 있음
            dismiss()
            activity?.onBackPressed()
        }
    }

    private fun addUserStatusWithChip(binding: ActivityReviewBinding) {
        // todo : 한글 키보드는 ENTER를 치게 되면 줄바꿈이 된다. 이거 처리를 해줘야 한다.
        // 이거 처리만 해주면 끝이 납니다
        // 글자수에 따라 엔터를 먹히지 않게 한다던지 하면 될거 같음
        // 다이얼로그가 왜 뜨는 지 모르겠는데 일단 뜸 ㅋㅋ
        binding.reviewEditKeyword.writeReview(binding.reviewFlexBox)
    }

    private fun sendReviewToServer(binding: ActivityReviewBinding) {
        binding.reviewAdminAccept.setOnClickListener {
            // 물주는 모션이 뜨면서 다시 main으로 넘어가면 됨
            // 리뷰를 전부 적지 않으면 안된다는 것이라던지에 대한 로직만 넣으면 해결됨

            // reviewAPI 다시 한번 생각해보자

            viewModel.sendReviewToServer(
                reviewWateringReq = ReviewWateringReq(
                    binding.reviewMemo.text.toString(),
                    binding.reviewFlexBox.getChip(0)?.text.toString(),
                    binding.reviewFlexBox.getChip(1)?.text.toString(),
                    binding.reviewFlexBox.getChip(2)?.text.toString(),
                    viewModel.cherishUser.value?.id!!.toString()
                )
            )
            showLoadingDialog()


        }
    }

    private fun ignoreSendReviewToServer(binding: ActivityReviewBinding) {
        binding.reviewIgnoreAccept.setOnClickListener {
            // 건너 뛰는 것도 뭐~ 그냥 main으로 넘어오면 됨

            dismiss()


        }
    }
}