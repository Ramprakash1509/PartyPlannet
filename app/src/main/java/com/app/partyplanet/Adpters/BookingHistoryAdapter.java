package com.app.partyplanet.Adpters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.partyplanet.DashBoad.RoomsHistry;
import com.app.partyplanet.Data.RoomPreViewModel;
import com.app.partyplanet.Data.SelectedRoomModel;
import com.app.partyplanet.R;
import com.app.partyplanet.Utils.ApplicationConstant;
import com.app.partyplanet.Utils.UtilsMethod;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookingHistoryAdapter extends   RecyclerView.Adapter<BookingHistoryAdapter.ViewHolder>
{   private Context context;
    private List<SelectedRoomModel> msubslider = new ArrayList<>();

    public BookingHistoryAdapter(Context context, List<SelectedRoomModel> msubslider) {
        this.context = context;
        this.msubslider = msubslider;
    }

    @NonNull
    @Override
    public BookingHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.booking_history_layout, parent, false);
        return  new ViewHolder(listItem);

    }
    @Override
    public void onBindViewHolder(@NonNull BookingHistoryAdapter.ViewHolder viewHolder, int position)
    {   SelectedRoomModel sliderItem = msubslider.get(position);
        viewHolder.title.setText(sliderItem.getName());
        viewHolder.tv_address.setText(sliderItem.getAddress());


      /*  String currentString = "Fruit: they taste good";
        String[] separated = currentString.split(":");
        separated[0]; // this will contain "Fruit"
        separated[1];*/


        if (sliderItem.getTime_slot()!=null)
        {
            viewHolder.timeslot.setVisibility(View.VISIBLE);
            viewHolder.time.setVisibility(View.VISIBLE);
            String currentString =sliderItem.getTime_slot();
            String[] separated = currentString.split("-");
            Log.d("djfgttyyt4","" +separated[0]);
            viewHolder.timeslot.setText(separated[0]+" To "+separated[1]);
        }

        String checkoutDate=sliderItem.getCheckindate();
        SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date time1= null;

        try {
            time1 = fmt.parse(checkoutDate);
         //   time2=newFormat.parse(checkoutDate);
            String sd = newFormat.format(time1);
           // String ed = newFormat.format(time2);
            viewHolder.date.setText(""+sd+"");

        } catch (ParseException e) {
            e.printStackTrace();
        }
        viewHolder.layout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                UtilsMethod.INSTANCE.getHistrory(context,sliderItem.getModule_id(),sliderItem.booking_id,sliderItem.getModule_name(),checkoutDate);

            }
        });

        Glide.with(viewHolder.itemView)
                .load(ApplicationConstant.INSTANCE.baseUrl + sliderItem.getBanner_img())
                .fitCenter()
                .error(R.drawable.imm)
                .into(viewHolder.image);

    }

    @Override
    public int getItemCount() {
        return msubslider.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {  TextView title,tv_address,date,timeslot,time;
        ImageView image;
        RelativeLayout layout;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView)
        {super(itemView);
            this.title=itemView.findViewById(R.id.title);
            this.image=itemView.findViewById(R.id.image);
            this.tv_address=itemView.findViewById(R.id.tv_address);
            this.layout=itemView.findViewById(R.id.layout);
            this.date=itemView.findViewById(R.id.date);
            this.timeslot=itemView.findViewById(R.id.timeslot);
            this.time=itemView.findViewById(R.id.time);

        }
    }
}
