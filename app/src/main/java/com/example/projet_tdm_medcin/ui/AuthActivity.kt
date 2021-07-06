package com.example.projet_tdm_medcin.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.projet_tdm.retrofit.RetrofitService
import com.example.projet_tdm_medcin.R
import com.example.projet_tdm_medcin.entity.Doctor
import com.example.projet_tdm_medcin.ui.bookings.MainActivity
import kotlinx.android.synthetic.main.activity_auth.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        login_button.setOnClickListener(){
            val phone = phone.text.toString()
            val password = password.text.toString()
            login(phone, password)
        }
    }

    fun login(phone: String, password: String){

        val call = RetrofitService.endpoint.authentification(phone, password)
        call.enqueue(object : Callback<Doctor> {

            override fun onResponse(call: Call<Doctor>, response: Response<Doctor>) {
                val res = response.body()
                if (response.isSuccessful){
                    if (res!= null){
                        Toast.makeText(this@AuthActivity, "Welcome !", Toast.LENGTH_SHORT).show()

                        val pref = applicationContext.getSharedPreferences("myPrefs", MODE_PRIVATE)
                        val editor = pref.edit()
                        editor.putBoolean("connected", true)
                        editor.putInt("idDoctor", res.idDoctor)
                        editor.putString("nameDoctor", res.nameDoctor)
                        editor.putString("lastNameDoctor", res.lastNameDoctor)
                        editor.putString("img", res.imageDoctor)

                        editor.commit()
                        val mainActivity = Intent(applicationContext, MainActivity::class.java)
                        startActivity(mainActivity)
                        finish()
                    }
                }
                else{
                    Toast.makeText(this@AuthActivity, "Echec !!!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Doctor>, t: Throwable) {
                Toast.makeText(this@AuthActivity, "Echec onFailure !", Toast.LENGTH_SHORT).show()
            }
        })
    }


}