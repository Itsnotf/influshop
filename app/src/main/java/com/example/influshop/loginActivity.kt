package com.example.influshop

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.influshop.api.ApiService
import com.example.influshop.api.ResultSingle
import com.example.influshop.databinding.ActivityLoginBinding
import com.example.influshop.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.security.auth.login.LoginException

class loginActivity : AppCompatActivity() {
    private lateinit var viewBinder : ActivityLoginBinding
    private var apiService = ApiService()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinder = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(viewBinder.root)


        pengaturanView()
        checkSudahLogin()
    }

    private fun pengaturanView(){
        viewBinder.btLogin.setOnClickListener{
            login()
        }
        viewBinder.btDaftar.setOnClickListener{
            startActivity(Intent(viewBinder.root.context,RegisterActivity::class.java))
        }
    }

    private fun checkSudahLogin(){
        val userId = PreferenceUtility.getInt(application,"user_id",0)

        if(userId > 0){
            startActivity(Intent(viewBinder.root.context,BottomMenuActivity::class.java))
            finish()
        }
    }

    private fun login() {
        val email = viewBinder.etUsername.text.toString()
        val password = viewBinder.etPassword.text.toString()

        apiService.login(User(0, email, password))
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object :
                DisposableSingleObserver<ResultSingle<User>>(){
                override fun onSuccess(value: ResultSingle<User>) {
                    PreferenceUtility.putInt(
                        application,
                        "user_id",
                        value.data.id
                    )
                    PreferenceUtility.putString(
                        application,
                        "email",
                        value.data.email
                    )
                    PreferenceUtility.putString(
                        application,
                        "picture",
                        value.data.picture
                    )
                    startActivity(Intent(viewBinder.root.context, BottomMenuActivity::class.java))
                    finish()
                }

                override fun onError(e: Throwable) {
                    e.message?.let {
                        Toast.makeText(viewBinder.root.context, it, Toast.LENGTH_SHORT).show()
                    }
                }

            })
    }


    }