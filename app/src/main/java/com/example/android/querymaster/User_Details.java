package com.example.android.querymaster;

import java.util.ArrayList;

class User_Details {
    String Username, Password, Email;
    int Age,DateOfBirth, ContactNo;
    Boolean IsAdmin;
    ArrayList<Booking> bookedflights=new ArrayList<>();






    public ArrayList<Booking> getBookedflights() {
        return bookedflights;
    }

    public void setBookedflights(ArrayList<Booking> bookedflights) {
        this.bookedflights = bookedflights;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public int getDateOfBirth() {
        return DateOfBirth;
    }



    public void setDateOfBirth(int dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public int getContactNo() {
        return ContactNo;
    }

    public void setContactNo(int contactNo) {
        ContactNo = contactNo;
    }

    public Boolean getAdmin() {
        return IsAdmin;
    }

    public void setAdmin(Boolean admin) {
        IsAdmin = admin;
    }
}
