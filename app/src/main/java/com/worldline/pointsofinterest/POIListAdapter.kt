package com.worldline.pointsofinterest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.worldline.pointsofinterest.model.PointOfInterest

internal class POIListAdapter (
    private val pointsOfInterest: MutableList<PointOfInterest>,
    private val itemClick: (PointOfInterest) -> Unit
) : RecyclerView.Adapter<POIListAdapter.ItemViewHolder>() {

    fun updatePOIs(pointsOfInterest: List<PointOfInterest>) {
        this.pointsOfInterest.apply {
            clear()
            addAll(pointsOfInterest)
        }
        this.notifyDataSetChanged()
    }

    inner class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val textView: TextView = view.findViewById(R.id.poi_title)

        fun bind(item: PointOfInterest) {
            textView.text = item.title

            itemView.setOnClickListener {
                itemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.poi_list_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = pointsOfInterest[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return pointsOfInterest.size
    }
}