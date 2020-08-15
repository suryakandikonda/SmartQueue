package com.example.smartqueue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BankServicesPage extends AppCompatActivity {

    private long backPressedTime;
    TextView branch_name,people,sample;

    String Token,b_name,Token_Number;
    DatabaseReference reff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_services_page);

        branch_name = (TextView)findViewById(R.id.branch_name_view);
        Intent intent = getIntent();
        b_name = intent.getStringExtra("branch");
        branch_name.setText(b_name);

    }

    @Override
    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis())
        {
            super.onBackPressed();

        }
        else
        {
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
            finish();
            System.exit(0);
        }

        backPressedTime = System.currentTimeMillis();
    }

    public void openAccountService(View view) {




        Intent intent = new Intent(this, OpenAccountService.class);
        intent.putExtra("branch",branch_name.getText().toString());

        intent.putExtra("token", Token_Number);
        intent.putExtra("service", "Open New Account");
        startActivity(intent);



    }

    public void openDepositService(View view) {




        Intent intent = new Intent(this, OpenAccountService.class);
        intent.putExtra("branch",branch_name.getText().toString());

        intent.putExtra("token", Token_Number);
        intent.putExtra("service", "Deposit Amount");
        startActivity(intent);



    }

    public void openWithdrawService(View view) {




        Intent intent = new Intent(this, OpenAccountService.class);
        intent.putExtra("branch",branch_name.getText().toString());

        intent.putExtra("token", Token_Number);
        intent.putExtra("service", "Withdraw Amount");
        startActivity(intent);



    }

    public void openCreditCardService(View view) {




        Intent intent = new Intent(this, OpenAccountService.class);
        intent.putExtra("branch",branch_name.getText().toString());

        intent.putExtra("token", Token_Number);
        intent.putExtra("service", "Credit Card");
        startActivity(intent);



    }
}
