package com.google.example.rpgnotes.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DataRpgNoteName {

    public static final String TABLE_NAME = "rpgnote";

    /**
     * Column name for note id and used as primary.
     */
    public static final String COL_NOTEID = "noteid";
    /**
     * Column name for note title.
     */
    public static final String COL_NOTETITLE = "notetitle";
    /**
     * Column name for note type.
     */
    public static final String COL_NOTETYPE = "notetype";
    /**
     * Column name for note content
     */
    public static final String COL_NOTECONTENT = "notecontent";
  /**
   *  Column name for note timestamp
     */
    public static final String COL_TIMESTAMP = "timestamp";


    public static final String date_pattern = "MM/dd/yyyy HH:mm:ss";

    public static final DateFormat date_format = new SimpleDateFormat(date_pattern);
}
