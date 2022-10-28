package com.app.partyplanet.DashBoad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.partyplanet.Data.DasboadModel;
import com.app.partyplanet.Data.Data;
import com.app.partyplanet.Data.secureLoginResponse;
import com.app.partyplanet.R;
import com.app.partyplanet.Utils.ApplicationConstant;
import com.app.partyplanet.Utils.UtilsMethod;
import com.google.gson.Gson;

public class FinalBookingActivity extends AppCompatActivity implements View.OnClickListener {
   String secureloginResponse;
   EditText fname,lname,e_mail,contact,adharnumber,country;
   TextView actualprice,price;
   Button reserve;
   String old_price,offer_price,cartId,selectRoom,userId,roomsNo;
    Data data;
    secureLoginResponse secureLoginResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_booking);
        contact=findViewById(R.id.contact);
        fname=findViewById(R.id.fname);
        lname=findViewById(R.id.lname);
        e_mail=findViewById(R.id.email);
        adharnumber=findViewById(R.id.adharnumber);
        country=findViewById(R.id.country);
        reserve=findViewById(R.id.reserve);
        actualprice=findViewById(R.id.actualprice);
        price=findViewById(R.id.price);
        reserve.setOnClickListener(this);
       /* i.putExtra("data",new Gson().toJson(response.body()).toString());
        i.putExtra("old_price",old_price);
        i.putExtra("offer_price",oldoffer_price);*/
        old_price=getIntent().getStringExtra("old_price");
        offer_price=getIntent().getStringExtra("offer_price");
        cartId=getIntent().getStringExtra("data");
        actualprice.setText("Rs."+old_price);
        price.setText("Rs."+offer_price);
        Gson gson=new Gson();
        secureLoginResponse = gson.fromJson(cartId, secureLoginResponse.class);
        data=secureLoginResponse.getData();
        roomsNo=""+data.getCart_id();
        Log.d("24353425",""+data.getCart_id());

        Toast.makeText(this, ""+data.getCart_id(), Toast.LENGTH_SHORT).show();



        SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        secureloginResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data securelogincheckResponse = new Gson().fromJson(secureloginResponse, Data.class);
        contact.setText(securelogincheckResponse.getPhoneNo());
        fname.setText(securelogincheckResponse.getFirstName());
        lname.setText(securelogincheckResponse.getLastName());
        e_mail.setText(securelogincheckResponse.getEmail());
        country.setText(securelogincheckResponse.getCity());
        userId=securelogincheckResponse.getId();

    }

    @Override
    public void onClick(View v) {
        if (v==reserve)
        {
             if (contact.getText().toString().isEmpty())
             {
                 contact.setError("Enter Contact");
                 contact.requestFocus();
             }
            else if (fname.getText().toString().isEmpty())
            {
                fname.setError("Enter first Name");
                fname.requestFocus();
            }
             else if (lname.getText().toString().isEmpty())
             {
                 lname.setError("Enter last Name");
                 lname.requestFocus();
             }
             else if (e_mail.getText().toString().isEmpty())
             {
                 e_mail.setError("Enter E-mail");
                 e_mail.requestFocus();
             }
             else if (country.getText().toString().isEmpty())
             {
                 country.setError("Enter Country/region");
                 country.requestFocus();
             }

             else if (UtilsMethod.INSTANCE.isNetworkAvialable(this) == false)
             {
                 Toast.makeText(this, "Please Check Your Network Connectivity", Toast.LENGTH_SHORT).show();
             }
             else
             {
               //  startActivity(new Intent(FinalBookingActivity.this,BookingReview.class));
                 if (!roomsNo.isEmpty() && !userId.isEmpty())
                 {
                     Log.d("dbfhsbcj",roomsNo+"   "+userId);
                     UtilsMethod.INSTANCE.RoomDeatailsByCartid(FinalBookingActivity.this, roomsNo,userId,fname.getText().toString()+" "+lname.getText().toString());

                 }
                 else
                 {
                     UtilsMethod.INSTANCE.alertBox("","Please choose  room ? ",this);
                 }
             }

        }

    }
}