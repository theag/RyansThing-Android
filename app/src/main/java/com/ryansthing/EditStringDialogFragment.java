package com.ryansthing;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by nbp184 on 2017/10/13.
 */

public class EditStringDialogFragment extends DialogFragment {

    public static final String VALUE_KEY = "editstringfragment.value";
    public static final String TITLE_KEY = "editstringfragment.title";

    private EditStringDialogListener listener;
    private EditText et;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getActivity() instanceof EditStringDialogListener) {
            listener = (EditStringDialogListener)getActivity();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_edit_string, null);
        et = (EditText)view.findViewById(R.id.txtText);
        et.setText(getArguments().getString(VALUE_KEY, ""));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getArguments().getString(TITLE_KEY))
                .setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doPositiveClick();
                    }
                })
                .setNegativeButton("Cancel", null);
        return builder.create();
    }

    public void doPositiveClick() {
        if(listener != null) {
            listener.onEdit(getTag(), et.getText().toString());
        }
    }

    public interface EditStringDialogListener {
        void onEdit(String tag, String value);
    }

}
