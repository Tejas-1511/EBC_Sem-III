package com.example.ebc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;

public class update extends AppCompatActivity {
Button Home;
TextView name,num,mail;
DatabaseReference rootDatabaseref;
FirebaseAuth fAuth;
FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Home=findViewById(R.id.home);
        name=findViewById(R.id.Name);
        mail=findViewById(R.id.outmail);
        num=findViewById(R.id.outnum);
        rootDatabaseref= FirebaseDatabase.getInstance().getReference().child("Users");
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        String userid=fAuth.getCurrentUser().getUid();

        DocumentReference documentReference=fStore.collection("Admin").document(userid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                mail.setText(value.getString("Email"));
                num.setText(value.getString("Number"));
                name.setText(value.getString("Name"));
            }
        });
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(update.this,Home.class);
                startActivity(intent);
            }
        });

    }
}
