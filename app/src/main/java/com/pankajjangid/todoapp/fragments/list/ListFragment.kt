package com.pankajjangid.todoapp.fragments.list

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pankajjangid.todoapp.R
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

        // Set Menu
        setHasOptionsMenu(true)

        //set Adapter
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        //observe the live data
        todoViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            mSharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })

        //check database is empty
        mSharedViewModel.emptyDatabase.observe(viewLifecycleOwner,{
            showEmptyDataBaseViews(it)
        })

        return binding.root
    }

    private fun showEmptyDataBaseViews(emptyDatabase: Boolean) {

        if (emptyDatabase){
            view?.no_data_image_view?.visibility = View.VISIBLE
            view?.no_data_text_view?.visibility = View.VISIBLE
        }else{

            view?.no_data_image_view?.visibility = View.INVISIBLE
            view?.no_data_text_view?.visibility = View.INVISIBLE
        }
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