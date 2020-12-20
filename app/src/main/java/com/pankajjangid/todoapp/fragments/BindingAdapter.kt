package com.pankajjangid.todoapp.fragments

import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pankajjangid.todoapp.R

class BindingAdapter {

    companion object {

        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean) {

            view.setOnClickListener {
                if (navigate) {

                    view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
                }
            }


        }
    }
}