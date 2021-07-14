package com.chinye.notes.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao() : NoteDAO

    companion object{
       private var database: NoteDatabase? = null
        fun getInstance(context: Context): NoteDatabase{
           return if (database != null){
                return database!!
            }else{
               database = Room.databaseBuilder(
                   context,
                   NoteDatabase::class.java,
                   "notes_database"
               ).build()
               database!!
           }
        }
    }

}