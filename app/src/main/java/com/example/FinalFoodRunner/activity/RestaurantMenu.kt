package com.example.FinalFoodRunner.activity

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.FinalFoodRunner.R
import com.example.FinalFoodRunner.adapter.MenuRecyclerAdapter
import com.example.FinalFoodRunner.model.Menu
import com.example.FinalFoodRunner.util.ConnectionManager
import org.json.JSONException
import java.lang.Exception
import java.util.concurrent.atomic.DoubleAdder

class   RestaurantMenu : AppCompatActivity() {

    lateinit var btnAdd:Button
    lateinit var btnProceed:Button
    lateinit var recyclerMenu: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    var restaurant_Id: String? = "1"
    lateinit var recyclerAdapter: MenuRecyclerAdapter
    lateinit var progressLayout:RelativeLayout
    lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_menu)

        btnAdd = findViewById(R.id.btnOrder)

        btnProceed = findViewById(R.id.btnProceed)

        recyclerMenu = findViewById(R.id.recyclerMenu)

        progressLayout = findViewById(R.id.progressLayout)

        progressBar = findViewById(R.id.progressBar)

        progressLayout.visibility = View.VISIBLE

        val menuInfoList = ArrayList<Menu>()

        if (intent != null) {
            restaurant_Id = intent.getStringExtra("restaurant_id")
        } else {
            finish()
            Toast.makeText(
                this@RestaurantMenu,
                "Some unexpected error occurred",
                Toast.LENGTH_SHORT
            ).show()
        }
        if (restaurant_Id == "1") {
            Toast.makeText(
                this@RestaurantMenu,
                "Some unexpected error occurred",
                Toast.LENGTH_SHORT
            ).show()
        }


        layoutManager = LinearLayoutManager(this@RestaurantMenu)



        val queue = Volley.newRequestQueue(this@RestaurantMenu)

        val url = "http://13.235.250.119/v2/restaurants/fetch_result/$restaurant_Id"

        if(ConnectionManager().checkConnectivity(this@RestaurantMenu)){
            val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {

                // here we will handle the response
                try {
                    progressLayout.visibility = View.GONE
                    val success = it.getBoolean("success")

                    if (success){
                        val data = it.getJSONArray("data")
                        for (i in 0 until data.length()){
                            val menuJsonObject = data.getJSONObject(i)
                            val menuObject = Menu(
                                menuJsonObject.getString("id"),
                                menuJsonObject.getString("name"),
                                menuJsonObject.getString("cost_for_one"),
                                menuJsonObject.getString("restaurant_id")
                            )
                            menuInfoList.add(menuObject)

                            recyclerAdapter = MenuRecyclerAdapter(this@RestaurantMenu, menuInfoList)

                            recyclerMenu.adapter = recyclerAdapter

                            recyclerMenu.layoutManager = layoutManager
                        }
                    }  else {
                        Toast.makeText(this@RestaurantMenu,"Some  error occurred",Toast.LENGTH_SHORT).show()

                    }

                } catch (e: JSONException) {
                    Toast.makeText(this@RestaurantMenu,"Some  unexpected error occurred",Toast.LENGTH_SHORT).show()

                }

            }, Response.ErrorListener {

                //here we will handle the errors
                Toast.makeText(this@RestaurantMenu,"Volley  error occurred",Toast.LENGTH_SHORT).show()


            }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application/json"
                    headers["token"] = "9bf534118365f1"
                    return headers
                }

            }
            queue.add(jsonObjectRequest)
        } else {
            val dialog = AlertDialog.Builder(this@RestaurantMenu)
            dialog.setTitle("error")
            dialog.setMessage("Internet connection not found")
            dialog.setPositiveButton("Open Settings") { text, listener ->
                val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                finish()
                //Do nothing
            }
            dialog.setNegativeButton("Exit") { text, listener ->
                ActivityCompat.finishAffinity(this@RestaurantMenu)
                //do nothing
            }
            dialog.create()
            dialog.show()
        }

        btnProceed.setOnClickListener {
            val intent = Intent(this@RestaurantMenu,OrderPlaced::class.java)
            startActivity(intent)
        }


    }
}