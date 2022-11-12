package com.tbdeveloper.warikanapp.feature_roulette.utils

import java.security.MessageDigest
import java.util.*
import kotlin.math.abs

/**
 * 現在時刻のstringを取得
 *
 * @return 現在時刻のstring
 */
fun getCalendarStr(): String {
    return Calendar.getInstance().time.toString()
}

/**
 * 文字列をMD5でハッシュ化し，ハッシュ値を10進数に変換
 *
 * @param original ハッシュ化したい文字列
 * @return 10進ハッシュ値
 */
fun getMD5HashInt(original: String): Int {
    val md = MessageDigest.getInstance("MD5")
    return md.digest(original.toByteArray()).toHexInt()
}

/**
 * バイトコードを10進数に変換
 *
 * @return バイトコードの10進数
 */
fun ByteArray.toHexInt(): Int {
    var result = 0
    for (i in 0..3) {
        result = result shl 8
        result = result or this[i].toUByte().toInt()
    }
    return abs(result)
}
