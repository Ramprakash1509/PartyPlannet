package com.app.partyplanet.DashBoad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.app.partyplanet.R;

public class SuccessFull extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_full);
    }

    @Override
    public void onBackPressed()
    {super.onBackPressed();
     startActivity(new Intent(SuccessFull.this,DashBoad.class));
    }
}