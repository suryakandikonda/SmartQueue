package com.example.smartqueue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class AdminService extends AppCompatActivity {

    TextView branch_view,service_view,dateDisplay,people_view,current_token_view;
    String service_type,b_name,date,QuitTokensString,currentToken,no_of_tokens;
    Integer counter_no;
    EditText counter_view;
    DatabaseReference reff,reff2,reff3;


    int RTokenCount, RPrevCount, RRate;

    int ArrivalRate;


    ArrayList<String> QuitTokensArray = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_service);


        Intent intent = getIntent();

        b_name = intent.getStringExtra("Branch");
        branch_view = (TextView)findViewById(R.id.branch);
        branch_view.setText(b_name);

        service_type = intent.getStringExtra("Service");
        service_view = (TextView)findViewById(R.id.service);
        service_view.setText(service_type);


        Calendar calendar = Calendar.getInstance();
        date = DateFormat.getDateInstance().format(calendar.getTime());

        dateDisplay = (TextView)findViewById(R.id.date_display);
        dateDisplay.setText(date.toString());





    }

    public void updateCounter(View view) {
        counter_view = (EditText)findViewById(R.id.counter_no_input);

        counter_no = Integer.parseInt(counter_view.getText().toString());
        reff = FirebaseDatabase.getInstance().getReference().child("Branches").child(b_name).child("Services").child(service_type).child(date);
        HashMap<String,Object> result = new HashMap<>();
        result.put("Counters",counter_no);
        result.put("Tokens",0);
        result.put("arrivalRatePerHour",15);
        result.put("currentToken",0);
        result.put("prevTokens",0);
        result.put("serviceRatePerHour",5);
        result.put("waitingTime",18);
        reff.updateChildren(result);
        reff = FirebaseDatabase.getInstance().getReference().child("Branches").child(b_name).child("Services").child(service_type).child(date).child("Quit");
        HashMap<String,Object> resultt = new HashMap<>();
        resultt.put("TokenNo","");
        reff.updateChildren(resultt);
        Toast.makeText(this, "Counter has been updated", Toast.LENGTH_SHORT).show();
    }

    public void notifyNext(View view) {

        people_view = (TextView)findViewById(R.id.people_in_queue);
        current_token_view = (TextView)findViewById(R.id.current_token_data);



        reff2 = FirebaseDatabase.getInstance().getReference().child("Branches").child(b_name).child("Services").child(service_type).child(date);

        reff2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                QuitTokensString = dataSnapshot.child("Quit").child("TokenNo").getValue().toString();
                currentToken = dataSnapshot.child("currentToken").getValue().toString();
                no_of_tokens = (dataSnapshot.child("Tokens").getValue().toString());


                String[] array = QuitTokensString.split(",");
                for (String token_no : array)
                {
                    if (token_no.equals(currentToken))
                    {
                        reff = FirebaseDatabase.getInstance().getReference().child("Branches").child(b_name).child("Services").child(service_type).child(date);
                        if (Integer.parseInt(currentToken)+1 < Integer.parseInt(no_of_tokens)) {
                            reff.child("currentToken").setValue(Integer.parseInt(currentToken) + 1);

                        }

                        else {
                            reff.child("currentToken").setValue(Integer.parseInt(currentToken));

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reff = FirebaseDatabase.getInstance().getReference().child("Branches").child(b_name).child("Services").child(service_type).child(date);
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentToken = (dataSnapshot.child("currentToken").getValue().toString());
                no_of_tokens = (dataSnapshot.child("Tokens").getValue().toString());
                people_view.setText(""+no_of_tokens);
                if (Integer.parseInt(currentToken)+1<=Integer.parseInt(no_of_tokens)) {
                    HashMap<String, Object> result = new HashMap<>();
                    result.put("currentToken", Integer.parseInt(currentToken) + 1);
                    reff.updateChildren(result);
                    Toast.makeText(AdminService.this, "Done..", Toast.LENGTH_SHORT).show();
                    int current = Integer.parseInt(currentToken);
                    current +=1;
                    current_token_view.setText(""+current);
                }
                else
                {
                    Toast.makeText(AdminService.this, "No Customers", Toast.LENGTH_SHORT).show();
                    current_token_view.setText(""+Integer.parseInt(currentToken));
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        /*
        reff = FirebaseDatabase.getInstance().getReference().child("Branches").child(b_name).child("Services").child(service_type).child(date);
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentToken = (dataSnapshot.child("currentToken").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        reff = FirebaseDatabase.getInstance().getReference().child("Branches").child(b_name).child("Services").child(service_type).child(date).child("Quit");
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                QuitTokensString = dataSnapshot.child("TokenNo").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        QuitTokensArray = (ArrayList<String>) Arrays.asList(QuitTokensString.split(","));

            if (QuitTokensArray.contains(currentToken))
            {
                reff = FirebaseDatabase.getInstance().getReference().child("Branches").child(b_name).child("Services").child(service_type).child(date).child("currentToken");
                reff.setValue(Integer.parseInt(currentToken)+1);
            }

            else
            {
                Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
            }

         */


        Timer timer = new Timer ();
        TimerTask hourlyTask = new TimerTask () {
            @Override
            public void run () {
                reff3 = FirebaseDatabase.getInstance().getReference().child("Branches").child(b_name).child("Services").child(service_type).child(date);
                reff3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        RTokenCount = Integer.parseInt(dataSnapshot.child("Tokens").getValue().toString());
                        RPrevCount = Integer.parseInt(dataSnapshot.child("prevTokens").getValue().toString());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                if (RTokenCount==RPrevCount)
                {
                    reff3.child("arrivalRatePerHour").setValue(RTokenCount);
                }
                else
                    reff3.child("arrivalRatePerHour").setValue(RTokenCount - RPrevCount);


            }
        };

// schedule the task to run starting now and then every hour...
        timer.schedule (hourlyTask, 0l, 1000*60*60);








    }
}
