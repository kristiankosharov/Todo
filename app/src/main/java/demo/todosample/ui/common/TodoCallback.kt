package demo.todosample.ui.common

import demo.todosample.entity.Todo

interface TodoCallback {
    fun edit(item: Todo)
}
