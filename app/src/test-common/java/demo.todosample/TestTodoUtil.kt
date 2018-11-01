package demo.todosample

import demo.todosample.entity.Todo


object TestTodoUtil {
    fun createTodo(now: Long) = Todo(id = 1, description = "Todo1", createdTime = now)
}