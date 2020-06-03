package net.dobnikar.foregroundmanager

/**
 * Created by dejandobnikar on 19/04/2017.
 */
interface ForegroundListener {
    fun onForegroundStateChanged(inForeground: Boolean)
}
