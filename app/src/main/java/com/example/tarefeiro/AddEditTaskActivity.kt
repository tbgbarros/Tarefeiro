package com.example.tarefeiro

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar
import java.util.UUID

class AddEditTaskActivity : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var dueDateEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var firestore: FirebaseFirestore
    private var taskId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_task)

        firestore = FirebaseFirestore.getInstance()
        titleEditText = findViewById(R.id.etTitle)
        descriptionEditText = findViewById(R.id.etDescription)
        dueDateEditText = findViewById(R.id.etDueDate)
        saveButton = findViewById(R.id.btnSave)

        taskId = intent.getStringExtra("taskId")
        if (taskId != null) {
            fetchTask()
        }

        saveButton.setOnClickListener {
            saveTask()
        }

        dueDateEditText.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun fetchTask() {
        firestore.collection("tasks").document(taskId!!).get()
            .addOnSuccessListener { document ->
                val task = document.toObject(Task::class.java)
                if (task != null) {
                    titleEditText.setText(task.title)
                    descriptionEditText.setText(task.description)
                    dueDateEditText.setText(task.dueDate)
                }
            }
    }

    private fun saveTask() {
        val title = titleEditText.text.toString()
        val description = descriptionEditText.text.toString()
        val dueDate = dueDateEditText.text.toString()

        if (taskId == null) {
            val newTask = Task(UUID.randomUUID().toString(), title, description, dueDate)
            firestore.collection("tasks").document(newTask.id).set(newTask)
        } else {
            val updatedTask = Task(taskId!!, title, description, dueDate)
            firestore.collection("tasks").document(taskId!!).set(updatedTask)
        }

        finish()
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
            dueDateEditText.setText("$dayOfMonth/${month + 1}/$year")
    },
            year, month, day)
        datePickerDialog.show()
    }
}