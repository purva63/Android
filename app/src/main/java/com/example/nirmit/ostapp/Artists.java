package com.example.nirmit.ostapp;

/**
 * Created by Nirmit on 02-Oct-16.
 */
public class Artists {

        String email,password,contact,name,occupation;
    Artists(){}
    Artists(String email,String pass,String name,String contact,String occupation)
        {
            this.email=email;
            password=pass;
            this.name=name;
            this.contact=contact;
            this.occupation=occupation;
        }
        public String getEmail(){return email;}
        public String getPassword(){return password;}
        public String getContact(){return contact;}
        public String getName(){return name;}
        public String getOccupation(){return occupation;}
}
