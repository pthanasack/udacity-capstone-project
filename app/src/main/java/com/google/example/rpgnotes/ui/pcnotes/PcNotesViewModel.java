package com.google.example.rpgnotes.ui.pcnotes;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.example.rpgnotes.data.DataRepository;
import com.google.example.rpgnotes.data.RpgNote;

import java.util.Date;
import java.util.List;

public class PcNotesViewModel extends AndroidViewModel {
    private DataRepository mRepository;
    public final LiveData<List<RpgNote>> mAllRpgNote;

    public PcNotesViewModel(Application application) {
        super(application);
        this.mRepository = new DataRepository(application);
        this.mAllRpgNote = mRepository.getPcNotes();
    }



}