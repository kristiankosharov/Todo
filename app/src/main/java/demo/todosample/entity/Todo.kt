package demo.todosample.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Todos")
data class Todo(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        val description: String,
        val createdTime: Long
)