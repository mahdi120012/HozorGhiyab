package hozorghiyab.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.Toast
import com.hozorghiyab.R
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import hozorghiyab.cityDetail.LoadData
import hozorghiyab.customClasses.EnglishNumberToPersian
import hozorghiyab.customClasses.SharedPrefClass
import hozorghiyab.customClasses.TimeKononi
import kotlinx.android.synthetic.main.darkhast_morkhasi.*
import kotlinx.android.synthetic.main.gozaresh_kar.imgBack
import kotlinx.android.synthetic.main.gozaresh_kar.imgHomeInRecevedMessageTeacher
import kotlinx.android.synthetic.main.gozaresh_kar.imgInboxMessageInRecevedPageStudent
import kotlinx.android.synthetic.main.gozaresh_kar.imgListGozareshat
import kotlinx.android.synthetic.main.gozaresh_kar.imgSend
import kotlinx.android.synthetic.main.gozaresh_kar.txCountNotReadMessageInTeacher
import kotlinx.android.synthetic.main.list_payam_haye_ersali.*
import kotlinx.android.synthetic.main.list_payam_haye_ersali.imgMassenger
import kotlinx.android.synthetic.main.net_connection.*


class DarkhastMorkhasi : AppCompatActivity(), DatePickerDialog.OnDateSetListener,com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog.OnTimeSetListener {

    var saatShoroYaPayan = ""
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.darkhast_morkhasi)

        imgListGozareshat.setOnClickListener {
            val intent = Intent(this, ListPayamHayeErsali::class.java)
            intent.putExtra("darkhast_morkhasi", "darkhast_morkhasi")
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
                val datePickerDialog = DatePickerDialog.newInstance(this@DarkhastMorkhasi,
                        persianCalendar.persianYear,
                        persianCalendar.persianMonth,
                        persianCalendar.persianDay)
                datePickerDialog.show(fragmentManager, "TarikhPayan")
                return@OnTouchListener true
            }
            false
        })


        etSaatShoro.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {

                saatShoroYaPayan = "saat_shoro"

                val persianCalendar = PersianCalendar()
                val timePickerDialog = com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog.newInstance(
                        this@DarkhastMorkhasi,
                        persianCalendar.get(PersianCalendar.HOUR_OF_DAY),
                        persianCalendar.get(PersianCalendar.MINUTE), true)
                timePickerDialog.show(fragmentManager, "saat_shoro")


                return@OnTouchListener true
            }
            false
        })

        etSaatPayan.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {


                saatShoroYaPayan = "saat_payan"

                val persianCalendar = PersianCalendar()
                val timePickerDialog = com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog.newInstance(
                        this@DarkhastMorkhasi,
                        persianCalendar.get(PersianCalendar.HOUR_OF_DAY),
                        persianCalendar.get(PersianCalendar.MINUTE), true)
                timePickerDialog.show(fragmentManager, "saat_payan")

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

                LoadData.loadCountMessageNotRead(this@DarkhastMorkhasi, txCountNotReadMessageInTeacher, username)

                ha.postDelayed(this, 1000)
            }
        }, 1000)

        imgInboxMessageInRecevedPageStudent.setOnClickListener{
            startActivity(Intent(this, InboxMessage::class.java))
            finish()
        }

        imgHomeInRecevedMessageTeacher.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        imgBack.setOnClickListener{
            finish()
        }

        imgMassenger.setOnClickListener{

            startActivity(Intent(this, InboxMessageChat::class.java))
            finish()
        }

    }


    override fun onBackPressed() {
        finish()
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {

        val month = monthOfYear + 1
        var fm = "" + month
        var fd = "" + dayOfMonth
        if (month < 10) {
            fm = "0$month"
        }
        if (dayOfMonth < 10) {
            fd = "0$dayOfMonth"
        }

        val date = "$year/$fm/$fd"

        if (view.toString().contains("TarikhShoro")){



            etTarikhShoro.setText(EnglishNumberToPersian().convert(date))
        }else{
            etTarikhPayan.setText(EnglishNumberToPersian().convert(date))
        }
    }

    override fun onTimeSet(view: RadialPickerLayout?, hourOfDay: Int, minute: Int) {
        val hourString = if (hourOfDay < 10) "0$hourOfDay" else "" + hourOfDay
        val minuteString = if (minute < 10) "0$minute" else "" + minute
        val time = "$hourString:$minuteString"


        if (saatShoroYaPayan.contains("saat_shoro")){
            etSaatShoro.setText(EnglishNumberToPersian().convert(time))
        }else{
            etSaatPayan.setText(EnglishNumberToPersian().convert(time))
        }
    }

}
