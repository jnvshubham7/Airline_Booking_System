package com.example.android.querymaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import static com.example.android.querymaster.Main_Page.graph;


public class Find_Flights extends AppCompatActivity implements Flight_Adapter.itemclicked, Indirect_Flight_Adapter.itemclicked1{

    RecyclerView recyclerview;
    RecyclerView.Adapter myadapter;
    RecyclerView.LayoutManager layoutManager;

    RecyclerView recyclerview1;
    RecyclerView.Adapter myadapter1;
    RecyclerView.LayoutManager layoutManager1;

    EditText ETsource;
    EditText ERdestination;
    EditText BTNdate;
    Button Search;
    String TAG="mera flight";
    String date;
    final ArrayList<Flight> ListOfDirectFlights=new ArrayList<>();
    final ArrayList<Flight> ListOfIndirectFlights= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_flights);
        recyclerview = (RecyclerView) findViewById(R.id.list);
        recyclerview.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setHasFixedSize(true);
        recyclerview.setAdapter(myadapter);

        recyclerview1 = (RecyclerView) findViewById(R.id.indirectflights);
        recyclerview1.setHasFixedSize(true);
        layoutManager1 = new LinearLayoutManager(this);
        recyclerview1.setLayoutManager(layoutManager1);
        recyclerview1.setHasFixedSize(true);
        recyclerview1.setAdapter(myadapter1);


        ETsource=findViewById(R.id.ETsource);
        ERdestination=findViewById(R.id.ETdestination);
        BTNdate=findViewById(R.id.date);
        Search=findViewById(R.id.button5);


        myadapter = new Flight_Adapter(Find_Flights.this,ListOfDirectFlights);
        recyclerview.setAdapter(myadapter);

        myadapter1= new Indirect_Flight_Adapter(Find_Flights.this,ListOfIndirectFlights);
        recyclerview1.setAdapter(myadapter1);

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fromv=ETsource.getText().toString();
                String tov=ERdestination.getText().toString();
                date=BTNdate.getText().toString();

                ListOfDirectFlights.clear();
                myadapter.notifyDataSetChanged();

                ArrayList<Flight> temp=graph.getDirectFlights(date,fromv, tov);
                graph.setVisited();
                ListOfDirectFlights.addAll(temp);
                myadapter.notifyDataSetChanged();


                ListOfIndirectFlights.clear();
                Log.d(TAG, "onClick: Cleared. size is "+ListOfIndirectFlights.size());
                myadapter1.notifyDataSetChanged();
                Log.d(TAG, "onClick: fromv is "+fromv+" tov is "+tov);

                ListOfIndirectFlights.addAll(graph.getModifiedListOfFlights(graph.getIndirectFlights(date, fromv, tov)));
            
                Log.d(TAG, "onClick: Cleared size is "+ListOfIndirectFlights.size());
                myadapter1.notifyDataSetChanged();

            }
        });

    }

    @Override
    public void onItemClicked(final int index) {

        Intent intent = new Intent(Find_Flights.this, Confirm_Booking.class);
        intent.putExtra("type", "DI");
        intent.putExtra("from", ListOfDirectFlights.get(index).getFrom());
        intent.putExtra("to",ListOfDirectFlights.get(index).getTo());
        intent.putExtra("doj",ListOfDirectFlights.get(index).getDateOfJourney());
        intent.putExtra("fare",ListOfDirectFlights.get(index).getCost()+"");
        intent.putExtra("duration",ListOfDirectFlights.get(index).getDurationOfFlight());
        intent.putExtra("code",ListOfDirectFlights.get(index).getCode());
        intent.putExtra("airline",ListOfDirectFlights.get(index).getAirlineName());
        intent.putExtra("starttime",ListOfDirectFlights.get(index).getStartTime());
        intent.putExtra("endtime",ListOfDirectFlights.get(index).getEndTime());
        startActivity(intent);
    }

    @Override
    public void onItemClicked1(final int index) {


        Intent intent = new Intent(Find_Flights.this, Confirm_Booking.class);
        intent.putExtra("type", "IND");
        intent.putExtra("from", ListOfIndirectFlights.get(index).getFrom());
        intent.putExtra("to",ListOfIndirectFlights.get(index).getTo());
        intent.putExtra("doj",ListOfIndirectFlights.get(index).getDateOfJourney());
        intent.putExtra("fare",ListOfIndirectFlights.get(index).getCost()+"");
        intent.putExtra("duration",ListOfIndirectFlights.get(index).getDurationOfFlight());
        intent.putExtra("code",ListOfIndirectFlights.get(index).getCode());
        intent.putExtra("airline",ListOfIndirectFlights.get(index).getAirlineName());
        intent.putExtra("starttime",ListOfIndirectFlights.get(index).getStartTime());
        intent.putExtra("endtime",ListOfIndirectFlights.get(index).getEndTime());
        startActivity(intent);
    }
}
