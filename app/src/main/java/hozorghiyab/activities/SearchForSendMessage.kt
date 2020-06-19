package hozorghiyab.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.SearchView
import hozorghiyab.cityDetail.*
import hozorghiyab.customClasses.SharedPrefClass
import kotlinx.android.synthetic.main.net_connection.*
import kotlinx.android.synthetic.main.search_for_send_message.*
import java.util.*

class SearchForSendMessage : AppCompatActivity() {
    private var rAdapterYouHaveKnow: RecyclerAdapterYouHaveKnow? = null
    private var rModelsYouHaveKnow: ArrayList<RecyclerModel>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.hozorghiyab.R.layout.search_for_send_message)

        var username = SharedPrefClass.getUserId(this,"user")
        var noe = SharedPrefClass.getUserId(this,"noe")
        var ahkam = if (intent.getExtras() == null) {}else{intent.extras!!.getString("ahkam")}
        var sepordanKar = if (intent.getExtras() == null) {}else{intent.extras!!.getString("sepordan_kar")}

        if (noe.equals("student")){

            rModelsYouHaveKnow = ArrayList()
            rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "search_by_student", this@SearchForSendMessage, rAdapterYouHaveKnow, "", txReciversList, clShowErsal, txEntekhabHame,"")
            Recyclerview.define_recyclerviewAddStudent(this@SearchForSendMessage, rvInSearchInTeacher, rAdapterYouHaveKnow,
                    rModelsYouHaveKnow, null)

            LoadData.LoadSearchResultStudent(this@SearchForSendMessage, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                    rvInSearchInTeacher, username, "")

            searchViewForSendMessageTeacher.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    //Toast.makeText(SearchForSendMessageTeacher.this, query, Toast.LENGTH_SHORT).show();
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    rModelsYouHaveKnow = ArrayList()
                    rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "search_by_student", this@SearchForSendMessage, rAdapterYouHaveKnow, "", txReciversList, clShowErsal, txEntekhabHame,"")
                    Recyclerview.define_recyclerviewAddStudent(this@SearchForSendMessage, rvInSearchInTeacher, rAdapterYouHaveKnow,
                            rModelsYouHaveKnow, null)
                    LoadData.LoadSearchResultStudent(this@SearchForSendMessage, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                            rvInSearchInTeacher, username, newText)

                    //Toast.makeText(SearchForSendMessageTeacher.this, newText, Toast.LENGTH_SHORT).show();
                    return false
                }
            })

            val ha = Handler()
            ha.postDelayed(object : Runnable {
                override fun run() {
                    //line zir baraye load name student Va Load Tedad Payam Haye Khande Nashodast.
                    val urlAppend = "?action=load_student_count_not_read_message" +
                            "&user1=" + UrlEncoderClass.urlEncoder(username)
                    LoadData.loadCountMessageNotRead(this@SearchForSendMessage, urlAppend, txCountNotReadMessageInSearchInTeacher)
                    ha.postDelayed(this, 1000)
                }
            }, 1000)

        }else{

            rModelsYouHaveKnow = ArrayList()
            rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "search_by_teacher", this@SearchForSendMessage, rAdapterYouHaveKnow, ahkam.toString(),txReciversList,clShowErsal,txEntekhabHame,sepordanKar.toString())
            Recyclerview.define_recyclerviewAddStudent(this@SearchForSendMessage, rvInSearchInTeacher, rAdapterYouHaveKnow,
                    rModelsYouHaveKnow, null)

            LoadData.LoadSearchResultTeacher(this@SearchForSendMessage, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                    rvInSearchInTeacher, username, "",clWifiState)


            searchViewForSendMessageTeacher.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean { //Toast.makeText(SearchForSendMessageTeacher.this, query, Toast.LENGTH_SHORT).show();
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    rModelsYouHaveKnow = ArrayList()
                    rAdapterYouHaveKnow = RecyclerAdapterYouHaveKnow(rModelsYouHaveKnow, "search_by_teacher", this@SearchForSendMessage, rAdapterYouHaveKnow, ahkam.toString(),null,null,null,sepordanKar.toString())
                    Recyclerview.define_recyclerviewAddStudent(this@SearchForSendMessage, rvInSearchInTeacher, rAdapterYouHaveKnow,
                            rModelsYouHaveKnow, null)
                    LoadData.LoadSearchResultTeacher(this@SearchForSendMessage, rAdapterYouHaveKnow, rModelsYouHaveKnow,
                            rvInSearchInTeacher, username, newText,clWifiState)
                    //Toast.makeText(SearchForSendMessageTeacher.this, newText, Toast.LENGTH_SHORT).show();
                    return false
                }
            })



            val ha = Handler()
            ha.postDelayed(object : Runnable {
                override fun run() {

                    LoadData.loadTeacherCountMessageNotRead(this@SearchForSendMessage, txCountNotReadMessageInSearchInTeacher, username)

                    ha.postDelayed(this, 1000)
                }
            }, 1000)

        }


        imgWriteMessageInSearchInTeacher.setOnClickListener{
             startActivity(Intent(this, ListPayamHayeErsali::class.java))
        }

        imgInboxMessageInSearchInTeacher.setOnClickListener{
                startActivity(Intent(this, InboxMessage::class.java))
                finish()
        }
        imgHomeInNavigationViewInSearchInTeacher.setOnClickListener{
            if (noe.equals("student")){
                startActivity(Intent(this, StudentPanelMainKt::class.java))
                finish()
            }else{
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }

        }

        imgBackInSearchInTeacher.setOnClickListener{
            finish()
        }

    }

    override fun onBackPressed() {
        finish()
    }
}
