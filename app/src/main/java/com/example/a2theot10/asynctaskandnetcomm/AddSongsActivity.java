package com.example.a2theot10.asynctaskandnetcomm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class AddSongsActivity extends AppCompatActivity implements View.OnClickListener {

    class MyTask extends AsyncTask<String, Void, String> {
        public String doInBackground(String... params) {
            HttpURLConnection conn = null;
            try {
                URL url = new URL("http://www.free-map.org.uk/course/mad/ws/addhit.php");
                conn = (HttpURLConnection) url.openConnection();

                String song = URLEncoder.encode(params[0], "UTF-8");
                String artist = URLEncoder.encode(params[1], "UTF-8");
                String year = URLEncoder.encode(params[2], "UTF-8");
                String postData = "song=" + song +
                        "&artist=" + artist +
                        "&year=" + year;

                // For POST
                conn.setDoOutput(true);
                conn.setFixedLengthStreamingMode(postData.length());

                OutputStream out = null;
                out = conn.getOutputStream();
                out.write(postData.getBytes());
                if (conn.getResponseCode() == 200) {
                    InputStream in = conn.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String all = "", line;
                    while ((line = br.readLine()) != null)
                        all += line;
                    return all;
                } else {
                    return "HTTP ERROR: " + conn.getResponseCode();
                }
            } catch (IOException e) {
                return e.toString();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }

        public void onPostExecute(String result) {

            new AlertDialog.Builder(AddSongsActivity.this).
                    setMessage("Server sent back: " + result).
                    setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    AddSongsActivity.this.finish();
                                }
                            }

                    ).show();

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addsong_activity);
        Button b = (Button) findViewById(R.id.btnaddsong);
        b.setOnClickListener(this);
    }

    public void onClick(View view) {
        MyTask task = new MyTask();
        EditText etsong = (EditText) findViewById(R.id.etsong);
        EditText etartist = (EditText) findViewById(R.id.etartist);
        EditText etyear = (EditText) findViewById(R.id.etyear);
        String song = etsong.getText().toString();
        String artist = etartist.getText().toString();
        String year = etyear.getText().toString();
        task.execute(song, artist, year);

    }

}
