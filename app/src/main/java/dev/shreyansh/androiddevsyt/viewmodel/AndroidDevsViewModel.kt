package dev.shreyansh.androiddevsyt.viewmodel

import android.app.Application
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.shreyansh.androiddevsyt.repository.AndroidDevsRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AndroidDevsViewModel @Inject constructor(androidDevsRepository: AndroidDevsRepository) : ViewModel() {

    enum class APIStatus { LOADING, DONE, FAILURE }

    val playlist = androidDevsRepository.videos

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
                androidDevsRepository.refreshVideos()
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
