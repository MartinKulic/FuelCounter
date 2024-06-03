package com.example.vapmzsem

import android.app.Application
import com.example.vapmzsem.data.AppContainer
import com.example.vapmzsem.data.AppDataContainer
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date

class MyApplication : Application(){
    lateinit var container : AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}