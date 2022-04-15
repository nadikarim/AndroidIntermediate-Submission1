package com.nadikarim.submision1.ui.story.add

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.nadikarim.submision1.data.model.stories.AddResponse
import com.nadikarim.submision1.data.remote.ApiConfig
import com.nadikarim.submision1.databinding.ActivityAddStoryBinding
import com.nadikarim.submision1.ui.main.MainActivity
import com.nadikarim.submision1.utils.LoginPreference
import com.nadikarim.submision1.utils.createTempFile
import com.nadikarim.submision1.utils.reduceFileImage
import com.nadikarim.submision1.utils.uriToFile
import com.uk.tastytoasty.TastyToasty
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AddStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStoryBinding

    private lateinit var mLoginPreference: LoginPreference
    private lateinit var currentPhotoPath: String

    private var getFile: File? = null

    companion object {

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Story"
        mLoginPreference = LoginPreference(this)

        binding.btnAddCamera.setOnClickListener { startTakePhoto() }
        binding.btnAddGallery.setOnClickListener { startGallery() }
        binding.btnUpload.setOnClickListener {
            addStory()
            startActivity(Intent(this, MainActivity::class.java))

            finish()
        }

    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@AddStoryActivity,
                "com.nadikarim.submision1",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            getFile = myFile

            val result = BitmapFactory.decodeFile(getFile?.path)
            binding.ivPreview.setImageBitmap(result)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri

            val myFile = uriToFile(selectedImg, this@AddStoryActivity)

            getFile = myFile

            binding.ivPreview.setImageURI(selectedImg)
        }
    }



    private fun addStory() {

        if (getFile != null) {
            val file = reduceFileImage(getFile as File)
            val descriptionText = binding.etAdd.text.toString()
            val description = descriptionText.toRequestBody("text/plain".toMediaType())
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )

            val token = "Bearer ${mLoginPreference.getUser().token}"
            val service = ApiConfig().getApiService().uploadImage(token, imageMultipart, description)
            service.enqueue(object : Callback<AddResponse> {
                override fun onResponse(call: Call<AddResponse>, response: Response<AddResponse>) {
                    if (response.isSuccessful) {
                        val responseBody =response.body()
                        if (responseBody != null && !responseBody.error) {
                            TastyToasty.success(this@AddStoryActivity, responseBody.message).show()
                        } else {
                            TastyToasty.error(this@AddStoryActivity, response.message()).show()
                        }
                    }
                }

                override fun onFailure(call: Call<AddResponse>, t: Throwable) {
                    TastyToasty.error(this@AddStoryActivity, "Gagal instance retrofit").show()
                }

            })
        } else {
            TastyToasty.error(this@AddStoryActivity, "Silahkan masukkan gambar terlebih dahulu.").show()
        }
        }

}