package com.nadikarim.submision1.data

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nadikarim.submision1.data.model.stories.Story
import com.nadikarim.submision1.databinding.ListStoryBinding
import com.nadikarim.submision1.ui.detail.DetailActivity
import com.nadikarim.submision1.utils.EXTRA_DESCRIPTION
import com.nadikarim.submision1.utils.EXTRA_IMAGE
import com.nadikarim.submision1.utils.STORY_NAME

class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    private val listStory = ArrayList<Story>()

    class ViewHolder(private val binding: ListStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(story: Story) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(story.photoUrl)
                    .into(imageView)

                tvJudul.text = story.name
                tvDesc.text = story.description

                cardView.setOnClickListener {

                    val intent = Intent(itemView.context, DetailActivity::class.java)

                    intent.putExtra(EXTRA_IMAGE, story.photoUrl)
                    intent.putExtra(EXTRA_DESCRIPTION, story.description)
                    intent.putExtra(STORY_NAME, story.name)

                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            itemView.context as Activity,
                            Pair(imageView, "photo"),
                            Pair(tvJudul, "name"),
                            Pair(tvDesc, "description"),
                        )
                    itemView.context.startActivity(intent, optionsCompat.toBundle())
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ListStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listStory[position])
    }

    override fun getItemCount(): Int = listStory.size

    @SuppressLint("NotifyDataSetChanged")
    fun setStory(story: ArrayList<Story>) {
        listStory.clear()
        listStory.addAll(story)
        notifyDataSetChanged()
    }

}