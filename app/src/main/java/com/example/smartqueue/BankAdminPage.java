package com.example.smartqueue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;

public class BankAdminPage extends AppCompatActivity {

    String b_name,service_input;

    TextView bran_name,token;
    EditText cn_input;
    Spinner service_selected;
    DatabaseReference reff2,reff,reff3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_admin_page);

        Intent intent = getIntent();
        b_name = intent.getStringExtra("branch");
        bran_name = (TextView)findViewById(R.id.branch_name);
        bran_name.setText(b_name);

        Spinner dropdown = findViewById(R.id.service_dropdown_input);
        String[] items = new String[]{"Open New Account", "Deposit Amount", "Withdraw Amount", "Credit Card"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);






    }

    public void start_admin_service(View view) {

        service_selected = (Spinner)findViewById(R.id.service_dropdown_input);
        service_input = service_selected.getSelectedItem().toString();



        Intent intent = new Intent(this,AdminService.class);
        intent.putExtra("Branch",b_name);
        intent.putExtra("Service",service_input);
        startActivity(intent);




        /*

        HashMap<String,Object> result = new HashMap<>();
        result.put("Counters",counter);

        reff2 = FirebaseDatabase.getInstance().getReference().child("Branches").child(b_name).child("Services").child(service_input);
        reff2.updateChildren(result);
        Toast.makeText(this, "Service has been started", Toast.LENGTH_LONG).show();

        reff = FirebaseDatabase.getInstance().getReference().child("Branches").child(b_name).child("Services").child(service_input);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String t_count = dataSnapshot.child("Tokens").getValue().toString();
                token.setText(t_count);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


         */






    }

    public void ClearQueue(View view) {

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setMessage("Are you sure want to Clear the Queue?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                HashMap<String,Object> result = new HashMap<>();
                result.put("Tokens",0);
                reff3 = FirebaseDatabase.getInstance().getReference().child("Branches").child(b_name).child("Services").child(service_input);
                reff3.updateChildren(result);

            }
        });


        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();


    }
}
