package com.ryansthing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ryansthing.data.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ListDialogFragment.ListDialogListener {

    private JournalCalendar jc;
    private InitiativeTracker it;
    private Log log;
    private ArrayAdapter<String> logAdapter;
    private ArrayList<Table> tables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jc = new JournalCalendar();
        it = new InitiativeTracker();
        log = new Log(this);
        TextView tv = (TextView)findViewById(R.id.lblLog);
        tv.setText(log.getLabel());
        ListView lv = (ListView)findViewById(R.id.lvLog);
        logAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, log.data);
        lv.setAdapter(logAdapter);
    }

    public void openDialog(View view) {
        ListDialogFragment frag = null;
        Bundle args = new Bundle();
        String tag = "";
        switch(view.getId()) {
            case R.id.btnAddTime:
                frag = new ListDialogFragment();
                args.putStringArray(ListDialogFragment.VALUE_KEY, new String[]{"Hour", "Day", "Week", "Season", "Year"});
                tag = "time";
                break;
        }
        frag.setArguments(args);
        frag.show(getSupportFragmentManager(), tag);
    }

    @Override
    public void onClick(String tag, String value) {
        switch (tag) {
            case "time":
                switch(value) {
                    case "Hour":
                        jc.addTime(JournalCalendar.HOUR);
                        break;
                    case "Day":
                        jc.addTime(JournalCalendar.DAY);
                        break;
                    case "Week":
                        jc.addTime(JournalCalendar.WEEK);
                        break;
                    case "Season":
                        jc.addTime(JournalCalendar.SEASON);
                        break;
                    case "Year":
                        jc.addTime(JournalCalendar.YEAR);
                        break;
                }
                log.addText(jc.toString());
                logAdapter.notifyDataSetChanged();
        }
    }
}
