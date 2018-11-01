package demo.todosample.db

import androidx.lifecycle.LiveData
import androidx.room.*
import demo.todosample.entity.Todo

@Dao
interface TodoDao {
    @Insert
    fun insert(item: Todo)

    @Query("SELECT * FROM Todos")
    fun getAllItems(): LiveData<List<Todo>>

    @Delete
    fun delete(item: Todo)

    @Update
    fun update(item: Todo)

    @Query("DELETE FROM Todos")
    fun deleteAll()
}