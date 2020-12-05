package com.example.youtubechannel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.myfavouriteyoutubechannels.handlers.ChannelHandler
import com.example.myfavouriteyoutubechannels.models.Channel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    lateinit var nameEt: EditText
    lateinit var linkEt: EditText
    lateinit var rankEt: EditText
    lateinit var reasonEt: EditText
    lateinit var addBtn: Button
    lateinit var channelHandler: ChannelHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameEt = findViewById(R.id.nameEt)
        linkEt = findViewById(R.id.linkEt)
        rankEt = findViewById(R.id.rankEt)
        reasonEt = findViewById(R.id.reasonEt)
        addBtn = findViewById(R.id.addBtn)
        channelHandler = ChannelHandler()

        addBtn.setOnClickListener{
            val name = nameEt.text.toString()
            val link = linkEt.text.toString()
            val rank = rankEt.text.toString().toInt()
            val reason = reasonEt.text.toString()

            val channel = Channel(name = name, link = link, rank = rank, reason = reason)

            if(channelHandler.create(channel)) {
                Toast.makeText(applicationContext, "Channel added", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, ListActivity::class.java))
            }
        }
    }


}