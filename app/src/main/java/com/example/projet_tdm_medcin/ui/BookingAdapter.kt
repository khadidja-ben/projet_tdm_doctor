package com.example.projet_tdm_medcin.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projet_tdm_medcin.R
import com.example.projet_tdm_medcin.entity.Booking

class BookingAdapter: RecyclerView.Adapter<BookingAdapter.BookingViewHolder>() {

    private val bookings = mutableListOf<Booking>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.booking_item_layout,
            parent, false)
        return BookingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val booking = bookings[position]
        holder.date.text = booking.bookingDate.toString()
        holder.heure.text = booking.bookingTime.toString()
        holder.medecin.text = booking.idDoctor.toString()
    }

    override fun getItemCount(): Int {
        return bookings.size
    }

    fun setBookings(list: List<Booking>){
        bookings.clear()
        bookings.addAll(list)
        notifyDataSetChanged()
    }

    class BookingViewHolder(view: View): RecyclerView.ViewHolder(view){

        val date: TextView = view.findViewById(R.id.viewDate)
        val heure: TextView = view.findViewById(R.id.viewTime)
        val medecin: TextView = view.findViewById(R.id.viewDoctorName)

    }
}