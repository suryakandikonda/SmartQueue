package com.example.smartqueue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class ServiceCompleted extends AppCompatActivity {

    String endTime,startTime;
    long waitingTime,end,start;

    double waitInSeconds;

    TextView waitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_completed);



        /*

        Intent intent1 = getIntent();
        start = intent1.getLongExtra("startTime",0);



        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
       // endTime = format.format(calendar.getTime());

        //end = System.nanoTime();

        end = System.currentTimeMillis();

        waitingTime = end - start;

        waitingTime = TimeUnit.MILLISECONDS
                .toMinutes(waitingTime);

        //waitInSeconds = (double)waitingTime/1_000_000_000.0;



        //waitView = (TextView)findViewById(R.id.waitTime);
        //waitView.setText(""+waitingTime);

         */




    }
}
