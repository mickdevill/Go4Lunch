package com.mickdevil.go4lunch.UI.Chat.localDB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;



import java.util.List;

@Dao
public interface TaskDAO {
    //query to get live data list of DB
    @Query("SELECT * FROM meseges")
    LiveData<List<Mesege>> getLiveData();

    //insert new Mesege
    @Insert
    void insert(Mesege mesege);

    //remove Mesege
    @Delete
    void delete(Mesege mesege);


}
