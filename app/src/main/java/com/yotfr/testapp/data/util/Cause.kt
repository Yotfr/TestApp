package com.yotfr.testapp.data.util

sealed interface Cause {
    object UnknownException: Cause
}