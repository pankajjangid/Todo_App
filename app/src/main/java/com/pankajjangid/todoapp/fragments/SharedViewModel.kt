package com.pankajjangid.todoapp.fragments

import android.app.Application
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.pankajjangid.todoapp.R
import com.pankajjangid.todoapp.data.models.Priority
import com.pankajjangid.todoapp.data.models.ToDoData

class SharedViewModel(application: Application) : AndroidViewModel(application) {
    val emptyDatabase = MutableLiveData(true)

    fun checkIfDatabaseEmpty(todoList: List<ToDoData>) {

        emptyDatabase.value = todoList.isEmpty()
    }

    val mListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            when (position) {
                0 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.red
                        )
                    )
                }
                1 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.yellow
                        )
                    )
                }
                2 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.green
                        )
                    )
                }
            }

        }

        override fun onNothingSelected(parent: AdapterView<*>?) {


        }
    }

    fun parsePriority(priority: String): Priority {

        return when (priority) {

            "High" -> Priority.HIGH
            "Medium" -> Priority.MEDIUM
            "Low" -> Priority.LOW
            else -> Priority.LOW

        }
    }



    fun verifyValidationFromUser(title: String, description: String): Boolean {

        return if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            false
        } else !(title.isEmpty() || description.isEmpty())
    }
}