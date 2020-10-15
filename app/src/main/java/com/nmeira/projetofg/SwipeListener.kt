package com.nmeira.projetofg

import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.nmeira.projetofg.adapter.AvatarAdapter
import com.nmeira.projetofg.model.Avatar

class SwipeListener(
    private val context: Context,
    private val adapter: AvatarAdapter,
    private val avatars: ArrayList<Avatar>
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val avatar = avatars[viewHolder.adapterPosition]

        avatars.remove(avatar)
        adapter.notifyItemRemoved(viewHolder.adapterPosition)

        Toast.makeText(
            context,
            context.getString(R.string.avatar_removed, avatar.name),
            Toast.LENGTH_LONG
        ).show()
    }
}
