package com.lipa.accountactivity

import android.app.Dialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.lipa.FirstActivity
import com.lipa.R
import com.lipa.helper.DatabaseHelper

open class SignIn : AppCompatActivity() {
    private lateinit var mTextEmail: EditText
    private lateinit var mTextPassword: EditText
    private lateinit var mButtonsignin: Button
    private lateinit var mButtonsignup: Button
    private lateinit var db: DatabaseHelper
    private var progressView: ViewGroup? = null
    private var isProgressShowing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        /*val dialog = Dialog(this, R.style.Theme_Translucent_NoTitleBar)
        val v = this.layoutInflater.inflate(R.layout.progressbar, null)
        dialog.setContentView(v)
        dialog.show()*/                                 // This block of code will Display the Progressbar for a while

        db = DatabaseHelper(this)
        mTextEmail = findViewById<View>(R.id.EmailsignIn) as EditText
        mTextPassword = findViewById<View>(R.id.PasswordsignIn) as EditText
        mButtonsignin = findViewById<View>(R.id.btn_login) as Button
        mButtonsignup = findViewById<View>(R.id.btn_signup) as Button
        mButtonsignup.setOnClickListener {
            val registerIntent = Intent(this@SignIn, Register::class.java)
            startActivity(registerIntent)
        }

        mButtonsignin.setOnClickListener {
            val user = mTextEmail.text.toString().trim { it <= ' ' }
            val pwd = mTextPassword.text.toString().trim { it <= ' ' }
            val res = db.checkUser(user, pwd)
            if (res == true) {
                val HomePage = Intent(this@SignIn, FirstActivity::class.java)
                startActivity(HomePage)
            } else {
                Toast.makeText(this@SignIn, "Login Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun showProgressingView() {

        if (!isProgressShowing) {
            val view = findViewById<View>(R.id.progressBar1)
            view.bringToFront()
        }
    }

    fun hideProgressingView() {
        val v = this.findViewById<View>(android.R.id.content).rootView
        val viewGroup = v as ViewGroup
        viewGroup.removeView(progressView)
        isProgressShowing = false
    }
}