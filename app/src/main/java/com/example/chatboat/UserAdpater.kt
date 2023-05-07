package com.example.chatboat

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class UserAdpater(val context:Context,val userList:ArrayList<User>):
    RecyclerView.Adapter<UserAdpater.userviewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userviewholder {
       val view :View=LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false)
        return userviewholder(view)

    }

    override fun onBindViewHolder(holder: userviewholder, position: Int) {
      val currentuser=userList[position]

        holder.textname.text=currentuser.name

        holder.itemView.setOnClickListener{
            val intent=Intent(context,ChatActivity::class.java)
intent.putExtra("name",currentuser.name)
            intent.putExtra("uid",currentuser.uid)

            context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
     return userList.size
    }

    class userviewholder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
val textname=itemView.findViewById<TextView>(R.id.txt_name)
    }


}