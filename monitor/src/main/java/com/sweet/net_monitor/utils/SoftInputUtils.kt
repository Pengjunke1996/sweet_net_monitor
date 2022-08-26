/*
 * Copyright (C) 2018 Drake, https://github.com/liangjingkanji
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sweet.net_monitor.utils


import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.math.max
import kotlin.math.min


/**
 * 软键盘弹出后要求指定视图[float]悬浮在软键盘之上
 * 本方法重复调用会互相覆盖, 例如Fragment调用会覆盖其Activity的调用
 *
 * Api21以上本方法使用[WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING]
 * Api21下调用无效
 *
 * @param float 需要悬浮在软键盘之上的视图
 * @param transition 当软键盘显示隐藏时需要移动的视图, 使用[View.setTranslationY]移动
 * @param editText 指定的视图存在焦点才触发软键盘监听, null则全部视图都触发
 * @param margin 悬浮视图和软键盘间距
 * @param setPadding 使用[View.setPadding]来移动[transition]视图, 让可滚动视图自动收缩, 而不是向上偏移[View.setTranslationY]
 * @param onChanged 监听软键盘是否显示
 *
 * @see getSoftInputHeight 软键盘高度
 */
@JvmOverloads
fun Activity.setWindowSoftInput(
    float: View? = null,
    transition: View? = float?.parent as? View,
    editText: View? = null,
    margin: Int = 0,
    setPadding: Boolean = false,
    onChanged: (() -> Unit)? = null,
) = window.setWindowSoftInput(float, transition, editText, margin, setPadding, onChanged)

/**
 * 如果Fragment不是立即创建, 请为Fragment所在的Activity配置[[WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING]]
 *
 * 软键盘弹出后要求指定视图[float]悬浮在软键盘之上
 * 本方法重复调用会互相覆盖, 例如Fragment调用会覆盖其Activity的调用
 *
 * Api21以上本方法使用[WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING]
 * Api21下调用无效
 *
 * @param float 需要悬浮在软键盘之上的视图
 * @param transition 当软键盘显示隐藏时需要移动的视图, 使用[View.setTranslationY]移动
 * @param editText 指定的视图存在焦点才触发软键盘监听, null则全部视图都触发
 * @param margin 悬浮视图和软键盘间距
 * @param setPadding 使用[View.setPadding]来移动[transition]视图, 让可滚动视图自动收缩, 而不是向上偏移[View.setTranslationY]
 * @param onChanged 监听软键盘是否显示
 *
 * @see getSoftInputHeight 软键盘高度
 */
@JvmOverloads
fun Fragment.setWindowSoftInput(
    float: View? = null,
    transition: View? = view,
    editText: View? = null,
    margin: Int = 0,
    setPadding: Boolean = false,
    onChanged: (() -> Unit)? = null,
) = requireActivity().window.setWindowSoftInput(
    float,
    transition,
    editText,
    margin,
    setPadding,
    onChanged
)

/**
 * 软键盘弹出后要求指定视图[float]悬浮在软键盘之上
 * 本方法重复调用会互相覆盖, 例如Fragment调用会覆盖其Activity的调用
 *
 * Api21以上本方法使用[WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING]
 * Api21下调用无效
 *
 * @param float 需要悬浮在软键盘之上的视图
 * @param transition 当软键盘显示隐藏时需要移动的视图, 使用[View.setTranslationY]移动
 * @param editText 指定的视图存在焦点才触发软键盘监听, null则全部视图都触发
 * @param margin 悬浮视图和软键盘间距
 * @param setPadding 使用[View.setPadding]来移动[transition]视图, 让可滚动视图自动收缩, 而不是向上偏移[View.setTranslationY]
 * @param onChanged 监听软键盘是否显示
 *
 * @see getSoftInputHeight 软键盘高度
 */
