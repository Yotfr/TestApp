package com.yotfr.testapp.data.util

/**
 * [Cause] contains exception reasons
 */
sealed interface Cause {
    object UnknownException : Cause
}
