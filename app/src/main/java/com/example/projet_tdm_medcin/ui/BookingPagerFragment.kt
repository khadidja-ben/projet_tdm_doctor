package com.example.projet_tdm_medcin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projet_tdm_medcin.R
import com.example.projet_tdm_medcin.entity.Booking

class BookingPagerFragment: Fragment() {

    private lateinit var bookingRecyclerView: RecyclerView
    private val bookingAdapter = BookingAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.bookings_pager_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            //val textView: TextView = view.findViewById(android.R.id.text1)
            //textView.text = getInt(ARG_OBJECT).toString()
        //}
        bookingRecyclerView = view.findViewById(R.id.recyclerBookings)
        bookingRecyclerView.adapter = bookingAdapter
        bookingRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    fun updateTasks(tasks: List<Booking>) {
        bookingAdapter.setBookings(tasks)
    }


}