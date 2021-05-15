package com.mickdevil.go4lunch.UI.Chat.localDB;

import android.app.Application;

import androidx.room.Room;


//the singlton class where DB is builded and accessible by instance of this class, and it's used in repository, this class is extending Application,
// so it start first in app(watch the manifest, i declared it inside)
public class AppST extends Application {

    private DB database;
    private TaskDAO taskDAO;

    private static AppST instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;


        database = Room.databaseBuilder(getApplicationContext(),
                DB.class, "db").build();

        taskDAO = database.taskDAO();
    }

    public DB getDatabase() {
        return database;
    }

    public void setDatabase(DB database) {
        this.database = database;
    }

    public TaskDAO getTaskDAO() {
        return taskDAO;
    }

    public void setTaskDAO(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    public static AppST getInstance() {
        return instance;
    }
}
