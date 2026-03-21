package com.example.app

import android.app.Application
import com.appdimens.dynamic.core.DimenCache

class InitializeApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        /*
        * EN Initialize the AppDimens cache.
        * Initializes the cache persistence setting to avoid recalculations when opening the app after the first launch.
        *
        * PT Inicializa o cache AppDimens.
        * Inicializa a configuração de persistência do cache para evitar recalculações quando abrir o aplicativo após o primeiro lançamento.*/
        DimenCache.init(this)
    }
}