package com.example.dietplanapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final String CREATE_USER = "create table User ("
            + "id integer primary key autoincrement, "
            + "username text, "
            + "password text, "
            + "phone text, "
            + "email text, "
            + "age integer, "
            + "tall integer, "
            + "usertype text, "
            + "registerDate date)";

    public static final String CREATE_ADMIN = "create table Admin ("
            + "id integer primary key autoincrement, "
            + "adminName text, "
            + "email text, "
            + "phone text)";

    public static final String CREATE_COACH = "create table Coach ("
            + "id integer primary key autoincrement, "
            + "coachName text, "
            + "email text, "
            + "phone text, "
            + "address text)";

    public static final String CREATE_MEMBER = "create table Member ("
            + "id integer primary key autoincrement, "
            + "memberName text, "
            + "email text, "
            + "phone text, "
            + "currentWeight text, "
            + "currentBodyfat text, "
            + "workoutRecord text)";

    public static final String CREATE_WORKOUT_PLAN = "create table WorkoutPlan ("
            + "planID integer primary key autoincrement, "
            + "planHistory text, "
            + "planCalorie text, "
            + "planStartDate date, "
            + "planLength text, "
            + "currentWeight text, "
            + "targetWeight text, "
            + "coachName text)";

    public static final String CREATE_EXCERCISE_GUIDANCE = "create table ExcerciseGuidance ("
            + "guidanceID integer primary key autoincrement, "
            + "guidanceContent text, "
            + "guidanceProgramme text, "
            + "coachName text, "
            + "guidanceGoal text)";

    public static final String CREATE_SCHEDULE = "create table Schedule ("
            + "scheduleID integer primary key autoincrement, "
            + "teachingDate date, "
            + "teachingContent text, "
            + "teachingPlace text, "
            + "groupLessonContent text, "
            + "privateLessonContent text, "
            + "attendanceRecord text)";

    public static final String CREATE_BOOKING = "create table Booking ("
            + "bookingID integer primary key autoincrement, "
            + "coachName text, "
            + "memberName text, "
            + "bookingDate date)";

    public static final String CREATE_SERVICE_ITEM = "create table ServiceItem ("
            + "itemID integer primary key autoincrement, "
            + "itemTitle text, "
            + "itemPrice text, "
            + "serviceCategoryID text, "
            + "subCategory text)";

    public static final String CREATE_MEMBERSHIP_PAYMENT_RECORD = "create table MembershipPayRecord ("
            + "recordID integer primary key autoincrement, "
            + "recordDate date, "
            + "recordLength text, "
            + "recordPaymentContent text, "
            + "recordRenew text)";

    public static final String CREATE_EQUIPMENT_UPDATES = "create table EquipmentUpdates ("
            + "equipmentID integer primary key autoincrement, "
            + "updateDate date, "
            + "updateContent text, "
            + "recordUasgeFrequency text)";

    public static final String CREATE_COACH_REVIEWS = "create table CoachReviews ("
            + "coachReviewsID integer primary key autoincrement, "
            + "coachName text, "
            + "reviewId text, "
            + "reviewContent text)";

    public static final String CREATE_EXCERCISE_TIPS = "create table ExerciseTips ("
            + "tipsID integer primary key autoincrement, "
            + "tipsContent text, "
            + "tipsCoachname text)";

    private Context mContext;


    public MySQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER);
        sqLiteDatabase.execSQL(CREATE_ADMIN);
        sqLiteDatabase.execSQL(CREATE_COACH);
        sqLiteDatabase.execSQL(CREATE_MEMBER);
        sqLiteDatabase.execSQL(CREATE_WORKOUT_PLAN);
        sqLiteDatabase.execSQL(CREATE_EXCERCISE_GUIDANCE);
        sqLiteDatabase.execSQL(CREATE_SCHEDULE);
        sqLiteDatabase.execSQL(CREATE_BOOKING);
        sqLiteDatabase.execSQL(CREATE_SERVICE_ITEM);
        sqLiteDatabase.execSQL(CREATE_MEMBERSHIP_PAYMENT_RECORD);
        sqLiteDatabase.execSQL(CREATE_EQUIPMENT_UPDATES);
        sqLiteDatabase.execSQL(CREATE_COACH_REVIEWS);
        sqLiteDatabase.execSQL(CREATE_EXCERCISE_TIPS);
        Toast.makeText(mContext,"Create table succeed！",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
