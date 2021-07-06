package com.example.projet_tdm_medcin.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projet_tdm_medcin.ui.bookings.DetailBookingActivity
import com.example.projet_tdm_medcin.R
import com.example.projet_tdm_medcin.entity.Booking

class BookingAdapter(val context: Context): RecyclerView.Adapter<BookingAdapter.BookingViewHolder>() {

    private val bookings = mutableListOf<Booking>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.booking_item_layout,
            parent, false)
        return BookingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val booking = bookings[position]
        holder.date.text = booking.bookingDate
        holder.heure.text = booking.bookingTime
        holder.patient.text = booking.namePatient

        holder.itemView.setOnClickListener(){
            val intent = Intent(context, DetailBookingActivity::class.java)
            intent.putExtra("booking",bookings[position])
            context.startActivity(intent)
        }
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
        val patient: TextView = view.findViewById(R.id.viewPatientName)

    }
}