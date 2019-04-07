package com.example.anas.shoppingmall.utillity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mhamedsayed on 3/16/2019.
 */

public class SharedUtils {
    public static final String email = "m@m.com";

    public static String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return format.format(date);
    }

    private void sendNotification() {

    }
}
