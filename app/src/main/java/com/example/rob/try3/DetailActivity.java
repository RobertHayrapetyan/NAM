package com.example.rob.try3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.example.rob.try3.YerevanFragment.EXTRA_DATE;
import static com.example.rob.try3.YerevanFragment.EXTRA_DESCRIPTION;
import static com.example.rob.try3.YerevanFragment.EXTRA_NAME;
import static com.example.rob.try3.YerevanFragment.EXTRA_POSTER;


public class DetailActivity extends AppCompatActivity {

    ImageView imageViewEventPoster;
    TextView textViewEventName;
    TextView textViewEventDate;
    TextView textViewEventDescription;

    String poster;
    String eventName;
    String eventDate;
    String eventDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        poster = intent.getStringExtra(EXTRA_POSTER);
        eventName = intent.getStringExtra(EXTRA_NAME);
        eventDate = intent.getStringExtra(EXTRA_DATE);
        eventDescription = intent.getStringExtra(EXTRA_DESCRIPTION);

        imageViewEventPoster = (ImageView)findViewById(R.id.event_poster_detail);
        textViewEventName = (TextView)findViewById(R.id.text_view_event_name_detail);
        textViewEventDate = (TextView)findViewById(R.id.text_view_event_date_detail);
        textViewEventDescription = (TextView)findViewById(R.id.text_view_event_description_detail);

        Picasso.with(this).load(poster).fit().centerInside().into(imageViewEventPoster);
        textViewEventName.setText(eventName);
        textViewEventDate.setText(eventDate);
        textViewEventDescription.setText(eventDescription);
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, PictureActivity.class);
        intent.putExtra(EXTRA_POSTER, poster);
        Log.d("LOG", poster);
        startActivity(intent);

    }
}