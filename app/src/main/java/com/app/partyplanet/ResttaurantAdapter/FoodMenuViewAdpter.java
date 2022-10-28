package com.app.partyplanet.ResttaurantAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.partyplanet.R;
import com.app.partyplanet.RestaurantData.CartViewData;
import com.app.partyplanet.RestaurantData.ResturantData;
import com.app.partyplanet.Restaurants.RestauMenuBooking;
import com.app.partyplanet.Utils.ApplicationConstant;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class FoodMenuViewAdpter extends   RecyclerView.Adapter<FoodMenuViewAdpter.ViewHolder>
{

    private Context context;
    private List<CartViewData> msubslider = new ArrayList<>();
    private List<CartViewData> list = new ArrayList<>();

    public FoodMenuViewAdpter(Context context, List<CartViewData> msubslider) {
        this.context = context;
        this.msubslider = msubslider;
    }

    @NonNull
    @Override
    public FoodMenuViewAdpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.shownenulist, parent, false);
        return  new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodMenuViewAdpter.ViewHolder holder, int position)
    {
        CartViewData menu=msubslider.get(position);
        int amt=0;
        holder.title.setText(menu.getTitle());
        holder.quantity.setText(menu.getQty());
        holder.plate.setText(menu.getSize()+" Plate ");
        holder.price.setText("Rs."+menu.getTotal_amt());

        ((RestauMenuBooking) context).getTotalAmn(Integer.parseInt(menu.getTotal_amt()));

        Glide.with(holder.itemView)
                .load(ApplicationConstant.INSTANCE.baseUrl + menu.getImage())
                .fitCenter()
                .error(R.drawable.foo)
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return msubslider.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    { ImageView image;
      TextView title,quantity,price,plate;
        public ViewHolder(@NonNull View itemView)
        {  super(itemView);
            image=itemView.findViewById(R.id.image);
            title=itemView.findViewById(R.id.title);
            quantity=itemView.findViewById(R.id.quantity);
            price=itemView.findViewById(R.id.price);
            plate=itemView.findViewById(R.id.plate);
        }
    }
}
