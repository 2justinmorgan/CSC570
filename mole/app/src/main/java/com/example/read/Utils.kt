package com.example.read

import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class Utils {
    companion object {
        private val HEX_CHARS = "0123456789ABCDEF"
        fun hexStringToByteArray(data: String) : ByteArray {

            val result = ByteArray(data.length / 2)

            for (i in 0 until data.length step 2) {
                val firstIndex = HEX_CHARS.indexOf(data[i]);
                val secondIndex = HEX_CHARS.indexOf(data[i + 1]);

                val octet = firstIndex.shl(4).or(secondIndex)
                result.set(i.shr(1), octet.toByte())
            }

            return result
        }

        private val HEX_CHARS_ARRAY = "0123456789ABCDEF".toCharArray()
        fun toHex(byteArray: ByteArray) : String {
            val result = StringBuffer()
            byteArray.forEach {

                val octet = it.toInt()
                val firstIndex = (octet and 0xF0).ushr(4)
                val secondIndex = octet and 0x0F
                result.append(HEX_CHARS_ARRAY[firstIndex])
                result.append(HEX_CHARS_ARRAY[secondIndex])
            }

            return result.toString()
        }

        fun getFromServer(url: String) : String {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.setRequestMethod("GET")
            var inputStream = connection.getInputStream()
            while (inputStream == null) {
                inputStream = connection.getInputStream()
            }
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val line = bufferedReader.readLine()

            connection.disconnect()
            return line
        }

        fun postToServer(url: String, msg: String) {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.setRequestMethod("POST")
            val outputStream = connection.getOutputStream()
            val outputStreamWriter = OutputStreamWriter(outputStream)
            val bufferedWriter = BufferedWriter(outputStreamWriter)
            val line = bufferedWriter.write(msg)
            connection.disconnect()
        }
    }
}