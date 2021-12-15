package com.example.ebc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.LoginFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button signup,letslogin,Home;
    FirebaseAuth fAuth;
    EditText memail,mpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Home=findViewById(R.id.hm);
        memail=findViewById(R.id.Aemail);
        mpassword=findViewById(R.id.Apassword);
        signup=findViewById(R.id.UserSignUp);
        letslogin=findViewById(R.id.LetsLogin);
        fAuth=FirebaseAuth.getInstance();

     letslogin.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             String email=memail.getText().toString().trim();
             String password=mpassword.getText().toString().trim();

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
             fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                     if(task.isSuccessful()){
                         Toast.makeText(MainActivity.this, "Login Succesful", Toast.LENGTH_SHORT).show();
                         startActivity(new Intent(getApplicationContext(),Home.class));
                     }
                     else{
                         Toast.makeText(MainActivity.this, "Error!"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                     }
                 }
             });
         }
     });

     Home.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent intent=new Intent(MainActivity.this,Start.class);
             startActivity(intent);
         }
     });

     signup.setOnClickListener(new View.OnClickListener(){

         @Override
         public void onClick(View view) {
             Intent intent=new Intent(MainActivity.this,UserSignUp.class);
             startActivity(intent);
         }
     });
    }
}
