package com.lipa.accountactivity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.lipa.R
import com.lipa.helper.DatabaseHelper

class Register : AppCompatActivity() {
    private lateinit var db: DatabaseHelper
    private lateinit var mTextFullname: EditText
    private lateinit var mTextEmail: EditText
    private lateinit var mTextPassword: EditText
    private lateinit var mTextConfirmPassword: EditText
    private lateinit var mTextPhoneNumber: EditText
    private lateinit var mButtonsignup: Button
    private lateinit var mButtonsignin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        db = DatabaseHelper(this)
        mTextFullname = findViewById<View>(R.id.FullName) as EditText
        mTextEmail = findViewById<View>(R.id.Email) as EditText
        mTextPassword = findViewById<View>(R.id.Password) as EditText
        mTextConfirmPassword = findViewById<View>(R.id.ConfirmPassword) as EditText
        mTextPhoneNumber = findViewById<View>(R.id.PhoneNumber) as EditText
        mButtonsignup = findViewById<View>(R.id.btn_signup) as Button
        mButtonsignin = findViewById<View>(R.id.btn_signin) as Button
        mButtonsignin.setOnClickListener {
            val loginIntent = Intent(this@Register, SignIn::class.java)
            startActivity(loginIntent)
        }

        mButtonsignup.setOnClickListener {
            val user = mTextFullname.text.toString().trim { it <= ' ' }
            val pwd = mTextPassword.text.toString().trim { it <= ' ' }
            val cnf_pwd = mTextConfirmPassword.text.toString().trim { it <= ' ' }

            if (pwd == cnf_pwd) {
                val `val` = db.addUser(user, pwd)
                if (`val` > 0) {
                    Toast.makeText(this@Register, "You have Successfully registered", Toast.LENGTH_SHORT).show()
                    val moveToLogin = Intent(this@Register, SignIn::class.java)
                    startActivity(moveToLogin)
                } else {
                    Toast.makeText(this@Register, "Registration Failed", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this@Register, "Password is does not match", Toast.LENGTH_SHORT).show()
            }
        }
    }
}