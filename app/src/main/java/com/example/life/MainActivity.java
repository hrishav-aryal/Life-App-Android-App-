package com.example.life;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private DatabaseHelper dbhelper;
    private TextView countdown;

    private CountDownTimer countDownTimer;
    private long timeLeft;

    private int ryear, rmonth, rday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbhelper =  new DatabaseHelper(this);

        countdown = (TextView) findViewById(R.id.lifeTick);

        displayLifeTime();
    }

    private void displayLifeTime() {
        Cursor data = dbhelper.getData();

        int year = 0, month = 0, day = 0, age = 0;
        while(data.moveToNext()){
            year = data.getInt(1);
            month = data.getInt(2);
            day = data.getInt(3);
            age = data.getInt(4);
        }

        countdown.setText(year + " " + month);

        Calendar currDate = Calendar.getInstance();

        int chour = currDate.get(Calendar.HOUR_OF_DAY);
        int cmin = currDate.get(Calendar.MINUTE);
        int csec = currDate.get(Calendar.SECOND);

        setRemainingTime(year, month, day, age);

        int rhour, rmin, rsec;
        rhour = 24 - chour;
        rmin = 60 - cmin;
        rsec = 60 - csec;

        timeLeft = rhour * 60 * 60 * 1000 + rmin * 60 * 1000 + rsec * 1000;
        startTime();
    }

    private void setRemainingTime(int year, int month, int day, int dage) {

        Calendar currDate = Calendar.getInstance();

        int cyear = currDate.get(Calendar.YEAR);
        int cmonth = currDate.get(Calendar.MONTH) + 1;
        int cday = currDate.get(Calendar.DAY_OF_MONTH);

            //validate year - no need
            //calculate remaining year
            if(month > cmonth){
                ryear = dage - (cyear - year);
                if(day >= cday){
                    rmonth = month - cmonth;
                    rday = day - cday;
                } else {
                    rmonth = month - cmonth - 1;
                    rday = 30 - (cday - day);
                }
            }
            else if(month == cmonth){
                if(day >= cday){
                    ryear = dage - (cyear - year);
                    rmonth = month - cmonth;
                    rday = day - cday;
                }else {
                    ryear = dage - (cyear - year + 1);
                    rmonth = 11;
                    rday = 30 - (cday - day);
                }
            }else{
                ryear = dage - (cyear - year + 1);
                if(cday > day){
                    rmonth = 12 - (cmonth - month + 1);
                    rday = 30 - (cday - day);
                } else if(cday == day){
                    rmonth = 12 - (cmonth - month);
                    rday = cday - day;
                }else{
                    rmonth = 12 - (cmonth - month);
                    rday = day - cday;
                }

            }


    }

    private void startTime() {

        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long l) {
                timeLeft = l;
                updateTime();

                if(timeLeft == 0) displayLifeTime();
            }

            @Override
            public void onFinish() {
            }
        }.start();

    }

    public  void updateTime(){
        int hour = (int) timeLeft / 3600000;
        int minute = (int) timeLeft % 3600000 / 60000;
        int second = (int) timeLeft % 60000 / 1000;

        String display = "";

        if(ryear < 0){
            display = "Already Dead!!";
        }else {
            display = "" + ryear  + " Years" + "\n" +
                    rmonth +  " Months" + "\n" +
                    rday + " Days" + "\n";

            if(hour<10) display += "0";
            display += hour + " Hours" + "\n";

            if(minute<10) display += "0";
            display += minute + " Minutes" + "\n";
            //display += ":";

            if(second < 10) display += "0";
            display += second + " Seconds" + "\n";
        }
        countdown.setText(display);
    }

    private void toastMessage(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
