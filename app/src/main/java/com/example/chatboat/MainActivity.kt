package com.example.chatboat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

private  lateinit var userrecylerview:RecyclerView
private  lateinit var userlist:ArrayList<User>
private  lateinit var adapter:UserAdpater
private  lateinit var mauth:FirebaseAuth
private  lateinit var mdbref:DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mauth=FirebaseAuth.getInstance()
        mdbref=FirebaseDatabase.getInstance().getReference()

        userlist= ArrayList()
        adapter= UserAdpater(this,userlist)

        userrecylerview=findViewById(R.id.userRecycerview)

        userrecylerview.layoutManager=LinearLayoutManager(this)
        userrecylerview.adapter=adapter

        mdbref.child("user").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                userlist.clear()
                for(postSnapshot in snapshot.children){
                    val currentuser=postSnapshot.getValue(User::class.java)

                    if(mauth.currentUser?.uid!=currentuser?.uid) {
                        userlist.add(currentuser!!)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId==R.id.logout){
            mauth.signOut()
            val inteint= Intent(this@MainActivity,Login::class.java)
            finish()
            startActivity(inteint)
            return true
        }

        return true
    }
}