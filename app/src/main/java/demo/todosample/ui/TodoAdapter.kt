package demo.todosample.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import demo.todosample.R
import demo.todosample.databinding.ItemTodoBinding
import demo.todosample.entity.Todo
import demo.todosample.ui.common.DataBoundViewHolder
import demo.todosample.util.AppExecutors


class TodoAdapter(
        appExecutors: AppExecutors,
        diffCallback: DiffUtil.ItemCallback<Todo> = object : DiffUtil.ItemCallback<Todo>() {
            override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem == newItem
            }
        }
) : ListAdapter<Todo, DataBoundViewHolder<ItemTodoBinding>>(
        AsyncDifferConfig.Builder<Todo>(diffCallback)
                .setBackgroundThreadExecutor(appExecutors.diskIO())
                .build()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<ItemTodoBinding> {
        val binding = createBinding(parent)
        return DataBoundViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder<ItemTodoBinding>, position: Int) {
        bind(holder.binding, getItem(position))
        holder.binding.executePendingBindings()
    }

    private fun createBinding(parent: ViewGroup): ItemTodoBinding {
        return DataBindingUtil
                .inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_todo,
                        parent,
                        false
                )
    }

    private fun bind(binding: ItemTodoBinding, item: Todo) {
        binding.todoEntity = item
    }

}