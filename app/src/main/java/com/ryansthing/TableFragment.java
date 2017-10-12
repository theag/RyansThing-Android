package com.ryansthing;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.ryansthing.data.TempTable;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TableFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TableFragment extends Fragment {
    private static final String ARG_TABLE = "com.ryansthing.tablefragment.table";

    private TempTable table;

    private OnFragmentInteractionListener mListener;

    public TableFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TableFragment.
     */
    public static TableFragment newInstance(TempTable table) {
        TableFragment fragment = new TableFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_TABLE, table);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            table = getArguments().getParcelable(ARG_TABLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_table, container, false);
        ((EditText)view.findViewById(R.id.txtName)).setText(table.name);
        if(table.getHasItems()) {
            //todo load items fragment
        } else {
            ((RadioButton)view.findViewById(R.id.rbTextRollon)).setChecked(true);
            //todo load text-rollon fragment
        }
        Button btn = (Button)view.findViewById(R.id.btnUpdateTab);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(v.getId());
            }
        });
        btn = (Button)view.findViewById(R.id.btnDelete);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(v.getId());
            }
        });
        return view;
    }

    public void onButtonPressed(int id) {
        if (mListener != null) {
            mListener.onFragmentInteraction(id);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(int id);
    }
}
