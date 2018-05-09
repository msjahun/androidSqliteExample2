package com.example.campus_comuputer.sqldatabasesample;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CoursesListActivity extends AppCompatActivity {
    private static final String TAG = "CoursesListActivity";
     ArrayList<student> studentsList= new ArrayList<>();
    ArrayList<String> array = new ArrayList<>();
    private coursesDB v1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_list);
        final ListView listall;
        final String dbTableName;
        final ArrayAdapter<String> adapter;


        v1=new coursesDB(this);

        Bundle bundle = getIntent().getExtras();
      studentsList= (ArrayList<student>) bundle.getSerializable("StudentsList");
        dbTableName = (String)bundle.getString("dbTableName");



        listall=(ListView)findViewById(R.id.listView);
        
        for(student stu : studentsList){
         array.add(stu.getStudentNumber()+" "+stu.getStudentGrade());

        }

        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,array);
        listall.setAdapter(adapter);

                    listall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



                            SQLiteDatabase db =v1.getWritableDatabase();
                            db.execSQL("DELETE FROM "+dbTableName +" WHERE NUMBER ='"+studentsList.get(i).getStudentNumber()+"' AND GRADE ='"+studentsList.get(i).getStudentGrade()+"' ");
                            Toast.makeText(CoursesListActivity.this, "deleted record "+studentsList.get(i).getStudentNumber(), Toast.LENGTH_SHORT).show();

                            array.remove(i);
                            studentsList.remove(i);

                            listall.setAdapter(adapter);
                        }
                    });

                   listall.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                       @Override
                       public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                           Log.d(TAG, "onItemLongClick: called");
                           Intent intent = new Intent(CoursesListActivity.this, CourseModifyActivity.class);
                           Bundle bundle=new Bundle();
                           bundle.putString("dbTableName", dbTableName);
                           bundle.putString("bundleNumber", studentsList.get(i).getStudentNumber());
                           bundle.putString("bundleGrade", studentsList.get(i).getStudentGrade());



                           intent.putExtras(bundle);
                           startActivity(intent);
                           Toast.makeText(CoursesListActivity.this, "On item long click "+studentsList.get(i).getStudentNumber(), Toast.LENGTH_SHORT).show();
                           return true;
                       }
                   });
    }
}
