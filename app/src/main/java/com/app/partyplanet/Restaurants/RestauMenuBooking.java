package com.app.partyplanet.Restaurants;

import static com.app.partyplanet.R.color.red;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.partyplanet.DashBoad.BookingHotel;
import com.app.partyplanet.Data.DasboadModel;
import com.app.partyplanet.Data.Data;
import com.app.partyplanet.R;
import com.app.partyplanet.RestaurantData.CartViewData;
import com.app.partyplanet.RestaurantData.CartViewModel;
import com.app.partyplanet.RestaurantData.TimeSlotData;
import com.app.partyplanet.RestaurantData.TimeSlotModel;
import com.app.partyplanet.ResttaurantAdapter.FoodMenuViewAdpter;
import com.app.partyplanet.ResttaurantAdapter.RestaurantAdeptar;
import com.app.partyplanet.ResttaurantAdapter.TimeSlotAdapter;
import com.app.partyplanet.Utils.ApplicationConstant;
import com.app.partyplanet.Utils.FragmentActivityMessage;
import com.app.partyplanet.Utils.GlobalBus;
import com.app.partyplanet.Utils.UtilsMethod;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class RestauMenuBooking extends AppCompatActivity implements View.OnClickListener
{
    DatePickerDialog datePicker;
    private int mYear, mMonth, mDay, mHour, mMinute;
    TextView slectdate,amt;
    TimeSlotModel timeSlotModel;
    ArrayList<TimeSlotData> timeSlotData;
    String date="";
    String dated;
    private TextView dateTimeDisplay;
    Spinner spinner;
    String[] persion = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
    private SimpleDateFormat dateFormat;
    RecyclerView timeslot,recyclerview;
    Button bookNow;
    String restaurantId;
      int id;
    CartViewModel cartViewModel;
    int totalamount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restau_menu_booking);
        slectdate = findViewById(R.id.slectdate);
        spinner = findViewById(R.id.persion_spinner);
        bookNow = findViewById(R.id.bookNow);
        amt = findViewById(R.id.amt);
        bookNow.setOnClickListener(this);

        timeslot = findViewById(R.id.timeslot);
        recyclerview = findViewById(R.id.recyclerview);
        restaurantId = getIntent().getStringExtra("restaurantId");
        final Calendar c = Calendar.getInstance();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            datePicker = new DatePickerDialog(RestauMenuBooking.this);
        }
        slectdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(RestauMenuBooking.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        // showdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        //date=(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        date = (year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date time1 = null;
                        try {
                            time1 = sdf.parse(date);
                            SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");
                            String sd = newFormat.format(time1);
                            slectdate.setText(sd);
                           /* dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                            dated = dateFormat.format(c.getTime());
                            slectdate.setText(date);*/


                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }
                }, mYear, mMonth, mDay);

                /* datePickerDialog.show();*/

                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                datePickerDialog.show();
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, persion);
        spinner.setAdapter(adapter);

    }

    @Subscribe
    public void onFragmentActivityMessage(FragmentActivityMessage activityFragmentMessage) {
        if (activityFragmentMessage.getMessage().equalsIgnoreCase("TimeSlot"))
        {
            dataParse(activityFragmentMessage.getFrom());
        }
        else if (activityFragmentMessage.getMessage().equalsIgnoreCase("ShowFoodMenu"))
        {
            foodmenuList(activityFragmentMessage.getFrom());
        }

    }

    private void foodmenuList(String from)
    {
        Gson gson = new Gson();
        cartViewModel = gson.fromJson(from, CartViewModel.class);
        ArrayList<CartViewData> data = cartViewModel.getData();
        Collections.reverse(cartViewModel.getData());
        FoodMenuViewAdpter adapter=new FoodMenuViewAdpter(RestauMenuBooking.this,data);
        recyclerview.setHasFixedSize(false);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(llm);
        recyclerview.setAdapter(adapter);
    }

    private void dataParse(String from)
    {
        Log.d("ssdfwedfskdhf", "" + from);
        Gson gson = new Gson();
        timeSlotModel = gson.fromJson(from, TimeSlotModel.class);
        timeSlotData = timeSlotModel.getData();
        TimeSlotAdapter adapter=new TimeSlotAdapter(RestauMenuBooking.this,timeSlotData);
        timeslot.setHasFixedSize(false);
        timeslot.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        timeslot.setLayoutManager(llm);
        timeslot.setAdapter(adapter);

    }

  public  void getDataAdapter(int  carId)
  { id=carId;
  }
public void getTotalAmn(int amttotal)
{

    totalamount=amttotal+totalamount;
    amt.setText("Total amount:  "+totalamount);

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
        if (v==bookNow)
        {
            getSlot();
        }
    }
    private void getSlot()
    {
        Log.d("amtt",""+totalamount);
        SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String secureloginResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data securelogincheckResponse = new Gson().fromJson(secureloginResponse, Data.class);
        String userId = securelogincheckResponse.getId();
        String persion = String.valueOf(spinner.getSelectedItem());
      //  Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
        Log.d("sdfbjdsfnvjdfvj", id + "  " + persion + "  " + date);
        if (date.isEmpty())
        {
            Toast.makeText(RestauMenuBooking.this, "Please Select  Date", Toast.LENGTH_SHORT).show();

        }
        else if (id<=0)
        {    Toast.makeText(RestauMenuBooking.this, "Please select time slot", Toast.LENGTH_SHORT).show();

        } else if (persion.isEmpty()) {
            Toast.makeText(RestauMenuBooking.this, "please select Guests ", Toast.LENGTH_SHORT).show();
        } else if (UtilsMethod.INSTANCE.isNetworkAvialable(RestauMenuBooking.this) == false) {
            Toast.makeText(RestauMenuBooking.this, "Please Check Your Network Connectivity", Toast.LENGTH_SHORT).show();
        } else {
            UtilsMethod.INSTANCE.checkSlotAvailability(RestauMenuBooking.this, userId, date, persion, String.valueOf(id), restaurantId, String.valueOf(totalamount));
        }

    }
}