package com.line.connect

/**
 * created by chenliu on  2022/2/17 4:10 下午.
 */
interface WriteDataCallback {
    fun onSuccess()
    fun onFailed(t: Throwable)
}