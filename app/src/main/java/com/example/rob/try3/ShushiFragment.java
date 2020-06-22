package com.example.rob.try3;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShushiFragment extends Fragment implements RecyclerViewAdapter.OnItemClickListener {

    public static final String EXTRA_NAME = "eventName";
    public static final String EXTRA_DATE = "eventDate";
    public static final String EXTRA_POSTER = "eventPoster";
    public static final String EXTRA_DESCRIPTION = "eventDescription";

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private ArrayList<RecyclerViewItem> mExampleList;
    private RequestQueue mRequestQueue;
    SwipeRefreshLayout swipeRefreshLayout;


    public ShushiFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shushi, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_shushi);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_to_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onStart();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        mExampleList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(getContext());
        parseJSON();

        return view;
    }
    private void parseJSON(){
        String url = "https://robertrobert.000webhostapp.com/EventShushi.php";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("hits");

                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject hit = jsonArray.getJSONObject(i);

                                String eventName = hit.getString("eventName");
                                String eventPoster = hit.getString("eventPoster");
                                String eventDate = hit.getString("eventDate");
                                String eventDescription = hit.getString("eventDescription");

                                mExampleList.add(new RecyclerViewItem(eventPoster, eventName, eventDate , eventDescription));
                            }

                            mRecyclerViewAdapter = new RecyclerViewAdapter(getContext(), mExampleList);
                            mRecyclerView.setAdapter(mRecyclerViewAdapter);
                            mRecyclerViewAdapter.setOnItemClickListener(ShushiFragment.this);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(getContext(), DetailActivity.class);
        RecyclerViewItem clickedItem = mExampleList.get(position);

        detailIntent.putExtra(EXTRA_POSTER, clickedItem.getEvevtPoster());
        detailIntent.putExtra(EXTRA_NAME, clickedItem.getEventName());
        detailIntent.putExtra(EXTRA_DATE, clickedItem.getEventDate());
        detailIntent.putExtra(EXTRA_DESCRIPTION, clickedItem.getEventDescription());

        startActivity(detailIntent);
    }
}



