package com.morladim.luigi

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.morladim.mario.ipc.ITalkAidlInterface
import com.morladim.mario.ipc.ITalkCallbackAidlInterface

class MainActivity : AppCompatActivity() {

    lateinit var talkAidlInterface: ITalkAidlInterface
    lateinit var serviceConnection: ServiceConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent("com.morladim.mario.AidlService")
        intent.`package` = "com.morladim.mario"
        serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                talkAidlInterface = ITalkAidlInterface.Stub.asInterface(service)
                println(talkAidlInterface.message)
                talkAidlInterface.registerCallback(callback)
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                talkAidlInterface.unRegisterCallback(callback)
                println("unregister")
            }

        }
        bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE)
    }

    val callback = object : ITalkCallbackAidlInterface.Stub() {

        override fun onMessage(s: String?) {
            println(s)
            talkAidlInterface.tellServer("dddd")
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConnection)
    }
}