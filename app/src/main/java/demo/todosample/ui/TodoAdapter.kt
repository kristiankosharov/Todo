package demo.todosample.ui

import android.graphics.Paint
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
import java.util.logging.Logger


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
), ItemTouchHelperAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<ItemTodoBinding> {
        val binding = createBinding(parent)
        return DataBoundViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder<ItemTodoBinding>, position: Int) {
        val item = getItem(position)
        item.position = position
        bind(holder.binding, item, position)
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

    private fun bind(binding: ItemTodoBinding, item: Todo, position: Int) {
        binding.todoAction = object : TodoCallback {
            override fun edit() {
                item.forDelete = !item.forDelete

                Logger.getLogger("ADAPTER").info("Click: " + item.forDelete + " , " + item.description + " , " + position)
                notifyItemChanged(position)
            }
        }
        binding.todoEntity = item
        binding.todoItem.paintFlags = if (item.forDelete) Paint.STRIKE_THRU_TEXT_FLAG else 0
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
