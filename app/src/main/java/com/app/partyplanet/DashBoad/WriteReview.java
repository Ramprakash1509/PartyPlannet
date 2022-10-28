package com.app.partyplanet.DashBoad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.partyplanet.Data.Data;
import com.app.partyplanet.R;
import com.app.partyplanet.Utils.ApplicationConstant;
import com.app.partyplanet.Utils.Loader;
import com.app.partyplanet.Utils.UtilsMethod;
import com.google.gson.Gson;

public class WriteReview extends AppCompatActivity implements View.OnClickListener {
  SeekBar seekBar;
  String hotel_id,secureloginResponse,userId;
  TextView value;
  ImageView back_review;
  String r1,r2,r3,r4,r5;
  Button submit;
  TextView comments;
    Loader loader;

  RatingBar ratingBar,ratingBar_staffandeservice,ratingBar_amenties,ratingBar_eco_friendness,ratingBar_facilities;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);
        seekBar=findViewById(R.id.seekBar);
        ratingBar=findViewById(R.id.ratingBar);
        submit=findViewById(R.id.submit);
        ratingBar_staffandeservice=findViewById(R.id.ratingBar_staffandeservice);
        ratingBar_amenties=findViewById(R.id.ratingBar_amenties);
        ratingBar_eco_friendness=findViewById(R.id.ratingBar_eco_friendness);
        ratingBar_facilities=findViewById(R.id.ratingBar_facilities);
        back_review=findViewById(R.id.back_review);
        comments=findViewById(R.id.comments);
        back_review.setOnClickListener(this);
        submit.setOnClickListener(this);
        loader = new Loader(this, android.R.style.Theme_Translucent_NoTitleBar);

       /* SharedPreferences myPreferences = this.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        secureloginResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data securelogincheckResponse = new Gson().fromJson(secureloginResponse, Data.class);
        userId = securelogincheckResponse.getId();*/

      /*  ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser)
            {
                value2= String.valueOf(rating);
                Toast.makeText(WriteReview.this, ""+rating, Toast.LENGTH_SHORT).show();
            }
        });*/
        hotel_id=getIntent().getStringExtra("hotelId");
        userId=getIntent().getStringExtra("userId");

       /* seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                //value.setText(""+progress);
                ratingBar.setVisibility(View.VISIBLE);
                ratingBar.setNumStars(progress);
                ratingBar.setRating( progress);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });*/
      //UtilsMethod.INSTANCE.review(WriteReview.this,hotel_id,userId,"3","4","4","5","4","dfjkhjkjv");
      //  Log.d("value2",value2);
    }

    @Override
    public void onClick(View v)
    {
        if (v==back_review)
        {
            finish();
        }
        if (v==submit)
        {
           if (UtilsMethod.INSTANCE.isNetworkAvialable(this) == false)
           {
            Toast.makeText(this, "Please Check Your Network Connectivity", Toast.LENGTH_SHORT).show();
          }
           else {
               loader.show();
               loader.setCancelable(false);
               loader.setCanceledOnTouchOutside(false);
               Log.d("whduy", String.valueOf(ratingBar.getRating()));
               UtilsMethod.INSTANCE.review(loader,WriteReview.this, hotel_id, userId, String.valueOf(ratingBar.getRating()), String.valueOf(ratingBar_staffandeservice.getRating()), String.valueOf(ratingBar_amenties.getRating()), String.valueOf(ratingBar_eco_friendness.getRating()), String.valueOf(ratingBar_facilities.getRating()), comments.getText().toString());

           }
        }

    }
}