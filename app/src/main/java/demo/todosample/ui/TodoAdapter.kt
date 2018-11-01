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
import demo.todosample.ui.common.ItemTouchHelperAdapter
import demo.todosample.ui.common.TodoCallback
import demo.todosample.util.AppExecutors


class TodoAdapter(
        val clickListener: TodoCallback,
        appExecutors: AppExecutors,
        diffCallback: DiffUtil.ItemCallback<Todo> = object : DiffUtil.ItemCallback<Todo>() {
            override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                val result = oldItem == newItem
                return result
            }

            override fun getChangePayload(oldItem: Todo, newItem: Todo): Any {
                return newItem
            }
        }
) : ListAdapter<Todo, DataBoundViewHolder<ItemTodoBinding>>(
        AsyncDifferConfig.Builder<Todo>(diffCallback)
                .setBackgroundThreadExecutor(appExecutors.diskIO())
                .build()
), ItemTouchHelperAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<ItemTodoBinding> {
        val binding = createBinding(parent)
        return DataBoundViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder<ItemTodoBinding>, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            val item = payloads[0] as Todo
            bindHolder(item, holder, position)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder<ItemTodoBinding>, position: Int) {
        val item = getItem(position)
        bindHolder(item, holder, position)
    }

    private fun bindHolder(item: Todo, holder: DataBoundViewHolder<ItemTodoBinding>, position: Int) {
        item.position = position
        bind(holder.binding, item)
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
        binding.todoAction = object : TodoCallback {
            override fun edit(item: Todo) {
                item.forDelete = !item.forDelete
                if (item.forDelete) binding.todoItem.strikeThrough() else binding.todoItem.release()
                clickListener.edit(item)
            }
        }
        binding.todoEntity = item
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
