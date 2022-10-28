package com.app.partyplanet.DashBoad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.partyplanet.Adpters.AllReviewsAdapter;
import com.app.partyplanet.Adpters.DasboadAdapter;
import com.app.partyplanet.Data.DasboadModel;
import com.app.partyplanet.Data.Data;
import com.app.partyplanet.Data.ReviewList;
import com.app.partyplanet.Data.secureLoginResponse;
import com.app.partyplanet.R;
import com.app.partyplanet.Utils.FragmentActivityMessage;
import com.app.partyplanet.Utils.GlobalBus;
import com.app.partyplanet.Utils.UtilsMethod;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class ShowReview extends AppCompatActivity implements View.OnClickListener {
    String hotel_id;
    secureLoginResponse data;
    ArrayList<ReviewList> reviewLists;
    RecyclerView recyclerview;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_review);
        hotel_id=getIntent().getStringExtra("hotel_id");
        Log.d("efjguagfjchcc",hotel_id+"           bvjkbvjbxcv");
       // UtilsMethod.INSTANCE.showReview(ShowReview.this,hotel_id);
        recyclerview=findViewById(R.id.recyclerview);
        back=findViewById(R.id.back);
        back.setOnClickListener(this);
    }

    @Subscribe
    public void onFragmentActivityMessage(FragmentActivityMessage activityFragmentMessage)
    {
        if (activityFragmentMessage.getMessage().equalsIgnoreCase("showReview"))
        {
            dataParse(activityFragmentMessage.getFrom());
        }

    }

    private void dataParse(String from)
    {

        Log.d("romddjdf",from);
        Gson gson = new Gson();
        data = gson.fromJson(from, secureLoginResponse.class);
        if (data.getData().getTotalReviews()>0)
        {
            reviewLists = data.getData().getReviewLists();
            AllReviewsAdapter adapter = new AllReviewsAdapter(this, reviewLists);
            recyclerview.setHasFixedSize(false);
            recyclerview.setLayoutManager(new LinearLayoutManager(ShowReview.this));
            LinearLayoutManager llm = new LinearLayoutManager(ShowReview.this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerview.setLayoutManager(llm);
            recyclerview.setAdapter(adapter);
        }
        else
        {
            Toast.makeText(this, "No_Review ", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public void onDestroy() {
        // Unregister the registered event.
        GlobalBus.getBus().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onStart()
    {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            GlobalBus.getBus().register(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (v==back)
        {
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}