package com.example.FinalFoodRunner.fragment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.FinalFoodRunner.R
import com.example.FinalFoodRunner.adapter.FavouriteRecyclerAdapter
import com.example.FinalFoodRunner.database.RestaurantDatabase
import com.example.FinalFoodRunner.database.RestaurantEntity

class FavouriteRestaurants : Fragment() {

lateinit var recyclerFavourite: RecyclerView
    lateinit var progressLayout: RelativeLayout
    lateinit var progressBAr: ProgressBar
    lateinit var layoutManager : RecyclerView.LayoutManager
    lateinit var recyclerAdapter: FavouriteRecyclerAdapter
    var dbRestaurantList = listOf<RestaurantEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favourite_restaurants, container, false)

        recyclerFavourite = view.findViewById(R.id.recyclerFavourites)
        progressLayout = view.findViewById(R.id.progressLayout)
        progressBAr = view.findViewById(R.id.progressBar)

        layoutManager = GridLayoutManager(activity as Context , 2)

        dbRestaurantList = RetrieveFavourites(activity as Context ).execute().get()

        if (activity != null) {
            progressLayout.visibility = View.GONE
            recyclerAdapter = FavouriteRecyclerAdapter(activity as Context, dbRestaurantList)
            recyclerFavourite.adapter = recyclerAdapter
            recyclerFavourite.layoutManager = layoutManager
        }


        return view
    }

    class RetrieveFavourites(val context: Context): AsyncTask<Void , Void ,List<RestaurantEntity>>(){
        override fun doInBackground(vararg params: Void?): List<RestaurantEntity> {

            val db = Room.databaseBuilder(context , RestaurantDatabase::class.java, "restaurants-db").build()

            return db.restaurantDao().getAllRestaurant()
        }

    }
}