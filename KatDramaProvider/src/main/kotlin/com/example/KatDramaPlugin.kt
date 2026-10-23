package com.example

import com.lagradost.cloudstream3.plugins.CloudStreamPlugin
import com.lagradost.cloudstream3.plugins.Plugin
import android.content.Context

@CloudStreamPlugin
class KatDramaPlugin: Plugin() {
    override fun load(context: Context) {
        registerMainAPI(KatDramaProvider())
    }
}
