package com.google.example.rpgnotes.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.example.rpgnotes.R;
import com.google.example.rpgnotes.data.DataRpgNoteName;
import com.google.example.rpgnotes.data.RpgNote;

public class RpgNoteAdapter extends ListAdapter<RpgNote, RpgNoteAdapter.RpgNoteViewHolder> {

    //onClickHandler
    public RpgNoteAdapter(@NonNull DiffUtil.ItemCallback<RpgNote> diffCallback, RpgNoteAdapterClickHandler clickHandler) {
        super(diffCallback);
        mClickHandler = clickHandler;
    }

    private final RpgNoteAdapterClickHandler mClickHandler;

    public interface RpgNoteAdapterClickHandler{
        void onClick(int noteId, String noteTitle, String noteType, String noteContent);
    }

    //Adapter methods
    @NonNull
    @Override
    public RpgNoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_home_item, parent, false);
        return new RpgNoteViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RpgNoteViewHolder holder, int position) {
            RpgNote current = getItem(position);
            holder.bind(current);
    }

    public static class RpgNoteDiff extends DiffUtil.ItemCallback<RpgNote> {

        @Override
        public boolean areItemsTheSame(@NonNull RpgNote oldItem, @NonNull RpgNote newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull RpgNote oldItem, @NonNull RpgNote newItem) {
            return oldItem.getMNoteTitle().equals(newItem.getMNoteTitle());
        }
    }


    public class RpgNoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

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
            //set the onclick listener
            view.setOnClickListener(this);
        }

        void bind(RpgNote rpgNote){
            mNoteId.setText(String.valueOf(rpgNote.getMNoteId()));
            mTitle.setText(String.valueOf(rpgNote.getMNoteTitle()));
            mType.setText(String.valueOf(rpgNote.getMNoteType()));
            mContent.setText(String.valueOf(rpgNote.getMNoteContent()));
            mDate.setText(String.valueOf(DataRpgNoteName.date_format.format(rpgNote.getMNoteTimestamp())));
        }

        @Override
        public void onClick(View v) {
            String stringNoteId = (String) mNoteId.getText();
            mClickHandler.onClick(Integer.parseInt(stringNoteId), (String) mTitle.getText(), (String) mType.getText(), (String) mContent.getText());
        }
    }



}
