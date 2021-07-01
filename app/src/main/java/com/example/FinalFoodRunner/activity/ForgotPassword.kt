package com.example.FinalFoodRunner.activity

import android.app.DownloadManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.FinalFoodRunner.R
import com.example.FinalFoodRunner.util.ConnectionManager
import org.json.JSONObject
import java.lang.Exception

class ForgotPassword : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val submitButton :Button = findViewById(R.id.forgot_btn)
        val email:EditText = findViewById(R.id.edit_Email)
        val number:EditText = findViewById(R.id.edit_Number)


        submitButton.setOnClickListener{
            if (ConnectionManager().checkConnectivity(this@ForgotPassword)){
               val sendEmail =  email.text.toString()
                val sendNumber =  number.text.toString()

                val queue = Volley.newRequestQueue(this@ForgotPassword)
                val url = "http://13.235.250.119/v2/forgot_password/fetch_result"

                val jsonParams = JSONObject()
                jsonParams.put("mobile_number",sendNumber)
                jsonParams.put("email",sendEmail)

                val jsonRequest = object: JsonObjectRequest(Request.Method.POST,url,jsonParams, Response.Listener {
                    try {
                        val success = it.getBoolean("success")
                        if (success){
                            val intent = Intent(this@ForgotPassword,ResetPassword::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@ForgotPassword,"Some Unexpected error occurred",
                                Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception){

                        Toast.makeText(this@ForgotPassword,"Some Unexpected error occurred", Toast.LENGTH_SHORT).show()

                    }

                }, Response.ErrorListener {

                    Toast.makeText(this@ForgotPassword,"Volley Did some mistake $it",Toast.LENGTH_SHORT).show()

                }){
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Content-type"] = "application/json"
                        headers["token"] = "9bf534118365f1"
                        return headers
                    }
                }
                queue.add(jsonRequest)
            } else {
                //Connection not established
                val dialog = AlertDialog.Builder(this@ForgotPassword)
                dialog.setTitle("error")
                dialog.setMessage("Internet connection not found")
                dialog.setPositiveButton("Open Settings") { text, listener ->
                    val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    startActivity(settingsIntent)
                    finish()
                    //Do nothing
                }
                dialog.setNegativeButton("Exit") { text, listener ->
                    ActivityCompat.finishAffinity(this@ForgotPassword)
                    //do nothing
                }
                dialog.create()
                dialog.show()

            }
        }


    }
}