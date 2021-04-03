package com.google.example.rpgnotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.google.example.rpgnotes.data.RpgNote;
import com.google.example.rpgnotes.data.RpgNoteViewModel;
import com.google.example.rpgnotes.ui.add.AddViewModel;

import java.util.Calendar;

public class DetailActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private AddViewModel addViewModel;
    private RpgNoteViewModel model;
    private String spinnerValue;
    private int noteId = 0;

    final private String INTENT_NOTE_ID = "@string/INTENT_NOTE_ID";
    final private String INTENT_NOTE_TITLE = "@string/INTENT_NOTE_TITLE";
    final private String INTENT_NOTE_CONTENT = "@string/INTENT_NOTE_CONTENT";
    final private String INTENT_NOTE_TYPE = "@string/INTENT_NOTE_TYPE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_change_note);

        final TextInputEditText note_title_input = (TextInputEditText) findViewById(R.id.add_note_title_text);
        final TextInputEditText note_content_input = (TextInputEditText) findViewById(R.id.add_note_content_text);


        //default textview
        addViewModel =
                new ViewModelProvider(this).get(AddViewModel.class);
        final TextView textView = findViewById(R.id.text_gallery);
        addViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        //RpgNoteData with Shared ViewModel owned by the Activity
        //link the recyclerview to the adapter
        model = new ViewModelProvider(this).get(RpgNoteViewModel.class);

        //Spinner Data
        Spinner spinner = (Spinner) findViewById(R.id.add_note_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(this.getBaseContext(),
                R.array.note_type, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(spinner_adapter);
        spinner.setOnItemSelectedListener(this);

        // get the intent and note id
        Intent mDetailIntent = getIntent();
        if (mDetailIntent.hasExtra(INTENT_NOTE_ID)) {
            noteId = Integer.parseInt(mDetailIntent.getStringExtra(INTENT_NOTE_ID));
            //retrieve noteid info if any
            if (noteId > 0) {
                note_content_input.setText(mDetailIntent.getStringExtra(INTENT_NOTE_CONTENT));
                note_title_input.setText(mDetailIntent.getStringExtra(INTENT_NOTE_TITLE));
                final String note_content_type = mDetailIntent.getStringExtra(INTENT_NOTE_TYPE);
                spinner.setSelection(spinner_adapter.getPosition(note_content_type));
            }
        }

        //Save button functionality
        final Button saveButton = findViewById(R.id.button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note_title = note_title_input.getText().toString();
                String note_content = note_content_input.getText().toString();
                //if it's an existing note we need to save
                if (noteId>0){
                    model.updateById(noteId, note_title,  note_content, spinnerValue, Calendar.getInstance().getTime());
                    Toast.makeText(getApplicationContext(), note_title + getString(R.string.add_note_save_message), Toast.LENGTH_LONG).show();
                } else{
                    //otherwise it's a new note to save
                    if (!(note_title.isEmpty()) && !(note_content.isEmpty())) {
                        final RpgNote newRpgNote = new RpgNote(note_title, spinnerValue, note_content, Calendar.getInstance().getTime());
                        model.insert(newRpgNote);
                        Toast.makeText(getApplicationContext(), note_title + " "  + getString(R.string.add_note_save_message), Toast.LENGTH_LONG).show();
                    }else
                        {
                        note_title_input.setError( getString(R.string.input_title_error_message) );
                    }
                }
            }
        });


        //delete button functionality
        final Button deleteButton = findViewById(R.id.button_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note_title = note_title_input.getText().toString();
                model.deleteById(noteId);
                Toast.makeText(getApplicationContext(), note_title + " " + getString(R.string.add_note_delete_message), Toast.LENGTH_LONG).show();
            }
        });
    }


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        spinnerValue = (String )parent.getItemAtPosition(pos);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
