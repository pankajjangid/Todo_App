package com.pankajjangid.todoapp.fragments.list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pankajjangid.todoapp.R
import com.pankajjangid.todoapp.data.models.Priority
import com.pankajjangid.todoapp.data.models.ToDoData
import com.pankajjangid.todoapp.databinding.RowLayoutBinding
import kotlinx.android.synthetic.main.row_layout.view.*

class ListAdapter : RecyclerView.Adapter<ListAdapter.ListHolder>() {

    var dataList = emptyList<ToDoData>()

    class ListHolder(val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(toDoData: ToDoData) {
            binding.toDoData = toDoData
            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): ListHolder {
                val binding =
                    RowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

                return ListHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {

        return ListHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
      val currentItem = dataList[position]
        holder.onBind(currentItem)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setData(toDoData: List<ToDoData>) {
        this.dataList = toDoData
        notifyDataSetChanged()
    }
}

