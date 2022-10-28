package com.app.partyplanet.DashBoad;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.partyplanet.Adpters.BookingHistoryAdapter;
import com.app.partyplanet.Adpters.DasboadAdapter;
import com.app.partyplanet.Adpters.ServicesAdapter;
import com.app.partyplanet.Data.BookingHistoryModel;
import com.app.partyplanet.Data.DasboadModel;
import com.app.partyplanet.Data.Data;
import com.app.partyplanet.Data.SelectedRoomModel;
import com.app.partyplanet.R;
import com.app.partyplanet.Utils.ApplicationConstant;
import com.app.partyplanet.Utils.FragmentActivityMessage;
import com.app.partyplanet.Utils.GlobalBus;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;


public class BookingHistory extends Fragment
{

    RecyclerView recyclerview;
    String secureloginResponse;
    BookingHistoryModel bookingHistoryModel;
    ArrayList<SelectedRoomModel> selectedRoomModels;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_booking_history, container, false);
        GetId(v);
        return v;
    }
    public void  GetId(View v)
    {
        recyclerview=v.findViewById(R.id.recyclerview);
        SharedPreferences myPreferences = getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        secureloginResponse = myPreferences.getString(ApplicationConstant.INSTANCE.History, null);
       // Data securelogincheckResponse = new Gson().fromJson(secureloginResponse, Data.class);
        BookingHisList(secureloginResponse);


    }
    private void BookingHisList(String from)
    {
        Log.d("hjgdsdgusdfgfdgyuwe",from);
        Gson gson = new Gson();
        bookingHistoryModel = gson.fromJson(from, BookingHistoryModel.class);
        selectedRoomModels = bookingHistoryModel.getBanerData();
        Collections.reverse(bookingHistoryModel.getBanerData());
        BookingHistoryAdapter servicesAdapter=new BookingHistoryAdapter(getActivity(),selectedRoomModels);
        recyclerview.setHasFixedSize(false);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(llm);
        recyclerview.setAdapter(servicesAdapter);



    }



  /*  @Subscribe
    public void onFragmentActivityMessage(FragmentActivityMessage activityFragmentMessage)
    {
        if (activityFragmentMessage.getMessage().equalsIgnoreCase("bookingHistory"))
        {
            BookingHisList(activityFragmentMessage.getFrom());
            Log.d("sgfjrueruyr",activityFragmentMessage.getFrom());
        }
    }
    private void BookingHisList(String from)
    {
        Log.d("hjgdsdgusdfgfdgyuwe",from);


    }*/

   /* @Override
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
    }*/


}