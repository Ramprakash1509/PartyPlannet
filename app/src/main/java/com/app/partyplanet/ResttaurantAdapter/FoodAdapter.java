package com.app.partyplanet.ResttaurantAdapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.lights.LightState;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.partyplanet.Adpters.RoomsAdapter;
import com.app.partyplanet.DashBoad.RoomActivity;
import com.app.partyplanet.Data.Data;
import com.app.partyplanet.Data.RoomData;
import com.app.partyplanet.R;
import com.app.partyplanet.RestaurantData.FoodManue;
import com.app.partyplanet.Restaurants.Restaurantbooking;
import com.app.partyplanet.Utils.ApplicationConstant;
import com.app.partyplanet.Utils.UtilsMethod;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends   RecyclerView.Adapter<FoodAdapter.ViewHolder>
{    List<FoodManue> menuList= new ArrayList<>();
     List<FoodManue> selectedItem= new ArrayList<>();
    String[] plate= new String[]{"Half plate","Full plate"};
    String[] quantity= new String[]{"1","2","3","4","5","6","7","8","9","10"};
    Context context;



    public interface onListClickedRowListner
    {
        void onListSelected(int status);


    }
   onListClickedRowListner listner;
    public FoodAdapter(List<FoodManue> menuList, Context context )
    {
        this.menuList = menuList;
        this.context=context;
        this.listner=listner;
    }
    public FoodAdapter( onListClickedRowListner listner )
    {
        this.listner=listner;
    }


    @NonNull
    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.menu_list, parent, false);
        return  new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.ViewHolder holder, int position)
    {   FoodManue slider=menuList.get(position);
        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String secureloginResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data securelogincheckResponse = new Gson().fromJson(secureloginResponse, Data.class);
        String  userId = securelogincheckResponse.getId();
        SharedPreferences sharedPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String listungId=sharedPreferences.getString("listingId","");

        holder.title.setText(menuList.get(position).getFood_title());

        holder.image.setImageResource(R.drawable.imm);
        Glide.with(holder.itemView)
                .load(ApplicationConstant.INSTANCE.baseUrl + slider.getFood_image())
                .fitCenter()
                .error(R.drawable.foo)
                .into(holder.image);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, plate);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinner.setAdapter(adapter);
        ArrayAdapter<String> quantityAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, quantity);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.quantity.setAdapter(quantityAdapter);

        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

             String selectItem= String.valueOf(holder.spinner.getSelectedItem());
             String quantity= String.valueOf(holder.quantity.getSelectedItem());
             if (selectItem.equalsIgnoreCase("Full plate"))
               {

                   slider.setPlate_quantity(position+"");
                   holder.price.setText("Rs."+slider.getPrice());

               }
             else if(selectItem.equalsIgnoreCase("Half plate"))
             {   slider.setPlate_quantity(position+"");
                 holder.price.setText("Rs. "+slider.getHalf_price());
             }

                holder.addmenu.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {

                        Toast.makeText(context, ""+holder.quantity.getSelectedItem(), Toast.LENGTH_SHORT).show();
                        String selectItem= String.valueOf(holder.spinner.getSelectedItem());
                        String quantity= String.valueOf(holder.quantity.getSelectedItem());
                        // listner.onListSelected(1);
                         UtilsMethod.INSTANCE.addMenu(context,slider.getId(),""+holder.spinner.getSelectedItemPosition(),userId,quantity,listungId);
                        holder.addmenu.setVisibility(View.GONE);
                        holder.remove.setVisibility(View.VISIBLE);

                    }
                });

                holder.remove.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                         //UtilsMethod.INSTANCE.addMenu(context,slider.getId(),""+holder.spinner.getSelectedItemPosition(),userId,quantity);
                          //listner.onListSelected(1);
                        UtilsMethod.INSTANCE.deleteMenu(context,userId,slider.getId());
                        holder.addmenu.setVisibility(View.VISIBLE);
                        holder.remove.setVisibility(View.GONE);

                    }
                });

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });



    }

    @Override
    public int getItemCount()
    {
        return menuList.size();
        //Log.d("sdfsdff",menuList.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {   TextView title,price;
        ImageView image,addmenu,remove;
        Spinner spinner,quantity;
        CheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title=itemView.findViewById(R.id.title);
            this.image=itemView.findViewById(R.id.image);
            this.spinner=itemView.findViewById(R.id.adults_spinner);
            this.price=itemView.findViewById(R.id.price);
            this.quantity=itemView.findViewById(R.id.quantity);
            this.addmenu=itemView.findViewById(R.id.addmenu);
            this.remove=itemView.findViewById(R.id.remove);

        }
    }
}
