package com.example.noteapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.databinding.NoteLayoutAdapterBinding
import com.example.noteapp.fragment.HomeFragmentDirections
import com.example.noteapp.model.Note

class NoteAdapter():RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
   private lateinit var binding: NoteLayoutAdapterBinding

   class NoteViewHolder(private val binding: NoteLayoutAdapterBinding):RecyclerView.ViewHolder(binding.root)

    private val differCallback =
        object : DiffUtil.ItemCallback<Note>(){
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }

        }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
       binding = NoteLayoutAdapterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NoteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = differ.currentList[position]
        holder.itemView.apply {
            binding.tvNoteTitle.text = currentNote.noteTitle
            binding.tvNoteBody.text = currentNote.noteBody
        }.setOnClickListener {mView->
            val derection = HomeFragmentDirections.actionHomeFragmentToUpdateNoteFragment(currentNote)
            mView.findNavController().navigate(derection)
        }
    }
}