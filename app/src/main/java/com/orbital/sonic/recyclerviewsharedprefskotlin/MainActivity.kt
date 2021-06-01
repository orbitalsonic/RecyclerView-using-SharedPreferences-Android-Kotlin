package com.orbital.sonic.recyclerviewsharedprefskotlin

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mRecyclerList: ArrayList<RecyclerViewItem>
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private lateinit var mAdapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadData()
        buildRecyclerView()

        val buttonSave = findViewById<Button>(R.id.button_save)
        buttonSave.setOnClickListener {
            saveData()
        }

        val buttonInsert = findViewById<Button>(R.id.button_insert)
        buttonInsert.setOnClickListener {
            val line1 = findViewById<EditText>(R.id.edittext_line_1)
            val line2 = findViewById<EditText>(R.id.edittext_line_2)
            if (line1.text.toString().isNotEmpty() && line2.text.toString().isNotEmpty())
            insertItem(line1.text.toString(), line2.text.toString())
        }
    }

    private fun buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerview)
        mLayoutManager = LinearLayoutManager(this)
        mAdapter = RecyclerAdapter(mRecyclerList)
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mAdapter


        mAdapter.setOnItemClickListener(object : RecyclerAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                Toast.makeText(
                    this@MainActivity,
                    getRecyclerItem(position).mLine1,
                    Toast.LENGTH_SHORT
                ).show()

            }

            override fun onDeleteClick(position: Int) {
                removeRecyclerItem(position)
            }
        })
    }

    private fun saveData() {
        val sharedPreferences = getSharedPreferences("shared preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(mRecyclerList)
        editor.putString("recycler list", json)
        editor.apply()
    }

    private fun loadData() {
        mRecyclerList = ArrayList()
        val sharedPreferences = getSharedPreferences("shared preferences", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("recycler list", null)
        val type = object : TypeToken<ArrayList<RecyclerViewItem>>() {}.type

        if (json != null) {
            mRecyclerList = gson.fromJson(json, type)
        }

    }

    private fun insertItem(line1: String, line2: String) {
        mRecyclerList.add(RecyclerViewItem(line1, line2))
        mAdapter.notifyItemInserted(mRecyclerList.size)
    }

    fun removeRecyclerItem(mPosition: Int){
        mRecyclerList.removeAt(mPosition)
        mAdapter.notifyItemRemoved(mPosition)
    }

    fun getRecyclerItem(mPosition: Int): RecyclerViewItem {
        return mRecyclerList[mPosition]
    }

}