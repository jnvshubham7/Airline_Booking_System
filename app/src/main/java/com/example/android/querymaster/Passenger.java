package com.example.android.querymaster;

import java.util.ArrayList;

class Passenger extends User_Details {

    ArrayList<Booking> UserBooking = new ArrayList<>();

    public Passenger(String username, String password, String email, int age, int dateOfBirth, int contactNo) {
        Username = username;
        Password = password;
        Email = email;
        Age = age;
        DateOfBirth = dateOfBirth;
        ContactNo = contactNo;
        IsAdmin = false;
    }

    public void bookTicket(Graph graph){
        //calling graph methods
        //send flight, doj
    }

    public ArrayList<Booking> viewOldBookings(){
        return UserBooking;
    }

    public void cancelTicket(Booking booking){
        //Calling graph methods.
        //send flight, doj
        UserBooking.remove(booking);
    }

}

