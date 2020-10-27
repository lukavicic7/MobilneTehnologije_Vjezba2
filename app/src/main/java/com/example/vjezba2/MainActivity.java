package com.example.vjezba2;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    public static final String BASE_URL = "https://api.github.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.rec_view);
        recyclerView.setHasFixedSize(true);

        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        final ArrayList<MyData> myDataset = new ArrayList<MyData>();

        final RecyclerView.Adapter adapter = new MyAdapter(Glide.with(this),myDataset);
        recyclerView.setAdapter(adapter);
        /*
        String url = "https://run.mocky.io/v3/00c17ed2-4343-4e69-a328-e948353d0cda";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        MyData myData = gson.fromJson(String.valueOf(jsonObject), MyData.class);
                        myDataset.add(myData);

                    }
                    Log.d("jeee", "evo");
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("jeee", "lose");
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("jeee", error.getMessage());

            }
        }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
*/
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
                    String errorData = response.errorBody().toString();
                }
                else {
                    GitHubData apiData = response.body();
                    List<Item> itemList = apiData.getItems();
                    for (int i=0;i<itemList.size();i++){
                        Owner o = itemList.get(i).getOwner();
                        MyData myData = new MyData(o.getAvatarUrl(),itemList.get(i).getName(),itemList.get(i).getStargazersCount().toString());
                        myDataset.add(myData);
                        Log.d("Item", myData.avatar_url + "\n"+ myData.numberOfStars + "\n"+myData.repositoryName);
                        adapter.notifyDataSetChanged();
                    }
                    /*itemList = apiData.getItems();*/
                }
            }
            @Override
            public void onFailure(Call<GitHubData> call, Throwable t) {
                String errorData = t.getMessage();
            }
        });

    }
}