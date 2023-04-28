package com.task.actionlist.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.task.actionlist.R
import com.task.actionlist.data.model.ActionListItem
import com.task.actionlist.databinding.AdapterNoteListBinding

class ActionAdapter(list: MutableList<ActionListItem>) :
    BaseBindingAdapter<ActionListItem, AdapterNoteListBinding>(list) {
    override fun getLayoutResId(viewType: Int): Int {
        return R.layout.adapter_note_list
    }

    override fun onBindItem(
        binding: AdapterNoteListBinding,
        item: ActionListItem,
        holder: RecyclerView.ViewHolder
    ) {
        binding.action = item
        val position = holder.bindingAdapterPosition
        binding.cl.setOnClickListener { v ->
            itemClick?.invoke(v.id, item, position)
        }
        binding.btnMark.setOnClickListener { v ->
//            item.toggleType(ActionListItem.TYPE_MARKED)
            notifyItemChanged(position)
            notifyItemRangeChanged(position, 1)
            itemClick?.invoke(v.id, item, position)
        }
//        binding.btnTopping.setOnClickListener { v ->
//            item.toggleType(ActionListItem.TYPE_TOPPING)
//            itemClick?.invoke(v.id, item, position)
//        }
        binding.btnDelete.setOnClickListener { v ->
            notifyItemRemoved(position)
            _list.removeAt(position)
            notifyItemRangeRemoved(position, list.size - position)
            itemClick?.invoke(v.id, item, position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
