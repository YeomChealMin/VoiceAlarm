package com.galaxy.voicealarm;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.naver.naverspeech.kfgd_naver.IManagerCommand;
import com.naver.naverspeech.kfgd_naver.NaverSpeechManager;
import com.stacktips.view.CalendarListener;
import com.stacktips.view.CustomCalendarView;
import com.stacktips.view.DayDecorator;
import com.stacktips.view.DayView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //Member of Data
    List<StringBuilder> memoList = new ArrayList<>();
    Calendar currentCalendar;

    //Member of Widgets
    CustomCalendarView calendarView;
    TextView memoText;
    ImageButton memoBtn;
    Memo selectedMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize CustomCalendarView from layout
        calendarView = (CustomCalendarView) findViewById(R.id.calendar_view);
        InitializeCalender();

        //Memo
        memoText = (TextView) findViewById(R.id.memo_text);
        memoBtn = (ImageButton)findViewById(R.id.memo_btn);
        memoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        List decorators = new ArrayList();
        decorators.add(new ColorDecorator());
        //calendarView.setDecorators();
    }

    public void OnResetFocus(View view){
        calendarView.refreshCalendar(currentCalendar);
        selectedMemo = null;
        memoText.setText("");
    }

    public void RunAlarmList(View view){
        Intent intent=new Intent(MainActivity.this, AlarmList.class);
        startActivity(intent);
    }

    private void InitializeCalender() {

        //Initialize calendar with date
        currentCalendar = Calendar.getInstance(Locale.getDefault());

        //Show monday as first date of week
        calendarView.setFirstDayOfWeek(Calendar.MONDAY);

        //Show/hide overflow days of a month
        calendarView.setShowOverflowDate(false);

        //call refreshCalendar to update calendar the view
        calendarView.refreshCalendar(currentCalendar);

        //Handling custom calendar events
        calendarView.setCalendarListener(new CalendarListener() {
            @Override
            public void onDateSelected(Date date) {
                //SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                //Toast.makeText(MainActivity.this, df.format(date), Toast.LENGTH_SHORT).show();
                selectedMemo = VoiceAlarmApplication.getMemo(df.format(date));
                if(null != selectedMemo)
                    memoText.setText(selectedMemo.getDataTimeToString());
                else {
                    memoText.setText("일정없음");
                    selectedMemo = new Memo(df.format(date));
                }

            }

            @Override
            public void onMonthChanged(Date date) {
                SimpleDateFormat df = new SimpleDateFormat("MM-yyyy");
                Toast.makeText(MainActivity.this, df.format(date), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class ColorDecorator implements DayDecorator{

        @Override
        public void decorate(DayView cell) {

        }
    }
}
