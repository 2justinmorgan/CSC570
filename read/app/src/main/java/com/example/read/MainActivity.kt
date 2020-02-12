package com.example.read

import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.IsoDep
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TimingLogger
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NfcAdapter.ReaderCallback {

    private var nfcAdapter: NfcAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
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
        val timings = TimingLogger("TAG", "reader")

        val isoDep = IsoDep.get(tag)
        isoDep.connect()
        timings.addSplit("connected")
        val response = isoDep.transceive(Utils.hexStringToByteArray(
            "00A4040007A0000000031010"))
        timings.addSplit("response received")
        runOnUiThread { textView.append("\nCard Response: "
                + Utils.toHex(response) )}
        isoDep.close()
        timings.addSplit("connection closed")
        timings.dumpToLog()
    }
}
