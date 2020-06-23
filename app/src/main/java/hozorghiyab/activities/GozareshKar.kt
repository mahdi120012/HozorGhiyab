package hozorghiyab.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.hozorghiyab.R
import hozorghiyab.cityDetail.LoadData
import hozorghiyab.cityDetail.RecyclerAdapterYouHaveKnow
import hozorghiyab.cityDetail.RecyclerModel
import hozorghiyab.customClasses.SharedPrefClass
import hozorghiyab.customClasses.TimeKononi
import kotlinx.android.synthetic.main.gozaresh_kar.*
import kotlinx.android.synthetic.main.net_connection.*
import kotlinx.android.synthetic.main.write_new_message_teacher.imgHomeInNavigationViewInSendMessageTeacher
import kotlinx.android.synthetic.main.write_new_message_teacher.imgInboxMessageInSendMessageTeacher
import kotlinx.android.synthetic.main.write_new_message_teacher.txCountNotReadMessageInSendMessageTeacher
import java.util.*


class GozareshKar : AppCompatActivity(), View.OnTouchListener {

    private val rAdapterYouHaveKnow: RecyclerAdapterYouHaveKnow? = null
    private val rModelsYouHaveKnow: ArrayList<RecyclerModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gozaresh_kar)

        imgListGozareshat.setOnClickListener {
            val intent = Intent(this, ListPayamHayeErsali::class.java)
            intent.putExtra("gozaresh_kar", "gozaresh_kar")
            startActivity(intent)
            finish()
        }

        etGozaresh.setOnTouchListener(this)
        etNatige.setOnTouchListener(this)

        val timeKononi = TimeKononi()
        txDate.setText(timeKononi.persianTime)

        var username = SharedPrefClass.getUserId(this,"user")

        imgSend.setOnClickListener{
            val gozaresh: String = etGozaresh.getText().toString()
            val natige: String = etNatige.getText().toString()
            val date: String = txDate.getText().toString()

            if (gozaresh.length <= 0 || gozaresh == null || natige.length <= 0 || natige == null) {
                Toast.makeText(this, "لطفا همه فیلد ها را تکمیل نمایید", Toast.LENGTH_SHORT).show()
            } else {
                LoadData.sendGozareshKar(this, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                        username, "100010", gozaresh, natige,date,clWifiState)
            }
        }

        val ha = Handler()
        ha.postDelayed(object : Runnable {
            override fun run() {

                LoadData.loadCountMessageNotRead(this@GozareshKar, txCountNotReadMessageInSendMessageTeacher, username)

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

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        if (view.id == R.id.etMatnSendTeacherMessage) {
            view.parent.requestDisallowInterceptTouchEvent(true)
            when (motionEvent.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_UP -> view.parent.requestDisallowInterceptTouchEvent(false)
            }
        } else if (view.id == R.id.etOnvanSendTeacherMessage) {
            view.parent.requestDisallowInterceptTouchEvent(true)
            when (motionEvent.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_UP -> view.parent.requestDisallowInterceptTouchEvent(false)
            }
        }
        return false
    }

    override fun onBackPressed() {
        finish()
    }
}
