package com.example.noteapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.noteapp.MainActivity
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentUpdateNoteBinding
import com.example.noteapp.model.Note
import com.example.noteapp.toast
import com.example.noteapp.viewmodel.NoteViewModel

class UpdateNoteFragment : Fragment() {

    private lateinit var binding: FragmentUpdateNoteBinding
    private val args: UpdateNoteFragmentArgs by navArgs()
    private lateinit var currentNote: Note
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        noteViewModel = (activity as MainActivity).noteViewModel

        super.onViewCreated(view, savedInstanceState)
        currentNote = args.note!!
        binding.edtNoteBodyUpdate.setText(currentNote.noteBody)
        binding.edtNoteTitleUpdate.setText(currentNote.noteTitle)


        binding.fabUpdate.setOnClickListener {
            val title = binding.edtNoteTitleUpdate.text.toString().trim()
            val body = binding.edtNoteBodyUpdate.text.toString().trim()

            if(title.isNotEmpty()){
                val note = Note(currentNote.id, title, body)
                noteViewModel.addNote(note)
                view.findNavController().navigate(
                    R.id.action_updateNoteFragment_to_homeFragment
                )
            }else{
                activity?.toast("Vui long nhap title")
            }
        }
    }

    private fun deleteNote(){
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Delete note")
            setMessage("Bạn có muốn xóa ghi chú này không?")
            setPositiveButton("Delete") { _, _ ->
                noteViewModel.deleteNote(currentNote)
                view?.findNavController()?.navigate(R.id.action_updateNoteFragment_to_homeFragment)
            }
            setNegativeButton("CANCEL", null)
        }.create().show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.delete_menu ->{
                deleteNote()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.update_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)

    }
}