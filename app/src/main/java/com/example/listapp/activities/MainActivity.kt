package com.example.listapp.activities

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listapp.R
import com.example.listapp.SharedPreferences.ListDataManager
import com.example.listapp.adapters.TaskSelectionRecyclerViewAdapter
import com.example.listapp.models.TaskList

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), TaskSelectionRecyclerViewAdapter.TaskSelectionRecyclerViewListener{

    private lateinit var  tasksAdapter: TaskSelectionRecyclerViewAdapter
    val listDataManager : ListDataManager = ListDataManager(this)

    companion object{
        val INTENT_LIST_KEY = "list"
        val LIST_DETAIL_REQUEST_CODE = 12
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val list = listDataManager.readLists()
        tasksAdapter = TaskSelectionRecyclerViewAdapter(list, this)
        tasksRecyclerView.apply {
            adapter = tasksAdapter
            layoutManager = GridLayoutManager(context, 1)
        }

        fab.setOnClickListener { view ->
            showCreateTaskDialog()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when(item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showCreateTaskDialog(){
        val dialogTitle = getString(R.string.name_of_list)
        val positiveButtonTitle = getString(R.string.create_list)

        val builder = AlertDialog.Builder(this)
        val listTitleEditText = EditText(this)
        listTitleEditText.inputType = InputType.TYPE_CLASS_TEXT
        builder.setTitle(dialogTitle)
        builder.setView(listTitleEditText)
        builder.setPositiveButton(positiveButtonTitle) { dialog, i ->
            val list = TaskList(listTitleEditText.text.toString())
            listDataManager.saveList(list)
            tasksAdapter.addList(list)
            dialog.dismiss()
            showListDetail(list)
        }
        builder.create().show()

    }

    private fun showListDetail(list: TaskList){
        var listDetailIntent = Intent(this, ListDetailActivity::class.java)
        listDetailIntent.putExtra(INTENT_LIST_KEY, list)
        startActivityForResult(listDetailIntent, LIST_DETAIL_REQUEST_CODE)
    }

    override fun listItemClicked(list: TaskList) {
        showListDetail(list)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == LIST_DETAIL_REQUEST_CODE){
            data?.let {
                listDataManager.saveList(data.getSerializableExtra(INTENT_LIST_KEY) as TaskList)
                updateLists()
            }
        }
    }

    private fun updateLists() {
        var lists = listDataManager.readLists()
        tasksAdapter = TaskSelectionRecyclerViewAdapter(lists, this)
        tasksRecyclerView.apply {
            adapter = tasksAdapter
        }
    }
}
