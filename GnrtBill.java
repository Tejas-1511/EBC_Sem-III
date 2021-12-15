package com.example.ebc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class generatebill1 extends AppCompatActivity {
EditText time,consumption;
Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generatebill1);
        consumption=findViewById(R.id.units);
        time=findViewById(R.id.date);
        next=findViewById(R.id.Createbill);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String duration=time.getText().toString();
                String unit=consumption.getText().toString();

                Intent intent=new Intent(generatebill1.this,generatebill2.class);

                intent.putExtra("Dates",duration);
                intent.putExtra("UnitsUsed",unit);
                startActivity(intent);
            }
        });
    }
}
