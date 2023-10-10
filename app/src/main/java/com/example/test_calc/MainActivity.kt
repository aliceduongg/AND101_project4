package com.example.test_calc

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView

private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENT = 1
class MainActivity : AppCompatActivity() {
    private lateinit var editBaseAmount: EditText
    private lateinit var seekBarTip: SeekBar
    private lateinit var tagTipPercent: TextView
    private lateinit var screenTipAmount: TextView
    private lateinit var screenTotalAmount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editBaseAmount = findViewById(R.id.editBaseAmount)
        seekBarTip = findViewById(R.id.seekBarTip)
        tagTipPercent = findViewById(R.id.tagTipPercent)
        screenTipAmount = findViewById(R.id.screenTipAmount)
        screenTotalAmount = findViewById(R.id.screenTotalAmount)

        seekBarTip.progress = INITIAL_TIP_PERCENT
        tagTipPercent.text = "$INITIAL_TIP_PERCENT%"
        seekBarTip.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                Log.i(TAG, "onProgressChanged $p1")
                tagTipPercent.text="$p1%"
                computeTipAndTotal()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}

        })
        editBaseAmount.addTextChangedListener(object:TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "afterTextChanged $s")
                computeTipAndTotal()
            }

        })
    }
    private fun computeTipAndTotal() {
        // 1. Indicate the amount of base & tip %
        val baseAmount = editBaseAmount.text.toString().toDouble()
        val tipPercent = seekBarTip.progress
        // 2. Calc tip & total amount
        val tipAmount = baseAmount * tipPercent / 100
        val totalAmount = baseAmount + tipAmount
        //3. UI performance
        screenTipAmount.text = "%.2f".format(tipAmount)
        screenTotalAmount.text=  "%.2f".format(totalAmount)
    }
}