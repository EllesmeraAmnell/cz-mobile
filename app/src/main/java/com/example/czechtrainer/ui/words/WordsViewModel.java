package com.example.czechtrainer.ui.words;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class WordsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public WordsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is words fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}