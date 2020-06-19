package hozorghiyab.activities

import android.R
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.github.javiersantos.appupdater.AppUpdater
import com.github.javiersantos.appupdater.enums.UpdateFrom
import hozorghiyab.cityDetail.LoadData
import hozorghiyab.customClasses.AppVersionName
import hozorghiyab.customClasses.SharedPrefClass
import hozorghiyab.user_info.Main_user_login_activity
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.navigation_main.*
import kotlinx.android.synthetic.main.net_connection.*
import kotlinx.android.synthetic.main.toolbar_button.*
import kotlinx.android.synthetic.main.toolbar_top.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.hozorghiyab.R.layout.navigation_main)

        nav_footer_txVesionCodeInMainPageTeacher.setText("نسخه " + AppVersionName.getVersionName(this))

        //line zir baraye update automatic ee.
        val appUpdater = AppUpdater(this).setUpdateFrom(UpdateFrom.JSON).
                setUpdateJSON("http://robika.ir/ultitled/practice/tavasi_update_checker.json").
                setTitleOnUpdateAvailable("بروزرسانی جدید موجوده!").setButtonUpdate("بروزرسانی").
                setButtonDismiss("فعلا نه").setButtonDoNotShowAgain("")
        appUpdater.start()

        var username = SharedPrefClass.getUserId(this,"user")
        var noe = SharedPrefClass.getUserId(this,"noe")
        var name = SharedPrefClass.getUserId(this,"name")
        if(name.isEmpty()){

            val ha = Handler()
            ha.postDelayed(object : Runnable {
                override fun run() {
                    LoadData.loadTeacherNameAndCountMessageNotRead(this@MainActivity,
                            txTecherNameInMainActivity, txCountNotReadMessageInToolbarButton, username,imgTeacherPicture,clWifiState)

                    ha.postDelayed(this, 2000)
                }
            }, 2000)
        }else{
            txTecherNameInMainActivity.setText(name)
        }

        if (username == "100010"){
            clHameyePayamHaClickInMainActivity.setVisibility(View.VISIBLE);
            imgLine5.setVisibility(View.VISIBLE);
        }


        if (noe == "karkonan"){
            clClassClickInMainActivity.setVisibility(View.GONE);
            clStudentClickInMainActivity.setVisibility(View.GONE);
            clHozorGhiyabClickInMainActivity.setVisibility(View.GONE);
            clSepordanKar.setVisibility(View.GONE);
            clGozareshKar.setVisibility(View.VISIBLE);
            imgLine1.setVisibility(View.GONE);
            imgLine2.setVisibility(View.GONE);
            imgLine4.setVisibility(View.GONE);
            imgLine5.setVisibility(View.GONE);
            imgLine6.setVisibility(View.GONE);

            clAhkam.setOnClickListener(){

                val intent = Intent(this, InboxMessage::class.java)
                intent.putExtra("ahkam", "ahkam")
                startActivity(intent)
            }

        }else{
            clAhkam.setOnClickListener(){

                val intent = Intent(this, SearchForSendMessage::class.java)
                intent.putExtra("ahkam", "ahkam")
                startActivity(intent)


            }
        }

        clMorkhasi.setOnClickListener(){
            //startActivity(Intent(this, GozareshKar::class.java))
        }

        clGozareshKar.setOnClickListener(){
            startActivity(Intent(this, GozareshKar::class.java))
        }

        clSepordanKar.setOnClickListener(){

            val intent = Intent(this, SearchForSendMessage::class.java)
            intent.putExtra("sepordan_kar", "sepordan_kar")
            startActivity(intent)
        }

        clVorodKhoroj.setOnClickListener(){

            val intent = Intent(this, VorodKhoroj::class.java)
            startActivity(intent)
        }




        imgInboxMessageInToolbarButton.setOnClickListener(){
            startActivity(Intent(this, InboxMessage::class.java))
        }

        imgWriteMessageInToolbarButton.setOnClickListener(){
            startActivity(Intent(this, ListPayamHayeErsali::class.java))
        }

        clHameyePayamHaClickInMainActivity.setOnClickListener(){
            startActivity(Intent(this, ListHameyePayamHaBarayeAdmin::class.java))
        }

        clClassClickInMainActivity.setOnClickListener(){
            startActivity(Intent(this, AddClass::class.java))
        }

        clStudentClickInMainActivity.setOnClickListener(){
            startActivity(Intent(this, AddStudent::class.java))
        }

        clHozorGhiyabClickInMainActivity.setOnClickListener(){
            startActivity(Intent(this, HozorGhiyabMain::class.java))

        }
        txMenuAboutme.setOnClickListener(){
            Toast.makeText(this,"درباره ما",Toast.LENGTH_SHORT).show()
        }

        txExit.setOnClickListener(){
            SharedPrefClass.clearData(this)
            Toast.makeText(this,"شما خارج شدید",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, Main_user_login_activity::class.java))
            finish()
        }

        imgNavigationViewInToolbarTop.setOnClickListener(){
            if (drawer_layout.isDrawerOpen(Gravity.RIGHT)) {
                drawer_layout.closeDrawer(Gravity.RIGHT)
            } else {
                drawer_layout.openDrawer(Gravity.RIGHT)
            }
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(Gravity.RIGHT)) {
            drawer_layout.closeDrawer(Gravity.RIGHT)
        } else {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}
