package com.deshkevich.bstuschedule.utils;

import java.util.ArrayList;
import java.util.Calendar;

import com.deshkevich.bstuschedule.beans.Lesson;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbUtil extends SQLiteOpenHelper {
	
	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "schedule";

	public static final String TABLE_NAME = "lessons";
	
	public static final String LESSON = "lesson";
	public static final String TEACHER = "teacher";
	public static final String DAY = "day";
	public static final String TIME = "time";
	public static final String AUDIENCE = "audience";
	public static final String BUILDING = "building";
	public static final String WEEK = "week";
	
	public static final String MONDAY = "Понедельник";
	public static final String TUESDAY = "Вторник";
	public static final String WEDNESDAY = "Среда";
	public static final String THURSDAY = "Четверг";
	public static final String FRIDAY = "Пятница";
	public static final String SATURDAY = "Суббота";
	public static final String SUNDAY = "Воскресенье";
	
//	private static String DB_PATH = "/data/data/com.tmm.android.chuck/databases/";
	
	private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( _id integer primary key autoincrement, "
	      + LESSON + " TEXT, " + TEACHER + " TEXT, " + DAY + " TEXT, " + TIME + " TEXT, " + AUDIENCE + " TEXT, " + 
			BUILDING + " TEXT, " + WEEK + " TEXT )";

	public DbUtil(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		
		// TODO Auto-generated constructor stub
	}
	
	public boolean addLesson(Lesson lesson){
		SQLiteDatabase readDb = this.getReadableDatabase();
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE day = '" + lesson.getDay() + "' AND time = '" 
						+ lesson.getTime().substring(0, 2) + "'";
		if(!lesson.getWeek().equals("0")){
			query = query + " AND (week = '" + lesson.getWeek() + "' OR week='0')";
		}
		Cursor cursor = readDb.rawQuery(query, null);
		if(cursor.moveToFirst()){
			return false;
		} else{			
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues cv = new ContentValues();
			cv.put(LESSON, lesson.getLessonName());
			cv.put(TEACHER, lesson.getTeacherName());
			cv.put(DAY, lesson.getDay());
			cv.put(TIME, lesson.getTime().substring(0, 2));
			cv.put(AUDIENCE, lesson.getAudience());
			cv.put(BUILDING, lesson.getBuilding());
			cv.put(WEEK, lesson.getWeek());
			db.insert(TABLE_NAME, null, cv);
			db.close();
			return true;
		}
	}
	
	public ArrayList<Lesson> getLessons(String day){
		Calendar c = Calendar.getInstance();		
		int weekNumber = c.get(Calendar.WEEK_OF_YEAR);
		SQLiteDatabase db = this.getReadableDatabase();
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE day = '" + day + "'";
		if(weekNumber%2 != 1){
			query = query + " AND (week = '1' OR week='0')";
		} else{
			query = query + " AND (week = '2' OR week='0')";
		}
		ArrayList<Lesson> lessons = new ArrayList<Lesson>();		
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst()){
			do{
				Lesson lesson = new Lesson();
				lesson.setLessonName(cursor.getString(1));
				lesson.setTeacherName(cursor.getString(2));
				lesson.setDay(cursor.getString(3));
				lesson.setTime(cursor.getString(4));
				lesson.setAudience(cursor.getString(5));
				lesson.setBuilding(cursor.getString(6));
				lesson.setWeek(cursor.getString(7));
				lessons.add(lesson);
			} while(cursor.moveToNext());
		}
		db.close();		
		return lessons;	
	}
	
	public ArrayList<Lesson> getTodayLessons(){
		String day = null;
		Calendar c = Calendar.getInstance();
		int dayNumber = c.get(Calendar.DAY_OF_WEEK);
		switch(dayNumber){
			case Calendar.MONDAY:
				day = MONDAY;
				break;
			case Calendar.TUESDAY:
				day = TUESDAY;
				break;
			case Calendar.WEDNESDAY:
				day = WEDNESDAY;
				break;
			case Calendar.THURSDAY:
				day = THURSDAY;
				break;
			case Calendar.FRIDAY:
				day = FRIDAY;
				break;
			case Calendar.SATURDAY:
				day = SATURDAY;
				break;
			case Calendar.SUNDAY:
				day = SUNDAY;
				break;
			default: 
				break;				
		}
		return getLessons(day);		
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		arg0.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
