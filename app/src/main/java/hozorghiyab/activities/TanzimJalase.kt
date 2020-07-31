package hozorghiyab.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.hozorghiyab.R
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import hozorghiyab.cityDetail.*
import hozorghiyab.customClasses.EnglishNumberToPersian
import hozorghiyab.customClasses.SharedPrefClass
import hozorghiyab.customClasses.TimeKononi
import kotlinx.android.synthetic.main.tanzim_jalase.*
import kotlinx.android.synthetic.main.tanzim_jalase.imgSend
import kotlinx.android.synthetic.main.toolbar_button.*
import kotlinx.android.synthetic.main.write_new_message_teacher.imgBackInSendMessageTeacher
import java.util.*

class TanzimJalase : AppCompatActivity(), DatePickerDialog.OnDateSetListener,com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog.OnTimeSetListener {

    private val rAdapterYouHaveKnow: RecyclerAdapterYouHaveKnow? = null
    private val rModelsYouHaveKnow: ArrayList<RecyclerModel>? = null
    var rvListYouHaveKnow: RecyclerView? = null
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tanzim_jalase)

        /*val intent = intent
        val list_id = intent.getStringArrayListExtra("id")
        val list_family = intent.getStringArrayListExtra("family")
        var ahkam = if (intent.getExtras() == null) {}else{intent.extras!!.getString("ahkam")}
        var sepordanKar = if (intent.getExtras() == null) {}else{intent.extras!!.getString("sepordan_kar")}

        var s = ""
        for (i in list_family.indices) {
            s += list_family[i].toString() + ", "
        }*/

        txReciver.setText("ادمین")

        etTarikh.setOnTouchListener(View.OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val persianCalendar = PersianCalendar()
                val datePickerDialog = DatePickerDialog.newInstance(
                        this@TanzimJalase,
                        persianCalendar.persianYear,
                        persianCalendar.persianMonth,
                        persianCalendar.persianDay
                )
                datePickerDialog.show(fragmentManager, "TarikhShoro")
                return@OnTouchListener true
            }
            false
        })

        etSaat.setOnTouchListener(View.OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {

                //saatShoroYaPayan = "saat_shoro"

                val persianCalendar = PersianCalendar()
                val timePickerDialog = com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog.newInstance(
                        this@TanzimJalase,
                        persianCalendar.get(PersianCalendar.HOUR_OF_DAY),
                        persianCalendar.get(PersianCalendar.MINUTE), true)
                timePickerDialog.show(fragmentManager, "saat_shoro")


                return@OnTouchListener true
            }
            false
        })


        var username = SharedPrefClass.getUserId(this,"user")
        var noe = SharedPrefClass.getUserId(this,"noe")

        if (noe.equals("student")){

            val ha = Handler()
            ha.postDelayed(object : Runnable {
                override fun run() {
                    //line zir baraye load name student Va Load Tedad Payam Haye Khande Nashodast.
                    val urlAppend = "?action=load_student_count_not_read_message" +
                            "&user1=" + UrlEncoderClass.urlEncoder(username)
                    LoadData.loadCountMessageNotRead(this@TanzimJalase, txCountNotReadMessage,username)
                    ha.postDelayed(this, 1000)
                }
            }, 1000)

        }else{
            val ha = Handler()
            ha.postDelayed(object : Runnable {
                override fun run() {

                    LoadData.loadCountMessageNotRead(this@TanzimJalase, txCountNotReadMessage, username)

                    ha.postDelayed(this, 1000)
                }
            }, 1000)
        }



        imgSend.setOnClickListener{
            var onvan: String = etOnvan.getText().toString()
            val tarikh: String = etTarikh.getText().toString()
            val saat: String = etSaat.getText().toString()


            if (onvan.length <= 0 || onvan == null || tarikh.length <= 0 || tarikh == null ||
                    saat.length <= 0 || saat == null) {
                Toast.makeText(this@TanzimJalase, "لطفا همه فیلد ها را تکمیل نمایید", Toast.LENGTH_SHORT).show()
            } else {
                val timeKononi = TimeKononi()
                var nowTime = timeKononi.persianTime
                //LoadData.sendDarkhastMorkhasi(this, username, timeKononi.persianTime  ,tarikhPayan + " " + saatKhoroj,tarikhShoro + " " + saatVorod, elat,clWifiState)


            }
        }

        imgInboxMessage.setOnClickListener{
                startActivity(Intent(this, InboxMessage::class.java))
                finish()
        }

        imgHome.setOnClickListener{
            if (noe.equals("student")){
                startActivity(Intent(this, StudentPanelMainKt::class.java))
                finish()
            }else{
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }

        }
        imgBackInSendMessageTeacher.setOnClickListener{
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

        etTarikh.setText(EnglishNumberToPersian().convert(date))

    }

    override fun onTimeSet(view: RadialPickerLayout?, hourOfDay: Int, minute: Int) {
        val hourString = if (hourOfDay < 10) "0$hourOfDay" else "" + hourOfDay
        val minuteString = if (minute < 10) "0$minute" else "" + minute
        val time = "$hourString:$minuteString"

        etSaat.setText(EnglishNumberToPersian().convert(time))

    }
}
