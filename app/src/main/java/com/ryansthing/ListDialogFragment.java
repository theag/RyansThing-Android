package com.ryansthing;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by nbp184 on 2017/09/28.
 */
public class ListDialogFragment extends DialogFragment {

    public static final String VALUE_KEY = "listfragment.values";

    private ListDialogListener listener;

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
        builder.setTitle("Add")
                .setItems(getArguments().getStringArray(VALUE_KEY), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onClick(getTag(), getArguments().getStringArray(VALUE_KEY)[i]);
                    }
                });
        return builder.create();
    }

    public interface ListDialogListener {
        public void onClick(String tag, String value);
    }

}
