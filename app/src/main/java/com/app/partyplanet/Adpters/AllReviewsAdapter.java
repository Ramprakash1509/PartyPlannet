package com.app.partyplanet.Adpters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.partyplanet.Data.Data;
import com.app.partyplanet.Data.Dataum;
import com.app.partyplanet.Data.ReviewList;
import com.app.partyplanet.R;
import com.app.partyplanet.Utils.ApplicationConstant;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllReviewsAdapter extends  RecyclerView.Adapter<AllReviewsAdapter.ViewHolder>
{
    private Context context;
    private ArrayList<ReviewList> msubslider = new ArrayList<>();

    public AllReviewsAdapter(Context context, ArrayList<ReviewList> msubslider) {
        this.context = context;
        this.msubslider = msubslider;
    }

    @NonNull
    @Override
    public AllReviewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.showreview_users, parent, false);
        return  new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull AllReviewsAdapter.ViewHolder viewHolder, int position) {
        ReviewList sliderItem = msubslider.get(position);

        Log.d("uiuryueyr",sliderItem.getUpdated_at());
        viewHolder.description.setText("- " + sliderItem.getReviews());
        viewHolder.date.setText(sliderItem.getUpdated_at());
        viewHolder.name.setText(sliderItem.getFirst_name()+""+sliderItem.getLast_name());
        Glide.with(viewHolder.itemView)
                .load(ApplicationConstant.INSTANCE.baseUrl + sliderItem.getImage())
                .fitCenter()
                .error(R.drawable.nature)
                .into(viewHolder.img);
    }

    @Override
    public int getItemCount()
    {
        return msubslider.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,date,description,country;
        CircleImageView img;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            this.name=itemView.findViewById(R.id.name);
            this.date=itemView.findViewById(R.id.date);
            this.description=itemView.findViewById(R.id.description);
            this.img=itemView.findViewById(R.id.profile);


        }
    }
}
