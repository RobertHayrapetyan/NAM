package com.example.rob.try3;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.rob.try3.YerevanFragment.EXTRA_POSTER;

public class PictureActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    ImageView imageViewPicture;
    String poster;
    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;

    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd_hh:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        imageViewPicture = (ImageView) findViewById(R.id.imageViewPicture);
        Intent intent = getIntent();
        poster = intent.getStringExtra(EXTRA_POSTER);
        Picasso.with(this).load(poster).fit().centerInside().into(imageViewPicture);
        Log.d("LOG", poster);

        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        mScaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    public void onMenuClick(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.save:
                new DownloadFile().execute(poster);
                return true;
            case R.id.share:
                return true;
            default:
                return false;
        }
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(1.0f,
                    Math.min(mScaleFactor, 10.0f));
            imageViewPicture.setScaleX(mScaleFactor);
            imageViewPicture.setScaleY(mScaleFactor);
            return true;
        }
    }

    class DownloadFile extends AsyncTask<String, Integer, Long> {
        ProgressDialog mProgressDialog = new ProgressDialog(PictureActivity.this);// Change Mainactivity.this with your activity name.
        String strFolderName;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.setMessage("Downloading");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.show();
        }

        @Override
        protected Long doInBackground(String... aurl) {
            int count;
            try {
                URL url = new URL((String) aurl[0]);
                Log.d("LOG", "0");
                URLConnection conection = url.openConnection();
                Log.d("LOG", "1");
                conection.connect();
                Log.d("LOG", "2");
                Log.d("LOG", "3");
                int lenghtOfFile = conection.getContentLength();
                Log.d("LOG", "4");
                String PATH = Environment.getExternalStorageDirectory() + "/" + "NAM" + "/";
                Log.d("LOG", "5");
                File folder = new File(PATH);
                Log.d("LOG", "6");
                if (!folder.exists()) {
                    folder.mkdir();//If there is no folder it will be created.
                    Log.d("LOG", "7");
                }
                InputStream input = new BufferedInputStream(url.openStream());
                Log.d("LOG", "8");
                OutputStream output = new FileOutputStream(PATH + dateFormat.format(new Date()).toString() + ".jpg");
                Log.d("LOG", "9");
                byte data[] = new byte[1024];
                Log.d("LOG", "10");
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress((int) (total * 100 / lenghtOfFile));
                    output.write(data, 0, count);
                }
                Log.d("LOG", "11");
                output.flush();
                Log.d("LOG", "12");
                output.close();
                Log.d("LOG", "13");
                input.close();
                Log.d("LOG", "14");
            } catch (Exception e) {
                Log.d("LOG", "Exception!!!!");
            }
            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
            mProgressDialog.setProgress(progress[0]);
            if (mProgressDialog.getProgress() == mProgressDialog.getMax()) {
                mProgressDialog.dismiss();
                Toast.makeText(PictureActivity.this, "File Downloaded", Toast.LENGTH_LONG).show();
            }
        }

        protected void onPostExecute(String result) {
        }
    }
}


