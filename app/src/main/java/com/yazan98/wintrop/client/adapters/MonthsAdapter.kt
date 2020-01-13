package com.yazan98.wintrop.client.adapters

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.yazan98.wintrop.client.R
import com.yazan98.wintrop.client.utils.Utils
import com.yazan98.wintrop.data.models.MonthResponse
import io.vortex.android.utils.random.VortexBaseAdapter
import kotlinx.android.synthetic.main.row_month.view.*
import javax.inject.Inject

class MonthsAdapter @Inject constructor(private val data: List<MonthResponse>) : VortexBaseAdapter<MonthsAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getLayoutRes(): Int {
        return R.layout.row_month
    }

    override fun getViewHolder(view: View): ViewHolder {
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item?.let {
            it.setBackgroundResource(Utils.colors[position.rem(Utils.colors.size - 1)])
        }

        data[position].name.let { result ->
            holder.name?.let {
                it.text = result
            }
        }

        data[position].mavTemp.let { result ->
            holder.up?.let {
                it.text = result
            }
        }

        data[position].minTemp.let { result ->
            holder.down?.let {
                it.text = result
            }
        }
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name: TextView? = view.MonthName
        val up: TextView? = view.CardUp
        val down: TextView? = view.CardDown
        val item: ConstraintLayout? = view.Item
    }

}