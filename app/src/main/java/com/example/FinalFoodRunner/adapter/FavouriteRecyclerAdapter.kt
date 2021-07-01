package com.example.FinalFoodRunner.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.FinalFoodRunner.R
import com.example.FinalFoodRunner.database.RestaurantEntity
import com.squareup.picasso.Picasso

class FavouriteRecyclerAdapter(val context : Context , val restaurantList: List<RestaurantEntity>) : RecyclerView.Adapter<FavouriteRecyclerAdapter.FavouriteViewHolder>() {

       override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {

              val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_favourite_single_row,parent
                      ,false)

              return FavouriteViewHolder(view)
       }

       override fun getItemCount(): Int {
              return restaurantList.size
       }

       override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
              val restaurant = restaurantList[position]
              holder.txtRestaurantName.text = restaurant.restaurantName
              holder.txtRestaurantPrice.text = restaurant.restaurantCostPerPerson
              holder.txtRestaurantRating.text = restaurant.restaurantRating
              Picasso.get().load(restaurant.restaurantImage_url).error(R.drawable.default_book_cover).into(holder.restaurantImage)

       }

       class FavouriteViewHolder(view: View): RecyclerView.ViewHolder(view){
              val txtRestaurantName:TextView = view.findViewById(R.id.txtRestaurantName)
              val txtRestaurantPrice:TextView = view.findViewById(R.id.txtRestaurantPrice)
              val txtRestaurantRating:TextView = view.findViewById(R.id.txtRestaurantRating)
              val restaurantImage :ImageView = view.findViewById(R.id.imgRestaurantImage)
       }
}