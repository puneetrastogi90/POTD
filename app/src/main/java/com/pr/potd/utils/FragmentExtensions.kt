package com.pr.potd.utils

import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

/**
 * Adds the Fragment into layout container.
 *
 * @param container               Resource _id of the layout in which Fragment will be added
 * @param currentFragment         Current loaded Fragment to be hide
 * @param nextFragment            New Fragment to be loaded into container
 * @param requiredAnimation       true if screen transition animation is required
 * @param commitAllowingStateLoss true if commitAllowingStateLoss is needed
 * @return true if new Fragment added successfully into container, false otherwise
 * @throws ClassCastException    Throws exception if getActivity() is not an instance of BaseActivity
 * @throws IllegalStateException Exception if Fragment transaction is invalid
 */
@Throws(ClassCastException::class, IllegalStateException::class)
fun Fragment.addFragment(
    container: Int,
    currentFragment: Fragment,
    nextFragment: Fragment,
    commitAllowingStateLoss: Boolean
): Boolean {

    return if (activity != null) {
        (activity as AppCompatActivity).addFragment(
            container,
            currentFragment,
            nextFragment,
            commitAllowingStateLoss
        )

    } else false
}


internal fun Fragment.displayProgressBar(progressBar: ProgressBar, visible: Boolean) {
    if (visible) {
        progressBar.visibility = View.VISIBLE
    } else {
        progressBar.visibility = View.INVISIBLE
    }
}

internal fun Fragment.showToast(str: String) =
    Toast.makeText(requireContext(), str, Toast.LENGTH_LONG).show()