package com.app.partyplanet.DashBoad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.partyplanet.Data.BookingHistoryModel;
import com.app.partyplanet.Data.Galleryimages;
import com.app.partyplanet.Data.RoomHistory;
import com.app.partyplanet.Data.RoomHistoryModel;
import com.app.partyplanet.Data.RoomPreViewModel;
import com.app.partyplanet.Data.SelectedRoomModel;
import com.app.partyplanet.R;
import com.app.partyplanet.Utils.ApplicationConstant;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RoomsHistry extends AppCompatActivity {
RecyclerView recyclerview;
String data,dated;
ImageView back;
LinearLayout layout_list;
  Galleryimages galleryimages;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms_histry);
        layout_list=findViewById(R.id.layout_list);
        back=findViewById(R.id.back);
        data=getIntent().getStringExtra("data");
        dated=getIntent().getStringExtra("date");
        ShowRooms(data);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

    }

    private void ShowRooms(String data)
    {
        Log.d("dsjkffdgrrhreer",""+data);
        ArrayList<RoomHistory> roomPreViewModel;
        RoomHistoryModel selectedRoomModels;

        Gson gson = new Gson();
        selectedRoomModels=gson.fromJson(data, RoomHistoryModel.class);
        for(RoomHistory d:selectedRoomModels.getBanerData())
        {
            LayoutInflater inflater2 = null;
            inflater2 = (LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE);
            final View mLinearView = inflater2.inflate(R.layout.bookinghistory, null);
            TextView name=mLinearView.findViewById(R.id.title);
            TextView bedtype=mLinearView.findViewById(R.id.bedtype);
            TextView room_size=mLinearView.findViewById(R.id.room_size);
            TextView type=mLinearView.findViewById(R.id.type);
            TextView date=mLinearView.findViewById(R.id.date);
            TextView child=mLinearView.findViewById(R.id.child);
            ImageView image=mLinearView.findViewById(R.id.image);

          //  TextView wifi=mLinearView.findViewById(R.id.wifi);
            name.setText(d.getRoom_type());
            bedtype.setText(d.getBed_type());
            room_size.setText("Room Size: "+d.getRoom_size());
            type.setText("Room Type: "+d.getAc_type());
            child.setText("Child :  "+d.getMax_child() +"     Adults :  "+ d.getMax_adult());
           // date.setText(d.getd);
            galleryimages=d.getGalleryimages().get(0);
            String img=galleryimages.getImage();
            String checkoutDate=d.getCreated_at();
            SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date time1= null;

            try {
                time1 = fmt.parse(dated);
                //   time2=newFormat.parse(checkoutDate);
                String sd = newFormat.format(time1);
                // String ed = newFormat.format(time2);
                date.setText("Booking Date:  "+sd+"");

            } catch (ParseException e) {
                e.printStackTrace();
            }

            Glide.with(mLinearView)
                    .load(ApplicationConstant.INSTANCE.baseUrl + img)
                    .placeholder(R.drawable.imm)
                    .fitCenter()
                    .into(image);
            layout_list.addView(mLinearView);
        }

    }

}