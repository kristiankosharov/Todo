package demo.todosample.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*


@Entity(tableName = "Todos")
data class Todo(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        val description: String,
        val createdTime: Long
) {
    @Ignore
    val convertedTime = getTime(createdTime)

    @Ignore
    var position: Int = 0

    var forDelete: Boolean = false

    private fun getTime(timestamp: Long): String {
        val formatter = SimpleDateFormat("HH:mm:SS dd/MM/yyyy", Locale.getDefault())
        return formatter.format(Date(timestamp))
    }
}