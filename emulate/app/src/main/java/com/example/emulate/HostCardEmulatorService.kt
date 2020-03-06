package com.example.emulate
import android.nfc.cardemulation.HostApduService
import android.os.Bundle
import android.util.Log


class HostCardEmulatorService: HostApduService() {

    companion object {
        val TAG = "Host Card Emulator"
        val STATUS_SUCCESS = "9000"
        val STATUS_FAILED = "6F00"
        val CLA_NOT_SUPPORTED = "6E00"
        val INS_NOT_SUPPORTED = "6D00"
        val AID = "A0000000031010"
        val SELECT_INS = "A4"
        val DEFAULT_CLA = "00"
        val MIN_APDU_LENGTH = 12
    }

    override fun onDeactivated(reason: Int) {
        Log.d(TAG, "Deactivated: " + reason)
        //close connection
        //val proxy = (this.getApplication() as RunProxy).proxy
        //val t = Thread(Runnable { proxy?.sendData("Exit") })
        //t.start()
        //t.join()
    }

    override fun processCommandApdu(commandApdu: ByteArray?,
                                    extras: Bundle?): ByteArray {

        if (commandApdu == null) {
            return Utils.hexStringToByteArray(STATUS_FAILED)
        }

        val proxy = (this.getApplication() as RunProxy).proxy
        val hexCommandApdu = Utils.toHex(commandApdu)
        var response = ""

        if (proxy != null) {
            //put command apdu in server
            var t = Thread(Runnable { proxy.sendData(hexCommandApdu) })
            t.start()
            t.join()
            //return response from server in result
            t = Thread(Runnable {response = (proxy.getData()) })
            t.start()
            t.join()
        }
        if (response == "") {
            return Utils.hexStringToByteArray(STATUS_FAILED)
        }
        return Utils.hexStringToByteArray(response)
    }
}