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
import android.widget.RadioButton
import com.bumptech.glide.Glide
import com.example.influshop.api.ApiService
import com.example.influshop.api.ResultSingle
import com.example.influshop.databinding.ActivityPembayaranBinding
import com.example.influshop.model.Order
import com.example.influshop.model.Payment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream

class PembayaranActivity : AppCompatActivity() {
    private lateinit var viewBinder : ActivityPembayaranBinding
    private val apiService = ApiService()
    private var optionclick = ""
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var selectedFileUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinder = ActivityPembayaranBinding.inflate(layoutInflater)
        setContentView(viewBinder.root)
        prosesOption()
        setupView()
        bayar()

    }

    private fun setupView(){
        val amount = PreferenceUtility.getInt(application,"amount",0);
        viewBinder.etTotalHarga2.text = "Total : ${amount}"

        viewBinder.btUnggahBuktiTf.setOnClickListener{
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
            val fileName = getFileName(selectedFileUri)
            Log.d("File Name", "Selected File: $fileName")

            val imageView = findViewById<ImageView>(R.id.ivSelectedPayment)
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
        val fileName = "profile_image.jpg"
        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), inputStream.readBytes())
        return MultipartBody.Part.createFormData("bukti_tf", fileName, requestFile)
    }


    private fun bayar(){
        viewBinder.btSimpanBuktiTf.setOnClickListener{
            processBayar()
        }
    }

    private fun prosesOption(){
        val radioButtonOption1 = viewBinder.radioButtonOption1
        val radioButtonOption2 = viewBinder.radioButtonOption2

        radioButtonOption1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                optionclick = radioButtonOption1.text.toString()
                Log.d("opsi 1",optionclick)

            }
        }

        radioButtonOption2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                optionclick = radioButtonOption2.text.toString()
                Log.d("opsi 2",optionclick)
            }
        }

    }

    private fun processBayar() {
        val id_user = PreferenceUtility.getInt(application, "user_id", 0)
        val amount = PreferenceUtility.getInt(application, "amount", 0)
        val nama_perusahaan = PreferenceUtility.getString(application, "nama_perusahaan", "")
        val sosmed_perusahaan = PreferenceUtility.getString(application, "sosmed_perusahaan", "")
        val deskripsi_bisnis = PreferenceUtility.getString(application, "deskripsi_bisnis", "")
        val pesan_influencer = PreferenceUtility.getString(application, "pesan_influencer", "")

        val filePart = prepareFilePart(selectedFileUri)

        Log.d("amount", amount.toString())

        val requestBodyMap = mutableMapOf<String, RequestBody>()
        requestBodyMap["id_user"] = id_user.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        requestBodyMap["amount"] = amount.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        requestBodyMap["busines_name"] = nama_perusahaan.toRequestBody("text/plain".toMediaTypeOrNull())
        requestBodyMap["busines_socmed"] = sosmed_perusahaan.toRequestBody("text/plain".toMediaTypeOrNull())
        requestBodyMap["busines_desc"] = deskripsi_bisnis.toRequestBody("text/plain".toMediaTypeOrNull())
        requestBodyMap["todo"] = pesan_influencer.toRequestBody("text/plain".toMediaTypeOrNull())
        requestBodyMap["payment_method"] = optionclick.toRequestBody("text/plain".toMediaTypeOrNull())

        apiService.bayar(id_user, filePart, requestBodyMap["busines_name"]!!, requestBodyMap["busines_socmed"]!!, requestBodyMap["busines_desc"]!!, requestBodyMap["todo"]!!, requestBodyMap["amount"]!!, requestBodyMap["payment_method"]!!)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<ResultSingle<Payment>>() {
                override fun onNext(value: ResultSingle<Payment>) {
                    val order = value.data
                    Log.d("data payment", order.toString())
                }
                override fun onComplete() {
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }
            })
    }

}