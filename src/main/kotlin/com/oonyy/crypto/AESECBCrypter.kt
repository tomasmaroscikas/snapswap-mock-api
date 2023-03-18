package com.oonyy.crypto

import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.SecretKeySpec

class AESECBCrypter {

    companion object {

        const val ENCRYPTION_STANDARD = "AES"
        const val KEY_SIZE = 128

        val cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING")

        fun encrypt(aes128KeyData: ByteArray, dataToEncrypt: String): ByteArray {
            val aesKey = SecretKeySpec(aes128KeyData, ENCRYPTION_STANDARD)
            cipher.init(Cipher.ENCRYPT_MODE, aesKey)
            return cipher.doFinal(dataToEncrypt.toByteArray(Charsets.UTF_8))
        }
    }
}