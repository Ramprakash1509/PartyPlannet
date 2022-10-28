package com.app.partyplanet.DashBoad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.app.partyplanet.Adpters.DasboadAdapter;
import com.app.partyplanet.Adpters.SearchAdapter;
import com.app.partyplanet.Data.DasboadModel;
import com.app.partyplanet.Data.Dataum;
import com.app.partyplanet.R;
import com.app.partyplanet.Utils.FragmentActivityMessage;
import com.app.partyplanet.Utils.GlobalBus;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class SearchingShowData extends AppCompatActivity implements View.OnClickListener {
private RecyclerView recyclerview;
private LottieAnimationView lottieAnimationView;
     ImageView back;
    DasboadModel dasboadModel;
    ArrayList<Dataum> banerData;
    Button cart,filter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_show_data);
        GetId();
    }

    private void GetId()
    {

        lottieAnimationView=findViewById(R.id.lottie_main);
        lottieAnimationView.setOnClickListener(this);
        recyclerview=findViewById(R.id.recyclerview);
        filter=findViewById(R.id.filter);
        cart=findViewById(R.id.cart);
        cart.setOnClickListener(this);
        filter.setOnClickListener(this);
        back=findViewById(R.id.back);
        back.setOnClickListener(this);
    }

    @Subscribe
    public void onFragmentActivityMessage(FragmentActivityMessage activityFragmentMessage)
    {
        if (activityFragmentMessage.getMessage().equalsIgnoreCase("searchingmoduls"))
        {
            dataParse(activityFragmentMessage.getFrom());
        }

    }

    private void dataParse(String from)
    {
        Log.d("kdghkhvcxvbdgivvugjd",""+from);
        Gson gson = new Gson();
        dasboadModel = gson.fromJson(from, DasboadModel.class);
        banerData = dasboadModel.getBanerData();
        SearchAdapter adapter=new SearchAdapter(SearchingShowData.this,banerData);
        recyclerview.setHasFixedSize(false);
        recyclerview.setLayoutManager(new LinearLayoutManager(SearchingShowData.this));
        LinearLayoutManager llm = new LinearLayoutManager(SearchingShowData.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(llm);
        recyclerview.setAdapter(adapter);

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
    public void onClick(View v)
    {
        if (v==back || v== lottieAnimationView)
        {
            finish();
        }
        if (v==cart)
        {
            startActivity(new Intent(SearchingShowData.this,CartViewActivity.class));

        }
        if (v==filter)
        {
            startActivity(new Intent(SearchingShowData.this,FilterActivity.class));
        }

    }
}