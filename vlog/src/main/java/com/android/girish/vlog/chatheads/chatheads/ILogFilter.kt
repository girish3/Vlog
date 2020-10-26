package com.android.girish.vlog.chatheads.chatheads

interface ILogFilter<T> {
    fun filter(input: List<T>): List<T>
}