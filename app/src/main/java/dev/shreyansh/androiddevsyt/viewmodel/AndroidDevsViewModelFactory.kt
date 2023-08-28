package dev.shreyansh.androiddevsyt.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


//class AndroidDevsViewModelFactory(val app: Application) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(AndroidDevsViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return AndroidDevsViewModel(app) as T
//        }
//        throw IllegalArgumentException("Unable to construct viewmodel")
//    }
//}