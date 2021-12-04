package hozorghiyab.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hozorghiyab.R
import hozorghiyab.cityDetail.LoadData
import hozorghiyab.cityDetail.UrlEncoderClass
import hozorghiyab.user_info.Login_helper
import hozorghiyab.user_info.Login_helper_by_phone_number
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.verification_code.*

class VerificationCode : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.verification_code)
        val randomNumber:String = intent.getStringExtra("randomNumber").toString()
        val phone:String = intent.getStringExtra("phone").toString()

        etVcode1.requestFocus()
        txPhoneNumber.append(" " + phone + ".")
        //Toast.makeText(this, randomNumber.toString(), Toast.LENGTH_SHORT).show()

        //LoadData.sendVerificationCode(this, phone, randomNumber)

        etVcode1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                if (etVcode1.getText().toString().length == 1) {
                    etVcode2.requestFocus()
                } else if (etVcode1.getText().toString().length == 0) {
                    etVcode1.requestFocus();
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        etVcode2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (etVcode2.getText().toString().length == 1) {
                    etVcode3.requestFocus()
                } else if (etVcode2.getText().toString().length == 0) {
                    etVcode1.requestFocus();
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        etVcode3.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                if (etVcode3.getText().toString().length == 1) {
                    etVcode4.requestFocus()
                } else if (etVcode3.getText().toString().length == 0) {
                    etVcode2.requestFocus();
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        etVcode4.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (etVcode4.getText().toString().length == 1) {
                    etVcode5.requestFocus()
                } else if (etVcode4.getText().toString().length == 0) {
                    etVcode3.requestFocus();
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })


        etVcode5.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                if (etVcode5.getText().toString().length == 0) {
                    etVcode4.requestFocus();
                }


                var CompleteCode = etVcode1.text.toString() + etVcode2.text.toString() +
                        etVcode3.text.toString() + etVcode4.text.toString() + etVcode5.text.toString()

                if (CompleteCode!!.length == 5) {

                }

                if (randomNumber == randomNumber) {


                    val usernameOrEmail = phone
                    //val password: String = "0371456231"
                    if (usernameOrEmail.length <= 0 || usernameOrEmail == null) {
                        Toast.makeText(this@VerificationCode, "لطفا همه فیلد ها را تکمیل نمایید", Toast.LENGTH_SHORT).show()
                    } else {

                        var userNameEncode = UrlEncoderClass.urlEncoder(usernameOrEmail)
                        //var passwordEncode = UrlEncoderClass.urlEncoder(password)

                        val url = "https://hozori.ir/android/load_data.php?action=login_karkonan&karkon_id=$userNameEncode"
                        Login_helper_by_phone_number(this@VerificationCode, url, usernameOrEmail, usernameOrEmail).execute()
                    }

                    /* val sharedPreferences: SharedPreferences = this@VerificationCode.getSharedPreferences("file", MODE_PRIVATE)
                     val editor = sharedPreferences.edit()
                     editor.putString("user", phone)
                     editor.putString("pass", "0371456231")
                     editor.putString("name", "مهدی")
                     editor.putString("noe", "karkonan")
                     editor.commit()

                     val intent = Intent(this@VerificationCode, MainActivity::class.java)
                     intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                     startActivity(intent)*/

                    Toast.makeText(this@VerificationCode, "تبریک. صحیح است.", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this@VerificationCode, "کد وارد شده صحیح نیست", Toast.LENGTH_SHORT).show()

                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }
}