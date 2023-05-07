package com.example.chatboat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.currentCoroutineContext

class messageadpater(val context:Context,val message:ArrayList<Message>):RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    val ITEM_RECIVE=1
    val ITEM_SEND=2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
if(viewType==1){
    val view :View= LayoutInflater.from(context).inflate(R.layout.recive,parent,false)
    return ReciveViewHolder(view)
}
        else{
    val view :View= LayoutInflater.from(context).inflate(R.layout.send,parent,false)
    return SendViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentmessage=message[position]
        if(holder.javaClass==SendViewHolder::class.java){


val viewholder=holder as SendViewHolder
            holder.sendmessage.text=currentmessage.message

        }
        else{
            val viewHolder =holder as ReciveViewHolder
            holder.recivemessage.text= currentmessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {

        val currentmessage=message[position]

        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentmessage.senderId)){
            return ITEM_SEND
        }
        else{
            return ITEM_RECIVE
        }
    }

    override fun getItemCount(): Int {
return message.size
    }

    class SendViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
  val sendmessage=itemView.findViewById<TextView>(R.id.txt_send_message)
    }

    class ReciveViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val recivemessage=itemView.findViewById<TextView>(R.id.txt_recive_message)
    }
}