package hozorghiyab.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import hozorghiyab.cityDetail.*
import hozorghiyab.customClasses.SharedPrefClass
import kotlinx.android.synthetic.main.gozaresh_kar.imgErsalGozaresh
import kotlinx.android.synthetic.main.inbox_message.*
import kotlinx.android.synthetic.main.inbox_message.imgTooTitlelbarMainAct
import kotlinx.android.synthetic.main.inbox_message.tabLayout
import kotlinx.android.synthetic.main.inbox_message.txTitle
import kotlinx.android.synthetic.main.list_payam_haye_ersali.*
import kotlinx.android.synthetic.main.net_connection.*
import java.util.*

class ListPayamHayeErsali : AppCompatActivity() {
    private var rAdapterYouHaveKnow: RecyclerAdapterYouHaveKnow? = null
    private var rModelsYouHaveKnow: ArrayList<RecyclerModel>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.hozorghiyab.R.layout.list_payam_haye_ersali)

        var username = SharedPrefClass.getUserId(this,"user")
        var noe = SharedPrefClass.getUserId(this,"noe")

        var gozaresh_kar = if (intent.getExtras() == null) {}else{intent.extras!!.getString("gozaresh_kar")}
        var vorod_khoroj = if (intent.getExtras() == null) {}else{intent.extras!!.getString("vorod_khoroj")}

        rModelsYouHaveKnow = ArrayList()
        rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "recived_message", this@ListPayamHayeErsali, rAdapterYouHaveKnow, "",null,null,null,"")
        Recyclerview.define_recyclerviewAddStudent(this@ListPayamHayeErsali, rvInPayamHayeErsaliTeacher, rAdapterYouHaveKnow,
                rModelsYouHaveKnow, null)

        LoadData.firstLoadDataListPayamHayeErsaliTeacher(this@ListPayamHayeErsali, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                rvInPayamHayeErsaliTeacher, username,"all",clWifiState)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        //همه

                        rModelsYouHaveKnow = ArrayList()
                        rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "recived_message", this@ListPayamHayeErsali, rAdapterYouHaveKnow, "",null,null,null,"")
                        Recyclerview.define_recyclerviewAddStudent(this@ListPayamHayeErsali, rvInPayamHayeErsaliTeacher, rAdapterYouHaveKnow,
                                rModelsYouHaveKnow, null)

                        LoadData.firstLoadDataListPayamHayeErsaliTeacher(this@ListPayamHayeErsali, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                                rvInPayamHayeErsaliTeacher, username,"all",clWifiState)
                    }
                    1 -> {
                        //خصوصی

                        rModelsYouHaveKnow = ArrayList()
                        rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "recived_message", this@ListPayamHayeErsali, rAdapterYouHaveKnow, "",null,null,null,"")
                        Recyclerview.define_recyclerviewAddStudent(this@ListPayamHayeErsali, rvInPayamHayeErsaliTeacher, rAdapterYouHaveKnow,
                                rModelsYouHaveKnow, null)

                        LoadData.firstLoadDataListPayamHayeErsaliTeacher(this@ListPayamHayeErsali, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                                rvInPayamHayeErsaliTeacher, username,"",clWifiState)
                    }
                    2 -> {
                        //گزارشات

                        rModelsYouHaveKnow = ArrayList()
                        rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "recived_message", this@ListPayamHayeErsali, rAdapterYouHaveKnow, "",null,null,null,"")
                        Recyclerview.define_recyclerviewAddStudent(this@ListPayamHayeErsali, rvInPayamHayeErsaliTeacher, rAdapterYouHaveKnow,
                                rModelsYouHaveKnow, null)

                        LoadData.firstLoadDataListPayamHayeErsaliTeacher(this@ListPayamHayeErsali, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                                rvInPayamHayeErsaliTeacher, username,"gozaresh_kar",clWifiState)
                    }
                    3 -> {
                        //کارها
                        rModelsYouHaveKnow = ArrayList()
                        rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "recived_message", this@ListPayamHayeErsali, rAdapterYouHaveKnow, "",null,null,null,"")
                        Recyclerview.define_recyclerviewAddStudent(this@ListPayamHayeErsali, rvInPayamHayeErsaliTeacher, rAdapterYouHaveKnow,
                                rModelsYouHaveKnow, null)

                        LoadData.firstLoadDataListPayamHayeErsaliTeacher(this@ListPayamHayeErsali, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                                rvInPayamHayeErsaliTeacher, username,"sepordan_kar",clWifiState)
                    }
                    4 -> {
                        //احکام
                        rModelsYouHaveKnow = ArrayList()
                        rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "recived_message", this@ListPayamHayeErsali, rAdapterYouHaveKnow, "",null,null,null,"")
                        Recyclerview.define_recyclerviewAddStudent(this@ListPayamHayeErsali, rvInPayamHayeErsaliTeacher, rAdapterYouHaveKnow,
                                rModelsYouHaveKnow, null)

                        LoadData.firstLoadDataListPayamHayeErsaliTeacher(this@ListPayamHayeErsali, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                                rvInPayamHayeErsaliTeacher, username,"ahkam",clWifiState)
                    }
                    else -> {
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        if (vorod_khoroj.toString()!!.equals("vorod_khoroj")){
            clGozareshat.visibility = View.VISIBLE
            imgSendNewMessageInTeacher.visibility = View.GONE
            imgTooTitlelbarMainAct.visibility = View.GONE
            txTitle.setText("لیست ورود خروج")
            textView20.setText("ثبت ورود خروج")
            textView21.setText("لیست ورود خروج")
        }

        if (gozaresh_kar.toString()!!.equals("gozaresh_kar")){
            clGozareshat.visibility = View.VISIBLE
            imgSendNewMessageInTeacher.visibility = View.GONE
            imgTooTitlelbarMainAct.visibility = View.GONE
            txTitle.setText("لیست گزارشات")
        }

        if(noe.equals("student")){
            LoadData.firstLoadDataListPayamHayeErsaliStudent(this, rAdapterYouHaveKnow, rModelsYouHaveKnow, rvInPayamHayeErsaliTeacher, username, clWifiState)

            val ha = Handler()
            ha.postDelayed(object : Runnable {
                override fun run() {
                    //line zir baraye load name student Va Load Tedad Payam Haye Khande Nashodast.
                    val urlAppend = "?action=load_student_count_not_read_message" +
                            "&user1=" + UrlEncoderClass.urlEncoder(username)
                    LoadData.loadCountMessageNotRead(this@ListPayamHayeErsali, urlAppend, txCountNotReadMessageInPayamHayeErsaliTeacher)
                    ha.postDelayed(this, 1000)
                }
            }, 1000)

        }else{

            if (vorod_khoroj.toString()!!.equals("vorod_khoroj")){
                rModelsYouHaveKnow = ArrayList()
                rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "vorod_khoroj", this, rAdapterYouHaveKnow, "",null,null,null,"")
                Recyclerview.define_recyclerviewAddStudent(this, rvInPayamHayeErsaliTeacher, rAdapterYouHaveKnow,
                        rModelsYouHaveKnow, null)
                LoadData.ListVorodKhorojErsali(this, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                        rvInPayamHayeErsaliTeacher, username,clWifiState)

            }else{

            val ha = Handler()
            ha.postDelayed(object : Runnable {
                override fun run() {
                    //line zir baraye load name student Va Load Tedad Payam Haye Khande Nashodast.
                    LoadData.loadTeacherCountMessageNotRead(this@ListPayamHayeErsali,txCountNotReadMessageInPayamHayeErsaliTeacher,username);

                    ha.postDelayed(this, 1000)
                }
            }, 1000)

            }
        }


        imgErsalGozaresh.setOnClickListener {
            if (vorod_khoroj.toString()!!.equals("vorod_khoroj")){
                startActivity(Intent(this, VorodKhoroj::class.java))
                finish()
            }else{
                startActivity(Intent(this, GozareshKar::class.java))
                finish()
            }
        }

        imgSendNewMessageInTeacher.setOnClickListener {
                startActivity(Intent(this, SearchForSendMessage::class.java))
        }

        imgInboxMessageInPayamHayeErsaliTeacher.setOnClickListener{
                startActivity(Intent(this, InboxMessage::class.java))
                finish()
        }

        imgHomeInNavigationViewInPayamHayeErsaliTeacher.setOnClickListener{
            if(noe.equals("student")){
                val intent = Intent(applicationContext, StudentPanelMainKt::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }else{
                finish()
            }

        }

        imgBackInPayamHayeErsaliTeacher.setOnClickListener{
            finish()
        }

    }

    override fun onBackPressed() {
        finish()
    }

}
