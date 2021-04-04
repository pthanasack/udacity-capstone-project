package com.google.example.rpgnotes;

import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.example.rpgnotes.data.RpgNoteViewModel;
import com.google.example.rpgnotes.ui.RpgNoteAdapter;
import com.google.example.rpgnotes.ui.search.SearchViewModel;

public class SearchableActivity extends AppCompatActivity implements RpgNoteAdapter.RpgNoteAdapterClickHandler {
    private RpgNoteViewModel model;

    private SearchViewModel searchViewModel;

    final private String INTENT_NOTE_ID = "@string/INTENT_NOTE_ID";
    final private String INTENT_NOTE_TITLE = "@string/INTENT_NOTE_TITLE";
    final private String INTENT_NOTE_CONTENT = "@string/INTENT_NOTE_CONTENT";
    final private String INTENT_NOTE_TYPE = "@string/INTENT_NOTE_TYPE";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search);
        handleIntent(getIntent());
        searchViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);
        final TextView textView = findViewById(R.id.text_slideshow);
        searchViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }


    private void handleIntent(Intent intent) {
        // Get the intent, verify the action and get the query
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
            //RpgNoteData with Shared ViewModel owned by the Activity
            model = new ViewModelProvider(this).get(RpgNoteViewModel.class);
            //link the recyclerview to the adapter
            RecyclerView recyclerView = this.findViewById(R.id.search_recyclerview);
            final RpgNoteAdapter adapter = new RpgNoteAdapter(new RpgNoteAdapter.RpgNoteDiff(), this);
            model.searchNotesLike(query).observe(this, adapter::submitList);
            recyclerView.setAdapter(adapter);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            //set divider in recyclerview
            DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                    ((LinearLayoutManager) layoutManager).getOrientation());
            recyclerView.addItemDecoration(mDividerItemDecoration);
        }
    }

    @Override
    public void onClick(int noteId, String noteTitle, String noteType, String noteContent) {
        //open detail activity
        //Intent parameters: context, destination class
        Context context = this;
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
