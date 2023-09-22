package com.example.influshop

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.influshop.api.ApiService
import com.example.influshop.api.ResultSingle
import com.example.influshop.databinding.ActivityRegisterBinding
import com.example.influshop.model.UserRegister
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream


//register
class RegisterActivity : AppCompatActivity() {
    private lateinit var viewBinder: ActivityRegisterBinding
    private val PICK_IMAGE_REQUEST = 1
    private var selectedFileUri: Uri? = null
    private val apiService = ApiService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinder = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(viewBinder.root)

        setupViews()
    }

    private fun setupViews() {
        viewBinder.btTerdaftar.setOnClickListener {
            register()
        }
        viewBinder.btFoto.setOnClickListener {
            chooseImage()
        }
    }

    private fun chooseImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedFileUri = data.data!!
            val fileName = getFileName(selectedFileUri!!)
            Log.d("File Name", "Selected File: $fileName")

            val imageView = findViewById<ImageView>(R.id.ivSelected)
            Glide.with(this)
                .load(selectedFileUri)
                .into(imageView)
            imageView.visibility = View.VISIBLE
        }
    }

    private fun getFileName(uri: Uri): String {
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    return it.getString(nameIndex)
                }
            }
        }
        return ""
    }

    private fun prepareFilePart(fileUri: Uri): MultipartBody.Part {
        val inputStream: InputStream = contentResolver.openInputStream(fileUri)!!

        // Use a unique name for the file
        val fileName = "profile_image.jpg"

        // Create a request body for the file
        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), inputStream.readBytes())

        // Create the multipart part with the file name and the request body
        return MultipartBody.Part.createFormData("profile_picture", fileName, requestFile)
    }

    private fun register() {
        val email = viewBinder.etEmail.text.toString()
        val password = viewBinder.etPass.text.toString()
        val nohp = viewBinder.etNohp.text.toString()
        val username = viewBinder.etUser.text.toString()

        val filePart = prepareFilePart(selectedFileUri!!)

        // Create request bodies for user data
        val nameRequestBody = username.toRequestBody("text/plain".toMediaTypeOrNull())
        val emailRequestBody = email.toRequestBody("text/plain".toMediaTypeOrNull())
        val nohpRequestBody = nohp.toRequestBody("text/plain".toMediaTypeOrNull())
        val passwordRequestBody = password.toRequestBody("text/plain".toMediaTypeOrNull())

        apiService.register(filePart, nameRequestBody, emailRequestBody, nohpRequestBody, passwordRequestBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ResultSingle<UserRegister>>() {
                override fun onNext(value: ResultSingle<UserRegister>) {
                    // Handle the response
                    if (value.success == 1) {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Registration Successful, Please Login",
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(this@RegisterActivity,loginActivity::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        Toast.makeText(this@RegisterActivity, value.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onError(e: Throwable) {
                    // Handle errors
                    Log.e("Registration Error", e.message ?: "Unknown error")
                    Toast.makeText(this@RegisterActivity, "Registration failed", Toast.LENGTH_SHORT).show()
                }

                override fun onComplete() {
                    // Handle completion if needed
                }
            })
    }
}
