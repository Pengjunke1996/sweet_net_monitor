package com.sweet.net_monitor.ui.sp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.sweet.net_monitor.utils.flowbus.core.postEvent
import com.sweet.net_monitor.MonitorHelper
import com.sweet.net_monitor.R
import com.sweet.net_monitor.databinding.DialogFragmentEditSpBinding
import com.sweet.net_monitor.enum.SPValueType
import com.sweet.net_monitor.event.RefreshSpEvent
import com.sweet.net_monitor.utils.showSoftInput

class SpEditDialogFragment : DialogFragment() {
    companion object {
        const val FILE_KEY = "FILE_KEY"
        const val KEY = "KEY"
        const val VALUE_TYPE = "VALUE_TYPE"
        fun showSafe(fileKey: String?, key: String?, valueType: String?,fragmentManager: FragmentManager) {
            try {
                SpEditDialogFragment().also {
                    it.arguments = Bundle().also {
                        it.putString(FILE_KEY, fileKey)
                        it.putString(KEY, key)
                        it.putString(VALUE_TYPE, valueType)
                    }
                }.show(fragmentManager,null)
            } catch (e: Exception) {
            }
        }
    }

    val key by lazy {
        arguments?.getString(KEY) ?: ""
    }

    val fileKey by lazy {
        arguments?.getString(FILE_KEY) ?: ""
    }

    val valueType by lazy {
        arguments?.getString(VALUE_TYPE) ?: ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogTheme)
    }

    val binding by lazy {
        DialogFragmentEditSpBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.etNum.showSoftInput()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvConfirm.setOnClickListener {
            val value = binding.etNum.text.toString()
            val realValue = when (valueType) {
                SPValueType.Int.value -> value.toIntOrNull()
                SPValueType.Double.value -> value.toDoubleOrNull()
                SPValueType.Long.value -> value.toLongOrNull()
                SPValueType.Float.value -> value.toFloatOrNull()
                SPValueType.Boolean.value -> value.toBoolean()
                else -> value
            }
            MonitorHelper.updateSpValue(fileKey, key, realValue)
            postEvent(RefreshSpEvent(),500)
            dismissAllowingStateLoss()
        }
    }
}