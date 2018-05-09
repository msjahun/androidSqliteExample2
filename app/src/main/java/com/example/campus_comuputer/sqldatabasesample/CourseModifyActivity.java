package com.example.campus_comuputer.sqldatabasesample;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CourseModifyActivity extends AppCompatActivity {
    private static final String TAG = "CourseModifyActivity";

    TextView textViewCourseName;
    EditText editTextCourseNumber;
    EditText editTextCourseGrade;
    Button buttonSave;
    Button buttonListAll;
    String dbTableName,bundleNumber, bundleGrade;
    ArrayList<student> studentsList= new ArrayList<>();


    private coursesDB v1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_modify);
        Bundle bundle = getIntent().getExtras();
       dbTableName = (String)bundle.getString("dbTableName");
       bundleNumber = (String)bundle.getString("bundleNumber");
       bundleGrade = (String)bundle.getString("bundleGrade");

        v1=new coursesDB(this);
        textViewCourseName = findViewById(R.id.textViewModifyCourseName);
        editTextCourseGrade = findViewById(R.id.editTextModifyGrade);
        editTextCourseNumber = findViewById(R.id.editTextModifyNumber);
        buttonSave = findViewById(R.id.buttonModifySave);
        buttonListAll = findViewById(R.id.buttonModifyListAll);
        editTextCourseNumber.setText(bundleNumber);
        editTextCourseGrade.setText(bundleGrade);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveRecord(editTextCourseNumber.getText().toString(), editTextCourseGrade.getText().toString());

            }
        });


    }


    private void getAllRecord() {
        Log.d(TAG, "getAllRecord: called");

        SQLiteDatabase db=v1.getReadableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM "+dbTableName +" ",null);


        studentsList.clear();
        while (c.moveToNext()) {
            Log.d(TAG, "getAllRecord: "+c.getString(0)+ "  " +c.getString(1));
            studentsList.add( new student( c.getString(0), c.getString(1)));
        }



        c.close();
        clear();

    }

    private void saveRecord(String number, String grade) {
        Log.d(TAG, "saveRecord: validateInput ="+validateNumberinput(number)+" "+ "Validate Grade ="+validateGradeInput(grade));
        Log.d(TAG, "saveRecord: number ="+number+ " "+"grade="+grade);
        if(validateNumberinput(number) && validateGradeInput(grade) ){
            Log.d(TAG, "saveRecord: About to execute the update query");
            Log.d(TAG, "saveRecord: sqlquery "+"UPDATE "+dbTableName +" SET  NUMBER ='"+number+"', GRADE ='"+grade+"'  WHERE NUMBER ='"+bundleNumber+"' ");




            SQLiteDatabase db =v1.getWritableDatabase();
            Cursor c=db.rawQuery("SELECT * FROM "+dbTableName +"   WHERE NUMBER ='"+bundleNumber+"' ",null);
            if(c.moveToFirst()){
                Log.d(TAG, "saveRecord: insidecursor");

            db.execSQL("UPDATE "+dbTableName +" SET  NUMBER ='"+number+"', GRADE ='"+grade+"'  WHERE NUMBER ='"+bundleNumber+"' AND GRADE ='"+bundleGrade+"'  ");
            Toast.makeText(this, "Record has been updated", Toast.LENGTH_SHORT).show();

            Log.d(TAG, "saveRecord: have finished Executing query");

                Toast.makeText(this,"Success Record Modified",Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(this,"Error!!!",Toast.LENGTH_SHORT).show();

            }









            Log.d(TAG, "onClickButtonListAll: called");
            getAllRecord();
            Intent intent = new Intent(CourseModifyActivity.this, CoursesListActivity.class);
            Bundle bundle=new Bundle();
            bundle.putSerializable("StudentsList",studentsList );
            bundle.putString("dbTableName", dbTableName);
            intent.putExtras(bundle);
            startActivity(intent);
        }



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
