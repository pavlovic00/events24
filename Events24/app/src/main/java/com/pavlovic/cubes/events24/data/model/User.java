package com.pavlovic.cubes.events24.data.model;

public class User {

    public String id;
    public String name;
    public String surname;
    public String email;
    public String city;
    public String address;
    public String phone;
    public String imageUrl;

    public User(){

    }

    public User(String id, String name, String surname, String email){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.city = "";
        this.address = "";
        this.phone = "";
        this.imageUrl = "";
    }
}
