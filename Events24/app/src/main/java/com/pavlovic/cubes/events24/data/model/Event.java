package com.pavlovic.cubes.events24.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.firebase.firestore.GeoPoint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

@Entity
public class Event implements Comparable{

    //id dokumenta.
    @PrimaryKey
    @NonNull
    public String id;
    @ColumnInfo(name = "title")
    public String title;
    @ColumnInfo
    public String date;
    @Ignore
    public String time;
    @ColumnInfo
    public String type;
    @ColumnInfo
    public String imageBig;
    @Ignore
    public String imageSmall;
    @Ignore
    public String location;
    @Ignore
    public String author;
    @Ignore
    public String priority;
    @Ignore
    public String shareLink;
    @Ignore
    public ArrayList<Ticket> tickets;
    @Ignore
    public ArrayList<Media> medias;
    @Ignore
    public GeoPoint coordinates;


    public Event() {
    }

    public Event(String id,Map<String,Object> map){
        this(map);
        this.id = id;

    }

    public Event(Map<String, Object> map) {

        if(map.containsKey("title")) {
            title = (String) map.get("title");
        }
        else {
            title = "";
        }

        if(map.containsKey("date")) {
            date = (String) map.get("date");
        }
        else {
            date = "";
        }

        if(map.containsKey("location")) {
            location = (String) map.get("location");
        }
        else {
            location = "";
        }

        if(map.containsKey("type")) {
            type = (String) map.get("type");
        }
        else {
            type = "";
        }

        if(map.containsKey("imageBig")) {
            imageBig = (String) map.get("imageBig");
        }
        else {
            imageBig = "";
        }

        if(map.containsKey("imageSmall")) {
            imageSmall = (String) map.get("imageSmall");
        }
        else {
            imageSmall = "";
        }

        if(map.containsKey("time")) {
            time = (String) map.get("time");
        }
        else {
            time = "";
        }

        if(map.containsKey("author")) {
            author = (String) map.get("author");
        }
        else {
            author = "";
        }

        if(map.containsKey("priority")) {
            priority = (String.valueOf(map.get("priority")));
        }
        else {
            priority = "";
        }
        if(map.containsKey("shareLink")) {
            shareLink = (String.valueOf(map.get("shareLink")));
        }
        else {
            shareLink = "";
        }
        if(map.containsKey("tickets")){
            ArrayList<Map<String,String>> maps = (ArrayList<Map<String, String>>) map.get("tickets");
            this.tickets = new ArrayList<>();

            for(int i = 0;i<maps.size();i++){
                this.tickets.add(new Ticket(maps.get(i)));
            }
        }
        else{
            this.tickets = new ArrayList<>();
        }
        if(map.containsKey("media")){
            ArrayList<Map<String,String>> maps = (ArrayList<Map<String, String>>) map.get("media");
            this.medias = new ArrayList<>();

            for(int i = 0;i<maps.size();i++){
                this.medias.add(new Media(maps.get(i)));
            }
        }
        else{
            this.medias = new ArrayList<>();
        }
        if (map.containsKey("coordinates")){
            this.coordinates = (GeoPoint) map.get("coordinates");
        }
    }



    @Override
    public int compareTo(Object obj) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd h:m");
        try {
            if ((sdf.parse(this.date).before(sdf.parse(((Event)obj).date))))
                return -1;
            else if ((sdf.parse(((Event)obj).date)).before(sdf.parse(this.date)))
                return 1;
            else return 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  0;
    }
}
