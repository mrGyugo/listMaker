package com.zebka.listmaker

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText

import kotlinx.android.synthetic.main.activity_list.*

class MainActivity : AppCompatActivity(), ListSelectionRecyclerViewAdapter.ListSelectionRecyclerViewClickListener {

    companion object {
        const val INTENT_LIST_KEY = "list"
    }

    private lateinit var listsRecyclerView: RecyclerView
    private val listDataManager: ListDataManager = ListDataManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val lists = listDataManager.readList()
        listsRecyclerView = findViewById(R.id.list_recyclerview)
        listsRecyclerView.layoutManager = LinearLayoutManager(this)

        listsRecyclerView.adapter = ListSelectionRecyclerViewAdapter(lists, this)


        fab.setOnClickListener { _ ->
            showCreateListDialog()
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
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun listItemClicked(list: TaskList) {
        showListDetail(list)
    }


    private fun showCreateListDialog() {

        val dialogTitle = getString(R.string.name_of_list)
        val positiveButtonTitle = getString(R.string.create_list)


        val builder = AlertDialog.Builder(this)
        val listTitleEditText = EditText(this)
        listTitleEditText.inputType = InputType.TYPE_CLASS_TEXT

        builder.setTitle(dialogTitle)
        builder.setView(listTitleEditText)

        builder.setPositiveButton(positiveButtonTitle) { dialog, _ ->
            val list = TaskList(listTitleEditText.text.toString())
            listDataManager.saveList(list)
            val recyclerAdapter = listsRecyclerView.adapter as ListSelectionRecyclerViewAdapter
            recyclerAdapter.addList(list)
            dialog.dismiss()
            showListDetail(list)
        }
        builder.create().show()
    }

    private fun showListDetail(list: TaskList) {
        val listDetailIntent = Intent(this, ListDetailActivity::class.java)
        listDetailIntent.putExtra(INTENT_LIST_KEY, list)

        startActivity(listDetailIntent)
    }



}
