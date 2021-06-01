package com.orbital.sonic.recyclerviewsharedprefskotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class RecyclerAdapter(recyclerList: ArrayList<RecyclerViewItem>) :
    RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>() {

    private var mRecyclerList: ArrayList<RecyclerViewItem> = recyclerList
    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onDeleteClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return RecyclerViewHolder(v, mListener)
    }

    override fun getItemCount(): Int {
        return mRecyclerList.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val currentItem: RecyclerViewItem = mRecyclerList[position]
        holder.tvLine1.text = currentItem.mLine1
        holder.tvLine2.text = currentItem.mLine2

    }

    class RecyclerViewHolder(itemView: View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        var tvLine1: TextView = itemView.findViewById(R.id.textview_line1)
        var tvLine2: TextView = itemView.findViewById(R.id.textview_line2)
        var mDeleteImage: ImageView = itemView.findViewById(R.id.image_delete)

        init {

            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position)
                }
            }

            mDeleteImage.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onDeleteClick(position)
                }
            }
        }

    }
}

