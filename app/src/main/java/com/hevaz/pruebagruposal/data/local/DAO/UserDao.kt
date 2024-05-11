package com.hevaz.pruebagruposal.data.local.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hevaz.pruebagruposal.data.local.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAllUsers(): LiveData<List<User>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User>)


    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)


    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserById(userId: Int): LiveData<User>
}