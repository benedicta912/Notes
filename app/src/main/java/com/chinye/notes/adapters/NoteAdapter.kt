package com.chinye.notes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chinye.notes.databinding.ActivityNoteDetailsBinding
import com.chinye.notes.databinding.NoteItemBinding
import com.chinye.notes.models.Note

class NoteAdapter(private val notes: List<Note>, val clicker: (Note) -> Unit): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(){

   inner class NoteViewHolder(private val binding: NoteItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.apply {
                binding.iddisplay.text = note.id.toString()
                binding.titlename.text = note.title
                root.setOnClickListener { clicker(note)}
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        var binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context))
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
       holder.bind(notes[position])
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    interface OnNoteItemClickListener{
        fun onClick(note: Note)
    }
}