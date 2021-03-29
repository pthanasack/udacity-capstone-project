package com.google.example.rpgnotes.data;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

public class DataRepository {
    private RpgNoteDao mRpgNoteDao;
    private LiveData<List<RpgNote>> mAllRpgNote;
    private static volatile DataRepository sInstance = null;

    DataRepository(Context context) {

        RpgNoteDatabase db = RpgNoteDatabase.getInstance(context);
        mRpgNoteDao = db.mRpgNoteDao();
        mAllRpgNote = mRpgNoteDao.getAll();

    }

    LiveData<List<RpgNote>> getAllRpgNote(){
        return mAllRpgNote;
    }

    void insert(RpgNote rpgNote){
        RpgNoteDatabase.databaseWriteExecutor.execute(() -> {
            mRpgNoteDao.insertRpgNote(rpgNote);
        });


    }

}
