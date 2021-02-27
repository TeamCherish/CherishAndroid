package com.sopt.cherish.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

/**
 * Dexter를 이용한 권한 요청 및 콜백에 관련된 유틸
 *
 * @author MJStudio
 * @see android.Manifest.permission
 */

object PermissionUtil {
    // todo : 잘 사용될 수 있게 내가 만들어야 겠음
    fun requestCherishPermission(activity: Activity, listener: PermissionListener) {
        requestPermissions(
            activity,
            listOf(
                android.Manifest.permission.READ_CONTACTS,
                android.Manifest.permission.CALL_PHONE,
                android.Manifest.permission.SEND_SMS
            ), listener
        )
    }

    private fun requestPermissions(
        activity: Activity,
        permissions: Collection<String>,
        listener: PermissionListener
    ) {
        val callbackListener: MultiplePermissionsListener =
            object : BaseMultiplePermissionsListener() {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    val deniedPermissions =
                        report.deniedPermissionResponses.map { it.permissionName }
                    val permanentlyDeniedPermissions =
                        report.deniedPermissionResponses.filter { it.isPermanentlyDenied }
                            .map { it.permissionName }

                    // 모든 권한이 허가되었다면
                    when {
                        report.areAllPermissionsGranted() -> {
                            listener.onPermissionGranted()
                        }
                        // 권한 중에 영구적으로 거부된 권한이 있다면
                        report.isAnyPermissionPermanentlyDenied -> {
                            listener.onAnyPermissionPermanentlyDenied(
                                deniedPermissions,
                                permanentlyDeniedPermissions
                            )
                        }
                        // 권한 중에 거부된 권한이 있다면
                        else -> {
                            listener.onPermissionShouldBeGranted(deniedPermissions)
                        }
                    }

                }
            }
        /**
         * Dexter로 activity를 이용한 권한 요청
         */
        Dexter.withContext(activity).withPermissions(permissions).withListener(callbackListener)
            .check()
    }

    fun openPermissionSettings(context: Context) {
        // 권한이 설정되어 있지 않습니다. 권한 설정 칸으로 이동합니다.
        context.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
        })
    }

    fun isCheckedCallPermission(context: Context): Boolean {
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) return true
        return false
    }

    fun isCheckedReadContactsPermission(context: Context): Boolean {
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) return true
        return false
    }

    fun isCheckedSendMessagePermission(context: Context): Boolean {
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.SEND_SMS
            ) == PackageManager.PERMISSION_GRANTED
        ) return true
        return false
    }

    interface PermissionListener {
        /**
         * 모든 권한 허용
         */
        fun onPermissionGranted() {}

        /**
         * 일부 권한이 거부되었다.
         * @param deniedPermissions 거부된 권한 목록들들
         */
        fun onPermissionShouldBeGranted(deniedPermissions: List<String>) {}

        /**
         * 일부 권한이 영구적으로 거부되었다.
         *
         * @param deniedPermissions 거부된 권한 목록들
         * @param permanentDeniedPermissions 영구적으로 거부된 권한 목록
         */
        fun onAnyPermissionPermanentlyDenied(
            deniedPermissions: List<String>, permanentDeniedPermissions: List<String>
        ) {

        }
    }
}