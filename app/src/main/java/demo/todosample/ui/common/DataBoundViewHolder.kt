package demo.todosample.ui.common

import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import demo.todosample.R

/**
 * A generic ViewHolder that works with a [ViewDataBinding].
 * @param <T> The type of the ViewDataBinding.
</T> */
class DataBoundViewHolder<out T : ViewDataBinding> constructor(val binding: T) :
        RecyclerView.ViewHolder(binding.root), ItemTouchHelperViewHolder {

    override fun onItemSelected() {
        binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.hover_effect))
    }

    override fun onItemClear() {
        binding.root.setBackgroundColor(Color.WHITE)
    }
}