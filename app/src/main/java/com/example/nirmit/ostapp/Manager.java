package com.example.nirmit.ostapp;

/**
 * Created by Nirmit on 02-Oct-16.
 */
public class Manager {

    String email,password,contact,name,address,schedule,occupation;
    Manager(){}
    Manager(String email,String pass,String name,String contact,String occupation,String address,String schedule)
    {
        this.email=email;
        password=pass;
        this.name=name;
        this.contact=contact;
        this.address=address;
        this.schedule=schedule;
        this.occupation=occupation;
    }
    public String getEmail(){return email;}
    public String getPassword(){return password;}
    public String getContact(){return contact;}
    public String getName(){return name;}
    public String getAddress(){return address;}
    public String getSchedule(){return schedule;}
    public String getOccupation(){return occupation;}
}

