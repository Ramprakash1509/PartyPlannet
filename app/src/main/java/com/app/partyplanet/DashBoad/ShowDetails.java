package com.app.partyplanet.DashBoad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.partyplanet.Adpters.DasboadAdapter;
import com.app.partyplanet.Adpters.GalleryAdapter;
import com.app.partyplanet.Data.DasboadModel;
import com.app.partyplanet.Data.Data;
import com.app.partyplanet.Data.Dataum;
import com.app.partyplanet.Data.Galleryimages;
import com.app.partyplanet.Data.RoomData;
import com.app.partyplanet.Lawns.LawnBookingActivity;
import com.app.partyplanet.Model.list;
import com.app.partyplanet.R;
import com.app.partyplanet.Restaurants.Restaurantbooking;
import com.app.partyplanet.Utils.ApplicationConstant;
import com.app.partyplanet.Utils.UtilsMethod;
import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ShowDetails extends AppCompatActivity implements View.OnClickListener, GalleryAdapter.onListClickedRowListner {
    String data;
    Dataum dataum;
    list list;
    TextView showreview,hotel,hotel_name,parking, renovation,tv_address, description,policies,contact,email,offer_price,current_price,language,health_standard;
    ImageView image;
    Button booking,addcart;
    String secureloginResponse;
    String id="";
    String listId="";
    Object language_obj;
    ImageView back;
    String status;
    Button write_review;
    String hotel_id;
    RecyclerView recyclerView_gall;
    DasboadModel dasboadModel;
    ArrayList<Dataum> banerData;
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;
    LinearLayout linearlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        image=findViewById(R.id.image);
        hotel=findViewById(R.id.hotel);
        recyclerView_gall=findViewById(R.id.recyclerView_gall);
        back=findViewById(R.id.back);
        hotel_name = findViewById(R.id.name);
        linearlayout = findViewById(R.id.linearlayout);
        showreview = findViewById(R.id.showreview);
        tv_address = findViewById(R.id.tv_address);
        description = findViewById(R.id.description);
        policies = findViewById(R.id.policies);
        contact = findViewById(R.id.contact);
        email = findViewById(R.id.email);
        booking = findViewById(R.id.booking);
        addcart = findViewById(R.id.addcart);
        offer_price=findViewById(R.id.offer_price);
        current_price=findViewById(R.id.current_price);
        language=findViewById(R.id.language);
        health_standard=findViewById(R.id.health_standard);
        renovation=findViewById(R.id.renovation);
        parking=findViewById(R.id.parking);
        booking.setOnClickListener(this);
        addcart.setOnClickListener(this);
        back.setOnClickListener(this);
        write_review=findViewById(R.id.write_review);
        write_review.setOnClickListener(this);
        showreview.setOnClickListener(this);
        status=getIntent().getStringExtra("status");
        Log.d("statuwerertgfdfgghdfgbs",""+status);
        data = getIntent().getStringExtra("data");
        dataum = new Gson().fromJson(data, Dataum.class);
        list = new Gson().fromJson(data, list.class);
        parshData(list);
        Log.d("ksjdhfjgbdbvhjsdif", "" + dataum.getLong_description());
        hotel_name.setText(dataum.getName());
        tv_address.setText(dataum.getAddress());
        description.setText(dataum.getLong_description());
        contact.setText("Phone No.  (+91)-"+dataum.getPhone_no());
        email.setText("Email:  "+dataum.getEmail_id());
        offer_price.setText("₹ "+dataum.getOffer_price());
        current_price.setText("₹ "+dataum.getCurrent_price());
        health_standard.setText(""+dataum.getHealth_and_hygiene());
        renovation.setText(""+dataum.getRenovation_and_closures_policy());
        hotel_id=dataum.getId();

         language.setText(""+dataum.getLanguages());
        parking.setText(""+dataum.getParking_policy());
        language_obj=dataum.getLanguages();
        String value= String.valueOf(language_obj);
        if (dataum.getModule_slug().equals("hotels"))
        {
            hotel.setText("Hotel Details");
            booking.setText("BOOK A STAY");
        }
        else if(dataum.getModule_slug().equals("restaurants"))
        {
            hotel.setText("Restaurant Details");
            booking.setText("BOOK NOW");
        }
        else if(dataum.getModule_slug().equals("lounges"))
        {
            hotel.setText("Lounges Details");
            booking.setText("BOOK NOW");
        }
        else if(dataum.getModule_slug().equals("lawns"))
        {

            linearlayout.setVisibility(View.VISIBLE);
            hotel.setText("Lawns Details");
            booking.setText("BOOK NOW");
        }


        policies.setText(dataum.getCatering_policy()+" and "+ dataum.getDj_policy()+"and "+dataum.getDecor_policy());
        Glide.with(this)
                .load(ApplicationConstant.INSTANCE.baseUrl+dataum.getBanner_img())
                .fitCenter()
                .error(R.drawable.imm)
                .into(image);
        listId=dataum.getId();
        SharedPreferences myPreferences = this.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        secureloginResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data securelogincheckResponse = new Gson().fromJson(secureloginResponse, Data.class);
        id = securelogincheckResponse.getId();
        Log.d("djgfhjsgdvf",id);
        if (status.equalsIgnoreCase("1"))
        {
            addcart.setVisibility(View.GONE);
        }
        else if (status.equalsIgnoreCase("2"))
        {
            addcart.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v)
    {
        if (v==booking)
        {
            if (dataum.getModule_slug().equals("hotels"))
            {
                startActivity(new Intent(this, BookingHotel.class).putExtra("data", data));
                UtilsMethod.INSTANCE.getRoomsList(this, hotel_id);
            }
            else if(dataum.getModule_slug().equals("restaurants"))
            {     SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                  SharedPreferences.Editor editor = prefs.edit();
                  editor.putString("listingId", hotel_id);
                  editor.commit();
                  UtilsMethod.INSTANCE.restaurantList(ShowDetails.this,dataum.getId(),data ,hotel_id);
           }
        else if(dataum.getModule_slug().equals("lounges"))
        {
            Toast.makeText(this, "Available soon", Toast.LENGTH_SHORT).show();
            SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("listingId", hotel_id);
            editor.commit();
            UtilsMethod.INSTANCE.restaurantList(ShowDetails.this,dataum.getId(),data ,hotel_id);


        }
        else if(dataum.getModule_slug().equals("lawns"))
        {
           // Toast.makeText(this, "Available soon", Toast.LENGTH_SHORT).show();
            Intent i=new Intent(ShowDetails.this, LawnBookingActivity.class);
            Log.d("ewury43iu557",dataum.getVeg_menu());
            i.putExtra("data",data);
            startActivity(i);
        }



        }
        if (v==addcart)
        {
            UtilsMethod.INSTANCE.addToCart(ShowDetails.this,id,listId,dataum.getName().toString());
        }
        if (v==back)
        {
            finish();
        }
        if (v==write_review)
        {
            Log.d("lotelId",hotel_id);
            startActivity(new Intent(this,WriteReview.class).putExtra("hotelId",hotel_id).putExtra("userId",id));
        }
        if (v==showreview)
        {
            startActivity(new Intent(this,ShowReview.class).putExtra("hotel_id",id));
            UtilsMethod.INSTANCE.showReview(ShowDetails.this,hotel_id);
             Log.d("hotel_id",hotel_id);
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();}
    public void parshData(list  list)
    {    Gson gson = new Gson();
        Log.d("kjgchgsudvjksbcvhusguvg",""+list);
        // Dataum dasboadModel = gson.fromJson(value, Dataum.class);
        ArrayList<Galleryimages>  banerData = list.getGalleryimages();
        GalleryAdapter adapter=new GalleryAdapter(this,banerData,this);
        recyclerView_gall.setHasFixedSize(false);
        recyclerView_gall.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView_gall.setLayoutManager(llm);
        recyclerView_gall.setAdapter(adapter);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        scaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    @Override
    public void onListSelected(int position)
    {


    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener
    {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector)
        {
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
            recyclerView_gall.setScaleX(mScaleFactor);
            recyclerView_gall.setScaleY(mScaleFactor);
            return true;
        }
    }




}