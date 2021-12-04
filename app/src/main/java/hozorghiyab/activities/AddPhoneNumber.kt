package hozorghiyab.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.hozorghiyab.R
import hozorghiyab.cityDetail.UrlEncoderClass
import hozorghiyab.customClasses.SharedPrefClass
import hozorghiyab.user_info.Login_helper_by_phone_number
import kotlinx.android.synthetic.main.add_phone_number.*

class AddPhoneNumber : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_phone_number)

        var username = SharedPrefClass.getUserId(this,"user")
        var noe = SharedPrefClass.getUserId(this,"noe")

        if (username!!.length <= 0) {
            //tx_user_state.setText("ابتدا در برنامه وارد شوید");
        } else {
            if (noe.equals("student")) {
                val intent = Intent(this, StudentPanelMainKt::class.java)
                startActivity(intent)
                this.finish()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                this.finish()
            }
            //tx_user_state.setText(username + " خوش آمدید ");
        }


        imgEdame.setOnClickListener {
            var phone = etPhoneNumber.text.toString()
            if (phone.startsWith('0')){
                phone = phone.substring(1)
            }

            val password = etPassword.text.toString()

            if (phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "لطفا همه فیلد ها را تکمیل نمایید", Toast.LENGTH_SHORT).show()
            }else if(phone.length < 10){
                Toast.makeText(this, "شماره تلفن را صحیح وارد کنید", Toast.LENGTH_SHORT).show()

            } else {

                val userNameEncode = UrlEncoderClass.urlEncoder(phone)
                val passwordEncode = UrlEncoderClass.urlEncoder(password)

                val url = "https://hozori.ir/android/load_data.php?action=login_karkonan&karkon_id=$userNameEncode + &pass1=$passwordEncode"
                Login_helper_by_phone_number(this, url, phone, password).execute()
            }

//            val randomNumber = (11111..99999).random()
//            val intent = Intent(this, VerificationCode::class.java)
//            intent.putExtra("randomNumber", randomNumber.toString())
//            intent.putExtra("phone", etPhoneNumber.text.toString())
//            intent.putExtra("password", etPassword.text.toString())
//            startActivity(intent)
        }
    }
}