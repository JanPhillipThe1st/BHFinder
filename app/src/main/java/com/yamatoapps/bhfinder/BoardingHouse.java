package com.yamatoapps.bhfinder;

public class BoardingHouse {
    public String name;
    public  String image;
    public  int capacity;
    public String location;
    public Double rate;
    public  String type;
    public  String features;
    public  String id;


    public BoardingHouse(String name, String image, int capacity, String location, Double rate, String type, String features, String id) {
        this.name = name;
        this.image = image;
        this.capacity = capacity;
        this.location = location;
        this.rate = rate;
        this.type = type;
        this.features = features;
        this.id = id;
    }
}
