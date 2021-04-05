package com.google.example.rpgnotes;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class RpgNoteAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int notesNumber, int pcNumber,
                                int appWidgetId) {

        String allNotesText = context.getString(R.string.appwidget_text);
        String pcNotesText = context.getString(R.string.appwidget_pcnotes);
        //Intent to launch MainActivity
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.rpg_note_app_widget);
        views.setTextViewText(R.id.appwidget_allnotes, allNotesText + notesNumber);
        views.setTextViewText(R.id.appwidget_PCnotes, pcNotesText +pcNumber );
        views.setOnClickPendingIntent(R.id.appwidget_allnotes, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            RpgNoteService.startActionUpdateRpgNoteService(context);
        }
    }

    public static void updateValuesAppWidget(Context context, AppWidgetManager appWidgetManager,
                                          int notesNumber, int pcNumber, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, notesNumber, pcNumber, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}