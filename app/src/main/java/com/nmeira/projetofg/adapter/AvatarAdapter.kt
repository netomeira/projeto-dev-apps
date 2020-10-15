package com.nmeira.projetofg.adapter

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nmeira.projetofg.R
import com.nmeira.projetofg.model.Avatar
import kotlinx.android.synthetic.main.avatar_card.view.*

class AvatarAdapter(private val context: Context,
					private val avatars: List<Avatar>,
					private val callback: (Avatar) -> Unit) : RecyclerView.Adapter<AvatarAdapter.ViewHolder>() {

	private val avatarImages: TypedArray by lazy {
		context.resources.obtainTypedArray(R.array.avatar_images)
	}

	override fun getItemCount() = avatars.size

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val itemView = LayoutInflater.from(parent.context).inflate(R.layout.avatar_card, parent, false)

		val vh = ViewHolder(itemView)
		vh.itemView.buttonLevelUp.setOnClickListener{
			avatars[vh.adapterPosition].lvl++
			notifyItemChanged(vh.adapterPosition)
		}

		vh.itemView.setOnClickListener {
			val avatar = avatars[vh.adapterPosition]
			callback(avatar)
		}

		return vh
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val avatar = avatars[position]

		holder.avatarImg.setImageDrawable(avatarImages.getDrawable(avatar.img))
		holder.textAvatarName.text = avatar.name
		holder.textAvatarClass.text = avatar.type
		getClassColor(avatar.type)?.let { holder.textAvatarClass.setTextColor(it) }
		holder.textAvatarLevel.text = context.getString(R.string.avatar_level, avatar.lvl.toString())
	}

	class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
		val avatarImg : ImageView = itemView.iconAvatar
		val textAvatarName: TextView = itemView.textAvatarName
		val textAvatarClass: TextView = itemView.textAvatarClass
		val textAvatarLevel: TextView = itemView.textAvatarLevel
	}

	fun getClassColor(className: String) : Int? {
		val colorMap : HashMap<String, Int> = HashMap<String, Int>()
		colorMap["Warrior"] = R.color.colorWarrior
		colorMap["Knight"] = R.color.colorKnight
		colorMap["Inventor"] = R.color.colorInventor
		colorMap["Defender"] = R.color.colorDefender
		colorMap["Barbarian"] = R.color.colorBarbarian
		colorMap["Ranger"] = R.color.colorRanger

		return colorMap[className]?.let { ContextCompat.getColor(context, it) }
	}
}
