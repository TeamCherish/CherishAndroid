package com.sopt.cherish.ui.review

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ActivityReviewBinding
import com.sopt.cherish.remote.api.ReviewWateringReq
import com.sopt.cherish.ui.dialog.CustomDialogFragment
import com.sopt.cherish.ui.main.MainViewModel
import com.sopt.cherish.util.DialogUtil
import com.sopt.cherish.util.SimpleLogger
import com.sopt.cherish.util.extension.FlexBoxExtension.addChip
import com.sopt.cherish.util.extension.FlexBoxExtension.getChip
import com.sopt.cherish.util.extension.FlexBoxExtension.getChipsCount
import com.sopt.cherish.util.extension.countNumberOfCharacters
import com.sopt.cherish.util.extension.shortToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class DialogReviewFragment : DialogFragment() {

    private val TAG = "ReviewFragment"

    private val viewModel: MainViewModel by activityViewModels()

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
        // 야매로함 , 근데 이거 안됨
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
        viewModel.fetchUsers()
        lifecycleScope.launch(Dispatchers.IO) {
            CustomDialogFragment(R.layout.dialog_loading).show(parentFragmentManager, TAG)
            delay(2000)
            // 만약에 홈 프라그먼트에서 물 주는 애니메이션을 해야한다 그러면 finishActivity로 변경해야할 수도 있음
            dismiss()
        }
    }

    private fun addUserStatusWithChip(binding: ActivityReviewBinding) {
        // todo : 한글 키보드는 ENTER를 치게 되면 줄바꿈이 된다. 이거 처리를 해줘야 한다.
        // 이거 처리만 해주면 끝이 납니다
        // 글자수에 따라 엔터를 먹히지 않게 한다던지 하면 될거 같음
        binding.reviewEditKeyword.setOnKeyListener { view, keyCode, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN || keyCode == KeyEvent.KEYCODE_ENTER) {
                val et = view as EditText
                val name = et.text.toString()
                if (binding.reviewFlexBox.getChipsCount() < 3)
                    binding.reviewFlexBox.addChip(name)
                else {
                    CustomDialogFragment(R.layout.sample_lottie2).show(parentFragmentManager, TAG)
                }
                et.text = null
            }
            return@setOnKeyListener false
        }
    }

    private fun sendReviewToServer(binding: ActivityReviewBinding) {
        binding.reviewAdminAccept.setOnClickListener {
            // 물주는 모션이 뜨면서 다시 main으로 넘어가면 됨
            // 리뷰를 전부 적지 않으면 안된다는 것이라던지에 대한 로직만 넣으면 해결됨

            // reviewAPI 다시 한번 생각해보자
            val now = System.currentTimeMillis()
            val date = Date(now)
            SimpleLogger.logI(date.toString())
            viewModel.sendReviewToServer(
                reviewWateringReq = ReviewWateringReq(
                    binding.reviewMemo.text.toString(),
                    binding.reviewFlexBox.getChip(0)?.text.toString(),
                    binding.reviewFlexBox.getChip(1)?.text.toString(),
                    binding.reviewFlexBox.getChip(2)?.text.toString(),
                    1
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