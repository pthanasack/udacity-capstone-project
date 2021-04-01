package com.google.example.rpgnotes.data;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

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

    LiveData<List<RpgNote>> getAllNoteByType(String type){
        mAllRpgNote = mRpgNoteDao.getAllByType(type);
        return mAllRpgNote;
    }


    void insert(RpgNote rpgNote){
        RpgNoteDatabase.databaseWriteExecutor.execute(() -> {
            mRpgNoteDao.insertRpgNote(rpgNote);
        });
    }

    void delete(RpgNote rpgNote){
        RpgNoteDatabase.databaseWriteExecutor.execute(() -> {
            mRpgNoteDao.deleteRpgNote(rpgNote);
        });
    }

    void deleteById(int noteId){
        RpgNoteDatabase.databaseWriteExecutor.execute(() -> {
            mRpgNoteDao.deleteRpgNoteById(noteId);
        });
    }

    void updateById(int noteId, String title, String content, String type, Date date){
        RpgNoteDatabase.databaseWriteExecutor.execute(() -> {
            mRpgNoteDao.updateRpgNoteById(noteId, title, content, type, date);
        });
    }

}
