package com.yazan98.wintrop.client.adapters

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.yazan98.wintrop.client.R
import com.yazan98.wintrop.client.utils.Utils
import com.yazan98.wintrop.client.utils.Utils.colors
import com.yazan98.wintrop.data.models.Weather
import io.vortex.android.utils.random.VortexBaseAdapter
import kotlinx.android.synthetic.main.row_day.view.*
import javax.inject.Inject

class DaysAdapter @Inject constructor(private val data: List<Weather>) : VortexBaseAdapter<DaysAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getLayoutRes(): Int {
        return R.layout.row_day
    }

    override fun getViewHolder(view: View): ViewHolder {
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item?.let {
            it.setBackgroundResource(colors[position.rem(colors.size - 1)])
        }

        data[position].date.let { result ->
            holder.date?.let {
                it.text = result
            }
        }

        data[position].hours[0].description[0].value.let {
            println("THe Text : ${it}")
            holder.image?.setImageResource(Utils.getImageByStatus(it))
        }

        data[position].hours[0].tempF.let { result ->
            holder.temp?.let {
                it.text = "${result}${context.getString(R.string.present)}"
            }
        }

        data[position].minF.let { result ->
            holder.down?.let {
                it.text = result
            }
        }

        data[position].maxF.let { result ->
            holder.up?.let {
                it.text = result
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val date: TextView? = view.Date
        val image: ImageView? = view.DayImage
        val temp: TextView? = view.TempItem
        val up: TextView? = view.CardUp
        val down: TextView? = view.CardDown
        val item: ConstraintLayout? = view.CardViewItem
    }

}
