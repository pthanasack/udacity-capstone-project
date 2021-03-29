package com.google.example.rpgnotes.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = DataRpgNoteName.TABLE_NAME)
public class RpgNote {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = DataRpgNoteName.COL_NOTEID)
    private int mNoteId;

    @ColumnInfo(name = DataRpgNoteName.COL_NOTETITLE)
    private String mNoteTitle;

    @ColumnInfo(name = DataRpgNoteName.COL_NOTETYPE)
    private String mNoteType;

    @ColumnInfo(name = DataRpgNoteName.COL_NOTECONTENT)
    private String mNoteContent;

    @ColumnInfo(name = DataRpgNoteName.COL_TIMESTAMP)
    private Date mNoteTimestamp;

            //constructor without id
    public RpgNote(String mNoteTitle, String mNoteType, String mNoteContent, Date mNoteTimestamp){
        this.mNoteTitle = mNoteTitle;
        this.mNoteType = mNoteType;
        this.mNoteContent = mNoteContent;
        this.mNoteTimestamp = mNoteTimestamp;
    }
            //constructor with id
    @Ignore
    public RpgNote(@NonNull int mNoteId, String mNoteTitle, String mNoteType, String mNoteContent, Date mNoteTimestamp){
        this.mNoteId = mNoteId;
        this.mNoteTitle = mNoteTitle;
        this.mNoteType = mNoteType;
        this.mNoteContent = mNoteContent;
        this.mNoteTimestamp = mNoteTimestamp;
    }

    @NonNull
    public int getMNoteId() {
        return mNoteId;
    }

    public void setMNoteId(@NonNull int mNoteId) {
        this.mNoteId = mNoteId;
    }

    public String getMNoteTitle() {
        return mNoteTitle;
    }

    public void setMNoteTitle(String mNoteTitle) {
        this.mNoteTitle = mNoteTitle;
    }

    public String getMNoteType() {
        return mNoteType;
    }

    public void setMNoteType(String mNoteType) {
        this.mNoteType = mNoteType;
    }

    public String getMNoteContent() {
        return mNoteContent;
    }

    public void setMNoteContent(String mNoteContent) {
        this.mNoteContent = mNoteContent;
    }

    public Date getMNoteTimestamp() {
        return mNoteTimestamp;
    }

    public void setMNoteTimestamp(Date mNoteTimestamp) {
        this.mNoteTimestamp = mNoteTimestamp;
    }
}
