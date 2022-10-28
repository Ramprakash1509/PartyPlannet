package com.app.partyplanet.DashBoad;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.partyplanet.Adpters.HAPPENINGS;
import com.app.partyplanet.Adpters.IntroSlider;
import com.app.partyplanet.Adpters.DasboadAdapter;
import com.app.partyplanet.Adpters.PopularDestination;
import com.app.partyplanet.Adpters.ServicesAdapter;
import com.app.partyplanet.Data.Dataum;
import com.app.partyplanet.Data.DasboadModel;
import com.app.partyplanet.Model.HappeningsModel;
import com.app.partyplanet.Model.PopularDestinationModel;
import com.app.partyplanet.Model.Services;
import com.app.partyplanet.Model.list;
import com.app.partyplanet.Model.offersandpramotion;
import com.app.partyplanet.R;
import com.app.partyplanet.Utils.ApplicationConstant;
import com.app.partyplanet.Utils.FragmentActivityMessage;
import com.app.partyplanet.Utils.GlobalBus;
import com.google.gson.Gson;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class Dasboadfragment extends Fragment implements View.OnClickListener {   RelativeLayout searchView;
    String img;
    DasboadModel dasboadModel;
    ArrayList<Dataum> banerData;
    private SliderView sliderView;
    List<Dataum> subSliderModelList;
    RecyclerView recyclerView,POPULARDESTINATION,happeningslist,SERVICES;
    List<offersandpramotion> list;
    DasboadAdapter adapter;
    List<PopularDestinationModel> popularDestinationModels;
    List<HappeningsModel> hlist;
    List<Services> services;
    String storelist="",bannerlist="",service;
    TextView termsandPolicies,helpdesk,frequentlyAQ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_dasboadfragment, container, false);
        GetId( v);
        return v;
    }
   public void GetId(View v)
    {
          searchView=v.findViewById(R.id.search);
          searchView.setOnClickListener(this);
          list=new ArrayList<>();
          subSliderModelList = new ArrayList<>();
          sliderView =v.findViewById(R.id.imageSlider);
           SERVICES=v.findViewById(R.id.SERVICES);
           termsandPolicies=v.findViewById(R.id.termsandPolicies);
           helpdesk=v.findViewById(R.id.helpdesk);
           frequentlyAQ=v.findViewById(R.id.askquestion);
           termsandPolicies.setOnClickListener(this);
           helpdesk.setOnClickListener(this);
           frequentlyAQ.setOnClickListener(this);


          /* subSliderModelList.add(new SubSliderModel(R.drawable.nature,0,"skip",getString(R.string.slider1text)));
          subSliderModelList.add(new SubSliderModel(R.drawable.pics,0,"skip",getString(R.string.slider1text)));
          subSliderModelList.add(new SubSliderModel(R.drawable.nature,0,"skip",getString(R.string.slider1text)));

          */
        Log.d("djvhjsdhvuesdsdfry",""+img);
        /*  subSliderModelList.add(new BanerData("","","", ApplicationConstant.INSTANCE.baseUrl+img,""));
          sliderView.setSliderAdapter(new IntroSlider(getActivity(),subSliderModelList));
         sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.ZOOMOUTTRANSFORMATION);
        sliderView.startAutoCycle();
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.BLACK);
        sliderView.setIndicatorUnselectedColor(Color.WHITE);
        sliderView.setScrollTimeInSec(2);*/

        list.add(new offersandpramotion(R.drawable.nature));
        list.add(new offersandpramotion(R.drawable.nature));
        list.add(new offersandpramotion(R.drawable.nature));
        recyclerView=v.findViewById(R.id.recyclerview);


       /* DasboadAdapter adapter=new DasboadAdapter(getActivity(),list);
        recyclerView=v.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);*/

        //POPULARDESTINATION
       /* POPULARDESTINATION=v.findViewById(R.id.POPULARDESTINATION);
        popularDestinationModels=new ArrayList<>();
        popularDestinationModels.add(new PopularDestinationModel(R.drawable.nature,"GOA"));
        popularDestinationModels.add(new PopularDestinationModel(R.drawable.pics,"GOA"));
        popularDestinationModels.add(new PopularDestinationModel(R.drawable.nature,"GOA"));
        PopularDestination pdAdapter=new PopularDestination(getActivity(),popularDestinationModels);


        POPULARDESTINATION.setHasFixedSize(false);
        LinearLayoutManager layout = new LinearLayoutManager(getActivity());
        layout.setOrientation(LinearLayoutManager.HORIZONTAL);
        POPULARDESTINATION.setLayoutManager(layout);
        POPULARDESTINATION.setAdapter(pdAdapter);


        happeningslist=v.findViewById(R.id.happenings);
        hlist=new ArrayList<>();
        hlist.add(new HappeningsModel(R.drawable.hotel,"MUMBAI","Tell Us Your Location"));
        hlist.add(new HappeningsModel(R.drawable.hotel,"MUMBAI","Tell Us Your Location"));
        hlist.add(new HappeningsModel(R.drawable.hotel,"MUMBAI","Tell Us Your Location"));
        hlist.add(new HappeningsModel(R.drawable.hotel,"MUMBAI","Tell Us Your Location"));
        HAPPENINGS happeningsAdapter=new HAPPENINGS(getActivity(),hlist);
        happeningslist.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        happeningslist.setLayoutManager(layoutManager);
        happeningslist.setAdapter(happeningsAdapter);

        SERVICES=v.findViewById(R.id.SERVICES);
        services=new ArrayList<>();
        services.add(new Services(R.drawable.weddings,"TIMES WEDDINGS"));
        services.add(new Services(R.drawable.hotel,"TIMES WEDDINGS"));
        services.add(new Services(R.drawable.weddings,"TIMES WEDDINGS"));
        services.add(new Services(R.drawable.weddings,"TIMES WEDDINGS"));
        services.add(new Services(R.drawable.hotel,"TIMES WEDDINGS"));
        servicesAdapter=new ServicesAdapter(getActivity(),services);
        SERVICES.setHasFixedSize(false);
        LinearLayoutManager SERVICESLayout = new LinearLayoutManager(getActivity());
        SERVICESLayout.setOrientation(LinearLayoutManager.HORIZONTAL);
        SERVICES.setLayoutManager(SERVICESLayout);
        SERVICES.setAdapter(servicesAdapter);*/
        SharedPreferences myPreferences = getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        storelist = myPreferences.getString(ApplicationConstant.INSTANCE.storelist, "")+"";
        bannerlist = myPreferences.getString(ApplicationConstant.INSTANCE.bannerlist, "");
        service = myPreferences.getString(ApplicationConstant.INSTANCE.bannerlist, "");
        if(!bannerlist.equalsIgnoreCase(""))
        {
            //listedView(storelist);
            dataParse(bannerlist);
           // userServices(service);
        }
       if (!storelist.equalsIgnoreCase(""))
        {
            listedView(storelist);
        }
        if (!service.equalsIgnoreCase(""))
        {
            userServices(service);
        }


        Log.d("weiurytyre",storelist);


    }

    @Subscribe
    public void onFragmentActivityMessage(FragmentActivityMessage activityFragmentMessage)
    {
        if (activityFragmentMessage.getMessage().equalsIgnoreCase("bannerList"))
        {
            if(storelist.equalsIgnoreCase(""))
            {
                dataParse(activityFragmentMessage.getFrom());
            }

        }
        else if(activityFragmentMessage.getMessage().equalsIgnoreCase("allmodulelisted"))
        {
            if(bannerlist.equalsIgnoreCase(""))
            {  listedView(activityFragmentMessage.getFrom());
            }
        }
        else if(activityFragmentMessage.getMessage().equalsIgnoreCase("userServices"))
        {
            if(service.equalsIgnoreCase(""))
            {
                userServices(activityFragmentMessage.getFrom());

            }



        }
    }

    private void userServices(String from)
    {
       Log.d("y6345",from);
        Gson gson = new Gson();
        dasboadModel = gson.fromJson(from, DasboadModel.class);
        banerData = dasboadModel.getBanerData();
        ServicesAdapter servicesAdapter=new ServicesAdapter(getActivity(),banerData);
        SERVICES.setHasFixedSize(false);
        SERVICES.setLayoutManager(new LinearLayoutManager(getActivity()));
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        SERVICES.setLayoutManager(llm);
        SERVICES.setAdapter(servicesAdapter);

    }

    private void listedView(String from)
    {

        Gson gson = new Gson();
        dasboadModel = gson.fromJson(from, DasboadModel.class);
        banerData = dasboadModel.getBanerData();
        DasboadAdapter adapter=new DasboadAdapter(getActivity(),banerData);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);



    }

    private void dataParse(String from)
    {
        Log.d("hjgdsdguyuwe",from);
        Gson gson = new Gson();
        dasboadModel = gson.fromJson(from, DasboadModel.class);
        banerData = dasboadModel.getBanerData();
        sliderView.setSliderAdapter(new IntroSlider(getActivity(),banerData));
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.ZOOMOUTTRANSFORMATION);
        sliderView.startAutoCycle();
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.BLACK);
        sliderView.setIndicatorUnselectedColor(Color.WHITE);
        sliderView.setScrollTimeInSec(2);



    }




    @Override
    public void onClick(View v)
    {
        if (v==searchView)
        {
          Fragment fragment = new SearchForHotelDesRestorentFm();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_container, fragment);
            fragmentTransaction.commit();

        /*  Fragment fragmentone=new Dasboadfragment();
            Fragment fragment = new SearchForHotelDesRestorentFm();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_container, fragment);
            fragmentTransaction.add(R.id.cantainer, fragmentone, "fragmentone");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();*/

        }
        if (v==termsandPolicies)
        {
            startActivity(new Intent(getActivity(),TermsAndConditions.class));
        }
        if (v==helpdesk)
        {
            startActivity(new Intent(getActivity(),Helpdesk.class));
        }
        if (v==frequentlyAQ)
        {
            startActivity(new Intent(getActivity(),frequentlyAQ.class));
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
}