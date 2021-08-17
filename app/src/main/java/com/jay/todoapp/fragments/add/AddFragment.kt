package com.jay.todoapp.fragments.add

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import com.jay.todoapp.R
import com.jay.todoapp.databinding.FragmentAddBinding

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Options Menu
        setHasOptionsMenu(true)

        // This is to set up the dropdown menu
        val items = listOf("High", "Medium", "Low")
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_list_item, items)
        binding.autocompleteTextView.setAdapter(adapter)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }
}