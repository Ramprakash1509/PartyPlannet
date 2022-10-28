package com.app.partyplanet.Lounges;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.partyplanet.Data.Dataum;
import com.app.partyplanet.R;
import com.app.partyplanet.RestaurantData.ResturantData;
import com.app.partyplanet.RestaurantData.ResturantModel;

import java.util.ArrayList;

public class LoungeBooking extends AppCompatActivity {
    String listvalue,data;
    Dataum dataum;
    ImageView image,back;
    TextView name;
    RecyclerView recyclerview;
    ResturantModel resturantModel;
    ArrayList<ResturantData> resturantData;
    Button reserve;
    String restaurantId;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lounge_booking);
    }
}