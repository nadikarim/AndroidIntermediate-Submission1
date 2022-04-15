package com.nadikarim.submision1.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import com.nadikarim.submision1.databinding.ActivityRegisterBinding
import com.nadikarim.submision1.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Register"

        setupViewModel()
        action()
        playAnimation()
    }

    private fun action() {
        binding.btnRegister.setOnClickListener {
            register()
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        viewModel.isLoading.observe(this) { showLoading(it) }
    }

    private fun register() {
        val name = binding.tvName.text.toString().trim()
        val email = binding.tvEmail.text.toString().trim()
        val password = binding.tvPassword.text.toString().trim()
        viewModel.registerUser(name, email, password)

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this@RegisterActivity as Activity).toBundle())
        finish()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.ivLogo, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val email = ObjectAnimator.ofFloat(binding.tvRegister, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(email)
            startDelay = 500
        }.start()
    }
}