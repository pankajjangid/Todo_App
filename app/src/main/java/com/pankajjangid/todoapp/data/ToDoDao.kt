package com.pankajjangid.todoapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pankajjangid.todoapp.data.models.ToDoData

@Dao
interface  ToDoDao {

    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<ToDoData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
   suspend fun insert(toDoData: ToDoData)

   @Update
   suspend fun update(toDoData: ToDoData)

   @Delete
   suspend fun delete(toDoData: ToDoData)

   @Query("DELETE FROM todo_table")
   suspend fun deleteAll()
}