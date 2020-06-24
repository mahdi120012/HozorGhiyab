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
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import hozorghiyab.cityDetail.LoadData
import hozorghiyab.customClasses.SharedPrefClass
import hozorghiyab.customClasses.TimeKononi
import kotlinx.android.synthetic.main.darkhast_morkhasi.*
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


class DarkhastMorkhasi : AppCompatActivity(), DatePickerDialog.OnDateSetListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.darkhast_morkhasi)

        imgListGozareshat.setOnClickListener {
            val intent = Intent(this, ListPayamHayeErsali::class.java)
            intent.putExtra("vorod_khoroj", "vorod_khoroj")
            startActivity(intent)
            finish()
        }
        var username = SharedPrefClass.getUserId(this,"user")


        etTarikhShoro.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val persianCalendar = PersianCalendar()
                val datePickerDialog = DatePickerDialog.newInstance(
                        this@DarkhastMorkhasi,
                        persianCalendar.persianYear,
                        persianCalendar.persianMonth,
                        persianCalendar.persianDay
                )
                datePickerDialog.show(fragmentManager, "TarikhShoro")
                return@OnTouchListener true
            }
            false
        })

        etTarikhPayan.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val persianCalendar = PersianCalendar()
                val datePickerDialog = DatePickerDialog.newInstance(
                        this@DarkhastMorkhasi,
                        persianCalendar.persianYear,
                        persianCalendar.persianMonth,
                        persianCalendar.persianDay
                )
                datePickerDialog.show(fragmentManager, "TarikhPayan")
                return@OnTouchListener true
            }
            false
        })


        etSaatShoro.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val mcurrentTime: Calendar = Calendar.getInstance()
                val hour: Int = mcurrentTime.get(Calendar.HOUR_OF_DAY)
                val minute: Int = mcurrentTime.get(Calendar.MINUTE)
                val mTimePicker: TimePickerDialog
                mTimePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
                    etSaatShoro.setText(String.format("%02d:%02d", selectedHour, selectedMinute))
                }, hour, minute, true) //Yes 24 hour time
                mTimePicker.setTitle("انتخاب زمان")
                mTimePicker.show()
                return@OnTouchListener true
            }
            false
        })

        etSaatPayan.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val mcurrentTime: Calendar = Calendar.getInstance()
                val hour: Int = mcurrentTime.get(Calendar.HOUR_OF_DAY)
                val minute: Int = mcurrentTime.get(Calendar.MINUTE)
                val mTimePicker: TimePickerDialog
                mTimePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
                    etSaatPayan.setText(String.format("%02d:%02d", selectedHour, selectedMinute))
                }, hour, minute, true) //Yes 24 hour time
                mTimePicker.setTitle("انتخاب زمان")
                mTimePicker.show()
                return@OnTouchListener true
            }
            false
        })


        imgSend.setOnClickListener{
            val saatVorod: String = etSaatShoro.getText().toString()
            val saatKhoroj: String = etSaatPayan.getText().toString()
            val tarikhShoro: String = etTarikhShoro.getText().toString()
            val tarikhPayan: String = etTarikhPayan.getText().toString()

            val elat: String = etElat.getText().toString()
            if (saatVorod.length <= 0 || saatVorod == null || saatKhoroj.length <= 0 || saatKhoroj == null
                    || tarikhShoro.length <= 0 || tarikhShoro == null || tarikhPayan.length <= 0 || tarikhPayan == null
                    || elat.length <= 0 || elat == null) {
                Toast.makeText(this, "لطفا همه فیلد ها را تکمیل نمایید", Toast.LENGTH_SHORT).show()
            } else {
                val timeKononi = TimeKononi()

                LoadData.sendDarkhastMorkhasi(this, username, timeKononi.persianTime  ,tarikhPayan + " " + saatKhoroj,tarikhShoro + " " + saatVorod, elat,clWifiState)
            }
        }

        val ha = Handler()
        ha.postDelayed(object : Runnable {
            override fun run() {

                LoadData.loadCountMessageNotRead(this@DarkhastMorkhasi, txCountNotReadMessageInSendMessageTeacher, username)

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

    fun onTimeSet(view: RadialPickerLayout?, hourOfDay: Int, minute: Int) {
        val time = "You picked the following time: " + hourOfDay + "h" + minute
        //timeTextView.setText(time)

        Toast.makeText(this,time,Toast.LENGTH_SHORT).show()
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {

        val date = (year.toString().substring(2) + "/" + (monthOfYear + 1) + "/" + dayOfMonth.toString())

        if (view.toString().contains("TarikhShoro")){
            etTarikhShoro.setText(date)
        }else{
            etTarikhPayan.setText(date)
        }
    }
}
