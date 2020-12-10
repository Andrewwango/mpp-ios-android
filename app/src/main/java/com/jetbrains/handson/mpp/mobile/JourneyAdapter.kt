package com.jetbrains.handson.mpp.mobile
import android.text.Layout
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.journey_item.view.*

class JourneyAdapter(val journeysList: Array<String>) : RecyclerView.Adapter<JourneyAdapter.JourneyViewHolder>() {

    class JourneyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val journeyTextView : TextView = itemView.journey_text
        fun bind(word:String) {
            journeyTextView.text = word
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JourneyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.journey_item, parent, false)
        return JourneyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return journeysList.size
    }

    override fun onBindViewHolder(holder: JourneyViewHolder, position: Int) {
        holder.bind(journeysList[position])
    }
}