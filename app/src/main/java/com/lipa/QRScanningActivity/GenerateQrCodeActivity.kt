package com.lipa.QRScanningActivity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.gson.Gson
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.lipa.R
import com.lipa.helper.EncryptionHelper
import com.lipa.helper.QRCodeHelper
import com.lipa.models.UserObject
import kotlinx.android.synthetic.main.activity_generate_qr_code.*

class GenerateQrCodeActivity : AppCompatActivity() {

    companion object {

        fun getGenerateQrCodeActivity(callingClassContext: Context) = Intent(callingClassContext, GenerateQrCodeActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_qr_code)
        generateQrCodeButton.setOnClickListener {
            if (checkEditText()) {
                hideKeyboard()
                val user = UserObject(ItemName = ItemNameEditText.text.toString(), ItemPrice = Integer.parseInt(ItemPriceEditText.text.toString()))
                val serializeString = Gson().toJson(user)
                val encryptedString = EncryptionHelper.getInstance().encryptionString(serializeString).encryptMsg()
                setImageBitmap(encryptedString)
            }
        }
    }

    private fun setImageBitmap(encryptedString: String?) {
        val bitmap = QRCodeHelper.newInstance(this).setContent(encryptedString).setErrorCorrectionLevel(ErrorCorrectionLevel.Q).setMargin(2).qrcOde
        qrCodeImageView.setImageBitmap(bitmap)
    }

    /**
     * Hides the soft input keyboard if it is shown to the screen.
     */

    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun checkEditText(): Boolean {
        if (TextUtils.isEmpty(ItemNameEditText.text.toString())) {
            Toast.makeText(this, "Item Name field cannot be empty!", Toast.LENGTH_SHORT).show()
            return false
        } else if (TextUtils.isEmpty(ItemPriceEditText.text.toString())) {
            Toast.makeText(this, "Item Price field cannot be empty!", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}
