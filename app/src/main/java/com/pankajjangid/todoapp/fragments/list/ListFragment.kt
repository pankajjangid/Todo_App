package com.pankajjangid.todoapp.fragments.list

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pankajjangid.todoapp.R
import com.pankajjangid.todoapp.SwipeToDelete
import com.pankajjangid.todoapp.data.viewmodel.ToDoViewModel
import com.pankajjangid.todoapp.databinding.FragmentListBinding
import com.pankajjangid.todoapp.fragments.SharedViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {

    private val todoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val adapter: ListAdapter by lazy { ListAdapter() }
    private var _binding:FragmentListBinding ?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentListBinding.inflate(inflater,container,false)

        _binding?.lifecycleOwner=this
        _binding?.mSharedViewModel=mSharedViewModel
        // Set Menu
        setHasOptionsMenu(true)

        //set Adapter
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        //swipe to delete
        setSwipeToDelete(binding.recyclerView)

        //observe the live data
        todoViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            mSharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })



        return binding.root
    }


    fun setSwipeToDelete(recyclerView: RecyclerView){
        val swipeToDeleteCallback = object :SwipeToDelete(){

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val item = adapter.dataList[viewHolder.adapterPosition]
                todoViewModel.deleteData(item)
                Toast.makeText(requireContext(), "'${item.title}' deleted successfully ", Toast.LENGTH_SHORT).show()
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.list_fragment_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_delete_all -> {
                confirmationItemRemoval()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //show alert dialog to delete all items
    private fun confirmationItemRemoval() {

        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            todoViewModel.deleteAll()
            Toast.makeText(
                requireContext(),
                "Successfully deleted",
                Toast.LENGTH_SHORT
            ).show()

        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete all items")
        builder.setMessage("Are you sure you want to delete all items?")
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}