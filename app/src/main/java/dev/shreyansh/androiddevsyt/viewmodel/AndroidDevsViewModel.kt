package dev.shreyansh.androiddevsyt.viewmodel

import android.app.Application
import androidx.lifecycle.*
import dev.shreyansh.androiddevsyt.database.AndroidDevDatabase.Companion.getInstance
import dev.shreyansh.androiddevsyt.repository.AndroidDevsRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class AndroidDevsViewModel(application: Application) : AndroidViewModel(application) {

    enum class APIStatus { LOADING, DONE, FAILURE }

    private val database = getInstance(application)
    private val repository = AndroidDevsRepository(database)
    val playlist = repository.videos

    private val _loginComplete = MutableLiveData<Boolean>()
    val loginComplete: LiveData<Boolean>
        get() = _loginComplete

    private val _status = MutableLiveData<APIStatus>()
    val status: LiveData<APIStatus>
        get() = _status


    init {
        viewModelScope.launch {
            _status.postValue(APIStatus.LOADING)
            try {
                repository.refreshVideos()
                _status.postValue(APIStatus.DONE)
            } catch (e: Exception) {
                Timber.i("${e.message}")
                _status.postValue(APIStatus.FAILURE)
            }
        }
    }

    fun onLogin() {
        _loginComplete.value = true
    }

    fun onLoginComplete() {
        _loginComplete.value = false
    }

    fun onLoginCancel() {
        _loginComplete.value = false
    }
}
