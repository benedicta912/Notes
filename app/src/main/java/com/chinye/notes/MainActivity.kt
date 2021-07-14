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
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var viewModel: MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//instantiating database

        database = NoteDatabase.getInstance(applicationContext)

//instantiating viewModel
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        viewModel.getNotes((database))

        //instantiate adapter with empty dataset
        noteAdapter = NoteAdapter(listOf<Note>()) {
            val intent = Intent(this@MainActivity, NoteDetailsActivity::class.java)
            intent.run {
                putExtra("id", it.id)
                putExtra("content", it.content)
                putExtra("title", it.title)
            }
            startActivity(intent)
        }

        //refreshing recycler view
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = noteAdapter
        }


//observe live data from view model
        viewModel.notesLiveData.observe(this, { notes ->
            noteAdapter.notes = notes
            noteAdapter.notifyDataSetChanged()
        })

        binding.button.setOnClickListener {
            val title = binding.titleInput.text.toString()
            val content = binding.contentInput.text.toString()

            saveNote(title, content)
        }
    }

    private fun saveNote(title: String, content: String) {
        val note = Note(id = 0, title, content)
        viewModel.addNote(database, note)
    }
}