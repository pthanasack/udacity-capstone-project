package com.google.example.rpgnotes.data;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.Date;
import java.util.List;

public class RpgNoteViewModel extends AndroidViewModel {
    private DataRepository mRepository;
    public final LiveData<List<RpgNote>> mAllRpgNote;

    public RpgNoteViewModel(Application application) {
        super(application);
        this.mRepository = new DataRepository(application);
        this.mAllRpgNote = mRepository.getAllRpgNote();
    }

    public RpgNoteViewModel(Application application, String type) {
        super(application);
        this.mRepository = new DataRepository(application);
        this.mAllRpgNote = mRepository.getAllNoteByType(type);
    }


    public LiveData<List<RpgNote>> getmAllRpgNote() {
        return mAllRpgNote;
    }


    public void insert(RpgNote rpgNote){
        mRepository.insert(rpgNote);
    }

    public void delete(RpgNote rpgNote){
        mRepository.delete(rpgNote);
    }

    public void deleteById(int idnote){
        mRepository.deleteById(idnote);
    }

    public void updateById(int idnote, String title, String content, String type, Date date){
        mRepository.updateById(idnote, title, content, type, date);
    }

}
