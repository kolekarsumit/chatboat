package com.example.chatboat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {

    private lateinit var edtemail:EditText
    private  lateinit var  edipass:EditText
    private  lateinit var btnlogin:Button
    private  lateinit var  btnsingup:Button
    private  lateinit var  mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
supportActionBar?.hide()
        mAuth=FirebaseAuth.getInstance()

        edtemail=findViewById(R.id.edt_email)
        edipass=findViewById(R.id.edt_password)
        btnlogin=findViewById(R.id.btn_login)
        btnsingup=findViewById(R.id.btn_Singup)

        btnsingup.setOnClickListener{
            val intent=Intent(this,Singup::class.java)
            startActivity(intent)
        }

        btnlogin.setOnClickListener{
            val mail=edtemail.text.toString()
            val password=edipass.text.toString()

            login(mail,password)
        }


    }
    private fun login(email:String,password:String){

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {


                    val intent=Intent(this@Login,MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(this@Login,"User Does not exist",Toast.LENGTH_SHORT).show()
                }
            }
    }
}