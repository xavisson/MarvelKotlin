package com.xavisson.marvel.data.utils

import com.xavisson.marvel.data.BuildConfig
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

fun getHash(timestamp: String): String {

    var pass = timestamp + BuildConfig.PrivateApiKey + BuildConfig.PublicApiKey
    var hash = ""
    val mdEnc: MessageDigest
    try {
        mdEnc = MessageDigest.getInstance("MD5")
        mdEnc.update(pass.toByteArray(), 0, pass.length)
        pass = BigInteger(1, mdEnc.digest()).toString(16)
        while (pass.length < 32) {
            pass = "0$pass"
        }
        hash = pass
    } catch (exception: NoSuchAlgorithmException) {
        exception.printStackTrace()
    }

    return hash
}
