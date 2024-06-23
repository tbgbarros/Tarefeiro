package com.example.tarefeiro

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firestore = FirebaseFirestore.getInstance()
        recyclerView = findViewById(R.id.recyclerView)
        taskAdapter = TaskAdapter(mutableListOf())

        recyclerView.adapter = taskAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchTasks()

        findViewById<FloatingActionButton>(R.id.fabAddTask).setOnClickListener {
            startActivity(Intent(this, AddEditTaskActivity::class.java))
        }
    }

    private fun fetchTasks() {
        firestore.collection("tasks").addSnapshotListener { snapshot, e ->
            if (e != null || snapshot == null) {
                Log.e("MainActivity", "Erro de gravação da tarefa", e)
                return@addSnapshotListener
            }
            val tasks = snapshot.toObjects(Task::class.java)
            taskAdapter.updateTasks(tasks)
        }
    }
}
