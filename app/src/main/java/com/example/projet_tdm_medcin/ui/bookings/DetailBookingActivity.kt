package com.example.projet_tdm_medcin.ui.bookings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projet_tdm_medcin.R
import com.example.projet_tdm_medcin.entity.Booking
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.activity_detail_booking.*


class DetailBookingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_booking)

        val booking= intent.getSerializableExtra("booking") as Booking

        heureRdv.setText(booking.bookingTime)
        dateRdv.setText(booking.bookingDate)
        rdvText.setText("Vous avez un rendez-vous avec  "+booking.lastNamePatient+" "+booking.namePatient)

        val multiFormatWriter = MultiFormatWriter()
        try {
            val bitMatrix = multiFormatWriter.encode(
                booking.codeQR!!.toString(),
                BarcodeFormat.QR_CODE,
                500,
                500
            )
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.createBitmap(bitMatrix)
            qrCode!!.setImageBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
