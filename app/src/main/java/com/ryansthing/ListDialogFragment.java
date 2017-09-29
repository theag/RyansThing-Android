package com.ryansthing;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by nbp184 on 2017/09/28.
 */
public class ListDialogFragment extends DialogFragment {

    public static final String VALUE_KEY = "listfragment.values";
    public static final String TITLE_KEY = "listFragment.title";

    public Object[] values;
    private ListDialogListener listener;
    private View tablesLayout;
    private SearchAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getActivity() instanceof ListDialogListener) {
            listener = (ListDialogListener)getActivity();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getArguments().getString(TITLE_KEY));
        if(values == null) {
            builder.setItems(getArguments().getStringArray(VALUE_KEY), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    listener.onClick(getTag(), getArguments().getStringArray(VALUE_KEY)[i]);
                }
            });
        } else {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            tablesLayout = inflater.inflate(R.layout.dialog_tables, null);
            tablesLayout.findViewById(R.id.btnSearch).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doSearch();
                }
            });
            ListView lv = tablesLayout.findViewById(R.id.lvTables);
            adapter = new SearchAdapter(getContext(), values);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    dismiss();
                    listener.onClick(getTag(), adapter.getItem(i));
                }
            });
            builder.setView(tablesLayout);
        }
        return builder.create();
    }

    public void doSearch() {
        TextView tv = tablesLayout.findViewById(R.id.txtSearch);
        adapter.search(tv.getText().toString().trim());
    }

    public interface ListDialogListener {
        void onClick(String tag, Object value);
    }

}
