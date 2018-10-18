package cz.fatty.mannheim.extensions

import android.animation.Animator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import cz.fatty.mannheim.App.Companion.app
import org.jetbrains.anko.displayMetrics
import org.jetbrains.anko.inputMethodManager
import org.jetbrains.anko.layoutInflater

/**
 * Extension methods related to UI components
 *
 * @author Josef Hruška (pepa.hruska@gmail.com)
 */

// Method for inflating root layout resources of custom views.
fun ViewGroup.inflateCustomViewContent(@LayoutRes layoutRes: Int) {
    this.context.layoutInflater.inflate(layoutRes, this, true)
}

// Method for inflating other views.
fun Context.inflate(@LayoutRes layoutRes: Int, rootViewGroup: ViewGroup? = null): View =
    layoutInflater.inflate(layoutRes, rootViewGroup)

fun Activity.showKeyboard() {
    if (this.currentFocus != null) {
        val inputMethodManager = inputMethodManager
        inputMethodManager.showSoftInput(currentFocus, 0)
    }
}

fun Activity.hideKeyboard(clearFocus: Boolean = false) {
    if (currentFocus != null) {
        val inputMethodManager = inputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        if (clearFocus) {
            currentFocus.clearFocus()
        }
    }
}

/**
 * Converts color resource to drawable.
 */
fun Int.toDrawable() = ContextCompat.getDrawable(app(), this)

/**
 * Converts color resource to int color.
 */
fun Int.toColor() = ContextCompat.getColor(app(), this)

/**
 * Converts hexacode to int color.
 */
fun String.toColor() = Color.parseColor(this)

/**
 * Converts string resource to text.
 */
fun Int.toText(parameter: String? = null): String = if (parameter == null) {
    app().getString(this)
} else {
    app().getString(this, parameter)
}

fun Int.dpToPx() = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), app().displayMetrics
).toInt()

fun View.hide() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.setHardwareAccelerated(): View {
    return this.apply {
        if (!isHardwareAccelerated) {
            setLayerType(View.LAYER_TYPE_HARDWARE, null)
        }
    }
}

fun View.onGlobalLayout(callback: () -> Unit) {
    val thiz = this
    thiz.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            thiz.viewTreeObserver.removeOnGlobalLayoutListener(this)
            callback()
        }
    })
}

fun View.visibleHeight(): Int {
    var h = 0
    if (this.visibility == View.VISIBLE || this.visibility == View.INVISIBLE) {
        h += this.height
        val lp = this.layoutParams
        if (lp is ViewGroup.MarginLayoutParams) {
            h += lp.topMargin + lp.bottomMargin
        }
    }
    return h
}

fun ViewGroup.visibleHeight(): Int {
    return (this as View).visibleHeight()
}

fun Int.toArray() = app().resources.getStringArray(this)

fun Context.getDrawable(@DrawableRes drawableIdRes: Int): Drawable? =
    ContextCompat.getDrawable(this, drawableIdRes)

fun View.makeInvisible() {
    visibility = View.INVISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.showAnim(dur: Long = 200, end: () -> Unit = {}): ValueAnimator {
    return ValueAnimator.ofFloat(this.alpha, 1.0f).apply {
        duration = dur
        addUpdateListener {
            this@showAnim.alpha = (it.animatedValue as Float)
        }
        addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                end()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }
        })
        start()
    }
}

fun View.onClick(onClick: (View) -> Unit) {
    setOnClickListener {
        onClick.invoke(it)
    }
}

fun WebView.onURLLoaded(onLoaded: () -> Unit) {
    webViewClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView, url: String) {
            onLoaded()
        }
    }
}