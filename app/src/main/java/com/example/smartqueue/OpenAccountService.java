package com.example.smartqueue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.DateFormat;


import java.util.HashMap;

import dmax.dialog.SpotsDialog;


public class OpenAccountService extends AppCompatActivity {

    AlertDialog waitingDialog;

    DatabaseReference reff,reff2,reff3;
    TextView counter,token,branch_name,service_tag;

    int token_no,my_token_no=-1;
    Services member;

    String b_name,service_type,Counter,Token,Del_Token,date,startTime,endTime,QuitTokens,currentToken;
     TextView dateDisplay;

     Calendar calendar;
     SimpleDateFormat format;
     //Long waitingTime;

     long start;

     long waitingTime,arrivalRate,serviceRate;
     TextView waitTimeDisplay;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_account_service);



        waitingDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Data has been updated")
                .setCancelable(true)
                .build();

        Intent intent = getIntent();
        b_name = intent.getStringExtra("branch");
        branch_name = (TextView)findViewById(R.id.branch_name);
        branch_name.setText(b_name);
        service_type = intent.getStringExtra("service");

        service_tag = (TextView)findViewById(R.id.service_view);
        service_tag.setText(service_type);




        counter = (TextView)findViewById(R.id.counter_no);
        token = (TextView)findViewById(R.id.token_no);


        Calendar calendar = Calendar.getInstance();
        date = DateFormat.getDateInstance().format(calendar.getTime());

         SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        //startTime = format.format(calendar.getTime());

        //start = System.nanoTime();
        start = System.currentTimeMillis();



        //Date currentTime = Calendar.getInstance().getTime();
        dateDisplay = (TextView)findViewById(R.id.date_display);
        dateDisplay.setText(date.toString());



        reff = FirebaseDatabase.getInstance().getReference().child("Branches").child(b_name).child("Services").child(service_type).child(date);








        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("Counters").getValue()==null || dataSnapshot.child("Tokens").getValue()==null)
                    {
                        reff = FirebaseDatabase.getInstance().getReference().child("Branches").child(b_name).child("Services").child(service_type).child(date);
                        reff.child("Counters").setValue(0);
                        reff.child("Tokens").setValue(0);
                    }

                Counter = dataSnapshot.child("Counters").getValue().toString();


                Token = dataSnapshot.child("Tokens").getValue().toString();
                token_no = Integer.parseInt(Token) +1;

                if (my_token_no!=-1)
                {
                    token.setText(""+my_token_no);
                    counter.setText(Counter);
                }

                else
                {
                    my_token_no = token_no;
                    token.setText(""+my_token_no);
                    counter.setText(Counter);

                    reff = FirebaseDatabase.getInstance().getReference().child("Branches").child(b_name).child("Services").child(service_type).child(date);
                    reff.child("Tokens").setValue(token_no);
                }





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });



        reff2 = FirebaseDatabase.getInstance().getReference().child("Branches").child(b_name).child("Services").child(service_type).child(date);
        reff2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentToken = dataSnapshot.child("currentToken").getValue().toString();

                serviceCompleted();

                /*

                if (my_token_no==Integer.parseInt(currentToken))
                {

                    Intent intent = new Intent(this, ServiceCompleted.this);


                 */


                    /*


                    Calendar calendar = Calendar.getInstance();

                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                    endTime = format.format(calendar.getTime());

                    //calculateWaitingTime();


                    waitingTime = Long.parseLong(endTime) - Long.parseLong(startTime);

                    //waitingTime = waitingTime/(60 * 1000) % 60;

                    //Log.i("Waiting time", ""+waitingTime);

                    dateDisplay.setText(" "+ waitingTime);





                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(.this);
                    builder.setMessage("This is your turn");
                    androidx.appcompat.app.AlertDialog dialog = builder.create();
                    dialog.show();











                }
                */
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    /*

        HashMap<String, Object> result = new HashMap<>();
        token_no+=1;
        result.put("Tokens", token_no);
        reff2 = FirebaseDatabase.getInstance().getReference().child("Branches").child(b_name).child("Services").child(service_type);
        reff2.updateChildren(result);
        Log.i("Token Number", ""+token_no);

     */


    reff3 = FirebaseDatabase.getInstance().getReference().child("Branches").child(b_name).child("Services").child(service_type).child(date);
    reff3.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            arrivalRate = Long.parseLong(dataSnapshot.child("arrivalRatePerHour").getValue().toString());
            serviceRate = Long.parseLong(dataSnapshot.child("serviceRatePerHour").getValue().toString());

            calculatewaitingTime();





        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });





    }

    private void calculatewaitingTime() {

        waitingTime = arrivalRate/serviceRate*(serviceRate-arrivalRate);

        waitTimeDisplay = (TextView)findViewById(R.id.waitingTimeView);
        waitTimeDisplay.setText("Your service will be completed in less than\n" + Math.abs(waitingTime) + " Minutes");
    }

    private void serviceCompleted() {

        if (my_token_no==Integer.parseInt(currentToken))6
        {
            Intent intent1 = new Intent(this,ServiceCompleted.class);
            intent1.putExtra("startTime",start);
            startActivity(intent1);
        }
    }

    public void service_quit(final View view) {

        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Del_Token = dataSnapshot.child("Tokens").getValue().toString();
                QuitTokens = dataSnapshot.child("Quit").child("TokenNo").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setMessage("Are you sure want to Quit from the Queue?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //token_no-=1;
                HashMap<String,Object> result = new HashMap<>();
                QuitTokens += "," + my_token_no;
                result.put("TokenNo",(QuitTokens));
                reff2 = FirebaseDatabase.getInstance().getReference().child("Branches").child(b_name).child("Services").child(service_type).child(date).child("Quit");
                reff2.updateChildren(result);
                finish();

            }
        });


        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();
    }
}
