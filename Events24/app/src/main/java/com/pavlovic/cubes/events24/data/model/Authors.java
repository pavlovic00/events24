package com.pavlovic.cubes.events24.data.model;

import java.util.Map;

public class Authors {

    public String name;
    public String surname;
    public String image;


    public Authors() {

    }

    public Authors(Map<String, Object> map) {

        if(map.containsKey("name")) {
            name = (String) map.get("name");
        }
        else {
            name = "";
        }

        if(map.containsKey("surname")) {
            surname = (String) map.get("surname");
        }
        else {
            surname = "";
        }

        if(map.containsKey("image")) {
            image = (String) map.get("image");
        }
        else {
            image = "";
        }
    }
}
