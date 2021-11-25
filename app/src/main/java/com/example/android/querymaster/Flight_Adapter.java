package com.example.android.querymaster;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Flight_Adapter extends RecyclerView.Adapter<Flight_Adapter.ViewHolder> {


    Flight_Adapter.itemclicked activity;
    private ArrayList<Flight> directflights;
    public interface itemclicked{
        void onItemClicked(int index);
    }

    public Flight_Adapter(Context context, ArrayList<Flight> flights){
        directflights=flights;
        activity=(itemclicked)context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView TVairlinename,TVsource, TVdestination, TVduration, TVdeparture, TVarrival, TVcost;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            TVairlinename=itemView.findViewById(R.id.TVairlinename);
            TVsource=itemView.findViewById(R.id.TVsource);
            TVdestination=itemView.findViewById(R.id.TVdestination);
            TVduration=itemView.findViewById(R.id.TVduration);
            TVdeparture=itemView.findViewById(R.id.TVdeparture);
            TVarrival=itemView.findViewById(R.id.TVarrival);
            TVcost=itemView.findViewById(R.id.TVcost);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemClicked(directflights.indexOf((Flight)itemView.getTag()));
                }
            });
        }
    }

    @NonNull
    @Override
    public Flight_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_items,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Flight_Adapter.ViewHolder viewHolder, int i) {
        viewHolder.itemView.setTag(directflights.get(i));
        viewHolder.TVairlinename.setText(directflights.get(i).getAirlineName());
        viewHolder.TVsource.setText("Source: "+directflights.get(i).getFrom());
        viewHolder.TVdestination.setText("Dest: "+directflights.get(i).getTo());
        viewHolder.TVduration.setText("Time: "+directflights.get(i).getDurationOfFlight() + " hr");
        viewHolder.TVdeparture.setText("Depart: "+directflights.get(i).getStartTime() + " PM");
        viewHolder.TVarrival.setText("Arri: "+directflights.get(i).getEndTime() + " PM");
        viewHolder.TVcost.setText("Cost: Rs."+directflights.get(i).getCost()+"");
    }

    @Override
    public int getItemCount() {
        return directflights.size();
    }
}
