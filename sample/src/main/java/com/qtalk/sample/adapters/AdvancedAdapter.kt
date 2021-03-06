package com.qtalk.sample.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.qtalk.recyclerviewfastscroller.RecyclerViewFastScroller
import com.qtalk.sample.R
import com.qtalk.sample.data.Country
import kotlinx.android.synthetic.main.recycler_view_list_item.view.*

class AdvancedAdapter(private val mContext : Context?, private val countriesList : List<Country>, private val handleDrawable : Drawable?) : RecyclerView.Adapter<AdvancedAdapter.ViewHolder>()
        , RecyclerViewFastScroller.OnPopupViewUpdate {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mTextView?.text = countriesList[position].countryName
    }

    override fun getItemCount() = countriesList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        return ViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.recycler_view_list_item, parent, false))
    }

    override fun onUpdate(position: Int, popupTextView: TextView) {
        //setting colorfilter using HSV hue to get a gradient effect on the PopupTextView and Hanlde background
        popupTextView.background.colorFilter = PorterDuffColorFilter(getColor(power = position),
                PorterDuff.Mode.SRC_IN)
        handleDrawable?.colorFilter = PorterDuffColorFilter(getColor( power = position),
                PorterDuff.Mode.SRC_IN)

        popupTextView.text = countriesList[position].population.withSuffix()
    }

    // answer courtesy: https://stackoverflow.com/questions/9769554/how-to-convert-number-into-k-thousands-m-million-and-b-billion-suffix-in-jsp
    private fun Long.withSuffix(): String {
        if (this < 1000) return "" + this
        val exp = (Math.log(this.toDouble()) / Math.log(1000.0)).toInt()
        return String.format("%.1f%c",
                this / Math.pow(1000.0, exp.toDouble()),
                "kMBTPE"[exp - 1])
    }
    private fun getColor(power: Int): Int {
        val h = (itemCount - power) * 100/itemCount
        val s = 1 // Saturation
        val v = 0.8 // Value
        return Color.HSVToColor(floatArrayOf(h.toFloat(), s.toFloat(), v.toFloat()))
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val mTextView: TextView? = view.text_view
    }
}