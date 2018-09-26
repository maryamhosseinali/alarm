package com.example.maryam.alarmtori;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    TimePicker timePicker;

    RadioButton optAlarm, optNotification;
    Button btnStart;
    TextView info;

    Uri uriAlarm, uriNotification, uriRingtone;

    final static int RQS_RINGTONEPICKER = 1;

    final static int RQS_1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timePicker = (TimePicker)findViewById(R.id.timepicker);

        optAlarm = (RadioButton)findViewById(R.id.optAlarm);
        optNotification = (RadioButton)findViewById(R.id.optNotification);
        btnStart = (Button)findViewById(R.id.start);
        info = (TextView)findViewById(R.id.info);

        uriAlarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        uriNotification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        uriRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        optAlarm.setText("آلارم");
        optNotification.setText("ناتیفیکشن");

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (optAlarm.isChecked()) {
                    setAlarm(uriAlarm);
                } else if (optNotification.isChecked()) {
                    setAlarm(uriNotification);
                }

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RQS_RINGTONEPICKER && resultCode == RESULT_OK){
            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            setAlarm(uri);
        }
    }

    private void setAlarm(Uri passuri){

        Calendar cal = Calendar.getInstance();
        cal.set(timePicker.getCurrentHour(),
                timePicker.getCurrentMinute(),
                00);

        String passString = passuri.toString();
        info.setText("\n\n\n"
                + "ساعت زنگ دار در ساعت  " + cal.getTime() + "\n"+
                "تنظیم شد"
                );

        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        intent.putExtra("KEY_TONE_URL", passString);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getBaseContext(),
                RQS_1,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }
}
