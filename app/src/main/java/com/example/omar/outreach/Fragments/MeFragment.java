package com.example.omar.outreach.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.omar.outreach.Activities.MainActivity;
import com.example.omar.outreach.Activities.PeriodicalFormActivity_1;
import com.example.omar.outreach.Adapters.EntriesAdapter;
import com.example.omar.outreach.App;
import com.example.omar.outreach.Helping.FormEntries.PassingString;
import com.example.omar.outreach.Interfaces.BottomNavObserver;
import com.example.omar.outreach.Managers.EntriesManager;
import com.example.omar.outreach.Managers.RewardManager;
import com.example.omar.outreach.Models.Entry;
import com.example.omar.outreach.Provider.EntriesDataSource;
import com.example.omar.outreach.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeFragment extends Fragment implements BottomNavObserver {

    private EntriesDataSource ds;
    private OnFragmentInteractionListener mListener;
    private EntriesAdapter entriesAdapter;

    // views
    private ListView listView;
    private TextView rewardTV;
    private TextView emojieView;
    private LinearLayout emptyView;

    public MeFragment() {
        // Required empty public constructor
    }

    public static MeFragment newInstance() {
        MeFragment fragment = new MeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_me, container, false);
        view.setBackgroundColor(getResources().getColor(R.color.colorLightGrey));

        //init ui
        rewardTV = view.findViewById(R.id.rewardTV);
        listView = view.findViewById(R.id.listView);
        emojieView = view.findViewById(R.id.emojieView);
        emptyView = view.findViewById(R.id.emptyScreenView);

        // setup view
        setupListView(getContext());
        setupRewardTV();

        //enter animations
        runEnterAnimations();

        return view;

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            Log.d("MeFragment","attach");

        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    private void runEnterAnimations() {

//        ViewPropertyAnimator anim = addEntryButton.animate().translationY(100).setDuration(0).translationY(0).setDuration(500);

    }

    @Override
    public void bottomNavClicked(int id) {
        Log.d("Fragment","in Me Fragment" + "id: " + id );
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void setupRewardTV() {

        ds = new EntriesDataSource(getActivity());
        Double totalReward = RewardManager.calculateReward(ds.getNumOfItems());
        rewardTV.setText("$"+totalReward);

    }

    private void setupListView(final Context context) {

        ds = new EntriesDataSource(context);

        if(App.entriesList == null){
            App.entriesList = new ArrayList<>();
        }

        // setup list view
        final List<Entry> entriesList = ds.getAllItemsOrderedByDate(true);

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                entriesAdapter = new EntriesAdapter(context, entriesList);
                listView.setAdapter(entriesAdapter);
            }
        });


        if(entriesList.size() == 0){
            emptyView.setVisibility(View.VISIBLE);
            emojieView.setVisibility(View.VISIBLE);
        }else{
            emptyView.setVisibility(View.INVISIBLE);
            emojieView.setVisibility(View.INVISIBLE);
        }
        
    }
}
