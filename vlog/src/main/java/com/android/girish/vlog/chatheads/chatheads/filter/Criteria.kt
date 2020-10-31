package com.android.girish.vlog.chatheads.chatheads.filter

interface Criteria<T> {
    fun meetCriteria(input: List<T>): List<T>
    fun reset()
}
