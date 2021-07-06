package com.example.projet_tdm_medcin.ui.bookings


import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager
import com.budiyev.android.codescanner.CodeScanner
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.projet_tdm.retrofit.RetrofitService
import com.example.projet_tdm.url
import com.example.projet_tdm_medcin.R
import com.example.projet_tdm_medcin.entity.Booking
import com.example.projet_tdm_medcin.ui.adapters.BookingAdapter
import kotlinx.android.synthetic.main.activity_agenda_bookings.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    var bookingAdapter : BookingAdapter = BookingAdapter(this)

    private lateinit var codeScanner: CodeScanner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agenda_bookings)




        val pref = applicationContext.getSharedPreferences("myPrefs", MODE_PRIVATE)
        var nomDocteur=pref.getString("nameDoctor", "")
        var lastNameDocteur=pref.getString("lastNameDoctor", "")
        var imageDoctor=pref.getString("img", "")
        val id = pref.getInt("idDoctor", 1)

        nom.setText("Salut "+nomDocteur+" "+lastNameDocteur)

        Glide.with(this).load(url +imageDoctor)
            .apply(
                RequestOptions().placeholder(R.drawable.placeholder
                ))
            .into(doctorImage)


        val sdf = SimpleDateFormat("yyyy-M-d")
        var curDate: String=sdf.format(Date())
        getBookingTimes(1, curDate)

        calendarBookings.setOnDateChangeListener(CalendarView.OnDateChangeListener { view, year, month, dayOfMonth ->
            curDate = year.toString() + "-" + (month+1).toString() + "-" + dayOfMonth.toString()
            Toast.makeText(this, curDate, Toast.LENGTH_SHORT).show()
            getBookingTimes(id, curDate)
        })
        bookingsRecyclerViewAgenda.adapter = bookingAdapter
        bookingsRecyclerViewAgenda.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        floatingActionScan.setOnClickListener(){
            val intent = Intent(this, ScanActivity::class.java)
            //intent.putExtra("Dr",doctor)
            this.startActivity(intent)
        }


    }

    fun getBookingTimes(idDoctor: Int, bookingDate: String){
        val call = RetrofitService.endpoint.getAllBookingDoctorByDate(idDoctor, bookingDate)
        call.enqueue(object : Callback<List<Booking>> {

            override fun onResponse(call: Call<List<Booking>>, response: Response<List<Booking>>) {
                if(response.isSuccessful){
                    val list = response.body()
                    if(list!=null){
                        bookingAdapter.setBookings(list)
                        Toast.makeText(this@MainActivity, list.size.toString(), Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this@MainActivity, "Une erreur response is not successful!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Booking>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Une erreur s'est produite onFailure!", Toast.LENGTH_SHORT).show()
            }
        })
    }






}