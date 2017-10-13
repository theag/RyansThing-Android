package com.ryansthing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.ryansthing.data.Dice;
import com.ryansthing.data.TempEntry;

import java.util.ArrayList;

public class TableEntryActivity extends AppCompatActivity implements RollonAdapter.AdapterInteractionListener, EditStringDialogFragment.EditStringDialogListener {

    public static final String TABLE_NAME = "com.ryansthing.tableentryactivity.tablename";
    public static final String TEMP_ENTRY = "com.ryansthing.tableentryactivity.tempentry";
    public static final String TEMP_ENTRY_POSITION = "com.ryansthing.tableentryactivity.tempentry.position";

    private RollonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_entry);
        setTitle(getTitle() +" - " +getIntent().getStringExtra(TABLE_NAME));
        ArrayList<String> entries = new ArrayList<>();
        if(getIntent().hasExtra(TEMP_ENTRY)) {
            TempEntry entry = getIntent().getParcelableExtra(TEMP_ENTRY);
            CheckBox chk;
            EditText et;
            if(entry.text != null) {
                chk = (CheckBox)findViewById(R.id.chkText);
                chk.setChecked(true);
                et = (EditText)findViewById(R.id.txtText);
                et.setText(entry.text);
            }
            if(entry.die != null) {
                chk = (CheckBox)findViewById(R.id.chkDice);
                chk.setChecked(true);
                et = (EditText)findViewById(R.id.txtAmount);
                et.setText(""+entry.die.getAmount());
                et = (EditText)findViewById(R.id.txtSides);
                et.setText(""+entry.die.getSides());
                et = (EditText)findViewById(R.id.txtModifier);
                et.setText(""+entry.die.getModifier());
            }
            if(entry.unit != null) {
                chk = (CheckBox)findViewById(R.id.chkUnit);
                chk.setChecked(true);
                et = (EditText)findViewById(R.id.txtUnit);
                et.setText(entry.unit);
            }
            if(entry.appears > 1) {
                chk = (CheckBox)findViewById(R.id.chkAppears);
                chk.setChecked(true);
                et = (EditText)findViewById(R.id.txtAppears);
                et.setText(""+entry.appears);
            }
            if(entry.rollon != null) {
                entries.addAll(entry.rollon);
            }
        }
        adapter = new RollonAdapter(this, entries);
        adapter.setListener(this);
        ListView lv = (ListView)findViewById(R.id.lvRollon);
        lv.setAdapter(adapter);
    }

    public void buttonClick(View view) {
        switch(view.getId()) {
            case R.id.btnAddRollon:
                EditStringDialogFragment frag = new EditStringDialogFragment();
                Bundle args = new Bundle();
                args.putString(EditStringDialogFragment.TITLE_KEY, "Add Table to Roll On");
                frag.setArguments(args);
                frag.show(getSupportFragmentManager(), "-1");
                break;
            case R.id.btnSave:
                Intent intent = new Intent("com.ryansthings.RESULT_ACTION");
                TempEntry t = new TempEntry();
                TextView tv;
                CheckBox chk = (CheckBox)findViewById(R.id.chkText);
                if(chk.isChecked()) {
                    tv = (TextView)findViewById(R.id.txtText);
                    t.text = tv.getText().toString();
                }
                chk = (CheckBox)findViewById(R.id.chkDice);
                if(chk.isChecked()) {
                    int a, s, m;
                    tv = (TextView)findViewById(R.id.txtAmount);
                    a = Integer.parseInt(tv.getText().toString());
                    tv = (TextView)findViewById(R.id.txtSides);
                    s = Integer.parseInt(tv.getText().toString());
                    tv = (TextView)findViewById(R.id.txtModifier);
                    m = Integer.parseInt(tv.getText().toString());
                    t.die = new Dice(a, s, m);
                }
                chk = (CheckBox)findViewById(R.id.chkUnit);
                if(chk.isChecked()) {
                    tv = (TextView)findViewById(R.id.txtUnit);
                    t.unit = tv.getText().toString();
                }
                chk = (CheckBox)findViewById(R.id.chkAppears);
                if(chk.isChecked()) {
                    tv = (TextView)findViewById(R.id.txtAppears);
                    t.appears = Integer.parseInt(tv.getText().toString());
                }
                if(adapter.entries.isEmpty()) {
                    t.rollon = null;
                } else {
                    if(t.rollon == null) {
                        t.rollon = new ArrayList<>();
                    }
                    t.rollon.addAll(adapter.entries);
                }
                intent.putExtra(TEMP_ENTRY, t);
                if(getIntent().hasExtra(TEMP_ENTRY)) {
                    intent.putExtra(TEMP_ENTRY_POSITION, getIntent().getIntExtra(TEMP_ENTRY_POSITION, -1));
                }
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.btnCancel:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
    }

    @Override
    public void buttonPressed(int position, int id) {
        switch(id) {
            case R.id.loItemListEditDelete:
                EditStringDialogFragment frag = new EditStringDialogFragment();
                Bundle args = new Bundle();
                args.putString(EditStringDialogFragment.TITLE_KEY, "Edit Table to Roll On");
                args.putString(EditStringDialogFragment.VALUE_KEY, adapter.getItem(position));
                frag.setArguments(args);
                frag.show(getSupportFragmentManager(), ""+position);
                break;
            case R.id.btnDelete:
                adapter.entries.remove(position);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onEdit(String tag, String value) {
        if(tag.compareTo("-1") == 0) {
            adapter.entries.add(value);
        } else {
            adapter.entries.set(Integer.parseInt(tag), value);
        }
        adapter.notifyDataSetChanged();
    }
}
