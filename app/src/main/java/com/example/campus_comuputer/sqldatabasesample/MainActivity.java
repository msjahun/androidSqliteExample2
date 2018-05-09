package com.example.campus_comuputer.sqldatabasesample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button buttonCmpe419;
    Button buttonCmpe538;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonCmpe419 = findViewById(R.id.buttonCmpe419);
        buttonCmpe538 = findViewById(R.id.buttonCmpe538);

        buttonCmpe419.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CoursesEditActivity.class);
                intent.putExtra("dbTableName", "CMPE419");
                startActivity(intent);


            }
        });



        buttonCmpe538.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CoursesEditActivity.class);
                intent.putExtra("dbTableName", "CMPE538");
                startActivity(intent);

            }
        });
    }
}
