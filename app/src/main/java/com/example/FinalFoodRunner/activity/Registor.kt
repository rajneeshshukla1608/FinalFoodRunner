package com.example.FinalFoodRunner.activity

//import android.widget.Toolbar
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.FinalFoodRunner.R
import com.example.FinalFoodRunner.util.ConnectionManager
import org.json.JSONObject
import java.lang.Exception

class Registor : AppCompatActivity() {

    lateinit var sharedPreference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registor)
        title = "Register Yourself"

        val registerButton: Button = findViewById(R.id.Register_btn)
        val editName: TextView = findViewById(R.id.edit_Name)
        val editEmail: TextView = findViewById(R.id.edit_Email)
        val editNumber: TextView = findViewById(R.id.edit_Number)
        val editDelivery: TextView = findViewById(R.id.edit_delivery)
        val editPassword: TextView = findViewById(R.id.edit_Password)
        val editConfirm: TextView = findViewById(R.id.edit_Confirm)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Register Now"

        registerButton.setOnClickListener {

            if (ConnectionManager().checkConnectivity(this@Registor)) {

                val userName = editName.text.toString()
                val userEmail = editEmail.text.toString()
                val userNumber = editNumber.text.toString()
                val userAddress = editDelivery.text.toString()
                val userPassword = editPassword.text.toString()
                val userConfirm = editConfirm.text.toString()

                if (userPassword == userConfirm) {

                    val queue = Volley.newRequestQueue(this@Registor)
                    val url = "http://13.235.250.119/v2/register/fetch_result"
                    val jsonParams = JSONObject()
                    jsonParams.put("name", userName)
                    jsonParams.put("mobile_number", userNumber)
                    jsonParams.put("password", userPassword)
                    jsonParams.put("address", userAddress)
                    jsonParams.put("email", userEmail)

                    val jsonRequest = object : JsonObjectRequest(Request.Method.POST, url, jsonParams, Response.Listener {
                        //Response
                        try {
                            val success = it.getBoolean("success")
                           if (success){
                               sharedPreference = getSharedPreferences("Register Preferences",Context.MODE_PRIVATE)

                               val registerJsonObject = it.getJSONObject("data")
                              val userId =  registerJsonObject.getString("user_id")
                               val name = registerJsonObject.getString("name")
                               val email = registerJsonObject.getString("email")
                               val mobileNumber = registerJsonObject.getString("mobile_number")
                               val address = registerJsonObject.getString("address")

                               sharedPreference.edit().putString("name",name).apply()
                               sharedPreference.edit().putString("email",email).apply()
                               sharedPreference.edit().putString("mobileNumber",mobileNumber).apply()
                               sharedPreference.edit().putString("address",address).apply()


                               Toast.makeText(this@Registor,"Account created",Toast.LENGTH_SHORT).show()
                               val intent = Intent(this@Registor,MainActivity::class.java)
                               startActivity(intent)

                           } else{
                               Toast.makeText(this@Registor,"Some unexpected error occurred",Toast.LENGTH_SHORT).show()
                           }

                        } catch (e:Exception){
                               Toast.makeText(this@Registor,"Some Unexpected error occurred",Toast.LENGTH_SHORT).show()
                        }

                    }, Response.ErrorListener {

                        //Error
                        Toast.makeText(this@Registor,"Volley Error Occurred",Toast.LENGTH_SHORT).show()


                    }) {
                        override fun getHeaders(): MutableMap<String, String> {
                            val headers = HashMap<String, String>()
                            headers["Content-type"] = "application/json"
                            headers["token"] = "9bf534118365f1"
                            return headers
                        }
                    }
                    queue.add(jsonRequest)
                } else {
                    Toast.makeText(this@Registor, "Password did not match correctly", Toast.LENGTH_SHORT).show()
                }
            } else {
                val dialog = AlertDialog.Builder(this@Registor)
                dialog.setTitle("error")
                dialog.setMessage("Internet connection not found")
                dialog.setPositiveButton("Open Settings") { text, listener ->
                    val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    startActivity(settingsIntent)
                    finish()
                    //Do nothing
                }
                dialog.setNegativeButton("Exit") { text, listener ->
                    ActivityCompat.finishAffinity(this@Registor)
                    //do nothing
                }
                dialog.create()
                dialog.show()
            }

        }
    }
}