package com.pankajjangid.todoapp.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.pankajjangid.todoapp.data.ToDoDatabase
import com.pankajjangid.todoapp.data.models.ToDoData
import com.pankajjangid.todoapp.data.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application) : AndroidViewModel(application) {

    private val toDoDao = ToDoDatabase.getDatabase(application).todoDao()
    private val repository: ToDoRepository

     val getAllData: LiveData<List<ToDoData>>

    init {
        repository = ToDoRepository(toDoDao)

        getAllData = repository.getAllData

    }

    fun insertData(toDoData: ToDoData){

        viewModelScope.launch(Dispatchers.IO){
            repository.insert(toDoData )
        }
    }

    fun  updateData(toDoData: ToDoData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(toDoData)
        }
    }

    fun deleteData(toDoData: ToDoData){
        viewModelScope.launch (Dispatchers.IO){
            repository.delete(toDoData)
        }
    }

    fun deleteAll(){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteAll()
        }
    }
}