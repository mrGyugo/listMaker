package com.zebka.listmaker

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ListSelectionFragment : Fragment(), ListSelectionRecyclerViewAdapter.ListSelectionRecyclerViewClickListener {

    private var listener: OnListItemFragmentInteractionListener? = null
    lateinit var listDataManager: ListDataManager
    lateinit var listsRecyclerView: RecyclerView

    interface OnListItemFragmentInteractionListener {
        fun onListItemClicked(list: TaskList)
    }



    companion object {
        fun newInstance(): ListSelectionFragment {
            val fragment = ListSelectionFragment()
            return  fragment
        }
    }

    override fun listItemClicked(list: TaskList) {
        listener?.onListItemClicked(list)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListItemFragmentInteractionListener) {
            listener = context
            listDataManager = ListDataManager(context)
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val lists = listDataManager.readList()
        view?.let {
            listsRecyclerView = it.findViewById(R.id.list_recyclerview)
            listsRecyclerView.layoutManager = LinearLayoutManager(activity)
            listsRecyclerView.adapter = ListSelectionRecyclerViewAdapter(lists, this)
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    fun addList(list: TaskList) {
        listDataManager.saveList(list)

        val recyclerAdapter = listsRecyclerView.adapter as ListSelectionRecyclerViewAdapter
        recyclerAdapter.addList(list)
    }

    fun saveList(list: TaskList) {
        listDataManager.saveList(list)
        updateLists()
    }
    private fun updateLists() {
        val lists = listDataManager.readList()
        listsRecyclerView.adapter = ListSelectionRecyclerViewAdapter(lists,
            this)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_selection, container, false)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }




}
