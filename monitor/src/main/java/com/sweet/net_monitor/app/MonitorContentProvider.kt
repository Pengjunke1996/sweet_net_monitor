package com.sweet.net_monitor.app

import android.app.Application
import android.content.ContentProvider
import android.content.ContentValues
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
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
            val intent = Intent(context, SpEditActivity::class.java)
            intent.action = Intent.ACTION_VIEW
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            shortcutManager?.dynamicShortcuts = listOf(
                ShortcutInfo.Builder(iContext.applicationContext, "sp")
                    .setShortLabel("Sp查看")
                    .setIntent(intent)
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