package com.zebka.listmaker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class ListDetailActivity : AppCompatActivity() {

    lateinit var list: TaskList
    lateinit var listItemsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_detail)


        list = intent.getParcelableExtra(MainActivity.INTENT_LIST_KEY)

        title = list.name


        listItemsRecyclerView = findViewById(R.id.list_items_reyclerview)

        listItemsRecyclerView.adapter = ListItemsRecyclerViewAdapter(list)

        listItemsRecyclerView.layoutManager = LinearLayoutManager(this)

    }
}
