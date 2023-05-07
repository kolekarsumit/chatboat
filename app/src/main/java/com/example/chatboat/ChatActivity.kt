package com.example.chatboat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {

    private  lateinit var chatrecyclerview:RecyclerView
    private  lateinit var messagebox:EditText
    private lateinit var sendbutton:ImageView
    private  lateinit var messageadapter:messageadpater
    private lateinit var messagelist:ArrayList<Message>
    private  lateinit var mdbref:DatabaseReference

    var reciverroom:String?=null
    var senderroom:String?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)



        val name=intent.getStringExtra("name")
        val reciveruid=intent.getStringExtra("uid")

        val senderuid=FirebaseAuth.getInstance().currentUser?.uid

        mdbref=FirebaseDatabase.getInstance().getReference()

        senderroom=reciveruid+senderuid
        reciverroom=senderuid+reciveruid

        supportActionBar?.title=name


        chatrecyclerview=findViewById(R.id.chatrecyclerview)
        messagebox=findViewById(R.id.massagebox)
        sendbutton=findViewById(R.id.sendbutton)
        messagelist= ArrayList()
        messageadapter= messageadpater(this,messagelist)


        chatrecyclerview.layoutManager=LinearLayoutManager(this)
        chatrecyclerview.adapter=messageadapter


        //logic for adding data in recycler view
        mdbref.child("chat").child(senderroom!!).child("messages")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    messagelist.clear()

                    for(postsnapshot in snapshot.children){

                        val message=postsnapshot.getValue(Message::class.java)
                        messagelist.add(message!!)
                    }
                    messageadapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })


        sendbutton.setOnClickListener {
val message=messagebox.text.toString()
            val messageobj=Message(message,senderuid)

            mdbref.child("chat").child(senderroom!!).child("messages").push()
                .setValue(messageobj).addOnSuccessListener {
                    mdbref.child("chat").child(reciverroom!!).child("messages").push()
                        .setValue(messageobj)
                }
            messagebox.setText("")
        }
    }
}