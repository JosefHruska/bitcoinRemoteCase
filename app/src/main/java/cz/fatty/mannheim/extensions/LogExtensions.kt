package cz.fatty.mannheim.extensions

import android.util.Log

/**
 * Log extensions
 *
 * @author Josef Hruška
 */

fun Any.ld(message: String, tag: String? = null) {
    Log.d(tag ?: javaClass.simpleName, message)
}

fun Any.lw(message: String, tag: String? = null) {
    Log.w(tag ?: javaClass.simpleName, message)
}

fun Any.le(message: String, tag: String? = null) {
    Log.e(tag ?: javaClass.simpleName, message)
}
