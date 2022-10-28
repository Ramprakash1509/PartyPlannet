package com.app.partyplanet.Restaurants;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.partyplanet.Data.RoomHistory;
import com.app.partyplanet.Data.RoomHistoryModel;
import com.app.partyplanet.R;
import com.app.partyplanet.Utils.ApplicationConstant;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FoodMenuHistory extends AppCompatActivity {
    RecyclerView recyclerview;
    String data ,dated;
    LinearLayout layout_list;
  ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_menu_history);
        layout_list = findViewById(R.id.layout_list);
        data = getIntent().getStringExtra("data");
        dated = getIntent().getStringExtra("date");
        ShowRooms(data);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void ShowRooms(String data)
    {
        Log.d("dsjkffdgrrhreer", "" + data);
        ArrayList<RoomHistory> roomPreViewModel;
        RoomHistoryModel selectedRoomModels;

        Gson gson = new Gson();
        selectedRoomModels = gson.fromJson(data, RoomHistoryModel.class);
        for (RoomHistory d : selectedRoomModels.getBanerData()) {
            LayoutInflater inflater2 = null;
            inflater2 = (LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE);
            final View mLinearView = inflater2.inflate(R.layout.foodmenu_history, null);
            TextView name = mLinearView.findViewById(R.id.title);
            TextView price = mLinearView.findViewById(R.id.price);
            TextView plate=mLinearView.findViewById(R.id.plate);
            TextView quantity = mLinearView.findViewById(R.id.quantity);
            TextView date = mLinearView.findViewById(R.id.date);
            TextView subtitle = mLinearView.findViewById(R.id.subtitle);
            ImageView image = mLinearView.findViewById(R.id.image);
            // TextView wifi=mLinearView.findViewById(R.id.wifi);

           Log.d("345456",d.getType());
            if (Integer.parseInt(d.getType())==1)
            {
                plate.setText("Full Plate ");
            }
            else if (Integer.parseInt(d.getType())==2)
            {
                plate.setText("Half Plate ");
            }
            name.setText(d.getTitle());
            price.setText("रु. "+d.getTotal_amt());
            quantity.setText(d.getQty());
            subtitle.setText(d.getSub_title());

            String checkoutDate=d.getCreated_at();
            SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date time1= null;
            try {
                time1 = fmt.parse(dated);
                //   time2=newFormat.parse(checkoutDate);
                String sd = newFormat.format(time1);
                // String ed = newFormat.format(time2);
               date.setText("Booking Date: "+sd+"");

            } catch (ParseException e) {
                e.printStackTrace();
            }// date.setText(d.getd);
            Glide.with(mLinearView)
                    .load(ApplicationConstant.INSTANCE.baseUrl + d.getImage())
                    .placeholder(R.drawable.imm)
                    .fitCenter()
                    .into(image);
            layout_list.addView(mLinearView);
        }

    }
}