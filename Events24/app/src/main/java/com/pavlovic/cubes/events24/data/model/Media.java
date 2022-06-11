package com.pavlovic.cubes.events24.data.model;

import java.io.Serializable;
import java.util.Map;

public class Media implements Serializable {
    public String image;
    public String clickUrl;
    public String type;

    public Media(){

    }

    public Media(Map<String,String> map){
        if(map.containsKey("image")){
            this.image = map.get("image");
        }
        else{
            this.image = "";
        }
        if(map.containsKey("clickUrl")){
            this.clickUrl = map.get("clickUrl");
        }
        else{
            this.clickUrl = "";
        }
        if(map.containsKey("type")){
            this.type = map.get("type");
        }
        else{
            this.type= "";
        }
    }
}
