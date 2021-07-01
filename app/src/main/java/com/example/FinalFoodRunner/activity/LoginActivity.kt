package com.example.FinalFoodRunner.activity

import android.app.Activity
import android.app.AlertDialog
import android.app.DownloadManager
import androidx.appcompat.widget.Toolbar
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
//import android.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.FinalFoodRunner.R
import com.example.FinalFoodRunner.util.ConnectionManager
import org.json.JSONObject
import java.lang.Exception

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val forgotPassword: TextView = findViewById(R.id.forgot_pass)
        val loginId: TextView = findViewById(R.id.edit_1)
        val loginPass: TextView = findViewById(R.id.edit_2)
        val toolbar:Toolbar  = findViewById(R.id.toolbar)
        val btnlogin: Button = findViewById(R.id.login_btn)
        val register: TextView = findViewById(R.id.sign_up)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Login Here"

        var sharedPreference: SharedPreferences = getSharedPreferences("login preferences", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreference.getBoolean("isLoggedIn", false)
        setContentView(R.layout.activity_login)

        if (isLoggedIn) {
            val Intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(Intent)
            finish()
        } else {
            sharedPreference.edit().putBoolean("isLoggedIn", true).apply()
        }

        forgotPassword.setOnClickListener {
            val intent = Intent(this@LoginActivity,ForgotPassword::class.java)
           startActivity(intent)
        }

        register.setOnClickListener {
            val intent = Intent(this@LoginActivity,Registor::class.java)
            startActivity(intent)
        }

        btnlogin.setOnClickListener{

             if (ConnectionManager().checkConnectivity(this@LoginActivity)){
                 val loginId = loginId.text.toString()
                 val loginPass = loginPass.text.toString()

                 val queue = Volley.newRequestQueue(this@LoginActivity)
                 val url = "http://13.235.250.119/v2/login/fetch_result"

                 val jsonParams = JSONObject()
                 jsonParams.put("mobile_number", loginId)
                 jsonParams.put("password",loginPass)
                 val jsonRequest = object: JsonObjectRequest(Request.Method.POST,url,jsonParams, Response.Listener {

                     try {
                         val success = it.getBoolean("success")
                         if (success){
                             val intent = Intent(this@LoginActivity,MainActivity::class.java)
                             intent.putExtra("login_id",loginId)
                             startActivity(intent)

                         } else {
                             Toast.makeText(this@LoginActivity,"Some Unexpected error occurred",Toast.LENGTH_SHORT).show()
                         }
                     } catch (e:Exception){
                         Toast.makeText(this@LoginActivity,"Some Unexpected error occurred",Toast.LENGTH_SHORT).show()
                     }

                 },Response.ErrorListener {
                     Toast.makeText(this@LoginActivity,"Volley Did some mistake $it",Toast.LENGTH_SHORT).show()
                 }){

                     override fun getHeaders(): MutableMap<String, String> {
                         val headers = HashMap<String, String>()
                         headers["Content-type"] = "application/json"
                         headers["token"] = "9bf534118365f1"
                         return headers
                     }
                 }
                 queue.add(jsonRequest)
             }else {
                 val dialog = AlertDialog.Builder(this@LoginActivity)
                 dialog.setTitle("error")
                 dialog.setMessage("Internet connection not found")
                 dialog.setPositiveButton("Open Settings") { text, listener ->
                     val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                     startActivity(settingsIntent)
                     finish()
                     //Do nothing
                 }
                 dialog.setNegativeButton("Exit") { text, listener ->
                     ActivityCompat.finishAffinity(this@LoginActivity)
                     //do nothing
                 }
                 dialog.create()
                 dialog.show()
             }
        }
    }
}