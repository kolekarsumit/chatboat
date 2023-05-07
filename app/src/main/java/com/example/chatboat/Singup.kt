package com.example.chatboat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class Singup : AppCompatActivity() {

    private lateinit var  edtName:EditText
    private lateinit var edtemail: EditText
    private  lateinit var  edipass: EditText
    private  lateinit var  btnsingup: Button
    private  lateinit var  mAuth: FirebaseAuth
private  lateinit var mdbref:DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singup)
        mAuth=FirebaseAuth.getInstance()
        edtemail=findViewById(R.id.edt_email)
        edtName=findViewById(R.id.edt_name)
        edipass=findViewById(R.id.edt_password)
        btnsingup=findViewById(R.id.btn_Singup)

        supportActionBar?.hide()
        btnsingup.setOnClickListener{
            val name=edtName.text.toString()
            val email=edtemail.text.toString()
            val password =edipass.text.toString()

            singup(name,email,password)
        }
    }
    private  fun singup(name:String,email:String, password:String ){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    addusertodatabase(name,email,mAuth.currentUser?.uid!!)
                    val intent=Intent(this@Singup,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
Toast.makeText(this@Singup,"Error Found",Toast.LENGTH_SHORT).show()
                }
            }
    }

    private  fun addusertodatabase(name:String,emil:String,uid:String){
mdbref=FirebaseDatabase.getInstance().getReference()

        mdbref.child("user").child(uid).setValue(User(name,emil,uid))
    }

}