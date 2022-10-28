package com.app.partyplanet.Adpters;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.partyplanet.Data.Dataum;
import com.app.partyplanet.Model.PopularDestinationModel;
import com.app.partyplanet.Model.Services;
import com.app.partyplanet.R;
import com.app.partyplanet.Utils.ApplicationConstant;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ServicesAdapter extends  RecyclerView.Adapter<ServicesAdapter.ViewHolder>
{
    private Context context;
    private List<Dataum> msubslider = new ArrayList<>();

    public ServicesAdapter(Context context, List<Dataum> msubslider)
    {
        this.context = context;
        this.msubslider = msubslider;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.services, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Dataum sliderItem = msubslider.get(position);
        viewHolder.cityname.setText(sliderItem.getName());
        Log.d("userservice",ApplicationConstant.INSTANCE.baseUrl+sliderItem.getIcon());

        Glide.with(viewHolder.itemView)
                .load(ApplicationConstant.INSTANCE.baseUrl+sliderItem.getIcon())
                .fitCenter()
                .into(viewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return msubslider.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView imageView;
        public RelativeLayout layout;
        public TextView cityname;

        public ViewHolder(@NonNull View v)
        {
            super(v);
            this.imageView=v.findViewById(R.id.image);
            this.layout=v.findViewById(R.id.layout);
            this.cityname=v.findViewById(R.id.cityname);
        }
    }
}
