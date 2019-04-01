package com.example.anas.shoppingmall.utils.notificationcall;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NotificationAPI {

    @Headers({"Authorization: key=AIzaSyAjfoKsiC5NTaZfq-4a-TTExoiVv8_C5_A",
            "Content-Type:application/json"})
    @POST("fcm/send")
    Call<NotificationResponse> sendNotification(@Body NotificationRequest request);
}
