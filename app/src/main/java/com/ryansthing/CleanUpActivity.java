package com.ryansthing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioGroup;

import java.io.File;
import java.util.Calendar;

public class CleanUpActivity extends AppCompatActivity {

    public static final String CURRENT_LOG = "clearup.curerntlog";

    private LogAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clean_up);
        ListView lv = (ListView)findViewById(R.id.lvLogs);
        adapter = new LogAdapter(this, getIntent().getStringExtra(CURRENT_LOG));
        lv.setAdapter(adapter);
    }

    public void doIt(View view) {
        switch (view.getId()) {
            case R.id.btnDelete:
                adapter.delete((File)view.getTag());
                break;
            case R.id.btnCleanUp:
                RadioGroup rg = (RadioGroup)findViewById(R.id.rgTime);
                Calendar calendar = Calendar.getInstance();
                long diff = calendar.getTimeInMillis();
                switch(rg.getCheckedRadioButtonId()) {
                    case R.id.rbYear:
                        diff = 1000*60*60*24*365;
                        break;
                    case R.id.rbMonth:
                        diff = 1000*60*60*24*30;
                        break;
                    case R.id.rbWeek:
                        diff = 1000*60*60*24*7;
                        break;
                    case R.id.rbAll:
                        diff = -1;
                        break;
                }
                adapter.cleanUp(calendar.getTimeInMillis(), diff);
                break;
        }
    }

}
