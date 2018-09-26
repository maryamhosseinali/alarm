package com.example.maryam.alarmtori;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by MARYAM on 1/10/2017.
 */

public class SecondActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener,
        View.OnClickListener{
    TextView secInfo;
    Button btnStop;

    Ringtone ringTone;


    SeekBar sb;
    boolean flag = false;
    Button Confirm;
    TextView seekbartest;
    RelativeLayout page_background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        secInfo = (TextView)findViewById(R.id.secinfo);
        btnStop = (Button)findViewById(R.id.stop);

        String stringUri = getIntent().getStringExtra("SEC_RINGTONE_URI");
        Uri uri = Uri.parse(stringUri);
        secInfo.setText("uri: " + uri + "\n");

        ringTone = RingtoneManager
                .getRingtone(getApplicationContext(), uri);

        secInfo.append(ringTone.getTitle(SecondActivity.this));

        ringTone.play();

        btnStop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(ringTone != null){
                    ringTone.stop();
                    ringTone = null;
                }
            }
        });


        Initialization();
        sb.setOnSeekBarChangeListener(this);
        page_background.setOnClickListener(this);

    }

    private void Initialization() {
        sb = (SeekBar) findViewById(R.id.myseek);
        seekbartest = (TextView) findViewById(R.id.slider_text);
        page_background = (RelativeLayout) findViewById(R.id.full_page_layout);
        seekbartest.setText("توقف");

    }

    @Override
    public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
        if (arg1 > 95) {
            arg0.setThumb(getResources().getDrawable(R.drawable.slider_icon));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar arg0) {
        seekbartest.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onStopTrackingTouch(SeekBar arg0) {
        Log.d("onStopTrackingTouch", "onStopTrackingTouch");
        if (arg0.getProgress() < 80) {
            arg0.setProgress(0);
            sb.setBackgroundResource(R.drawable.slider_back);
            seekbartest.setVisibility(View.VISIBLE);
            seekbartest.setText("Slide to Unlock");

        } else {
            arg0.setProgress(100);
            seekbartest.setVisibility(View.INVISIBLE);
            sb.setVisibility(View.INVISIBLE);
            if(ringTone != null){
                ringTone.stop();
                ringTone = null;
                finish();
            }

        }
    }

    @Override
    public void onClick(View v) {

        sb.setVisibility(View.VISIBLE);
        sb.setProgress(0);
        Confirm.setVisibility(View.INVISIBLE);
        sb.setBackgroundResource(R.drawable.slider_back);
        seekbartest.setVisibility(View.VISIBLE);
        seekbartest.setText("Slide to Unlock");
    }

}
