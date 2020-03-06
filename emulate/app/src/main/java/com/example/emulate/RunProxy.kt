package com.example.emulate

import android.app.Application
import Proxy

class RunProxy: Application() {

    var proxy: Proxy? = null
    fun startProxy() {
        Thread(Runnable { proxy = Proxy("129.65.128.80", 5018) }).start()
    }


}