package com.chinye.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.chinye.notes.adapters.NoteAdapter
import com.chinye.notes.databinding.ActivityMainBinding
import com.chinye.notes.models.Note
import com.chinye.notes.models.NoteDatabase
import com.chinye.notes.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: NoteDatabase
    private lateinit var noteAdapter : NoteAdapter
    private lateinit var viewModel: MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//instantiating database
        database = Room.databaseBuilder(
            applicationContext,
            NoteDatabase::class.java,
            "notes_database"
        ).allowMainThreadQueries().build()

//instantiating viewModel
        viewModel =  ViewModelProvider(this)[MainActivityViewModel::class.java]
        viewModel.getNotes((database))

        //observe live data
        viewModel.notesLiveData.observe(this, { notes ->
            noteAdapter = NoteAdapter(database.noteDao().getAllNotes()) {
                val intent = Intent(this@MainActivity, NoteDetailsActivity::class.java)
                intent.run {
                    putExtra("id", it.id)
                    startActivity(this,)
                }
            }

            //refreshing recycler view
            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = noteAdapter
            }
        })




        binding.button.setOnClickListener{
            val title = binding.titleInput.text.toString()
            val content = binding.contentInput.toString()

            saveNote(title, content)
        }

    }


    private fun saveNote(title: String, content: String){
        val note = Note(id = 0, title, content)
        database.noteDao().addNote(note)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }

    private val listener = object: NoteAdapter.OnNoteItemClickListener{
        override fun onClick(note: Note) {
            val intent = Intent(this@MainActivity, NoteDetailsActivity::class.java)
            intent.run {
                putExtra("id", note.id)
                startActivity(this)
            }
        }

    }
}