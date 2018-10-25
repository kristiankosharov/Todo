package demo.todosample.db

import androidx.room.Database
import androidx.room.RoomDatabase
import demo.todosample.entity.Todo

@Database(entities = [Todo::class], exportSchema = false, version = 1)
abstract class TodoDb : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}