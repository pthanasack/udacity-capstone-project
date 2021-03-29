package com.google.example.rpgnotes.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.google.example.rpgnotes.R;
import com.google.example.rpgnotes.data.RpgNote;

public class RpgNoteAdapter extends ListAdapter<RpgNote, RpgNoteViewHolder> {

    public RpgNoteAdapter(@NonNull DiffUtil.ItemCallback<RpgNote> diffCallback) {
        super(diffCallback);
    }

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



}
