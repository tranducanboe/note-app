package com.example.noteapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.noteapp.MainActivity
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentNewNoteBinding
import com.example.noteapp.model.Note
import com.example.noteapp.toast
import com.example.noteapp.viewmodel.NoteViewModel
import com.google.android.material.snackbar.Snackbar

class NewNoteFragment : Fragment() {
    private lateinit var binding: FragmentNewNoteBinding
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var mView: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteViewModel = (activity as MainActivity).noteViewModel
        mView = view
    }

    private fun saveNote(view: View){
        val noteTitle = binding.edtNoteTitleNew.text.toString().trim()
        val noteBody = binding.edtNoteBodyNew.text.toString().trim()
        if(noteTitle.isNotEmpty()){
            val note =  Note(0,noteTitle, noteBody)
            noteViewModel.addNote(note)
            Snackbar.make(
                view,
                "Ghi nhớ đã được luu",
                Snackbar.LENGTH_SHORT
            ).show()

            view.findNavController().navigate(
                R.id.action_newNoteFragment_to_homeFragment
            )
        }else{
            activity?.toast("Vui lòng nhập tiêu đề")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.save_menu -> {
                saveNote(mView)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.new_note_menu,menu)
    }
}