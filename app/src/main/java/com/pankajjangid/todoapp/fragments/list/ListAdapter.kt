package com.pankajjangid.todoapp.fragments.list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pankajjangid.todoapp.R
import com.pankajjangid.todoapp.data.models.Priority
import com.pankajjangid.todoapp.data.models.ToDoData
import kotlinx.android.synthetic.main.row_layout.view.*

class ListAdapter : RecyclerView.Adapter<ListAdapter.ListHolder>() {

    var dataList = emptyList<ToDoData>()

    class ListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {

        return ListHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        holder.itemView.title_txt.text = dataList[position].title
        holder.itemView.description_txt.text = dataList[position].description
        Log.d("Priority",dataList[position].priority.name)


        holder.itemView.row_background.setOnClickListener {
            
            val args = ListFragmentDirections.actionListFragmentToUpdateFragment(dataList[position])
            holder.itemView.findNavController().navigate(args)

        }
        when (dataList[position].priority) {
            Priority.HIGH -> {
                holder.itemView.priority_indicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context, R.color.red
                    )
                )
            }
            Priority.MEDIUM -> {
                holder.itemView.priority_indicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context, R.color.yellow
                    )
                )
            }
            Priority.LOW -> {
                holder.itemView.priority_indicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context, R.color.green
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setData(toDoData : List<ToDoData>){
        this.dataList = toDoData
        notifyDataSetChanged()
    }
}

