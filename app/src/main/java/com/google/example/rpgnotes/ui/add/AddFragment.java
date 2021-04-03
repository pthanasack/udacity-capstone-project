package com.google.example.rpgnotes.ui.add;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.example.rpgnotes.R;
import com.google.example.rpgnotes.data.RpgNote;
import com.google.example.rpgnotes.data.RpgNoteViewModel;
import com.google.example.rpgnotes.ui.RpgNoteAdapter;

import org.w3c.dom.Text;

import java.util.Calendar;

public class AddFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private AddViewModel addViewModel;
    private RpgNoteViewModel model;
    private String spinnerValue;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addViewModel =
                new ViewModelProvider(this).get(AddViewModel.class);
        View root = inflater.inflate(R.layout.activity_add_change_note, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        addViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        //Spinner Data
        Spinner spinner = (Spinner) root.findViewById(R.id.add_note_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.note_type, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(spinner_adapter);
        spinner.setOnItemSelectedListener(this);

        //RpgNoteData with Shared ViewModel owned by the Activity
        //link the recyclerview to the adapter
        model = new ViewModelProvider(requireActivity()).get(RpgNoteViewModel.class);

        //Save button functionality
        final Button saveButton = root.findViewById(R.id.button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextInputEditText note_title_input = (TextInputEditText) root.findViewById(R.id.add_note_title_text);
                String note_title = note_title_input.getText().toString();
                final TextInputEditText note_content_input = (TextInputEditText) root.findViewById(R.id.add_note_content_text);
                String note_content = note_content_input.getText().toString();
                final RpgNote newRpgNote = new RpgNote(note_title, spinnerValue, note_content, Calendar.getInstance().getTime());
                //save only if it has content
                if (!(note_title.isEmpty()) && !(note_content.isEmpty())) {
                    model.insert(newRpgNote);
                    Toast.makeText(getActivity(), note_title + " " + getString(R.string.add_note_save_message), Toast.LENGTH_LONG).show();
                } else
                {
                    note_title_input.setError(getString(R.string.input_title_error_message));
                }

            }
        });


        //delete button functionality
        final Button deleteButton = root.findViewById(R.id.button_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextInputEditText note_title_input = (TextInputEditText) root.findViewById(R.id.add_note_title_text);
                String note_title = note_title_input.getText().toString();
                final TextInputEditText note_content_input = (TextInputEditText) root.findViewById(R.id.add_note_content_text);
                String note_content = note_content_input.getText().toString();
                final RpgNote newRpgNote = new RpgNote(note_title, spinnerValue, note_content, Calendar.getInstance().getTime());
                model.delete(newRpgNote);
                Toast.makeText(getActivity(), note_title+ " " + getString(R.string.add_note_delete_message), Toast.LENGTH_LONG).show();
            }
        });


        return root;
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