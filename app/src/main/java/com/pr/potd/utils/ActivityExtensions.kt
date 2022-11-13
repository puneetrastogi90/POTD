package com.pr.potd.utils

import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

@Throws(IllegalStateException::class)
internal fun AppCompatActivity.addFragment(
    fragmentContainerResourceId: Int,
    currentFragment: Fragment?,
    nextFragment: Fragment?,
    commitAllowingStateLoss: Boolean
): Boolean {
    if (currentFragment == null || nextFragment == null) {
        return false
    }
    val fragmentManager = supportFragmentManager
    val fragmentTransaction = fragmentManager.beginTransaction()

    fragmentTransaction.add(
        fragmentContainerResourceId,
        nextFragment,
        nextFragment.javaClass.simpleName
    )
    fragmentTransaction.addToBackStack(nextFragment.javaClass.simpleName)

    val parentFragment = currentFragment.parentFragment
    fragmentTransaction.hide(parentFragment ?: currentFragment)

    if (!commitAllowingStateLoss) {
        fragmentTransaction.commit()
    } else {
        fragmentTransaction.commitAllowingStateLoss()
    }

    return true
}


@Throws(IllegalStateException::class)
internal fun AppCompatActivity.replaceFragment(
    fragmentContainerResourceId: Int,
    supportFragmentManager: FragmentManager?,
    nextFragment: Fragment?,
    commitAllowingStateLoss: Boolean,
    addTobackStack: Boolean = true
): Boolean {
    if (nextFragment == null || supportFragmentManager == null) {
        return false
    }
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    if (addTobackStack)
        fragmentTransaction.addToBackStack(nextFragment.javaClass.simpleName)
    fragmentTransaction.replace(
        fragmentContainerResourceId,
        nextFragment,
        nextFragment.javaClass.simpleName
    )

    if (!commitAllowingStateLoss) {
        fragmentTransaction.commit()
    } else {
        fragmentTransaction.commitAllowingStateLoss()
    }

    return true
}


