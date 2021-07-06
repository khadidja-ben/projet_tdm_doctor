package com.example.projet_tdm_medcin.entity

import java.io.Serializable
import java.sql.Time
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Booking")
data class Booking(var idBooking: Int,
                   var bookingTime:String,
                   var bookingDate: String,
                   var idDoctor:Int,
                   var idTreatment:Int,
                   var idPatient:Int,
                   var namePatient:String,
                   var lastNamePatient:String,
                   var codeQR: String,
                   var nameDoctor: String,
                   var lastNameDoctor:String,
                   var phoneDoctor: String,
                   var specialityId: Int,
                   var imageDoctor:String,
                   var latDoctor: Float,
                   var lngDoctor: Float,
                   var expDoctor : Int,
                   var fbDoctor : String,
                   var biographieDoctor : String
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idBooking")
    var idBook: Int?=null
}