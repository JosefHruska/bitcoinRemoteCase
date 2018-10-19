package cz.fatty.mannheim.extensions

import android.view.View
import cz.fatty.mannheim.App.Companion.app

/**
 * Extension methods related to UI components
 *
 * @author Josef Hruška (pepa.hruska@gmail.com)
 */

fun Int.toText(parameter: String? = null): String = if (parameter == null) {
    app().getString(this)
} else {
    app().getString(this, parameter)
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.onClick(onClick: (View) -> Unit) {
    setOnClickListener {
        onClick.invoke(it)
    }
}