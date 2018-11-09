package com.example.omar.outreach.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.omar.outreach.Activities.MainActivity;
import com.example.omar.outreach.Interfaces.BottomNavObserver;
import com.example.omar.outreach.R;


public class CommunityFragment extends Fragment implements BottomNavObserver {

    private OnFragmentInteractionListener mListener;

    public CommunityFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CommunityFragment newInstance() {
        CommunityFragment fragment = new CommunityFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_community, container, false);
        view.setBackgroundColor(Color.WHITE);


        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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

    @Override
    public void bottomNavClicked(int id) {
        Log.d("Fragment","in Community Fragment" + "id: " + id );
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
