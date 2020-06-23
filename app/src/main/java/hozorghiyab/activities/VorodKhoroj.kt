package hozorghiyab.activities

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.Toast
import com.hozorghiyab.R
import hozorghiyab.cityDetail.LoadData
import hozorghiyab.cityDetail.RecyclerAdapterYouHaveKnow
import hozorghiyab.cityDetail.RecyclerModel
import hozorghiyab.customClasses.SharedPrefClass
import hozorghiyab.customClasses.TimeKononi
import kotlinx.android.synthetic.main.gozaresh_kar.*
import kotlinx.android.synthetic.main.gozaresh_kar.imgBack
import kotlinx.android.synthetic.main.gozaresh_kar.imgListGozareshat
import kotlinx.android.synthetic.main.gozaresh_kar.imgSend
import kotlinx.android.synthetic.main.gozaresh_kar.txDate
import kotlinx.android.synthetic.main.net_connection.*
import kotlinx.android.synthetic.main.vorod_khoroj.*
import kotlinx.android.synthetic.main.write_new_message_teacher.imgHomeInNavigationViewInSendMessageTeacher
import kotlinx.android.synthetic.main.write_new_message_teacher.imgInboxMessageInSendMessageTeacher
import kotlinx.android.synthetic.main.write_new_message_teacher.txCountNotReadMessageInSendMessageTeacher
import java.util.*


class VorodKhoroj : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vorod_khoroj)

        imgListGozareshat.setOnClickListener {
            val intent = Intent(this, ListPayamHayeErsali::class.java)
            intent.putExtra("vorod_khoroj", "vorod_khoroj")
            startActivity(intent)
            finish()
        }
        var username = SharedPrefClass.getUserId(this,"user")
        val timeKononi = TimeKononi()

        LoadData.loadVorodKhorojGhabli(this,etSaatVorod,etSaatKhoroj,timeKononi.persianTime,username,clWifiState)

        etSaatVorod.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val mcurrentTime: Calendar = Calendar.getInstance()
                val hour: Int = mcurrentTime.get(Calendar.HOUR_OF_DAY)
                val minute: Int = mcurrentTime.get(Calendar.MINUTE)
                val mTimePicker: TimePickerDialog
                mTimePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
                    etSaatVorod.setText(String.format("%02d:%02d", selectedHour, selectedMinute))
                }, hour, minute, true) //Yes 24 hour time
                mTimePicker.setTitle("انتخاب زمان")
                mTimePicker.show()
                return@OnTouchListener true
            }
            false
        })

        etSaatKhoroj.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val mcurrentTime: Calendar = Calendar.getInstance()
                val hour: Int = mcurrentTime.get(Calendar.HOUR_OF_DAY)
                val minute: Int = mcurrentTime.get(Calendar.MINUTE)
                val mTimePicker: TimePickerDialog
                mTimePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
                    etSaatKhoroj.setText(String.format("%02d:%02d", selectedHour, selectedMinute))
                }, hour, minute, true) //Yes 24 hour time
                mTimePicker.setTitle("انتخاب زمان")
                mTimePicker.show()
                return@OnTouchListener true
            }
            false
        })


        txDate.setText(timeKononi.persianTime)

        imgSend.setOnClickListener{
            val saatVorod: String = etSaatVorod.getText().toString()
            val saatKhoroj: String = etSaatKhoroj.getText().toString()
            val date: String = txDate.getText().toString()

            if (saatVorod.length <= 0 || saatVorod == null || saatKhoroj.length <= 0 || saatKhoroj == null) {
                Toast.makeText(this, "لطفا همه فیلد ها را تکمیل نمایید", Toast.LENGTH_SHORT).show()
            } else {
                LoadData.sendVorodKhoroj(this, username, saatVorod, saatKhoroj,date,clWifiState)
            }
        }

        val ha = Handler()
        ha.postDelayed(object : Runnable {
            override fun run() {

                LoadData.loadCountMessageNotRead(this@VorodKhoroj, txCountNotReadMessageInSendMessageTeacher, username)

                ha.postDelayed(this, 1000)
            }
        }, 1000)

        imgInboxMessageInSendMessageTeacher.setOnClickListener{
            startActivity(Intent(this, InboxMessage::class.java))
            finish()
        }

        imgHomeInNavigationViewInSendMessageTeacher.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        imgBack.setOnClickListener{
            finish()
        }

    }


    override fun onBackPressed() {
        finish()
    }
}
