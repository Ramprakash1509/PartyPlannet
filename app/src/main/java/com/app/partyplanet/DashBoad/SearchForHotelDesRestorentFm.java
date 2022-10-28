package com.app.partyplanet.DashBoad;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.partyplanet.R;
import com.app.partyplanet.Utils.UtilsMethod;

import java.util.Calendar;
import java.util.Date;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class SearchForHotelDesRestorentFm extends Fragment implements View.OnClickListener{
    ImageView backPressed;
    private PopupWindow pw;
    RelativeLayout search;
    private BlurView topBlurView;
    LinearLayout advice;
   String end_date="";
   String month_count="";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // getActivity().onBackPressed();
                startActivity(new Intent(getActivity(),DashBoad.class));
                getActivity().finish();
               /* Fragment dasboadfragment=new Dasboadfragment();
                Fragment fragment = new Dasboadfragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
               // fragmentTransaction.replace(R.id.main_container, fragment);
                fragmentTransaction.add(dasboadfragment, "detail") // Add this transaction to the back stack (name is an optional name for this back stack state, or null).
                        .addToBackStack(null)
                        .commit();
             //   fragmentTransaction.commit();
*/
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v= inflater.inflate(R.layout.fragment_search_for_hotel_des_restorent_fm, container, false);
        GetId(v);
        return v;
    }

    private void GetId(View v)
    {
        backPressed=v.findViewById(R.id.backPressed);
        backPressed.setOnClickListener(this);
        search=v.findViewById(R.id.search);
        advice=v.findViewById(R.id.advice);
        search.setOnClickListener(this);
        advice.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v==backPressed)
        {
             startActivity(new Intent(getActivity(),DashBoad.class));
          /* Fragment fragment = new Dasboadfragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.addToBackStack("fragmentone");
            fragmentTransaction.remove(null).commit();*/
        }
        if (v==search)
        {
            onButtonShowPopupWindowClick(v);
        }
        if (v==advice)
        {
            startActivity(new Intent(getActivity(),CovidAdvice.class));
        }

    }

    public void onButtonShowPopupWindowClick(View view)
    {
        float radius = 10f;
        View decorView =getActivity().getWindow().getDecorView();
        ViewGroup rootView = (ViewGroup) decorView.findViewById(android.R.id.content);
        Drawable windowBackground = decorView.getBackground();

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.searchpopup, null);
        //////////////////////////////////////////////////
        //date piker
        ImageView btPickDate=popupView.findViewById(R.id.date);
        TextView tv_date=popupView.findViewById(R.id.checkIn);



        topBlurView=popupView.findViewById(R.id.blurView);
        topBlurView.setupWith(rootView, new RenderScriptBlur(getActivity()))
                .setFrameClearDrawable(windowBackground) // Optional
                .setBlurRadius(radius);
        EditText searching=popupView.findViewById(R.id.searching);
        Button   search=popupView.findViewById(R.id.search);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        popupWindow.setOutsideTouchable(false);


              search.setOnClickListener(new View.OnClickListener()
              {
                  @Override
                  public void onClick(View v) {

                      if (searching.getText().toString().isEmpty()) {
                          searching.setError("Is not Allowed blank");
                          searching.requestFocus();
                      } else if (UtilsMethod.INSTANCE.isNetworkAvialable(getActivity()) == false) {
                          Toast.makeText(getActivity(), "Please Check Your Network Connectivity", Toast.LENGTH_SHORT).show();
                      } else {
                          UtilsMethod.INSTANCE.searching(getActivity(), searching.getText().toString());
                          startActivity(new Intent(getActivity(), SearchingShowData.class));
                          popupWindow.dismiss();
                      }
                  }
              });
          }
  /*  public void Datepicker()
    {

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.datepicker_pop, null);

        Button tvLater =  view.findViewById(R.id.tv_later);
        Button tv_ok =  view.findViewById(R.id.tv_ok);
        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);

        final Dialog dialog = new Dialog(getActivity());

        dialog.setCancelable(false);
        dialog.setContentView(view);

        Calendar today = Calendar.getInstance();
        long now = today.getTimeInMillis();
        datePicker.setMinDate(now);

        Date currentTime = Calendar.getInstance().getTime();

        String timewah=currentTime.toString().replace(" ",",");

        String[] recent;
        recent = timewah.split(",");

        Log.e("currentTime","currentTime :   "+ recent[3] );

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                final Calendar calendar = Calendar.getInstance();
                dialog.dismiss();
                calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                String dateString = UtilsMethod.INSTANCE.dateForamterer(calendar.getTime());
                // ed_date.setText( datePicker.getYear() + "-" +  (datePicker.getMonth()+1) + "-" + datePicker.getDayOfMonth() );

             //  ed_date.setText(dateString);
                int up_mounth_it;
                int month_count_int = Integer.parseInt(month_count);

                up_mounth_it=(datePicker.getMonth()+1);
                up_mounth_it = up_mounth_it + month_count_int;

                if( up_mounth_it >= 12 ){

                    // more the 12

                    int mo_up_mounth_it= up_mounth_it - 12;

                    Log.e("end_date_vasa","AAA : "+ up_mounth_it +"  : month_count_int :  "+ month_count_int +"  : mo_up_mounth_it : "+ mo_up_mounth_it);

                    final Calendar calendarss = Calendar.getInstance();
                    calendarss.set(2023,mo_up_mounth_it,datePicker.getDayOfMonth());
                    String dateStrings = UtilsMethod.INSTANCE.dateForamterer(calendarss.getTime());
                    end_date=dateStrings;


                }else {
                    final Calendar calendarss1= Calendar.getInstance();
                    calendarss1.set(datePicker.getYear(),up_mounth_it,datePicker.getDayOfMonth());
                    String dateStrings1 = UtilsMethod.INSTANCE.dateForamterer(calendarss1.getTime());
                    end_date=dateStrings1;


                    Log.e("end_date_vasa","BBB : "+ up_mounth_it +"  : month_count_int :  "+ month_count_int);

                }

            }
        });



        tvLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

            }
        });



        dialog.show();
    }*/




}