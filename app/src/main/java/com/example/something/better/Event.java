package com.example.something.better;

import java.util.ArrayList;

/**
 * Created by Shiv on 2/20/17.
 */

public class Event {

    String date;
    String description;
    String email;
    String eventName;
    String imageURL;
    String key;
    String numInterested;
    ArrayList<String> peopleInterested;

    //Vijay - Add the address variable
    String address;
    //Vijay - Add the address variable

    public Event() {
        peopleInterested = new ArrayList<>();

    }
}
