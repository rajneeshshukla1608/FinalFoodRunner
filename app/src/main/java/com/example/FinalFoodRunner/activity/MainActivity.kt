package com.example.FinalFoodRunner.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
//import android.widget.Toolbar    // this library does't support the supportActionbar()
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.FinalFoodRunner.*
import com.example.FinalFoodRunner.fragment.*
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences

    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView

    var previousMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerlayout)
        coordinatorLayout = findViewById(R.id.coordinatorlayout)
        toolbar = findViewById(R.id.toolbar)
        frameLayout = findViewById(R.id.frame)
        navigationView = findViewById(R.id.navigationView)
        setUpToolbar()

        openHome()
        val actionBarDrawerToggle = ActionBarDrawerToggle(this@MainActivity, drawerLayout, R.string.open_drawer, R.string.close_drawer)


        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {

            if (previousMenuItem != null) {
                previousMenuItem?.isChecked = false
            }
            it.isCheckable = true
            it.isChecked = true
            previousMenuItem = it

            when (it.itemId) {
                R.id.home -> {
                    openHome()
                    drawerLayout.closeDrawers()
                }
                R.id.myprofile -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.frame, MyProfile())

                            .commit()
                    supportActionBar?.title = "My Profile"
                    drawerLayout.closeDrawers()
                }
                R.id.favrestaurants -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.frame, FavouriteRestaurants())

                            .commit()
                    supportActionBar?.title = "Favourites"
                    drawerLayout.closeDrawers()
                }
                R.id.faq -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.frame, Faqs())

                            .commit()
                    supportActionBar?.title = "FAQs"
                    drawerLayout.closeDrawers()
                }
                R.id.logout -> {
                    val dialog = AlertDialog.Builder(this@MainActivity)
                    dialog.setTitle("Confirmation")
                    dialog.setMessage("Are you sure want to logout?")
                    dialog.setPositiveButton("Yes") { text, listener ->
                        //Do nothing
                        val intent = Intent(this@MainActivity,LoginActivity::class.java)
                       startActivity(intent)

                        sharedPreferences.edit().clear().apply()
                    }
                    dialog.setNegativeButton("No") { text, listener ->
                        //do nothing

                    }
                    dialog.create()
                    dialog.show()

                    drawerLayout.closeDrawers()
                }
            }
            return@setNavigationItemSelectedListener true
        }
    }

    fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Toolbar Title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    fun openHome() {
        val fragment = HomeFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame, fragment)
        transaction.commit()
        supportActionBar?.title = "Home"
        navigationView.setCheckedItem(R.id.home)
    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.frame)

        when (frag) {
            !is HomeFragment -> openHome()
            else -> super.onBackPressed()
        }
    }



}