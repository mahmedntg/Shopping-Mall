package com.example.anas.shoppingmall.utils.notificationcall;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Controller implements Callback<NotificationResponse> {

    static final String BASE_URL = "https://fcm.googleapis.com/";

    public void start(NotificationRequest request) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        NotificationAPI notificationAPI = retrofit.create(NotificationAPI.class);

        Call<NotificationResponse> call = notificationAPI.sendNotification(request);
        call.enqueue(this);

    }


    @Override
    public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
        if (response.isSuccessful()) {
            response.body();
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<NotificationResponse> call, Throwable t) {
        t.printStackTrace();
    }

    OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
        @Override
        public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request.Builder builder = originalRequest.newBuilder().header("Authorization", "key=AIzaSyAjfoKsiC5NTaZfq-4a-TTExoiVv8_C5_A");
            Request newRequest = builder.build();
            return chain.proceed(newRequest);
        }
    }).build();


    public void sendNotification(String token, String title, String body) {

        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setTo(token);
        notificationRequest.setPriority("normal");
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setBody(body);
        notificationRequest.setNotification(notification);
        this.start(notificationRequest);
    }

    public void sendAllNotification(String topic, String title, String body) {

        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setTo("/topics/all");
        notificationRequest.setPriority("normal");
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setBody(body);
        notificationRequest.setNotification(notification);
        this.start(notificationRequest);
    }
}