@JvmOverloads
fun DialogFragment.setWindowSoftInput(
    float: View? = null,
    transition: View? = view,
    editText: View? = null,
    margin: Int = 0,
    setPadding: Boolean = false,
    onChanged: (() -> Unit)? = null,
) = dialog?.window?.setWindowSoftInput(float, transition, editText, margin, setPadding, onChanged)

/**
 * 软键盘弹出后要求指定视图[float]悬浮在软键盘之上
 * 本方法重复调用会互相覆盖, 例如Fragment调用会覆盖其Activity的调用
 *
 * Api21以上本方法使用[WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING]
 * Api21下调用无效
 *
 * @param float 需要悬浮在软键盘之上的视图
 * @param transition 当软键盘显示隐藏时需要移动的视图, 使用[View.setTranslationY]移动
 * @param editText 指定的视图存在焦点才触发软键盘监听, null则全部视图都触发
 * @param margin 悬浮视图和软键盘间距
 * @param setPadding 使用[View.setPadding]来移动[transition]视图, 让可滚动视图自动收缩, 而不是向上偏移[View.setTranslationY]
 * @param onChanged 监听软键盘是否显示
 *
 * @see getSoftInputHeight 软键盘高度
 */
@JvmOverloads
fun BottomSheetDialogFragment.setWindowSoftInput(
    float: View? = null,
    transition: View? = dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet),
    editText: View? = null,
    margin: Int = 0,
    setPadding: Boolean = false,
    onChanged: (() -> Unit)? = null,
) = dialog?.window?.setWindowSoftInput(float, transition, editText, margin, setPadding, onChanged)

/**
 * 软键盘弹出后要求指定视图[float]悬浮在软键盘之上
 * 本方法重复调用会互相覆盖, 例如Fragment调用会覆盖其Activity的调用
 *
 * Api21以上本方法使用[WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING]
 * Api21下调用无效
 *
 * @param float 需要悬浮在软键盘之上的视图
 * @param transition 当软键盘显示隐藏时需要移动的视图, 使用[View.setTranslationY]移动
 * @param editText 指定的视图存在焦点才触发软键盘监听, null则全部视图都触发
 * @param margin 悬浮视图和软键盘间距
 * @param setPadding 使用[View.setPadding]来移动[transition]视图, 让可滚动视图自动收缩, 而不是向上偏移[View.setTranslationY]
 * @param onChanged 监听软键盘是否显示
 *
 * @see getSoftInputHeight 软键盘高度
 */
@JvmOverloads
fun Dialog.setWindowSoftInput(
    float: View? = null,
    transition: View? = window?.decorView,
    editText: View? = null,
    margin: Int = 0,
    setPadding: Boolean = false,
    onChanged: (() -> Unit)? = null,
) = window?.setWindowSoftInput(float, transition, editText, margin, setPadding, onChanged)

@JvmOverloads
fun Dialog.setWindowSoftInputForever(
    transition: View? = window?.decorView,
    editText: View? = null,
    margin: Int = 0,
    setPadding: Boolean = false,
    onChanged: (() -> Unit)? = null,
) = run {
    setOnDismissListener {
        if (setPadding) {
            transition?.setPadding(0, 0, 0, 0)
        } else {
            transition?.translationY = 0f
        }
    }
    window?.setWindowSoftInputForever(transition, editText, margin, setPadding, onChanged)
}


/**
 * 软键盘弹出后要求指定视图[float]悬浮在软键盘之上
 * 本方法重复调用会互相覆盖, 例如Fragment调用会覆盖其Activity的调用
 *
 * Api21以上本方法使用[WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING]
 * Api21下调用无效
 *
 * @param float 需要悬浮在软键盘之上的视图
 * @param transition 当软键盘显示隐藏时需要移动的视图, 使用[View.setTranslationY]移动
 * @param editText 指定的视图存在焦点才触发软键盘监听, null则全部视图都触发
 * @param margin 悬浮视图和软键盘间距
 * @param setPadding 使用[View.setPadding]来移动[transition]视图, 让可滚动视图自动收缩, 而不是向上偏移[View.setTranslationY]
 * @param onChanged 监听软键盘是否显示
 *
 * @see getSoftInputHeight 软键盘高度
 */
