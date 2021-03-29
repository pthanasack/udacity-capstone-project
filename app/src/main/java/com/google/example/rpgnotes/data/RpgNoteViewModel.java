package com.google.example.rpgnotes.data;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class RpgNoteViewModel extends AndroidViewModel {
    private DataRepository mRepository;
    public final LiveData<List<RpgNote>> mAllRpgNote;

    public RpgNoteViewModel(Application application) {
        super(application);
        this.mRepository = new DataRepository(application);
        this.mAllRpgNote = mRepository.getAllRpgNote();
    }

    public LiveData<List<RpgNote>> getmAllRpgNote() {
        return mAllRpgNote;
    }

    public void insert(RpgNote rpgNote){
        mRepository.insert(rpgNote);
    }

}
