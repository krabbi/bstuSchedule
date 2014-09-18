package com.deshkevich.bstuschedule.activities;

import java.util.ArrayList;

import com.deshkevich.bstuschedule.beans.Lesson;
import com.deshkevich.bstuschedule.utils.DbUtil;
import com.example.bstuschedule.R;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
	


	TextView firstLesson = null;
	TextView firstTeacher = null;
	TextView firstCab = null;
	TextView secondLesson = null;
	TextView secondTeacher = null;
	TextView secondCab = null;
	TextView thirdLesson = null;
	TextView thirdTeacher = null;
	TextView thirdCab = null;
	TextView fourthLesson = null;
	TextView fourthTeacher = null;
	TextView fourthCab = null;
	TextView day = null;
	final String[] daysOfWeek ={"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        showSchedule();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private void showSchedule(){        
        firstLesson = (TextView)findViewById(R.id.first_lesson);
        firstTeacher = (TextView)findViewById(R.id.first_teacher);
        firstCab = (TextView)findViewById(R.id.first_cab);
        secondLesson = (TextView)findViewById(R.id.second_lesson);
        secondTeacher = (TextView)findViewById(R.id.second_teacher);
        secondCab = (TextView)findViewById(R.id.second_cab);
        thirdLesson = (TextView)findViewById(R.id.third_lesson);
        thirdTeacher = (TextView)findViewById(R.id.third_teacher);
        thirdCab = (TextView)findViewById(R.id.third_cab);
        fourthLesson = (TextView)findViewById(R.id.fourth_lesson);
        fourthTeacher = (TextView)findViewById(R.id.fourth_teacher);
        fourthCab = (TextView)findViewById(R.id.fourth_cab);
        day = (TextView)findViewById(R.id.editText1);
        
        DbUtil dbUtil = new DbUtil(MainActivity.this);        
        ArrayList<Lesson> lessons = dbUtil.getTodayLessons();
        for(Lesson lesson: lessons){
        	if(lesson.getTime().equals("13")){
        		firstLesson.setText(lesson.getLessonName());
        		firstTeacher.setText(lesson.getTeacherName());
        		firstCab.setText(lesson.getAudience() + "-" + lesson.getBuilding());
        	} else if(lesson.getTime().equals("15")){
        		secondLesson.setText(lesson.getLessonName());
        		secondTeacher.setText(lesson.getTeacherName());
        		secondCab.setText(lesson.getAudience() + "-" + lesson.getBuilding());
        	} else if(lesson.getTime().equals("17")){
        		thirdLesson.setText(lesson.getLessonName());
        		thirdTeacher.setText(lesson.getTeacherName());
        		thirdCab.setText(lesson.getAudience() + "-" + lesson.getBuilding());
        	} else if(lesson.getTime().equals("19")){
        		fourthLesson.setText(lesson.getLessonName());
        		fourthTeacher.setText(lesson.getTeacherName());
        		fourthCab.setText(lesson.getAudience() + "-" + lesson.getBuilding());
        	}
        }   
        
        day.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		        builder.setTitle("Расписание на:"); // 

		        builder.setItems(daysOfWeek, new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int item) {            
		                DbUtil dbUtil = new DbUtil(MainActivity.this);   
		            	ArrayList<Lesson> lessons = dbUtil.getLessons(daysOfWeek[item]);
		            	firstLesson.setText("");
		        		firstTeacher.setText("");
		        		firstCab.setText("");
		            	secondLesson.setText("");
		        		secondTeacher.setText("");
		        		secondCab.setText("");
		            	thirdLesson.setText("");
		        		thirdTeacher.setText("");
		        		thirdCab.setText("");
		            	fourthLesson.setText("");
		        		fourthTeacher.setText("");
		        		fourthCab.setText("");
		            	day.setText("Расписание на " + daysOfWeek[item]);
		                for(Lesson lesson: lessons){
		                	if(lesson.getTime().equals("13")){
		                		firstLesson.setText(lesson.getLessonName());
		                		firstTeacher.setText(lesson.getTeacherName());
		                		firstCab.setText(lesson.getAudience() + "-" + lesson.getBuilding());
		                	} else if(lesson.getTime().equals("15")){
		                		secondLesson.setText(lesson.getLessonName());
		                		secondTeacher.setText(lesson.getTeacherName());
		                		secondCab.setText(lesson.getAudience() + "-" + lesson.getBuilding());
		                	} else if(lesson.getTime().equals("17")){
		                		thirdLesson.setText(lesson.getLessonName());
		                		thirdTeacher.setText(lesson.getTeacherName());
		                		thirdCab.setText(lesson.getAudience() + "-" + lesson.getBuilding());
		                	} else if(lesson.getTime().equals("19")){
		                		fourthLesson.setText(lesson.getLessonName());
		                		fourthTeacher.setText(lesson.getTeacherName());
		                		fourthCab.setText(lesson.getAudience() + "-" + lesson.getBuilding());
		                	}
		                }    
		                Toast.makeText(getApplicationContext(),
		                        "Расписание на " + daysOfWeek[item],
		                        Toast.LENGTH_SHORT).show();		                
		            }
		        });
		        builder.setCancelable(true);
	    		AlertDialog alert = builder.create();
	    		alert.show();
			}
		});
    }
    
    public void newLessonClick(View v){
    	Intent intent = new Intent(MainActivity.this, NewLessonActivity.class);
    	finish();
    	startActivity(intent);
    }
    
}
