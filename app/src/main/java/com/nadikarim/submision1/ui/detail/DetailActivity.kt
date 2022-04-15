package com.nadikarim.submision1.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.nadikarim.submision1.databinding.ActivityDetailBinding
import com.nadikarim.submision1.utils.EXTRA_DESCRIPTION
import com.nadikarim.submision1.utils.EXTRA_IMAGE
import com.nadikarim.submision1.utils.STORY_NAME

class DetailActivity : AppCompatActivity() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.title = "Detail"
        setDeail()
    }

    private fun setDeail() {
        val name = intent.getStringExtra(STORY_NAME)
        val desc = intent.getStringExtra(EXTRA_DESCRIPTION)
        val image = intent.getStringExtra(EXTRA_IMAGE)

        binding.apply {
            tvName.text = name
            tvDesc.text = desc
            Glide.with(this@DetailActivity)
                .load(image)
                .into(ivStory)
        }
    }
}