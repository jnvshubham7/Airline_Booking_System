package com.example.android.querymaster;

import static com.example.android.querymaster.Main_Page.graph;

public class Admin extends User_Details {

    public Admin(String username, String password, String email, int age, int dateOfBirth, int contactNo, Boolean isAdmin) {
        Username = username;
        Password = password;
        Email = email;
        Age = age;
        DateOfBirth = dateOfBirth;
        ContactNo = contactNo;
        IsAdmin = isAdmin;
    }

    void addNewFlight(String code, String airlineName, String from, String to, String durationOfFlight, String startTime, String endTime, int cost, String date){
        Flight flight= new Flight(code,airlineName,from,to,durationOfFlight,startTime,endTime,cost,date);
        //calling graph methods
        graph.addNewFlight(flight);
    }

    void addNewAirport(String name, String code){
        Destination destination =new Destination(name,code);
        //calling graph methods
        graph.addNewAirport(destination);
    }

    void deleteAirport(String name, String code){
        Destination destination = new Destination(name,code);
        //calling graph methods
        graph.deleteAirport(destination);
    }

    void deleteFlight(String code, String airlineName, String from, String to, String durationOfFlight, String startTime, String endTime, int cost, String date){
        Flight flight= new Flight(code,airlineName,from,to,durationOfFlight,startTime,endTime,cost,date);
        //calling graph methods
        graph.deleteFlight(flight);
    }
}
