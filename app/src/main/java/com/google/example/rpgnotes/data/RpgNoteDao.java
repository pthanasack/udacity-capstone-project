package com.google.example.rpgnotes.data;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

@Dao
public interface RpgNoteDao {

    @Query("SELECT * FROM rpgnote ORDER BY timestamp DESC")
    LiveData<List<RpgNote>> getAll();

    @Query("SELECT * FROM rpgnote WHERE notetype = :type ORDER BY timestamp DESC")
    LiveData<List<RpgNote>> getAllByType(String type);

    @Query("SELECT * FROM rpgnote WHERE noteid = :id")
    RpgNote getRpgNoteById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRpgNote(RpgNote rpgNote);

    @Delete
    void deleteRpgNote(RpgNote rpgNote);

    @Query("DELETE FROM rpgnote")
    void deleteAllRpgNote();

    @Query("DELETE FROM rpgnote WHERE noteid = :id")
    void deleteRpgNoteById(int id);

    @Query("UPDATE rpgnote SET notetitle = :title , notecontent= :content , notetype= :type, timestamp= :date WHERE noteid= :id")
    void updateRpgNoteById(int id, String title, String content, String type, Date date);

}
