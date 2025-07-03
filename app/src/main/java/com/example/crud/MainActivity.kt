package com.example.crud

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var adapter: NewsAdapter
    private var newsList = mutableListOf<News>()
//    private val db = Firebase.firestore
    private val db by lazy { Firebase.firestore }
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)
        mAuth = FirebaseAuth.getInstance()

        recyclerView = findViewById(R.id.rcvNews)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fab = findViewById(R.id.floatAddNews)
        fab.setOnClickListener {
            startActivity(Intent(this, AddEditNewsActivity::class.java))
        }
        fetchNews()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate (R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_logout) {
            mAuth. signOut()
            Toast.makeText(this@MainActivity, "Logged out successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@MainActivity, DefaultActivity::class.java)
            startActivity(intent)
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun fetchNews() {
        db.collection("news")
            .get()
            .addOnSuccessListener { result ->
                newsList.clear()
                for (document in result) {
                    val news = document.toObject(News::class.java)
                    news.id = document.id
                    newsList.add(news)
                }

                adapter = NewsAdapter(newsList,
                    onItemClick = { selectedNews ->
                        val options = arrayOf("Lihat", "Edit")
                        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
                        builder.setTitle("Pilih Aksi")
                        builder.setItems(options) { _, which ->
                            when (which) {
                                0 -> { // Lihat
                                    val intent = Intent(this, DetailNewsActivity::class.java)
                                    intent.putExtra("news", selectedNews)
                                    startActivity(intent)
                                }
                                1 -> { // Edit
                                    val intent = Intent(this, AddEditNewsActivity::class.java)
                                    intent.putExtra("news", selectedNews)
                                    startActivity(intent)
                                }
                            }
                        }
                        builder.show()
                    },
                    onDeleteClick = { selectedNews ->
                        selectedNews.id?.let { docId ->
                            db.collection("news").document(docId)
                                .delete()
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Berhasil dihapus", Toast.LENGTH_SHORT).show()
                                    fetchNews()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(this, "Gagal menghapus", Toast.LENGTH_SHORT).show()
                                }
                        }
                    }
                )

                adapter.notifyDataSetChanged()

                recyclerView.adapter = adapter
            }
            .addOnFailureListener { exception ->
                Log.w("read check", "Error getting documents.", exception)
            }
    }

    override fun onResume() {
        super.onResume()
        fetchNews()
    }

    override fun onStart(){
        super.onStart()
        fetchNews()
    }
}