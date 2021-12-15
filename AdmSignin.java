package com.example.ebc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AdminSignup extends AppCompatActivity {
EditText mpassword,memail,mmobile,mname;
Button signins,Home;
FirebaseAuth fAuth;
FirebaseFirestore fStore;
String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_signup);
        mpassword=findViewById(R.id.Apassword);
        memail=findViewById(R.id.Aemail);
        mmobile=findViewById(R.id.Amobile);
        Home=findViewById(R.id.gohome);
        mname=findViewById(R.id.Aname);
        signins=findViewById(R.id.Asignup);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),AdminHome.class));
            finish();
        }

        signins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=memail.getText().toString().trim();
                String password=mpassword.getText().toString().trim();
                String number=mmobile.getText().toString().trim();
                String name=mname.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    memail.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mpassword.setError("Password is Required");
                    return;
                }
                if(password.length()<6){
                    mpassword.setError("Password must have greater than 5 characters");
                    return;
                }
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(AdminSignup.this, "User Created", Toast.LENGTH_SHORT).show();
                            userid=fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference= fStore.collection("Admin").document(userid);
                            Map<String,Object> user=new HashMap<>();
                            user.put("Number",number);
                            user.put("Email",email);
                            user.put("Name",name);
                            documentReference.set(user);
                            startActivity(new Intent(getApplicationContext(),AdminHome.class));
                        }
                        else {
                            Toast.makeText(AdminSignup.this, "Error!"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminSignup.this,Start.class);
                startActivity(intent);
            }
        });
    }
}
