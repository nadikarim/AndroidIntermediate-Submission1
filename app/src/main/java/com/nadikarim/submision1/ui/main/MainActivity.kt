package com.nadikarim.submision1.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nadikarim.submision1.R
import com.nadikarim.submision1.data.ListAdapter
import com.nadikarim.submision1.databinding.ActivityMainBinding
import com.nadikarim.submision1.ui.login.LoginActivity
import com.nadikarim.submision1.ui.story.add.AddStoryActivity
import com.nadikarim.submision1.utils.LoginPreference

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainVIewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var mLoginPreference: LoginPreference
    private lateinit var adapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Home"

        mLoginPreference = LoginPreference(this)
        adapter = ListAdapter()

        setRecyclerView()
        setupViewModel()
        validate()
        action()
    }

    private fun action() {
        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddStoryActivity::class.java)
            startActivity(intent,
                ActivityOptionsCompat.makeSceneTransitionAnimation(this@MainActivity as Activity).toBundle()
            )
        }
    }

    private fun validate() {
        if (!mLoginPreference.getUser().isLogin) {
            val login = mLoginPreference.getUser().isLogin
            Log.d("tag", login.toString())
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent,
                ActivityOptionsCompat.makeSceneTransitionAnimation(this@MainActivity as Activity).toBundle()
            )
            finish()
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[MainVIewModel::class.java]

        viewModel.getAllStories(mLoginPreference.getUser().token)
        viewModel.listStory.observe(this) {
            if (it != null) {
                adapter.setStory(it)
            }
        }
        viewModel.isLoading.observe(this) { showLoading(it) }
    }

    private fun setRecyclerView() {
        binding.apply {
            rvStory.layoutManager = LinearLayoutManager(this@MainActivity)
            rvStory.setHasFixedSize(true)
            rvStory.adapter = adapter

        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId) {
            R.id.menu_logout -> {
                mLoginPreference.logout()
                Log.d("tag", "token dihapus")
                Log.d("tag", mLoginPreference.getUser().isLogin.toString())
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                true
            }
            else -> {return super.onOptionsItemSelected(item)}
        }
    }
}