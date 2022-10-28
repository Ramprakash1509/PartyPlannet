package com.app.partyplanet.Adpters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.app.partyplanet.DashBoad.SearchingShowData;
import com.app.partyplanet.DashBoad.ShowDetails;
import com.app.partyplanet.Data.Dataum;
import com.app.partyplanet.Model.list;
import com.app.partyplanet.R;
import com.app.partyplanet.Utils.ApplicationConstant;
import com.app.partyplanet.Utils.UtilsMethod;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DasboadAdapter extends  RecyclerView.Adapter<DasboadAdapter.ViewHolder>
{
    private Context context;
    private List<Dataum> msubslider = new ArrayList<>();

    public DasboadAdapter(Context context, List<Dataum> msubslider)
    {
        this.context = context;
        this.msubslider = msubslider;
    }



    @NonNull
    @Override
    public DasboadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.offers_and_population, parent, false);
        return  new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull DasboadAdapter.ViewHolder viewHolder, int position)
    {


        Dataum sliderItem = msubslider.get(position);
        Log.d("ksdjfhsdgfgv",sliderItem.getName());
        viewHolder.tv_title.setText(sliderItem.getName());
        viewHolder.tv_title.setAllCaps(true);
        viewHolder.viewmore.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                UtilsMethod.INSTANCE.viewMore(context,sliderItem.getId());
                context.startActivity(new Intent(context, SearchingShowData.class));

            }
        });

        for (list d : sliderItem.getList())
        {
            LayoutInflater inflater2 = null;
            inflater2 = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            final View mLinearView = inflater2.inflate(R.layout.populardestination, null);
            ImageView image = mLinearView.findViewById(R.id.image);
            TextView cityname = mLinearView.findViewById(R.id.cityname);
            TextView location = mLinearView.findViewById(R.id.location);
            LinearLayout select = mLinearView.findViewById(R.id.select);
            Log.d("innkdfjsdfiu", "" + d.getName() + "    " + ApplicationConstant.INSTANCE.baseUrl + d.getBanner_img());
            Glide.with(viewHolder.itemView)
                    .load(ApplicationConstant.INSTANCE.baseUrl + d.getBanner_img())
                    .fitCenter()
                    .error(R.drawable.imm)
                    .into(image);
            cityname.setText(d.getName());
            location.setText(d.getCity());
            select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "fbjhsfvb", Toast.LENGTH_SHORT).show();
                   /* context.startActivity(new Intent(context,
                            ShowDetails.class).putExtra("data",
                            new Gson().toJson(d)).putExtra("status",2));*/

                    Intent i=new Intent(context,ShowDetails.class);
                    i.putExtra("data",  new Gson().toJson(d));
                    i.putExtra("status","2");
                    context.startActivity(i);

                }
            });
            viewHolder.layout_view.addView(mLinearView);
        }
//        Glide.with(viewHolder.itemView)
//                .load(ApplicationConstant.INSTANCE.baseUrl+sliderItem.getBanner_img())
//                .fitCenter()
//                .into(viewHolder.imageView);


    }

    @Override
    public int getItemCount() {
        return msubslider.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

       public TextView tv_title;
       LinearLayout layout_view;
        LottieAnimationView viewmore;

        public ViewHolder(@NonNull View v)
        {
            super(v);
            this.tv_title=v.findViewById(R.id.tv_title);
            this.layout_view=v.findViewById(R.id.layout_view);
            this.viewmore=v.findViewById(R.id.viewmore);


        }
    }
}
