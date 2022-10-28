package com.app.partyplanet.Lawns;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.app.partyplanet.Data.Dataum;
import com.app.partyplanet.R;
import com.app.partyplanet.Restaurants.RestauMenuBooking;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LawnBookingActivity extends AppCompatActivity implements View.OnClickListener {
    DatePickerDialog datePicker;
    private int mYear, mMonth, mDay, mHour, mMinute;
    ImageView back;
    RadioButton radia_id1,radia_id2;
    String data,date;
    Dataum dataum;
    TextView vegprice,nonvegprice,slectdate,enddate,hotel;
    EditText function,guest;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawn_booking2);
        radia_id1=findViewById(R.id.radia_id1);
        radia_id2=findViewById(R.id.radia_id2);
        vegprice=findViewById(R.id.vegprice);
        enddate=findViewById(R.id.enddate);
        hotel=findViewById(R.id.hotel);
        function=findViewById(R.id.function);
        guest=findViewById(R.id.guest);
        back=findViewById(R.id.back);
        back.setOnClickListener(this);
        nonvegprice=findViewById(R.id.nonvegprice);
        slectdate=findViewById(R.id.slectdate);
        data=getIntent().getStringExtra("data");
        dataum = new Gson().fromJson(data, Dataum.class);
        vegprice.setText("₹. "+dataum.getVeg_menu());
        nonvegprice.setText("₹. "+dataum.getNon_veg_menu());
        hotel.setText(dataum.getName());


        final Calendar c = Calendar.getInstance();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            datePicker = new DatePickerDialog(LawnBookingActivity.this);
        }
        slectdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(LawnBookingActivity.this, new DatePickerDialog.OnDateSetListener() {
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

        enddate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(LawnBookingActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                            enddate.setText(sd);
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

    }

    @Override
    public void onClick(View v) {
        finish();

    }
}