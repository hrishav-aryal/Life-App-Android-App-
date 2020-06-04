package com.example.life;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class info extends AppCompatActivity {

    private TextView birth, death;
    private Spinner year, month, day, age;
    private Button done;

    private DatabaseHelper dbhelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        birth = (TextView) findViewById(R.id.birthDate);
        death = (TextView) findViewById(R.id.deathAge);

        year = (Spinner) findViewById(R.id.year);
        month = (Spinner) findViewById(R.id.month);
        day = (Spinner) findViewById(R.id.day);
        age = (Spinner) findViewById(R.id.expectedDeathAge);

        done = (Button) findViewById(R.id.done);

        dbhelp = new DatabaseHelper(this);

        setSpinners();

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar currDate = Calendar.getInstance();
                int y = Integer.parseInt(year.getSelectedItem().toString());
                int m = Integer.parseInt(month.getSelectedItem().toString());
                int d = Integer.parseInt(day.getSelectedItem().toString());
                int a = Integer.parseInt(age.getSelectedItem().toString());

                int cmonth = currDate.get(Calendar.MONTH) + 1;
                int cday = currDate.get(Calendar.DAY_OF_MONTH);
                if(y == 2020 && m > cmonth){
                    toastMessage("Inavlid Date");
                }else if(y == 2020 && m == cmonth && d > cday){
                    toastMessage("Inavlid Date");
                }else {
                    addData(y,m,d,a);
                    Intent intent = new Intent(info.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    public void setSpinners(){
        Calendar currDate = Calendar.getInstance();

        int yy = currDate.get(Calendar.YEAR);
        int mm = currDate.get(Calendar.MONTH) + 1;
        int dd = currDate.get(Calendar.DAY_OF_MONTH);

        List<Integer> years = new ArrayList<Integer>();
        for(int i=1900; i<=yy; i++){
            years.add(i);
        }

        List<Integer> months = new ArrayList<Integer>();
        for(int i=1; i<=12; i++){
            months.add(i);
        }

        List<Integer> days = new ArrayList<Integer>();
        for(int i=1; i<=31; i++){
            days.add(i);
        }

        List<Integer> ages = new ArrayList<Integer>();
        for(int i=1; i<=120; i++){
            ages.add(i);
        }

        ArrayAdapter<Integer> adapter_year = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, years);
        adapter_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(adapter_year);
        year.setSelection(80);

        ArrayAdapter<Integer> adapter_month = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, months);
        adapter_month.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month.setAdapter(adapter_month);


        ArrayAdapter<Integer> adapter_day = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, days);
        adapter_day.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day.setAdapter(adapter_day);

        ArrayAdapter<Integer> adapter_age = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, ages);
        adapter_age.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        age.setAdapter(adapter_age);
        age.setSelection(69);
    }

    public void addData(int y, int m, int d, int a){
        boolean insertData = dbhelp.addData(y,m,d,a);

        if(insertData) toastMessage("Welcome to LIFE.");
        else toastMessage("Something went wrong");
    }

    private void toastMessage(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
