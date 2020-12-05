package com.example.youtubechannel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.youtubechannel.handlers.ChannelHandler
import com.example.youtubechannel.models.Channel

class EditActivity : AppCompatActivity() {
    lateinit var nameEditEt: EditText
    lateinit var linkEditEt: EditText
    lateinit var rankEditEt: EditText
    lateinit var reasonEditEt: EditText
    lateinit var updateBtn: Button
    lateinit var channelHandler: ChannelHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        nameEditEt = findViewById(R.id.nameEditEt)
        linkEditEt = findViewById(R.id.linkEditEt)
        rankEditEt = findViewById(R.id.rankEditEt)
        reasonEditEt = findViewById(R.id.reasonEditEt)
        updateBtn = findViewById(R.id.updateBtn)
        channelHandler = ChannelHandler()

        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("name")
        val link = intent.getStringExtra("link")
        val rankNum = intent.getStringExtra("rank")
        val reason = intent.getStringExtra("reason")

        nameEditEt.setText(name)
        linkEditEt.setText(link)
        rankEditEt.setText(rankNum)
        reasonEditEt.setText(reason)

        updateBtn.setOnClickListener{
            val name = nameEditEt.text.toString()
            val link = linkEditEt.text.toString()
            val rank = rankEditEt.text.toString().toInt()
            val reason = reasonEditEt.text.toString()

            val channel = Channel(id = id, name = name, link = link, rank = rank, reason = reason)

            if(channelHandler.update(channel)) {
                Toast.makeText(applicationContext, "Channel updated", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, ListActivity::class.java))
            }


        }

    }
}