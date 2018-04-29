package com.tatbiq.abosala7.inhalls.hallsearchadapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tatbiq.abosala7.inhalls.R;
import com.tatbiq.abosala7.inhalls.halldetails.HallDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class HallAdapter extends RecyclerView.Adapter<HallAdapter.HallViewHolder> implements Filterable {

    List<InHallsData> hallsData ;
    List<InHallsData> hallsDataFiltered;
    Context context;
    Intent intent;

    public HallAdapter(List<InHallsData> hallsData, Context context) {
        this.hallsData = hallsData;
        this.context =  context;
        this.hallsDataFiltered = hallsData ;
    }

    @Override
    public HallViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hall_card, parent, false);
        return new HallViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final HallViewHolder holder, int position) {

        final InHallsData inHallsData = hallsDataFiltered.get(position);
        holder.name.setText(inHallsData.getHallName());
        holder.price.setText("السعه: "+inHallsData.getCapacity());

        // loading album cover using Glide library

        Glide.with(context).load(inHallsData.getImagePath()).placeholder(R.drawable.logoar).into(holder.hallImage);
        holder.hallImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), HallDetailsActivity.class);
                intent.putExtra("name",inHallsData.getHallName());
                intent.putExtra("capacity",inHallsData.getCapacity());
                intent.putExtra("menCapacity",inHallsData.getMenCapacity());
                intent.putExtra("womenCapacity",inHallsData.getWomenCapacity());
                intent.putExtra("photos",inHallsData.getPhotos());
                intent.putExtra("services",inHallsData.getServices());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hallsDataFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    hallsDataFiltered = hallsData;
                } else {
                    List<InHallsData> filteredList = new ArrayList<>();
                    for (InHallsData InHallsData : hallsData) {

                        if (InHallsData.getHallName().contains(charString)) {
                            filteredList.add(InHallsData);
                        }
                    }

                    hallsDataFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = hallsDataFiltered;
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                hallsDataFiltered = (List<InHallsData>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class HallViewHolder extends RecyclerView.ViewHolder{

        ImageView hallImage;
        TextView name,price;

        public HallViewHolder(View itemView) {
            super(itemView);

            hallImage = (ImageView)itemView.findViewById(R.id.hallimage);
            name = (TextView)itemView.findViewById(R.id.hallname);
            price = (TextView)itemView.findViewById(R.id.hallPrice);
        }
    }
}

