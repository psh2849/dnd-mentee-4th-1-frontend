package com.example.myapplication.navigation.upload

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import kotlinx.android.synthetic.main.filter_list_item.view.*

class UploadFilterAdapter(
    val filterList: ArrayList<RecipeDTO.Themes>,
    val numberList : ArrayList<String>,
    val saveList: ArrayList<String>
) :
    RecyclerView.Adapter<UploadFilterAdapter.UploadFilterHolder>() {
    var count = 0
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UploadFilterAdapter.UploadFilterHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.filter_list_item, parent, false)
        return UploadFilterAdapter.UploadFilterHolder(view)
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: UploadFilterAdapter.UploadFilterHolder, position: Int) {
        holder.name.text = filterList[position].name

        holder.itemView.setOnClickListener {
            if (!saveList.contains(filterList[position].name)) {
                saveList.add(filterList[position].name.toString())
                numberList.add(filterList[position].id.toString())
                holder.name.setTextColor(Color.parseColor("#FF7051"))
                holder.name.setBackgroundResource(R.drawable.select_border_layout)
            } else {
                saveList.remove(filterList[position].name)
                numberList.remove(filterList[position].id)
                holder.name.setTextColor(Color.parseColor("#777777"))
                holder.name.setBackgroundResource(R.drawable.no_select_border_layout)
            }

        }
    }

    class UploadFilterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tv_filter_name)
    }
}