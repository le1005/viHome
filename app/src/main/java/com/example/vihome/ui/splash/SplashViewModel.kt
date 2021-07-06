package com.example.vihome.ui.splash

import android.os.Handler
import android.os.Looper
import androidx.hilt.lifecycle.ViewModelInject
import com.example.core.base.BaseViewModel
import com.example.core.utils.SingleLiveEvent

const val DURATION_SPLASH = 1000L

class SplashViewModel @ViewModelInject constructor() : BaseViewModel() {

    val actionSPlash = SingleLiveEvent<SplashActionState>()

    private val handler = Handler(Looper.getMainLooper())

    private val runnable = Runnable {
        actionSPlash.value = SplashActionState.Finish
    }

    init {
        handler.postDelayed(runnable, DURATION_SPLASH)
    }

    override fun onCleared() {
        handler.removeCallbacks(runnable)
        super.onCleared()
    }
}

sealed class SplashActionState {
    object Finish : SplashActionState()
}