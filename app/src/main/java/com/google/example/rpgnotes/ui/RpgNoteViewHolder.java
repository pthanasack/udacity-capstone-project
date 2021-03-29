package com.google.example.rpgnotes.ui;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.example.rpgnotes.R;
import com.google.example.rpgnotes.data.DataRpgNoteName;
import com.google.example.rpgnotes.data.RpgNote;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RpgNoteViewHolder extends RecyclerView.ViewHolder {

    private TextView mNoteId;
    private TextView mType;
    private  TextView mTitle;
    private  TextView mContent;
    private TextView mDate;

    RpgNoteViewHolder(View view){
        super(view);
        mNoteId = view.findViewById(R.id.note_id);
        mType    = view.findViewById(R.id.note_type);
        mContent = view.findViewById(R.id.note_content);
        mDate = view.findViewById(R.id.note_date);
        mTitle = view.findViewById(R.id.note_title);
    }

    void bind(RpgNote rpgNote){
        mNoteId.setText(String.valueOf(rpgNote.getMNoteId()));
        mTitle.setText(String.valueOf(rpgNote.getMNoteTitle()));
        mType.setText(String.valueOf(rpgNote.getMNoteType()));
        mContent.setText(String.valueOf(rpgNote.getMNoteContent()));
        mDate.setText(String.valueOf(DataRpgNoteName.date_format.format(rpgNote.getMNoteTimestamp())));
    }


}
