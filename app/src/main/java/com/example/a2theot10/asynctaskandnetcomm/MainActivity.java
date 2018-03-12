package com.example.a2theot10.asynctaskandnetcomm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.AsyncTask;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;




public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b = (Button)findViewById(R.id.btn1);
        b.setOnClickListener(this);
    }
    public void onClick(View view)
    {
        TestTask2 task = new TestTask2(this);
        EditText et = (EditText)findViewById(R.id.et1);
        TextView tv = (TextView)findViewById(R.id.tv1);
        String artist = et.getText().toString();
        task.execute("http://www.free-map.org.uk/index.php");

    }

}
