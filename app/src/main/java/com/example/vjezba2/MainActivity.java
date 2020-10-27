package com.example.vjezba2;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

        MyData myDataset1 = new MyData("a","ime1","100000");
        MyData myDataset2 = new MyData("a","ime2", "150000");
        MyData myDataset3 = new MyData("a","ime3", "200000");
        MyData myDataset4 = new MyData("a", "ime4","30000");
        final ArrayList<MyData> myDataset = new ArrayList<MyData>();
        myDataset.add(myDataset1);
        myDataset.add(myDataset2);
        myDataset.add(myDataset3);
        myDataset.add(myDataset4);

        final RecyclerView.Adapter adapter = new MyAdapter(myDataset);
        recyclerView.setAdapter(adapter);

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
                }
            }

            @Override
            public void onFailure(Call<GitHubData> call, Throwable t) {
                String errorData = t.getMessage();
            }
        });
    }
}