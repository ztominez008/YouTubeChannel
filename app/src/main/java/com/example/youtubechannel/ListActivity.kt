package com.example.youtubechannel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.youtubechannel.handlers.ChannelHandler
import com.example.youtubechannel.models.Channel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ListActivity : AppCompatActivity() {
    lateinit var channelHandler: ChannelHandler
    lateinit var channels: ArrayList<Channel>
    lateinit var channelListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        channelHandler = ChannelHandler()
        channels = ArrayList()
        channelListView = findViewById(R.id.channelListView)

        registerForContextMenu(channelListView)
    }
    override fun onStart() {
        super.onStart()
        channelHandler.channelRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                channels.clear()
                p0.children.forEach { it ->
                    val channel = it.getValue(Channel::class.java)

                    channels.add(channel!!)
                }
                val sortedChannel = channels.sortedBy { it.rank }
                val adapter = ArrayAdapter<Channel>(
                    applicationContext,
                    android.R.layout.simple_list_item_1,
                    sortedChannel
                )
                channelListView.adapter = adapter


            }

        })
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when(item.itemId) {
            R.id.edit_channel -> {
                val sortedChannel = channels.sortedBy { it.rank }
                val channel = sortedChannel[info.position]
                val channelId = channel.id
                val channelName = channel.name
                val channelLink = channel.link
                val channelRank = channel.rank.toString()
                val channelReason = channel.reason

                val intent = Intent(applicationContext, EditActivity::class.java)
                intent.putExtra("id", channelId)
                intent.putExtra("name", channelName)
                intent.putExtra("link", channelLink)
                intent.putExtra("rank", channelRank)
                intent.putExtra("reason", channelReason)
                startActivity(intent)
                true
            }R.id.delete_channel -> {
                val sortedChannel = channels.sortedBy { it.rank }
                val channel = sortedChannel[info.position]
                if(channelHandler.delete(channel)){
                    Toast.makeText(applicationContext, "Channel deleted", Toast.LENGTH_SHORT).show()
                }
                true
            }
            else ->super.onContextItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_song -> {
                startActivity(Intent(this, MainActivity::class.java))
                true
            }R.id.channel_list -> {
                startActivity(Intent(this, ListActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}