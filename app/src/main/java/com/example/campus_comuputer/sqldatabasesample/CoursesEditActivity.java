package com.example.campus_comuputer.sqldatabasesample;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CoursesEditActivity extends AppCompatActivity {
        TextView textViewCourseName;
        EditText editTextCourseNumber;
        EditText editTextCourseGrade;
        Button buttonAdd;
        Button buttonListAll;
    String dbTableName;
    private static final String TAG = "CoursesEditActivity";
    ArrayList<student> studentsList= new ArrayList<>();


    private coursesDB v1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_edit);
        Bundle bundle = getIntent().getExtras();
     dbTableName = (String)bundle.getString("dbTableName");


        textViewCourseName = findViewById(R.id.textViewCourseName);
        editTextCourseNumber = findViewById(R.id.editTextNumber);
        editTextCourseGrade = findViewById(R.id.editTextGrade);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonListAll = findViewById(R.id.buttonListAll);

        v1=new coursesDB(this);



        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClickButtonAdd: called");
            addRecord(editTextCourseNumber.getText().toString(), editTextCourseGrade.getText().toString());

            }
        });

        buttonListAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClickButtonListAll: called");
                getAllRecord();
                Intent intent = new Intent(CoursesEditActivity.this, CoursesListActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("StudentsList",studentsList );
               bundle.putString("dbTableName", dbTableName);
                intent.putExtras(bundle);
                startActivity(intent);

               // studentsList.clear();
            }
        });



    }

    private void addRecord(String number, String grade) {

        if(validateNumberinput(number) && validateGradeInput(grade) ){

            SQLiteDatabase db =v1.getWritableDatabase();
            db.execSQL("INSERT INTO "+dbTableName +" VALUES('"+number+"','"+grade+"');");
        }

        clear();

    }

    private void getAllRecord() {
        Log.d(TAG, "getAllRecord: called");

        SQLiteDatabase db=v1.getReadableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM "+dbTableName +" ",null);

        //this is where you should write your database reading code
      //add this line studentsList.removeAll(); or clearAll();
            //our Cursor is c.
          studentsList.clear();
          while (c.moveToNext()) {
              Log.d(TAG, "getAllRecord: "+c.getString(0)+ "  " +c.getString(1));
                  studentsList.add( new student( c.getString(0), c.getString(1)));
                       }
            
           
          
          //studentsList.add("number"+" "+"grade");
        //studentsList.add( "number1"+ " "+"grade1");

c.close();
        //clearText();

        clear();

    }

    private boolean validateNumberinput(String s){
        Log.d(TAG, "validateNumberinput: called");
        if (s.trim().length()==0){
            Toast.makeText(this,"Please enter Valid Number No!!!", Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;

    }


    private boolean validateGradeInput(String s){
        Log.d(TAG, "validateGradeInput: called");
        if (s.trim().length()==0){
            Toast.makeText(this,"Please enter Valid Grade!!!", Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;

    }


        public void clear(){
        editTextCourseGrade.setText("");
        editTextCourseNumber.setText("");
        }



}
