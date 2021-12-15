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
import java.util.Map;

public class UserSignUp extends AppCompatActivity {
Button usersignin,Home;
EditText uemail,uaddress,umeterid,upassword;
FirebaseAuth fAuth;
FirebaseFirestore fStore;
String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);

        Home=findViewById(R.id.hom);
        usersignin=findViewById(R.id.Usersignin);
        uaddress=findViewById(R.id.UAddress);
        uemail=findViewById(R.id.UEmail);
        umeterid=findViewById(R.id.UMeterid);
        upassword=findViewById(R.id.UPassword);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),Home.class));
            finish();
        }

        usersignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=uemail.getText().toString().trim();
                String password=upassword.getText().toString().trim();
                String meter=umeterid.getText().toString().trim();
                String address=uaddress.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    uemail.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    upassword.setError("Password is Required");
                    return;
                }
                if(password.length()<6){
                    upassword.setError("Password must have greater than 5 characters");
                    return;
                }
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(UserSignUp.this, "User Created", Toast.LENGTH_SHORT).show();
                            userid=fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference= fStore.collection("Users").document(userid);
                            Map<String,Object> user=new HashMap<>();
                            user.put("Address",address);
                            user.put("Email",email);
                            user.put("Meterid",meter);
                            documentReference.set(user);
                            startActivity(new Intent(getApplicationContext(),Home.class));
                        }
                        else {
                            Toast.makeText(UserSignUp.this, "Error!"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserSignUp.this,Start.class);
                startActivity(intent);
            }
        });
    }
}
