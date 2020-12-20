package com.pankajjangid.todoapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pankajjangid.todoapp.data.models.ToDoData

@Database(entities = [ToDoData::class],version = 1)
@TypeConverters(Converter::class)
abstract class ToDoDatabase:RoomDatabase() {

    abstract fun todoDao():ToDoDao

    companion object {

        @Volatile
        var INSTANCE:ToDoDatabase?=null

        fun getDatabase(context:Context):ToDoDatabase{
            val tempInstance = INSTANCE
            if (tempInstance!=null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,ToDoDatabase::class.java,"todo_database").build()

                INSTANCE =instance

                return  instance
            }
        }
    }


}