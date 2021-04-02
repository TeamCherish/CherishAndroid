package com.sopt.cherish.util.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

object fragmentExtension {
    fun Fragment.refreshFragment(fragment: Fragment, fragmentManager: FragmentManager) {
        fragmentManager.beginTransaction().detach(fragment).attach(fragment).commit()
    }
}