package com.pankajjangid.todoapp.fragments.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.pankajjangid.todoapp.R
import com.pankajjangid.todoapp.data.models.ToDoData
import com.pankajjangid.todoapp.fragments.SharedViewModel
import com.pankajjangid.todoapp.data.viewmodel.ToDoViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*

class AddFragment : Fragment() {

    private val mViewModel:ToDoViewModel by viewModels()
    private val mSharedViewModel : SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        //set menu
        setHasOptionsMenu(true)

        //set spinner listener
        view.priority_spinner.onItemSelectedListener = mSharedViewModel.mListener

        return view
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        if (item.itemId == R.id.menu_add) {
            insertDataToDB()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDB() {

        val title = title_et.text.toString()
        val description = description_et.text.toString()
        val priority = priority_spinner.selectedItem.toString();

       val validation = mSharedViewModel. verifyValidationFromUser(title, description)

        if (validation){
            val newData = ToDoData(0,title,mSharedViewModel.parsePriority(priority),description)
            mViewModel.insertData(newData)
            Toast.makeText(requireContext(),"Successfully added",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(),"Please fill add fields",Toast.LENGTH_SHORT).show()
        }
    }


}