package com.example.projet_tdm_medcin

import android.app.Application
//import com.example.projet_tdm.roomdao.RoomService

class App:Application(){
    override fun onCreate() {
        super.onCreate()
        //RoomService.context = applicationContext
    }
}