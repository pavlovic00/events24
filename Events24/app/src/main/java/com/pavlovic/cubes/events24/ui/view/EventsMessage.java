package com.pavlovic.cubes.events24.ui.view;

import android.content.Context;
import android.widget.Toast;

public class EventsMessage {


    public static void showMessage(Context context, String text){
        Toast.makeText(context,text,Toast.LENGTH_LONG).show();
    }

    public static void showMessage(Context context, int stringId){
        Toast.makeText(context,stringId,Toast.LENGTH_LONG).show();
    }

}
