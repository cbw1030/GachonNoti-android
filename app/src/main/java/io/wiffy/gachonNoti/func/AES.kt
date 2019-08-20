package io.wiffy.gachonNoti.func

import android.util.Base64
import java.lang.Exception
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

@Throws(Exception::class)
fun decrypt(text: String, key: String): String {
    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    val keyBytes = ByteArray(16)
    val b = key.toByteArray(charset("UTF-8"))
    var len = b.size
    if (len > keyBytes.size) len = keyBytes.size
    System.arraycopy(b, 0, keyBytes, 0, len)
    val keySpec = SecretKeySpec(keyBytes, "AES")
    val ivSpec = IvParameterSpec(keyBytes)
    cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
    val results = cipher.doFinal(Base64.decode(text, 0))
    return String(results, Charsets.UTF_8)
}

@Throws(Exception::class)
fun encrypt(text: String, key: String): String {
    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    val keyBytes = ByteArray(16)
    val b = key.toByteArray(charset("UTF-8"))
    var len = b.size
    if (len > keyBytes.size) len = keyBytes.size
    System.arraycopy(b, 0, keyBytes, 0, len)
    val keySpec = SecretKeySpec(keyBytes, "AES")
    val ivSpec = IvParameterSpec(keyBytes)
    cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
    val results = cipher.doFinal(text.toByteArray(charset("UTF-8")))
    return Base64.encodeToString(results, 0)
}