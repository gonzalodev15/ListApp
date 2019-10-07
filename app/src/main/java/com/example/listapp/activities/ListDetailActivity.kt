package com.example.listapp.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listapp.R
import com.example.listapp.adapters.StepAdapter
import com.example.listapp.models.TaskList
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_list_detail.*

class ListDetailActivity : AppCompatActivity() {

    lateinit var list: TaskList
    lateinit var stepItemsRecyclerView: RecyclerView
    lateinit var stepAdapter: StepAdapter
    lateinit var addStep: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_detail)
        addStep = findViewById<FloatingActionButton>(R.id.addStepButton)
        list = intent.getSerializableExtra(MainActivity.INTENT_LIST_KEY) as TaskList
        title = list.name
        stepAdapter = StepAdapter(list)
        stepItemsRecyclerView = findViewById<RecyclerView>(R.id.stepsRecyclerView)
        stepItemsRecyclerView.apply {
            adapter = stepAdapter
            layoutManager = GridLayoutManager(context, 1)
        }
        addStep.setOnClickListener {
            showCreateDialog()
        }
    }


    private fun showCreateDialog(){
        val stepEditText = EditText(this)

        stepEditText.inputType = InputType.TYPE_CLASS_TEXT
        AlertDialog.Builder(this).setTitle(R.string.step_to_add)
            .setView(stepEditText)
            .setPositiveButton(R.string.add_step) { dialog, _ ->
                val step = stepEditText.text.toString()
                list.tasks.add(step)
                val recyclerAdapter = stepItemsRecyclerView.adapter as StepAdapter
                recyclerAdapter.notifyItemInserted(list.tasks.size)
                dialog.dismiss()
            }.create().show()
    }

    override fun onBackPressed() {
        val bundle = Bundle()
        bundle.putSerializable(MainActivity.INTENT_LIST_KEY, list)
        val intent = Intent()
        intent.putExtras(bundle)
        setResult(Activity.RESULT_OK, intent)
        super.onBackPressed()
    }
}
