package com.ryansthing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ryansthing.data.*;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ListDialogFragment.ListDialogListener {

    private static final int REQUEST_ADD_TABLE = 1;

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
        tables = new ArrayList<>();
        try {
            for(String filename : getAssets().list("tables")) {
                ReadTable.read(getAssets().open("tables/"+filename), tables);
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch(item.getItemId()) {
            case R.id.miNew:
                ((TextView)findViewById(R.id.lblLog)).setText(log.makeNew());
                logAdapter.notifyDataSetChanged();
                return true;
            case R.id.miCleanup:
                intent = new Intent(this, CleanUpActivity.class);
                intent.putExtra(CleanUpActivity.CURRENT_LOG, log.getFilename());
                startActivity(intent);
                return true;
            case R.id.miAddTableFile:
                intent = new Intent(this, TablesActivity.class);
                startActivityForResult(intent, REQUEST_ADD_TABLE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(!log.data.isEmpty()) {
            log.save();
        }
    }

    public void rollHazard(View view) {
        String tblName;
        switch(view.getId()) {
            case R.id.btnWilderness:
                tblName = "Wilderness Hazards";
                break;
            case R.id.btnDungeon:
                tblName = "Dungeon Hazards";
                break;
            case R.id.btnHaven:
                tblName = "Haven Hazards";
                break;
            default:
                return;
        }
        it.addTurn();
        for(Table t : tables) {
            if(t.name.compareTo(tblName) == 0) {
                log.addText(it.toString() + ": " + doRoll(t));
                break;
            }
        }
        logAdapter.notifyDataSetChanged();
    }

    public void logCurrent(View view) {
        switch(view.getId()) {
            case R.id.btnCurrentTime:
                log.addText(jc.toString());
                break;
            case R.id.btnCurrentRoundTurn:
                log.addText(it.toString());
                break;
        }
        logAdapter.notifyDataSetChanged();
    }

    public void addRound(View view) {
        it.addRound();
        log.addText(it.toString());
        logAdapter.notifyDataSetChanged();
    }

    public void openDialog(View view) {
        ListDialogFragment frag = new ListDialogFragment();
        Bundle args = new Bundle();
        String tag;
        switch(view.getId()) {
            case R.id.btnAddTime:
                args.putStringArray(ListDialogFragment.VALUE_KEY, new String[]{"Hour", "Day", "Week", "Season", "Year"});
                args.putString(ListDialogFragment.TITLE_KEY, "Add");
                tag = "time";
                break;
            case R.id.btnTables:
                frag.values = tables.toArray();
                args.putString(ListDialogFragment.TITLE_KEY, "Add From Table");
                tag = "table";
                break;
            case R.id.btnLoadLog:
                args.putString(ListDialogFragment.TITLE_KEY, "Load Log");
                File[] files = getFilesDir().listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File file, String s) {
                        return s.startsWith("log-") && s.endsWith(".txt");
                    }
                });
                String[] names = new String[files.length];
                for(int i = 0; i < files.length; i++) {
                    names[i] = files[i].getName();
                }
                args.putStringArray(ListDialogFragment.VALUE_KEY, names);
                tag = "log";
                break;
            default:
                return;
        }
        frag.setArguments(args);
        frag.show(getSupportFragmentManager(), tag);
    }

    @Override
    public void onClick(String tag, Object value) {
        switch (tag) {
            case "time":
                switch(value.toString()) {
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
                break;
            case "table":
                Table tbl = (Table) value;
                log.addText(tbl.name +": " +doRoll(tbl));
                break;
            case "log":
                log.update(new File(getFilesDir(), value.toString()));
                ((TextView)findViewById(R.id.lblLog)).setText(value.toString());
                break;
        }
        logAdapter.notifyDataSetChanged();
    }

    private String doRoll(Table t) {
        String result = t.roll();
        while(result.contains("<")) {
            int i1 = result.indexOf("<");
            int i2 = result.indexOf(">");
            String tName = result.substring(i1+1, i2);
            boolean foundTable = false;
            for(Table o : tables) {
                if(o.name.compareTo(tName) == 0) {
                    result = result.substring(0, i1) +o.roll() +result.substring(i2+1);
                    foundTable = true;
                    break;
                }
            }
            if(!foundTable) {
                //todo:JOptionPane.showMessageDialog(this, "Can't find table \"" +tName +"\".", "Error", JOptionPane.ERROR_MESSAGE);
                result = result.replace("<" +tName +">", "(" +tName +")");
            }
        }
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

}
