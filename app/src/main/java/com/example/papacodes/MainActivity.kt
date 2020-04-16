package com.example.papacodes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.papacodes.common.navigation.NavigatorImpl
import org.koin.android.ext.android.getKoin

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getKoin().declare(NavigatorImpl(findNavController(R.id.nav_host_fragment), this))
    }
}
