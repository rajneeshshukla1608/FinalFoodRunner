package com.example.FinalFoodRunner.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
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

class ResetPassword : AppCompatActivity() {

    lateinit var sharedPreferences:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        val txtNumber: EditText = findViewById(R.id.edit_Number)
        val submit:Button = findViewById(R.id.Submit_btn)
        val txtOtp:EditText = findViewById(R.id.txtOTP)
        val txtNewPassword: EditText = findViewById(R.id.txtNewPassword)
        val txtConfirmPassword : EditText = findViewById(R.id.txtConfirmPassword)

        submit.setOnClickListener{
            if (ConnectionManager().checkConnectivity(this@ResetPassword)){

                val sendOtp = txtOtp.text.toString()
                val sendNewPassword = txtNewPassword.text.toString()
                val sendNumber = txtNumber.text.toString()

                val queue = Volley.newRequestQueue(this@ResetPassword)
                val url = "http://13.235.250.119/v2/reset_password/fetch_result"

                val jsonParams = JSONObject()
                jsonParams.put("mobile_number",sendNumber )
                jsonParams.put("password",sendNewPassword)
                jsonParams.put("otp",sendOtp)

                val jsonRequest = object: JsonObjectRequest(Request.Method.POST,url,jsonParams, Response.Listener {
                    try {
                        val success = it.getBoolean("success")
                        if (success){
                            Toast.makeText(this@ResetPassword,"Password reset successfully", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@ResetPassword,LoginActivity::class.java)
                            startActivity(intent)
                            sharedPreferences.edit().clear()
                        } else {
                            Toast.makeText(this@ResetPassword,"Some Unexpected error occurred",
                                Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception){

                        Toast.makeText(this@ResetPassword,"Some Unexpected error occurred", Toast.LENGTH_SHORT).show()

                    }


                }, Response.ErrorListener {

                    Toast.makeText(this@ResetPassword,"Volley Did some mistake $it", Toast.LENGTH_SHORT).show()

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
                val dialog = AlertDialog.Builder(this@ResetPassword)
                dialog.setTitle("error")
                dialog.setMessage("Internet connection not found")
                dialog.setPositiveButton("Open Settings") { text, listener ->
                    val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    startActivity(settingsIntent)
                    finish()
                    //Do nothing
                }
                dialog.setNegativeButton("Exit") { text, listener ->
                    ActivityCompat.finishAffinity(this@ResetPassword)
                    //do nothing
                }
                dialog.create()
                dialog.show()

            }

            }
        }
    }