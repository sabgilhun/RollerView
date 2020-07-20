package com.sabgil.roller.helper

fun findFirstItemIndex(diff: Int, calcItemStartPos: (Int) -> Int): Int {
    fun checkPreviousItem(): Int {
        var innerIndex = -1
        while (calcItemStartPos(innerIndex) > diff) {
            innerIndex--
        }
        return innerIndex + 1
    }

    fun checkNextItem(): Int {
        var innerIndex = 1
        while (calcItemStartPos(innerIndex) < diff) {
            innerIndex++
        }
        return innerIndex
    }

    return if (calcItemStartPos(0) > diff) {
        checkPreviousItem()
    } else {
        checkNextItem()
    }
}

fun findLastItemIndex(initIndex: Int, diff: Int, calcItemStartPos: (Int) -> Int): Int {
    var innerIndex = initIndex
    while (calcItemStartPos(innerIndex) < diff) {
        innerIndex++
    }
    return innerIndex - 1
}

fun coerceOrInset(limit: Int, actual: Int): Pair<Int, Int> =
    if (limit <= actual) {
        0 to limit
    } else {
        val insetStart = (limit - actual) / 2
        val insetEnd = limit - actual - insetStart
        insetStart to limit - insetEnd
    }

fun coerceLimit(limit: Int, actual: Int): Pair<Int, Int> =
    if (limit <= actual) {
        0 to limit
    } else {
        0 to actual
    }