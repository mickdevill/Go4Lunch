package com.mickdevil.go4lunch.UI.Chat.localDB;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MainViewModel extends androidx.lifecycle.ViewModel {

    private Repository repository;

    private LiveData<List<Mesege>> LVD;


    public MainViewModel() {
        repository = new Repository();
        LVD = repository.getLVD();
    }

    public LiveData<List<Mesege>> getLVD() {
        return LVD;
    }


    public void insert(Mesege mesege) {
        repository.insert(mesege);
    }

    public void delete(Mesege mesege) {
        repository.delete(mesege);
    }
}
