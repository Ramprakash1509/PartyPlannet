package com.app.partyplanet.DashBoad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.partyplanet.Activity.GetLocation;
import com.app.partyplanet.Activity.SignInEmail;
import com.app.partyplanet.Data.Data;
import com.app.partyplanet.R;
import com.app.partyplanet.Utils.ApplicationConstant;
import com.app.partyplanet.Utils.GlobalBus;
import com.app.partyplanet.Utils.UtilsMethod;
import com.bumptech.glide.Glide;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class DashBoad extends AppCompatActivity implements View.OnClickListener
        , BottomNavigationView.OnNavigationItemSelectedListener {
    DrawerLayout mDrawerLayout;
    RelativeLayout rlSideList;
    FrameLayout main_container;
    private Toolbar mToolbar;
    Button cart;
    GetLocation getLocation;
    Context context;
    Location gps_loc;
    Location network_loc;
    Location final_loc;
    double longitude;
    double latitude;
    String userCountry, userAddress;
    TextView city,address;
    String secureloginResponse;
    String userId;
    MeowBottomNavigation meowBottomNavigation;
    TextView profile,logOut, booking_history,home,tv_add_shop , tv_add_shop_list , id_Logout,adminNotification;
    CircleImageView drawer_img;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {   super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_boad);
        drawer_img=findViewById(R.id.drawer_img);
        GetId();
        initToolbar();
        UtilsMethod.INSTANCE.bannerSliderImage(DashBoad.this);
        UtilsMethod.INSTANCE.listingAllModule(DashBoad.this);
        UtilsMethod.INSTANCE.userServices(DashBoad.this);
        SharedPreferences myPreferences =getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        secureloginResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data securelogincheckResponse = new Gson().fromJson(secureloginResponse, Data.class);
        userId = securelogincheckResponse.getId();
        UtilsMethod.INSTANCE.bookingHistory(DashBoad.this,userId);

        drawer_img=findViewById(R.id.drawer_img);
        Glide.with(this)
                .load(ApplicationConstant.INSTANCE.baseUrl+securelogincheckResponse.getImage())
                .into( drawer_img);
     /*   meowBottomNavigation=findViewById(R.id.bottomnavigation);
        meowBottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_home));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.user));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_logout));

        meowBottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener()
        {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item)
            {
                Fragment fragment = null;

                switch (item.getId())
                {  case 1:
                    fragment = new Dasboadfragment();
                    break;
                    case 2:
                        fragment = new ProfileFragment();
                        break;
                    case 3:
                        fragment = new BookingHistory();
                        break;
                    case 4:
                        Logout_popup();
                        break;
                }
                 loadf(fragment);

            }
        });
        meowBottomNavigation.setCount(1,"10");
        meowBottomNavigation.show(2,true);
        meowBottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener()
        {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item)
            {

            }
        });
        meowBottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {

            }
        });*/






    }

    private void GetId()
    {
        city=findViewById(R.id.city);
        address=findViewById(R.id.address);
        mDrawerLayout=findViewById(R.id.drawer_layout);
        rlSideList=findViewById(R.id.side_list);
        profile=findViewById(R.id.profile_nav);
        cart=findViewById(R.id.cart);
        logOut=findViewById(R.id.logOut_nav);
        home=findViewById(R.id.home_nav);


        booking_history=findViewById(R.id.booking_history_nav);
        logOut.setOnClickListener(this);
        home.setOnClickListener(this);
        booking_history.setOnClickListener(this);
        profile.setOnClickListener(this);
        cart.setOnClickListener(this);
        loadFragment(new Dasboadfragment());
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            return ;}
        try {
            gps_loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            network_loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (gps_loc != null) {
            final_loc = gps_loc;
            latitude = final_loc.getLatitude();
            longitude = final_loc.getLongitude();
        }
        else if (network_loc != null) {
            final_loc = network_loc;
            latitude = final_loc.getLatitude();
            longitude = final_loc.getLongitude();
        }
        else {
            latitude = 0.0;
            longitude = 0.0;
        }
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE}, 1);
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0)
            {   userCountry = addresses.get(0).getLocality();
              //String userCountry1 = addresses.get(0).getSubLocality();

                userAddress = addresses.get(0).getAddressLine(0);
               city.setText(userCountry );
               address.setText(addresses.get(0).getSubLocality()+" "+addresses.get(0).getAdminArea());
            }
            else
            {   userCountry = "Unknown";
                //msg.setText(userCountry);
            }}
        catch (Exception e) {
            e.printStackTrace();}


        BottomNavigationView navigation = findViewById(R.id.bottam);
        navigation.setOnNavigationItemSelectedListener(this);



    }
    @Override
    public void onClick(View v)
    {
        if(v==profile)
        {
            closeDrawer();
            loadFragment(new ProfileFragment());
        }
        if (v==cart)
        {
            startActivity(new Intent(DashBoad.this,CartViewActivity.class));
        }
        if (v==logOut)
        {
            Logout_popup();
        }
      if (v==booking_history)
        {
            closeDrawer();
            loadFragment(new BookingHistory());
        }
      if (v==home)
      {
          closeDrawer();
          loadFragment(new Dasboadfragment());

      }

    }

    private void Logout_popup()
    {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewpop = inflater.inflate(R.layout.location_popup_box, null);

        Button okButton = (Button) viewpop.findViewById(R.id.okButton);
        Button Cancel = (Button) viewpop.findViewById(R.id.Cancel);
        TextView msg = (TextView) viewpop.findViewById(R.id.msg);

        final Dialog dialog = new Dialog(DashBoad.this);
        Cancel.setText("No");
        dialog.setCancelable(false);
        dialog.setContentView(viewpop);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        msg.setText("Do you want to logout ?");

        okButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                UtilsMethod.INSTANCE.logoutfromServer(DashBoad.this);

                dialog.dismiss();

            }
        });

        Cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        dialog.show();


    }
    private void loadf(Fragment fragment)
    {
        if (fragment != null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .commit();

        }

    }

    private boolean loadFragment(Fragment fragment)
    {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }



    public void closeDrawer()
    {
        mDrawerLayout.closeDrawer(rlSideList);
    }

    public void openDrawer() {

        mDrawerLayout.openDrawer(rlSideList);
        rlSideList=findViewById(R.id.side_list);
    }
    private void initToolbar()
    {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle("");

        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        mToolbar.setNavigationIcon(R.drawable.ic_lineing);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openDrawer();

            }
        });

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }






    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        Fragment fragment = null;

        switch (item.getItemId())
        {  case R.id.home:
                fragment = new Dasboadfragment();
                break;
            case R.id.profile:
                fragment = new ProfileFragment();
                break;
            case R.id.mybooking:
                fragment = new BookingHistory();
                break;
            case R.id.log_out:
                Logout_popup();
            break;
        }
        return loadFragment(fragment);

    }




}