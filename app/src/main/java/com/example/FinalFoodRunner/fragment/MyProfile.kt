package com.example.FinalFoodRunner.fragment

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.FinalFoodRunner.R
import org.w3c.dom.Text


class MyProfile : Fragment() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var txtName: TextView
    lateinit var txtNumber: TextView
    lateinit var txtEmail: TextView
    lateinit var txtAddress:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_profile, container, false)
        txtName = view.findViewById(R.id.txtName)
        txtNumber = view.findViewById(R.id.txtNumber)
        txtEmail = view.findViewById(R.id.txtEmail)
        txtAddress = view.findViewById(R.id.txtAddress)

        txtName.text = sharedPreferences.getString("name","Name")
        txtNumber.text = sharedPreferences.getString("email","ram@mail.com")
        txtEmail.text = sharedPreferences.getString("mobileNumber","+91 1234567890")
        txtAddress.text = sharedPreferences.getString("address","Delivery Here")

        return view
    }
}