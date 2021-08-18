package com.jay.todoapp.data.viewModel

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.jay.todoapp.R
import com.jay.todoapp.data.ToDoDatabase
import com.jay.todoapp.data.model.Priority
import com.jay.todoapp.data.model.ToDoData
import com.jay.todoapp.data.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// AndroidViewModel is just like viewModel but provides access to the application context directly
class ToDoViewModel(application: Application) : AndroidViewModel(application) {

    private val toDoDao = ToDoDatabase.getDatabase(application).toDoDao()
    private val repository : ToDoRepository
    private val getAllData: LiveData<List<ToDoData>>
    init {
        repository = ToDoRepository(toDoDao)
        getAllData = repository.getAllData
    }

    private fun insertData(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(toDoData)
        }
    }

    fun insertDataToDb(toDoTitle: String, toDoDesc: String, priorityLevel: String) : Boolean{
        return if(verifyUserData(toDoTitle, priorityLevel)) {
            val newData = ToDoData(
                0, // This is set to auto increment so room will handle it
                parsePriority(priorityLevel),
                toDoTitle,
                toDoDesc
            )
            insertData(newData)
            true
        } else false
    }

    private fun verifyUserData(title: String, priority: String) : Boolean{
        return when {
            title.trim() == "" -> {
                Toast.makeText(getApplication(), "Please enter a title", Toast.LENGTH_SHORT).show()
                false
            }
            priority.trim() == "" -> {
                Toast.makeText(getApplication(), "Please choose a priority level", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    private fun parsePriority(priority: String) : Priority {
        return when(priority) {
            "High" -> {
                Priority.HIGH}
            "Medium" -> {
                Priority.MEDIUM}
            else -> {
                Priority.LOW}
        }
    }

    // Selected Listener for the DropDown to change colors
    val dropDownListener : AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            when(position) {
                0 -> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.red))}
                1 -> {(parent?.getChildAt(1) as TextView).setTextColor(ContextCompat.getColor(application, R.color.yellow))}
                2 -> {(parent?.getChildAt(2) as TextView).setTextColor(ContextCompat.getColor(application, R.color.green))}
            }
        }
        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }

}