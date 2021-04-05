package com.google.example.rpgnotes;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import com.google.example.rpgnotes.data.RpgNoteDao;
import com.google.example.rpgnotes.data.RpgNoteDatabase;
import com.google.example.rpgnotes.data.RpgNoteViewModel;
import com.google.example.rpgnotes.ui.pcnotes.PcNotesViewModel;

public class RpgNoteService extends IntentService {

    public static final String ACTION_UPDATE_NOTES_NUMBER = "com.example.android.mygarden.action.water_plants";


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * param name Used to name the worker thread, important only for debugging.
     */
    public RpgNoteService() {
        super("RpgNoteService");
    }


    /**
     * Starts this service action with the given parameters. If
     * the service is already performing a task this action will be queued.
     */
    public static void startActionRpgNoteService(Context context) {
        Intent intent = new Intent(context, RpgNoteService.class);
        intent.setAction(ACTION_UPDATE_NOTES_NUMBER);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     */
    public static void startActionUpdateRpgNoteService(Context context) {
        Intent intent = new Intent(context, RpgNoteService.class);
        intent.setAction(ACTION_UPDATE_NOTES_NUMBER);
        context.startService(intent);
    }

    //define the service action according to Intent
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_NOTES_NUMBER.equals(action)) {
                handleActionUpdateNotesNumber();
            }
        }
    }

    private void handleActionUpdateNotesNumber(){
        RpgNoteDatabase db = RpgNoteDatabase.getInstance(getApplicationContext());
        RpgNoteDao mRpgNoteDao = db.mRpgNoteDao();
        int notesNumber = mRpgNoteDao.getRpgNoteNumber();
        int pcNumber = mRpgNoteDao.getPcNoteNumber();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RpgNoteAppWidget.class));
        RpgNoteAppWidget.updateValuesAppWidget(this, appWidgetManager, notesNumber, pcNumber, appWidgetIds);
    };


}
