package com.example.ahmed.refreshlistview;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private String URL = "https://api.androidhive.info/json/imdb_top_250.php?offset=";
    ListAdapter adapter;
    ListView listView;
    List<Movie> movieList;
    SwipeRefreshLayout swipeRefreshLayout;

    // initially offset will be 0. will be updated while parsing the json.
    private int offSet = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        movieList = new ArrayList<>();
        adapter = new ListAdapter(movieList, this);
        listView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(this);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                fetchMovies();
            }
        });
    }

    @Override
    public void onRefresh() {
        fetchMovies();
    }

    //Fetching movies json by making http call.
    private void fetchMovies() {

        swipeRefreshLayout.setRefreshing(true); // showing refresh animation before making http call

        String url = URL + offSet;

        // Volley's json array request object
        final JsonArrayRequest arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);

                            int rank = jsonObject.getInt("rank");
                            String title = jsonObject.getString("title");

                            Movie movie = new Movie(rank, title);

                            movieList.add(0, movie);

                            // updating offset value to highest value
                            if (rank >= offSet)
                                offSet = rank;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        MyApplication.getInstance().addToRequest(arrayRequest);
    }
}
