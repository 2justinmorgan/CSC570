package com.example.read

import android.app.IntentService
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.IsoDep
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TimingLogger
import kotlinx.android.synthetic.main.activity_main.*
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T





class MainActivity : AppCompatActivity(), NfcAdapter.ReaderCallback {

    private var nfcAdapter: NfcAdapter? = null
    var mole: Mole? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        Thread(Runnable { mole = Mole("129.65.128.80", 5019)}).start()

    }

    public override fun onResume() {
        super.onResume()
        nfcAdapter?.enableReaderMode(this, this,
            NfcAdapter.FLAG_READER_NFC_A or
                    NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK,
            null)
    }
    public override fun onPause() {
        super.onPause()
        nfcAdapter?.disableReaderMode(this)
    }
    override fun onTagDiscovered(tag: Tag?) {

        val isoDep = IsoDep.get(tag)
        isoDep.connect()

        //get msg from server to transceive
        var data = mole?.getData()
        var response: ByteArray
        while (data!= null && data != "Exit") {
            response = isoDep.transceive(Utils.hexStringToByteArray(data))

            //put response in server
            mole?.sendData(Utils.toHex(response))
            data = mole?.getData()
        }
        mole?.close()
        isoDep.close()
    }
}
