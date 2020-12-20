package com.pankajjangid.todoapp.data.repository

import androidx.lifecycle.LiveData
import com.pankajjangid.todoapp.data.ToDoDao
import com.pankajjangid.todoapp.data.models.ToDoData

class ToDoRepository(private val  dao: ToDoDao) {


     val getAllData : LiveData<List<ToDoData>> = dao.getAllData()

    suspend fun insert(toDoData: ToDoData){
        dao.insert(toDoData)
    }


    suspend fun update(toDoData: ToDoData){
        dao.update(toDoData)
    }

    suspend fun delete(toDoData: ToDoData){
        dao.delete(toDoData)
    }

    suspend fun deleteAll(){
        dao.deleteAll()
    }
}