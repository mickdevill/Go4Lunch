package com.mickdevil.go4lunch.UI.Chat.localDB;

import android.os.Message;

import androidx.lifecycle.LiveData;

import java.util.List;

//the repository class by google with out application class in constructor
public class Repository {

    private TaskDAO mTaskDAO;
    private LiveData<List<Mesege>> LVD;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public Repository() {
        DB db = AppST.getInstance().getDatabase();
        mTaskDAO = db.taskDAO();
        LVD = mTaskDAO.getLiveData();

    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Mesege>> getLVD() {
        return LVD;
    }


    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Mesege mesege) {
        DB.databaseWriteExecutor.execute(() -> {
            mTaskDAO.insert(mesege);
        });
    }

    public void delete(Mesege mesege) {
        DB.databaseWriteExecutor.execute(() -> {
            mTaskDAO.delete(mesege);
        });
    }

}
