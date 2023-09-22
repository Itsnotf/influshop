package com.example.influshop

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.influshop.api.ApiService
import com.example.influshop.model.UserProfile
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val _profileData = MutableLiveData<UserProfile>()
    val profileData: LiveData<UserProfile> = _profileData
    private val apiService = ApiService()
    private val disposables = CompositeDisposable()

    fun fetchProfileData() {
        val email = PreferenceUtility.getString(getApplication(), "email", "")
        val disposable = apiService.profile(email)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ resultSingle ->
                // Extract the UserProfile from the ResultSingle
                val userProfile = resultSingle.data // Modify this based on your ResultSingle structure
                Log.e("result fetch",userProfile.toString())
                _profileData.postValue(userProfile)
            }, { error ->
                // Handle the error here
            })

        disposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear() // Dispose of all disposables when the ViewModel is cleared
    }
}
