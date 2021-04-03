package com.google.example.rpgnotes.ui.pcnotes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PcNotesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PcNotesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}