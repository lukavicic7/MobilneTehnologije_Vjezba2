package com.example.vjezba2;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static final String BASE_URL = "https://api.github.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHubApiInterface apiInterface = retrofit.create(GitHubApiInterface.class);
        Call<GitHubData> data = apiInterface.getData();
        data.enqueue(new Callback<GitHubData>() {
            @Override
            public void onResponse(Call<GitHubData> call, Response<GitHubData> response) {
                if (!response.isSuccessful()) {
                    int statusCode = response.code();
                }
                else {
                    GitHubData data = response.body();
                }
            }

            @Override
            public void onFailure(Call<GitHubData> call, Throwable t) {

            }
        });
    }
}