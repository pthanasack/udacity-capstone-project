package com.google.example.rpgnotes.ui.pcnotes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.example.rpgnotes.DetailActivity;
import com.google.example.rpgnotes.R;
import com.google.example.rpgnotes.data.RpgNote;
import com.google.example.rpgnotes.data.RpgNoteViewModel;
import com.google.example.rpgnotes.ui.RpgNoteAdapter;
import com.google.example.rpgnotes.ui.add.AddViewModel;
import com.google.example.rpgnotes.ui.search.SearchViewModel;

import java.util.Calendar;

public class PcNotesFragment extends Fragment implements RpgNoteAdapter.RpgNoteAdapterClickHandler {

    private PcNotesViewModel model;
    final private String INTENT_NOTE_ID = "@string/INTENT_NOTE_ID";
    final private String INTENT_NOTE_TITLE = "@string/INTENT_NOTE_TITLE";
    final private String INTENT_NOTE_CONTENT = "@string/INTENT_NOTE_CONTENT";
    final private String INTENT_NOTE_TYPE = "@string/INTENT_NOTE_TYPE";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);


        //RpgNoteData with Shared ViewModel owned by the Activity
        model = new ViewModelProvider(requireActivity()).get(PcNotesViewModel.class);
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