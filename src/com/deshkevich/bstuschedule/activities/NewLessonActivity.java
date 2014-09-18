package com.deshkevich.bstuschedule.activities;

import com.deshkevich.bstuschedule.beans.Lesson;
import com.deshkevich.bstuschedule.utils.DbUtil;
import com.example.bstuschedule.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class NewLessonActivity extends Activity {

	EditText lessonEditText = null;
	EditText teacherEditText = null; 
	EditText audienceEditText = null;
	EditText buildingEditText = null;
	Spinner daySpinner = null;
	Spinner timeSpinner = null;
	Spinner weekSpinner = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add);
		
		lessonEditText = (EditText)findViewById(R.id.editText1);
		teacherEditText = (EditText)findViewById(R.id.editText4);
		audienceEditText = (EditText)findViewById(R.id.editText2);
		buildingEditText = (EditText)findViewById(R.id.editText3);
		daySpinner = (Spinner)findViewById(R.id.spinner1);
		timeSpinner = (Spinner)findViewById(R.id.spinner2);
		weekSpinner = (Spinner)findViewById(R.id.spinner3);
	}
	
    public void onClick(View v){
    	if(lessonEditText.getText().toString().trim().equals("")){
    		lessonEditText.setError(("Название предмета необходимо!"));
    	}else if(teacherEditText.getText().toString().trim().equals("")){
    		teacherEditText.setError(("Имя преподователя необходимо!"));
    	}else if(audienceEditText.getText().toString().trim().equals("")){
    		audienceEditText.setError("Номер аудитории обязателен!");
    	}else if(buildingEditText.getText().toString().trim().equals("")){
    		buildingEditText.setError("Необходимо ввести номер корпуса!");
    	} else if(Integer.parseInt(buildingEditText.getText().toString()) > 5){
    		buildingEditText.setError("Всего 5 корпусов!");
    	}else {    			    	
	    	Lesson lesson = new Lesson();
	    	lesson.setLessonName(lessonEditText.getText().toString());
	    	lesson.setTeacherName(teacherEditText.getText().toString());
	    	lesson.setAudience(audienceEditText.getText().toString());
	    	lesson.setBuilding(buildingEditText.getText().toString());
	    	lesson.setDay(daySpinner.getSelectedItem().toString());
	    	lesson.setTime(timeSpinner.getSelectedItem().toString());  
	    	lesson.setWeek(weekSpinner.getSelectedItem().toString());  	
	    	
	    	DbUtil dbUtil = new DbUtil(NewLessonActivity.this);
	    	if(dbUtil.addLesson(lesson)){	    	
	    		backClick(v);
	    	} else{
	    		AlertDialog.Builder builder = new AlertDialog.Builder(NewLessonActivity.this);
	    		builder.setTitle("Ошибка!")
	    				.setMessage("В это время идет другая пара!")
	    				.setCancelable(false)
	    				.setNegativeButton("Cча исправлю!",
	    						new DialogInterface.OnClickListener() {
	    							public void onClick(DialogInterface dialog, int id) {
	    								dialog.cancel();
	    							}
	    						});
	    		AlertDialog alert = builder.create();
	    		alert.show();
	    	}
    	}
    }
	
    public void backClick(View v){    	
	    Intent intent = new Intent(NewLessonActivity.this, MainActivity.class);
	    finish();
	    startActivity(intent);
    }

}
