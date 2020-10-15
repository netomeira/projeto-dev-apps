package com.nmeira.projetofg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.nmeira.projetofg.adapter.AvatarAdapter
import com.nmeira.projetofg.model.Avatar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.avatar_card.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    companion object {
        const val MAIN_ACTIVITY_AVATAR_EXTRA_ID = "AVATAR_EXTRA_ID"
        const val LAUNCH_SECOND_ACTIVITY_REQUEST_CODE = 1

    }
    private var avatarList = arrayListOf<Avatar>()
    private val avatarAdapter = AvatarAdapter(this, avatarList, this::onAvatarClickListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = avatarAdapter
        }

        val swipeListener = SwipeListener(this@MainActivity, avatarAdapter, avatarList)
        ItemTouchHelper(swipeListener).attachToRecyclerView(recyclerView)

        addButton()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == LAUNCH_SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                val avatar = data?.getParcelableExtra<Avatar>(AvatarActivity.RESULT)
                if (avatar != null) {
                    avatarList[avatar.id] = avatar
                    avatarAdapter.notifyItemChanged(avatar.id)
                }
            }
        }
    }

    private fun onAvatarClickListener(avatar: Avatar) {
        val intent = Intent(this, AvatarActivity::class.java)
        intent.putExtra(MAIN_ACTIVITY_AVATAR_EXTRA_ID, avatar)

        startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY_REQUEST_CODE)
    }

    private fun addButton() {
        addAvatarButton.setOnClickListener {
            val type = spinnerClass.selectedItem.toString()
            val name = editTextAvatarName.text.toString()

            if (name.isNotEmpty()) {
                val typeIndex = resources.getStringArray(R.array.avatar_classes).indexOf(type)
                val avatar = Avatar(avatarList.lastIndex + 1, type, name, 1, typeIndex)

                avatarList.add(avatar)
                avatarAdapter.notifyItemInserted(avatarList.lastIndex)

                editTextAvatarName.text.clear()
                editTextAvatarName.clearFocus()
            } else {
                Toast.makeText(this, getString(R.string.avatar_empty), Toast.LENGTH_SHORT).show()
            }
        }
    }
}

