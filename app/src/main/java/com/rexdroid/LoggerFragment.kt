package com.rexdroid

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rexdroid.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_logger.*
import kotlinx.android.synthetic.main.layout_logger_item.view.*

/**
 * Created by abhishek on 4/9/17.
 */

class LoggerFragment : BaseFragment() {

    private lateinit var adapter: LoggerRvAdapter

    companion object {
        fun getInstance() = LoggerFragment()
    }

    override fun getDefaultLayout(inflater: LayoutInflater?, container: ViewGroup?)
            = inflater?.inflate(R.layout.fragment_logger, container, false)

    override fun extractArguments(extras: Bundle) {
    }

    override fun init() {
        adapter = LoggerRvAdapter(context, mutableListOf())
        rv_logger.layoutManager = LinearLayoutManager(activity)
        rv_logger.adapter = adapter
    }

    class LoggerRvAdapter(private val context: Context, private val logs: MutableList<String>)
        : RecyclerView.Adapter<LoggerVH>() {

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int)
                = LoggerVH(LayoutInflater.from(context).inflate(R.layout.layout_logger_item, parent, false))

        override fun getItemCount() = logs.size

        override fun onBindViewHolder(holder: LoggerVH, position: Int) {
            holder.itemView.tv.text = logs[position]
        }

        fun addLogs(log: String) {
            logs.add(log)
            notifyItemInserted(logs.size - 1)
        }
    }

    class LoggerVH(rootView: View) : RecyclerView.ViewHolder(rootView)

    fun addLog(log: String) = adapter.addLogs(log)
}