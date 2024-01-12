package com.ktotiger.acrashjet

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.onesignal.OneSignal
import io.branch.referral.Branch

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
        OneSignal.promptForPushNotifications()
        Branch.getAutoInstance(this)
        Branch.enableLogging()
    }

    private val ONESIGNAL_APP_ID = "8915936b-e9a1-49cb-bfc8-6fded0e003fc"
}