@JvmOverloads
fun Window.setWindowSoftInput(
    float: View? = null,
    transition: View? = null,
    editText: View? = null,
    margin: Int = 0,
    setPadding: Boolean = false,
    onChanged: (() -> Unit)? = null,
) {
    // Api21下不需要用户体验, 直接不适配
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return
    // 部分系统不支持WindowInsets使用兼容方案处理
    if (!decorView.isSystemInsetsAnimationSupport()) {
        return setWindowSoftInputCompatible(float, transition, editText, margin, onChanged)
    }
    setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
    var matchEditText = false
    var hasSoftInput = false
    var floatInitialBottom = 0
    var startAnimation: WindowInsetsAnimationCompat? = null
    var transitionY = 0f
    val callback =
        object : WindowInsetsAnimationCompat.Callback(DISPATCH_MODE_CONTINUE_ON_SUBTREE) {

            override fun onStart(
                animation: WindowInsetsAnimationCompat,
                bounds: WindowInsetsAnimationCompat.BoundsCompat
            ): WindowInsetsAnimationCompat.BoundsCompat {
                if (float == null || transition == null) return bounds
                hasSoftInput = ViewCompat.getRootWindowInsets(decorView)
                    ?.isVisible(WindowInsetsCompat.Type.ime()) ?: false
                startAnimation = animation
                if (hasSoftInput) matchEditText = editText == null || editText.hasFocus()
                if (hasSoftInput) {
                    floatInitialBottom = run {
                        val r = IntArray(2)
                        float.getLocationInWindow(r)
                        r[1] + float.height
                    }
                }
                return bounds
            }

            override fun onEnd(animation: WindowInsetsAnimationCompat) {
                super.onEnd(animation)
                if (matchEditText) onChanged?.invoke()
            }

            override fun onProgress(
                insets: WindowInsetsCompat,
                runningAnimations: MutableList<WindowInsetsAnimationCompat>
            ): WindowInsetsCompat {
                val fraction = startAnimation?.fraction
                if (fraction == null || float == null || transition == null || !matchEditText) return insets
                val softInputHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
                val softInputTop = decorView.bottom - softInputHeight
                if (hasSoftInput && softInputTop < floatInitialBottom) {
                    val offset = (softInputTop - floatInitialBottom - margin).toFloat()
                    if (setPadding) {
                        transition.setPadding(0, 0, 0, -offset.toInt())
                        transitionY = -offset
                    } else {
                        transition.translationY = offset
                        transitionY = offset
                    }
                } else if (!hasSoftInput) {
                    if (setPadding) {
                        transition.setPadding(
                            0,
                            0,
                            0,
                            max((transitionY - transitionY * (fraction + 0.5f)), 0f).toInt()
                        )
                    } else {
                        transition.translationY =
                            min(transitionY - transitionY * (fraction + 0.5f), 0f)
                    }
                }
                return insets
            }
        }
    ViewCompat.setWindowInsetsAnimationCallback(decorView, callback)
}

/** 部分系统不支持WindowInsets使用兼容方案处理 */
private fun Window.setWindowSoftInputCompatible(
    float: View? = null,
    transition: View? = float?.parent as? View,
    editText: View? = null,
    margin: Int = 0,
    onChanged: (() -> Unit)? = null,
) {
    setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    var shown = false
    var matchEditText = false
    decorView.viewTreeObserver.addOnGlobalLayoutListener {
        val canTransition = float != null && transition != null
        val floatBottom = if (canTransition) {
            val r = IntArray(2)
            float!!.getLocationInWindow(r)
            r[1] + float.height
        } else 0
        val decorBottom = decorView.bottom
        val rootWindowInsets =
            ViewCompat.getRootWindowInsets(decorView) ?: return@addOnGlobalLayoutListener
        val softInputHeight = rootWindowInsets.getInsets(WindowInsetsCompat.Type.ime()).bottom
        val hasSoftInput = rootWindowInsets.isVisible(WindowInsetsCompat.Type.ime())
        val offset = (decorBottom - floatBottom - softInputHeight - margin).toFloat()
        if (hasSoftInput) {
            matchEditText = editText == null || editText.hasFocus()
            if (!shown && matchEditText) {
                transition?.translationY = offset
                onChanged?.invoke()
            }
            shown = true
        } else {
            if (shown && matchEditText) {
                transition?.translationY = 0f
                onChanged?.invoke()
            }
            shown = false
        }
    }
}

