package com.app.partyplanet.DashBoad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.partyplanet.R;

public class Helpdesk extends AppCompatActivity implements View.OnClickListener {
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpdesk);
        GetId();
    }

    private void GetId() {
        back=findViewById(R.id.back);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (v==back)
        {
            finish();
        }
    }
}