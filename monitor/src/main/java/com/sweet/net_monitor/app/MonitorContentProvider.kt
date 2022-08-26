package com.sweet.net_monitor.app

import android.app.Application
import android.content.ContentProvider
import android.content.ContentValues
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.database.Cursor
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
import com.sweet.net_monitor.R
import com.sweet.net_monitor.ui.MonitorMainActivity
import com.sweet.net_monitor.utils.flowbus.FlowEventBus
import com.sweet.net_monitor.ui.sp.SpEditActivity

class MonitorContentProvider : ContentProvider() {

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun onCreate(): Boolean {
        val iContext = context ?: return false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            val shortcutManager = iContext.getSystemService(ShortcutManager::class.java)

            shortcutManager?.dynamicShortcuts = listOf(
                ShortcutInfo.Builder(iContext.applicationContext, "sp")
                    .setShortLabel("SP编辑")
                    .setIcon(Icon.createWithResource(iContext, R.drawable.icon_sp_save))
                    .setIntent(Intent(context, SpEditActivity::class.java).also {
                        it.action = Intent.ACTION_VIEW
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    })
                    .build(),
                ShortcutInfo.Builder(iContext.applicationContext, "net")
                    .setShortLabel("网络抓包")
                    .setIcon(Icon.createWithResource(iContext, R.drawable.icon_net_monitor))
                    .setIntent(Intent(context, MonitorMainActivity::class.java).also {
                        it.action = Intent.ACTION_VIEW
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    })
                    .build()
            )
        }
        FlowEventBus.init(iContext.applicationContext as Application)
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return null
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}