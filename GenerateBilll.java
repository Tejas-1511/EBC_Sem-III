package com.example.ebc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class generatebill2 extends AppCompatActivity {
  TextView email,units,meterid,dates,grand,Addresses;
  Button Home;
  FirebaseAuth fAuth;
  FirebaseFirestore fStore;
  String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generatebill2);

        email=findViewById(R.id.inpemail);
        units=findViewById(R.id.inpunits);
        grand=findViewById(R.id.inptotal);
        meterid=findViewById(R.id.inpmeterid);
        dates=findViewById(R.id.inpDate);
        Home=findViewById(R.id.home);
        Addresses=findViewById(R.id.inpAddress);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        userid=fAuth.getCurrentUser().getUid();

        DocumentReference documentReference=fStore.collection("Users").document(userid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                email.setText(value.getString("Email"));
                meterid.setText(value.getString("Meterid"));
                Addresses.setText(value.getString("Address"));
            }
        });
        String consumed=getIntent().getStringExtra("UnitsUsed");
        String date=getIntent().getStringExtra("Dates");
        int amount=Integer.parseInt(consumed);
        amount=(amount*5)+200;
        dates.setText(date);
        units.setText(consumed);
        grand.setText(String.valueOf("Rs."+amount));

        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(generatebill2.this,Home.class);
                startActivity(intent);
            }
        });
    }
}
