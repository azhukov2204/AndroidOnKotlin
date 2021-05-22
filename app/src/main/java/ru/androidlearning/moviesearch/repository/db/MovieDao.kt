package ru.androidlearning.moviesearch.repository.db

import androidx.room.*

@Dao
interface MovieDao {
    @Query("SELECT * FROM MovieEntity")
    fun getAllMoviesList():List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movieEntity: MovieEntity)

    @Update
    fun update(movieEntity: MovieEntity)

    @Delete
    fun delete(movieEntity: MovieEntity)

    @Query("DELETE FROM MovieEntity")
    fun deleteAll()
}