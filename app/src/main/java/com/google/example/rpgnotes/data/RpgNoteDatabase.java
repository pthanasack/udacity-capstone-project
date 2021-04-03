package com.google.example.rpgnotes.data;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {RpgNote.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class RpgNoteDatabase extends RoomDatabase {

    public abstract RpgNoteDao mRpgNoteDao();
    private static volatile  RpgNoteDatabase mInstance = null;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    @NonNull
    public static synchronized RpgNoteDatabase getInstance(final Context context) {
        if (mInstance == null) {
            synchronized (RpgNoteDatabase.class) {
                if (mInstance == null) {
                    //DONE create a database instance and fill with data from JSON
                    mInstance = Room.databaseBuilder(context, RpgNoteDatabase.class, "rpgnote_database")
                            .build();
                    //execute filler
                    databaseWriteExecutor.execute(() -> { RpgNoteDatabase.fillWithDemoData(context);});
                }
            }
        }
        return mInstance;
    }


    private static void fillWithDemoData(Context context){


                // Get the database and empty it
                RpgNoteDao dao = getInstance(context).mRpgNoteDao();
                //dao.deleteAllRpgNote();
                // fill the database
                RpgNote note = new RpgNote(1, "Romeo", "NPC", "Romeo is a knight", Calendar.getInstance().getTime());
                dao.insertRpgNote(note);
                note = new RpgNote(2, "Beeholder", "Monster", "One eyed monster", Calendar.getInstance().getTime());
                dao.insertRpgNote(note);
                note = new RpgNote(3, "Forest", "Area", "Haunted forest", Calendar.getInstance().getTime());
                dao.insertRpgNote(note);
                note = new RpgNote(4, "Juliet", "PC", "Princess not to rescue", Calendar.getInstance().getTime());
                dao.insertRpgNote(note);
                note = new RpgNote(5, "Devin", "PC", "Skilled Bard", Calendar.getInstance().getTime());
                dao.insertRpgNote(note);
                note = new RpgNote(6, "Pesso", "PC", "Penguin Companion", Calendar.getInstance().getTime());
                dao.insertRpgNote(note);
                note = new RpgNote(7, "Emerald Sword", "Item", "Legendary Weapon", Calendar.getInstance().getTime());
                dao.insertRpgNote(note);
                note = new RpgNote(8, "Love Potion", "Item", "Random results", Calendar.getInstance().getTime());
                dao.insertRpgNote(note);


    };



}
