package com.chinye.notes.viewmodels

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.chinye.notes.models.Note
import com.chinye.notes.models.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    val notesLiveData = MutableLiveData<List<Note>>()
    init {

    }

    fun getNotes(database: NoteDatabase){
        viewModelScope.launch {
            notesLiveData.postValue(database.noteDao().getAllNotes())
        }
//        CoroutineScope(Dispatchers.IO)
//        notesLiveData.postValue(database.noteDao().getAllNotes())
    }

    fun addNote(database: NoteDatabase, note: Note){
        CoroutineScope(Dispatchers.IO).launch {
        database.noteDao().addNote(note)
        getNotes(database)
        }
    }
}