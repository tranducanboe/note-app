package com.example.noteapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.noteapp.MainActivity
import com.example.noteapp.R
import com.example.noteapp.adapter.NoteAdapter
import com.example.noteapp.databinding.FragmentHomeBinding
import com.example.noteapp.model.Note
import com.example.noteapp.viewmodel.NoteViewModel


class HomeFragment : Fragment(R.layout.fragment_home) {
    // Sử dụng view binding để liên kết giao diện với Fragment
    private var _binding: FragmentHomeBinding? = null
    // Truy cập binding an toàn với giá trị không null
    private val binding get() = _binding!!
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

    // Hàm này được gọi khi Fragment được khởi tạo. Dùng để thiết lập những thứ cần thiết cho Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Cho phép Fragment có thể có menu riêng
        setHasOptionsMenu(true)
    }

    // Hàm này được gọi để tạo và trả về View hierarchy liên kết với Fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Tạo binding cho fragment với giao diện
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        // Trả về root của layout đã được binding
        return binding.root
    }

    // Hàm này được gọi sau khi View đã được tạo xong, nơi thực hiện các thao tác với giao diện
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteViewModel = (activity as MainActivity).noteViewModel
        setUpRecyclerView()
        // Thiết lập sự kiện click cho FloatingActionButton (fabAddNote) để điều hướng đến NewNoteFragment
        binding.fabAddNote.setOnClickListener { mView ->
            // Điều hướng sang NewNoteFragment khi FAB được nhấn
            mView.findNavController().navigate(R.id.action_homeFragment_to_newNoteFragment)
        }
    }

    private fun setUpRecyclerView(){
        noteAdapter = NoteAdapter()
        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = noteAdapter
        }
        activity?.let {
            noteViewModel.getAllNote().observe(viewLifecycleOwner,{ note ->
                noteAdapter.differ.submitList(note)
                updateUI(note)
            })
        }
    }

    private fun updateUI(note: List<Note>){
        if(note.isNotEmpty()){
            binding.recyclerView.visibility = View.VISIBLE
            binding.tvNoNote.visibility = View.GONE
        }else{
            binding.recyclerView.visibility = View.GONE
            binding.tvNoNote.visibility = View.VISIBLE
        }
    }
    // Hàm này được gọi để khởi tạo menu trong Fragment
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        // Inflate (thổi phồng) menu từ resource XML (home_menu.xml) để hiển thị trên thanh công cụ
        inflater.inflate(R.menu.home_menu, menu)
    }

    // Hàm này được gọi khi Fragment bị hủy, nơi giải phóng các tài nguyên không cần thiết
    override fun onDestroy() {
        super.onDestroy()
        // Đảm bảo binding được giải phóng để tránh rò rỉ bộ nhớ
        _binding = null
    }
}