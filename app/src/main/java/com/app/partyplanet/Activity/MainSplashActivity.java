package com.app.partyplanet.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.partyplanet.DashBoad.DashBoad;
import com.app.partyplanet.R;
import com.app.partyplanet.Utils.ApplicationConstant;
import com.app.partyplanet.Utils.UtilsMethod;

import java.util.Timer;
import java.util.TimerTask;

public class MainSplashActivity extends AppCompatActivity {
    String type="";
     ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_splash);
        logo=findViewById(R.id.logo);



        SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        type = myPreferences.getString(ApplicationConstant.INSTANCE.one, "");
        if (type.equalsIgnoreCase("1"))
        {    if (UtilsMethod.INSTANCE.isNetworkAvialable(this) == false)
           {
               logo.setVisibility(View.VISIBLE);
              Toast.makeText(this, "Please Check Your Network Connection", Toast.LENGTH_SHORT).show();


           }
           else {
            Intent intent = new Intent(MainSplashActivity.this, DashBoad.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
             }
          }
        else {
            if (UtilsMethod.INSTANCE.isNetworkAvialable(this) == false)
            {    logo.setVisibility(View.VISIBLE);
                Toast.makeText(this, "Please Check Your Network Connection", Toast.LENGTH_SHORT).show();

            }
            else {
                Intent intent = new Intent(MainSplashActivity.this, SplashActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }


    }
}