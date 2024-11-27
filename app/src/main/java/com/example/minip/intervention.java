package com.example.minip;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class intervention extends AppCompatActivity {
    private Button btn1, btn2, btn3, mailButton, map, histo, cam;
    private TextView tx1, tx2, tx3, tx5, tx6, tx7, tx8, tx9, tx10;
    private ImageView garbeg, imageView; // To display the captured image
    private static final int REQUEST_IMAGE_CAPTURE = 100;
    JSONArray jsonArray = null;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intervention);

        new GetData().execute();

        // Initialize UI elements
        cam = findViewById(R.id.newButton1);
        btn1 = findViewById(R.id.button2);
        btn2 = findViewById(R.id.button3);
        btn3 = findViewById(R.id.button4);
        mailButton = findViewById(R.id.mail);
        map = findViewById(R.id.maps);
        histo = findViewById(R.id.histo);
        tx1 = findViewById(R.id.plan);
        tx2 = findViewById(R.id.plan2);
        tx3 = findViewById(R.id.plan3);
        tx5 = findViewById(R.id.ef);
        tx6 = findViewById(R.id.ef1);
        tx7 = findViewById(R.id.ef2);
        tx8 = findViewById(R.id.cl);
        tx9 = findViewById(R.id.cl1);
        tx10 = findViewById(R.id.cl2);
        garbeg = findViewById(R.id.garbeg);

        // Set click listener for the mail button
        mailButton.setOnClickListener(view -> {
            composeEmail(new String[]{"SONEDE@gmail.com"}, "Your intervention is done");
        });

        // Set click listener for the map button
        map.setOnClickListener(view -> {
            openGoogleMaps(36.839104030595934, 10.159523660075509);
        });

        // Set click listener for the history button
        histo.setOnClickListener(v -> {
            Intent i = new Intent(intervention.this, historique.class);
            startActivity(i);
        });

        btn2.setOnClickListener(v -> {
            // Hide the existing views
            findViewById(R.id.linearLayout3).setVisibility(View.GONE);
            findViewById(R.id.textView3).setVisibility(View.GONE);
            mailButton.setVisibility(View.GONE);
            findViewById(R.id.addresse).setVisibility(View.GONE);
            map.setVisibility(View.GONE);
            findViewById(R.id.hist).setVisibility(View.GONE);
            histo.setVisibility(View.GONE);
            findViewById(R.id.lncl).setVisibility(View.GONE);
            findViewById(R.id.lnef).setVisibility(View.GONE);
            findViewById(R.id.lnplan).setVisibility(View.GONE);

            // Show the new view with two buttons
            findViewById(R.id.newView).setVisibility(View.VISIBLE);

            // Optional: Set listeners for the new buttons
            Button newButton1 = findViewById(R.id.newButton1);
            Button newButton2 = findViewById(R.id.newButton2);

            newButton1.setOnClickListener(view -> {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            });

            newButton2.setOnClickListener(view -> {
                // Add logic for Button 2
            });
        });

        // Set click listener for the cam button to open the camera
        cam.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            // Check if there's a camera app to handle this intent
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, 100); // 100 is the request code for the camera
            }
        });
    }


    private void composeEmail(String[] addresses, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // Ensure this opens email apps only
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);

        // Check if an email client is available and start the activity
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    private void openGoogleMaps(double latitude, double longitude) {
        // Create a URI for the specified location
        String uri = "geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps"); // Optional: Ensures it opens in Google Maps app

        // Check if a map application is available and start the activity
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private class GetData extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {


            try {
                URL url = new URL("http://10.0.2.2/get.php");
                URLConnection urlConnection = url.openConnection();
                InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader BufferedReader = new BufferedReader(inputStreamReader);
                String Line;
                while ((Line = BufferedReader.readLine()) != null) {
                    jsonArray = new JSONArray(Line);
                }
                Log.d("result", jsonArray.toString());

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            return null;
        }
    }
}

