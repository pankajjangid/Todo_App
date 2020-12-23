package com.pankajjangid.todoapp.fragments.update

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.pankajjangid.todoapp.R
import com.pankajjangid.todoapp.data.models.ToDoData
import com.pankajjangid.todoapp.data.viewmodel.ToDoViewModel
import com.pankajjangid.todoapp.databinding.FragmentUpdateBinding
import com.pankajjangid.todoapp.fragments.SharedViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val mTodoViewModel: ToDoViewModel by viewModels()

    private val mSharedViewModel: SharedViewModel by viewModels()

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentUpdateBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.args = args
        //set menu
        setHasOptionsMenu(true)


        //spinner item selected listner
        binding.spinner.onItemSelectedListener = mSharedViewModel.mListener
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.update_menu_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> confirmationItemRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    //show alert dialog to item removal
    private fun confirmationItemRemoval() {

        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mTodoViewModel.deleteData(args.currentItem)
            Toast.makeText(
                requireContext(),
                "Successfully delete ${args.currentItem.title}",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete '${args.currentItem.title}'")
        builder.setMessage("Are you sure you want to delete '${args.currentItem.title}'?")
        builder.create().show()
    }

    private fun updateItem() {

        val title = current_title_et.text.toString()
        val description = current_description_et.text.toString()
        val priority = spinner.selectedItem.toString()

        val validation = mSharedViewModel.verifyValidationFromUser(title, description)
        if (validation) {
            val updateTodo = ToDoData(
                args.currentItem.id,
                title,
                mSharedViewModel.parsePriority(priority),
                description
            )

            mTodoViewModel.updateData(updateTodo)
            Toast.makeText(requireContext(), "Successfully updated", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT)
                .show()

        }
    }


    override fun onDestroy() {
        super.onDestroy()
        //to avoid the memory leaks
        _binding=null
    }
}