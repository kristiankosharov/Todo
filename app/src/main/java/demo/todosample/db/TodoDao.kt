package demo.todosample.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import demo.todosample.entity.Todo

@Dao
interface TodoDao {
    @Insert
    fun insert(item: Todo)

    @Query("SELECT * FROM Todos")
    fun getAllItems(): LiveData<List<Todo>>

    @Delete
    fun delete(item: Todo)
}