/** 判断系统是否支持[WindowInsetsAnimationCompat] */
internal fun View.isSystemInsetsAnimationSupport(): Boolean {
    val windowInsetsController = ViewCompat.getWindowInsetsController(this)
    return !(windowInsetsController == null || windowInsetsController.systemBarsBehavior == 0)
}

//<editor-fold desc="显示隐藏软键盘">

/**
 * 弹出软键盘
 * 如果要求页面显示立刻弹出软键盘，建议在onResume方法中调用
 */
fun EditText.showSoftInput() {
    isFocusable = true
    isFocusableInTouchMode = true
    requestFocus()
    if (isSystemInsetsAnimationSupport()) {
        ViewCompat.getWindowInsetsController(this)?.show(WindowInsetsCompat.Type.ime())
    } else {
        postDelayed({
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(this, 0)
        }, 300)
    }
}

/** 隐藏软键盘 */
fun Activity.hideSoftInput() {
    currentFocus?.let {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(it.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    } ?: let {
        ViewCompat.getWindowInsetsController(window.decorView)?.hide(WindowInsetsCompat.Type.ime())
    }
}

/** 隐藏软键盘 */
fun Fragment.hideSoftInput() = requireActivity().hideSoftInput()

/** 隐藏软键盘 */
fun EditText.hideSoftInput() {
    ViewCompat.getWindowInsetsController(this)?.hide(WindowInsetsCompat.Type.ime())
}
//</editor-fold>


//<editor-fold desc="软键盘属性">

/** 软键盘是否显示 */
fun Activity.hasSoftInput(): Boolean {
    return ViewCompat.getRootWindowInsets(window.decorView)
        ?.isVisible(WindowInsetsCompat.Type.ime()) ?: false
}

/** 软键盘是否显示 */
fun Fragment.hasSoftInput(): Boolean {
    return requireActivity().hasSoftInput()
}

/** 当前软键盘显示高度 */
fun Activity.getSoftInputHeight(): Int {
    val softInputHeight = ViewCompat.getRootWindowInsets(window.decorView)
        ?.getInsets(WindowInsetsCompat.Type.ime())?.bottom
    return softInputHeight ?: 0
}

/** 当前软键盘显示高度 */
fun Fragment.getSoftInputHeight(): Int {
    return requireActivity().getSoftInputHeight()
}
//</editor-fold>


/**
 * 软键盘弹出后要求指定视图[float]悬浮在软键盘之上
 * 本方法重复调用会互相覆盖, 例如Fragment调用会覆盖其Activity的调用
 *
 * Api21以上本方法使用[WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING]
 * Api21下调用无效
 *
 * @param float 需要悬浮在软键盘之上的视图
 * @param transition 当软键盘显示隐藏时需要移动的视图, 使用[View.setTranslationY]移动
 * @param editText 指定的视图存在焦点才触发软键盘监听, null则全部视图都触发
 * @param margin 悬浮视图和软键盘间距
 * @param setPadding 使用[View.setPadding]来移动[transition]视图, 让可滚动视图自动收缩, 而不是向上偏移[View.setTranslationY]
 * @param onChanged 监听软键盘是否显示
 *
 * @see getSoftInputHeight 软键盘高度
 */
@JvmOverloads
fun Window.setWindowSoftInputForever(
    transition: View? = null,
    editText: View? = null,
    margin: Int = 0,
    setPadding: Boolean = false,
    onChanged: (() -> Unit)? = null,
) {
    // Api21下不需要用户体验, 直接不适配
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return
    // 部分系统不支持WindowInsets使用兼容方案处理
    if (!decorView.isSystemInsetsAnimationSupport()) {
        return setWindowSoftInputCompatibleForever(transition, editText, margin, onChanged)
    }
    setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
    var matchEditText = false
    var hasSoftInput = false
    var floatInitialBottom = 0
    var startAnimation: WindowInsetsAnimationCompat? = null
    var transitionY = 0f
    val callback =
        object : WindowInsetsAnimationCompat.Callback(DISPATCH_MODE_CONTINUE_ON_SUBTREE) {

            override fun onStart(
                animation: WindowInsetsAnimationCompat,
                bounds: WindowInsetsAnimationCompat.BoundsCompat
            ): WindowInsetsAnimationCompat.BoundsCompat {
                if (transition == null) return bounds
                hasSoftInput = ViewCompat.getRootWindowInsets(decorView)
                    ?.isVisible(WindowInsetsCompat.Type.ime()) ?: false
                startAnimation = animation
                if (hasSoftInput) matchEditText = editText == null || editText.hasFocus()
                if (hasSoftInput) {
                }
                return bounds
            }

            override fun onEnd(animation: WindowInsetsAnimationCompat) {
                super.onEnd(animation)
                if (matchEditText) onChanged?.invoke()
            }

            override fun onProgress(
                insets: WindowInsetsCompat,
                runningAnimations: MutableList<WindowInsetsAnimationCompat>
            ): WindowInsetsCompat {
                val fraction = startAnimation?.fraction
                if (fraction == null || transition == null || !matchEditText) return insets
                val softInputHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
                val softInputTop = decorView.bottom - softInputHeight
                if (hasSoftInput) {
                    val offset = -(softInputTop - margin).toFloat()
                    if (setPadding) {
                        transition.setPadding(0, 0, 0, -offset.toInt())
                        transitionY = -offset
                    } else {
                        transition.translationY = offset
                        transitionY = offset
                    }
                } else if (!hasSoftInput) {
                    if (setPadding) {
                        transition.setPadding(
                            0,
                            0,
                            0,
                            max((transitionY - transitionY * (fraction + 0.5f)), 0f).toInt()
                        )
                    } else {
                        transition.translationY =
                            min(transitionY - transitionY * (fraction + 0.5f), 0f)
                    }
                }
                return insets
            }
        }
    ViewCompat.setWindowInsetsAnimationCallback(decorView, callback)
}

/** 部分系统不支持WindowInsets使用兼容方案处理 */
private fun Window.setWindowSoftInputCompatibleForever(
    transition: View? = null,
    editText: View? = null,
    margin: Int = 0,
    onChanged: (() -> Unit)? = null,
) {
    setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    var shown = false
    var matchEditText = false
    decorView.viewTreeObserver.addOnGlobalLayoutListener {
        val canTransition = transition != null
        val floatBottom = 0
        val decorBottom = decorView.bottom
        val rootWindowInsets =
            ViewCompat.getRootWindowInsets(decorView) ?: return@addOnGlobalLayoutListener
        val softInputHeight = rootWindowInsets.getInsets(WindowInsetsCompat.Type.ime()).bottom
        val hasSoftInput = rootWindowInsets.isVisible(WindowInsetsCompat.Type.ime())
        val offset = (decorBottom - floatBottom - softInputHeight - margin).toFloat()
        if (hasSoftInput) {
            matchEditText = editText == null || editText.hasFocus()
            if (!shown && matchEditText) {
                transition?.translationY = offset
                onChanged?.invoke()
            }
            shown = true
        } else {
            if (shown && matchEditText) {
                transition?.translationY = 0f
                onChanged?.invoke()
            }
            shown = false
        }
    }
}