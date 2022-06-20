package com.example.pocketwatching.Models;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

// Based on the following guide:
// https://guides.codepath.org/android/Building-Data-driven-Apps-with-Parse#installing-the-parse-sdk
public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Use for troubleshooting -- remove this line for production
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

        // Use for monitoring Parse OkHttp traffic
        // Can be Level.BASIC, Level.HEADERS, or Level.BODY
        // See https://square.github.io/okhttp/3.x/logging-interceptor/ to see the options.
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.networkInterceptors().add(httpLoggingInterceptor);

        // set applicationId, and server server based on the values in the back4app settings.
        // any network interceptors must be added with the Configuration Builder given this syntax
        ParseObject.registerSubclass(Wallet.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("uF2i7ZrD4CkPHTxsniwzhD5KtaIeLtAFcRUKjwHX") // should correspond to Application Id env variable
                .clientKey("i7snmuRhJJR2D6wAlq72pIteQwDr2K1Z33DPbm1v")  // should correspond to Client key env variable
                .server("https://parseapi.back4app.com").build());
    }


}