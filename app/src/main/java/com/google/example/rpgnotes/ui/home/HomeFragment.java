package com.google.example.rpgnotes.ui.home;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.example.rpgnotes.DetailActivity;
import com.google.example.rpgnotes.R;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.example.rpgnotes.analytics.AnalyticsApplication;
import com.google.example.rpgnotes.data.RpgNote;
import com.google.example.rpgnotes.data.RpgNoteViewModel;
import com.google.example.rpgnotes.ui.RpgNoteAdapter;

import java.util.List;

public class HomeFragment extends Fragment implements RpgNoteAdapter.RpgNoteAdapterClickHandler {
    private AdView mAdView;
    private HomeViewModel homeViewModel;
    private RpgNoteViewModel model;
    private Tracker mTracker;

    private String TAG_HOME_FRAGMENT_ANALYTICS = "@string/TAG_HOME_FRAGMENT_ANALYTICS" ;

    @Override
    public void onResume() {
        super.onResume();
        String MSG_HOME_ANALYTICS = "@string/MSG_HOME_ANALYTICS";
        Log.i(TAG_HOME_FRAGMENT_ANALYTICS, MSG_HOME_ANALYTICS);
        String IMAGE_HOME_ANALYTICS = "@string/IMAGE_HOME_ANALYTICS";
        mTracker.setScreenName(IMAGE_HOME_ANALYTICS);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        String EVENT_VIEW_ANALYTICS = "@string/EVENT_VIEW_ANALYTICS";
        String EVENT_RESUME_ANALYTICS = "@string/EVENT_RESUME_ANALYTICS";
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(EVENT_VIEW_ANALYTICS)
                .setAction(EVENT_RESUME_ANALYTICS)
                .build());
    }

    final private String INTENT_NOTE_ID = "@string/INTENT_NOTE_ID";
    final private String INTENT_NOTE_TITLE = "@string/INTENT_NOTE_TITLE";
    final private String INTENT_NOTE_CONTENT = "@string/INTENT_NOTE_CONTENT";
    final private String INTENT_NOTE_TYPE = "@string/INTENT_NOTE_TYPE";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        //RpgNoteData with Shared ViewModel owned by the Activity
        model = new ViewModelProvider(requireActivity()).get(RpgNoteViewModel.class);
        //link the recyclerview to the adapter
        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_home);
        final RpgNoteAdapter adapter = new RpgNoteAdapter(new RpgNoteAdapter.RpgNoteDiff(), this);
        model.mAllRpgNote.observe(getViewLifecycleOwner(), adapter::submitList);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        //set divider in recyclerview
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                ((LinearLayoutManager) layoutManager).getOrientation());
        recyclerView.addItemDecoration(mDividerItemDecoration);


        //AD
        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = root.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        FloatingActionButton fab = root.findViewById(R.id.fab_add_home);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open detail activity
                //Intent parameters: context, destination class
                Context context = getContext();
                Class destinationClass = DetailActivity.class;
                //new intent with empty note values
                Intent startDetailedActivityIntent = new Intent(context, destinationClass);
                startDetailedActivityIntent.putExtra(INTENT_NOTE_ID,"0");
                //start new activity with Intent
                startActivity(startDetailedActivityIntent);
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        //analytics
        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();

        return root;
    }

    @Override
    public void onClick(int noteId, String noteTitle, String noteType, String noteContent) {
        //open detail activity
        //Intent parameters: context, destination class
        Context context = getContext();
        Class destinationClass = DetailActivity.class;
        //new intent with note values
        Intent startDetailedActivityIntent = new Intent(context, destinationClass);
        startDetailedActivityIntent.putExtra(INTENT_NOTE_ID, String.valueOf(noteId));
        startDetailedActivityIntent.putExtra(INTENT_NOTE_TITLE,noteTitle);
        startDetailedActivityIntent.putExtra(INTENT_NOTE_CONTENT,noteContent);
        startDetailedActivityIntent.putExtra(INTENT_NOTE_TYPE,noteType);
        //start new activity with Intent
        startActivity(startDetailedActivityIntent);
        //Toast.makeText(context, String.valueOf(noteId), Toast.LENGTH_SHORT).show();
    }
}