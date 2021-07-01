package com.example.FinalFoodRunner.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.FinalFoodRunner.R

class MenuRecyclerAdapter(val context: Context, val itemList: ArrayList<com.example.FinalFoodRunner.model.Menu>):RecyclerView.Adapter<MenuRecyclerAdapter.MenuViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_menu_single_row,parent,false)
       return MenuViewHolder(view)
    }

    override fun getItemCount(): Int {

        return itemList.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {

        val menu = itemList[position]
        holder.txtMenuName.text = menu.menuName
        holder.menuCost_for_one.text = menu.menuCost_for_one

        holder.buttonAdd.setOnClickListener{


        }
    }
    class MenuViewHolder(view: View): RecyclerView.ViewHolder(view){

        val txtMenuName :TextView = view.findViewById(R.id.txtDishName)
        val menuCost_for_one:TextView = view.findViewById(R.id.txtCFO)
        val buttonAdd: Button = view.findViewById(R.id.btnOrder)

    }

}