package com.nmeira.projetofg

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nmeira.projetofg.model.Avatar
import kotlinx.android.synthetic.main.activity_avatar.*

class AvatarActivity : AppCompatActivity() {
    companion object {
        const val RESULT = "result"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avatar)

        val avatar = intent.extras?.getParcelable<Avatar>(MainActivity.MAIN_ACTIVITY_AVATAR_EXTRA_ID)

        if (avatar != null) {
            val images = applicationContext.resources.obtainTypedArray(R.array.avatar_images)
            val drawable = images.getDrawable(avatar.img)
            imgAvatarDetail.setImageDrawable(drawable)
            images.recycle()

            lblAvatarName.text = avatar.name
            lblAvatarClassName.text = avatar.type
            edtAvatarLevel.setText(avatar.lvl.toString())
        }

        saveAvatar(avatar)
    }

    private fun saveAvatar(avatar: Avatar?) {
        btnSaveAvatar.setOnClickListener {
            val level = if (edtAvatarLevel.text.isNullOrEmpty()) 1
                        else edtAvatarLevel.text.toString().toInt()

            if (avatar != null) {
                avatar.lvl = level
            }

            val intentBack = Intent(this, MainActivity::class.java)
            intentBack.putExtra(RESULT, avatar)
            setResult(RESULT_OK, intentBack)

            finish()
        }
    }
}
