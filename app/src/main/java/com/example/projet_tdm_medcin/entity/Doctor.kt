package com.example.projet_tdm_medcin.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "doctors")
data class Doctor(var idDoctor: Int,
                  var nameDoctor:String,
                  var lastNameDoctor:String,
                  var phoneDoctor: String,
                  var specialityId: Int,
                  var imageDoctor:String,
                  var latDoctor: Float,
                  var lngDoctor: Float,
                  var expDoctor : Int,
                  var fbDoctor : String,
                  var speciality : String,
                  var biographieDoctor : String) :Serializable {
                      @PrimaryKey(autoGenerate = true)
                      @ColumnInfo(name = "doctor_id")
                      var idDoc: Int=0
                  }