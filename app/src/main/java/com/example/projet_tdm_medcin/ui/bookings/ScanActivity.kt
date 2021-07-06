package com.example.projet_tdm_medcin.ui.bookings

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.projet_tdm.retrofit.RetrofitService
import com.example.projet_tdm_medcin.R
import com.example.projet_tdm_medcin.entity.Booking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScanActivity : AppCompatActivity() {

    private lateinit var codeScanner: CodeScanner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA )==PackageManager.PERMISSION_DENIED){
           ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.CAMERA),123)
       }
       else{
           scan()
       }
    }

    private fun scan(){
        val codeScannerView: CodeScannerView =findViewById<CodeScannerView>(R.id.scanner_view)
        codeScanner= CodeScanner(this,codeScannerView)
        codeScanner.camera= CodeScanner.CAMERA_BACK
        codeScanner.formats= CodeScanner.ALL_FORMATS

        codeScanner.autoFocusMode= AutoFocusMode.SAFE
        codeScanner.scanMode= ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled=true
        codeScanner.isFlashEnabled=false

        codeScanner.decodeCallback= DecodeCallback {
            runOnUiThread{
                Toast.makeText(this,"Scan result : "+it.text, Toast.LENGTH_LONG).show()
                getBookingByCodeQR(it.text)

            }
        }
        codeScanner.errorCallback= ErrorCallback {
            runOnUiThread {
                Toast.makeText(this,"Camera Error : "+it.message, Toast.LENGTH_LONG).show()

            }
        }

        codeScannerView.setOnClickListener(){
            codeScanner.startPreview()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==123){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "PERMISSION_GRANTED", Toast.LENGTH_LONG).show()
                scan()
            }
            else{
                Toast.makeText(this, "PERMISSION_DENIED", Toast.LENGTH_LONG).show()

            }
        }
    }

    override fun onResume() {
        super.onResume()
        if(::codeScanner.isInitialized){
            codeScanner.startPreview()
        }
    }

    override fun onPause() {
        if(::codeScanner.isInitialized){
            codeScanner.releaseResources()
        }
        super.onPause()
    }

    fun getBookingByCodeQR(codeQR: String){
        val call = RetrofitService.endpoint.getBookingByCodeQR(codeQR)
        call.enqueue(object : Callback<Booking> {
            override fun onResponse(call: Call<Booking>, response: Response<Booking>) {
                if(response.isSuccessful){
                    val booking = response.body()
                    if(booking!=null){
                        Toast.makeText(this@ScanActivity, "success", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@ScanActivity, DetailBookingActivity::class.java)
                        intent.putExtra("booking",booking)
                        startActivity(intent)
                    }
                }else{
                    Toast.makeText(this@ScanActivity, "Une erreur response is not successful!", Toast.LENGTH_SHORT).show()
                }            }

            override fun onFailure(call: Call<Booking>, t: Throwable) {
                Toast.makeText(this@ScanActivity, "Une erreur onFailure!", Toast.LENGTH_SHORT).show()
            }

        })
    }


}