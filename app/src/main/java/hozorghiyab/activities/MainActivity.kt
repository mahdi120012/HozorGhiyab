package hozorghiyab.activities

import android.app.ActivityManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.View
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
    //var mServiceIntent: Intent? = null
   //private var mYourService: NotificationService? = null
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


/*        createNotificationChannel()
        startService(Intent(this, NotificationService::class.java))

        mYourService = NotificationService()
        mServiceIntent = Intent(this, mYourService!!.javaClass)
        if (!isMyServiceRunning(mYourService!!.javaClass)) {
            startService(mServiceIntent)
        }



        LocalBroadcastManager.getInstance(this).registerReceiver(
                object : BroadcastReceiver() {
                    override fun onReceive(context: Context, intent: Intent) {
                        val latitude = intent.getDoubleExtra(NotificationService.EXTRA_LATITUDE, 0.0)
                        val longitude = intent.getDoubleExtra(NotificationService.EXTRA_LONGITUDE, 0.0)
                        Toast.makeText(this@MainActivity,latitude.toString(),Toast.LENGTH_LONG).show()
                        //textView.setText("Lat: $latitude, Lng: $longitude")
                    }
                }, IntentFilter(NotificationService.ACTION_LOCATION_BROADCAST)
        )*/


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


       /* if (noe == "teacher"){
            clMorkhasi.setVisibility(View.GONE);
            clVorodKhoroj.setVisibility(View.GONE);
            clAhkam.setVisibility(View.GONE);
            clSepordanKar.setVisibility(View.GONE);
            clGozareshKar.setVisibility(View.GONE);
            imgLine8.setVisibility(View.GONE);
            imgLine10.setVisibility(View.GONE);
            imgLine9.setVisibility(View.GONE);
            imgLine1.setVisibility(View.GONE);
            imgLine2.setVisibility(View.GONE);
            imgLine4.setVisibility(View.GONE);
            imgLine5.setVisibility(View.GONE);
            imgLine6.setVisibility(View.GONE);

        }*/

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
            startActivity(Intent(this, DarkhastMorkhasi::class.java))
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
/*    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "test"
            val descriptionText = "test"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("1", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                Log.i("Service status", "Running")
                return true
            }
        }
        Log.i("Service status", "Not running")
        return false
    }


    override fun onDestroy() {
        stopService(mServiceIntent)
        super.onDestroy()
    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter()
        intentFilter.addAction(NotificationService.MY_ACTION)
        registerReceiver(broadcastReceiver, intentFilter)
        //Start our own service
        val intent = Intent(this@MainActivity,
                NotificationService::class.java)
        startService(intent)
        super.onStart()
    }

    var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val datapassed = intent.getIntExtra("DATAPASSED", 0)
            val s = intent.action.toString()
            val s1 = intent.getStringExtra("DATAPASSED")

            if(s1.toString().equals("")){

            }else{
                //Toast.makeText(context,s1.toString(),Toast.LENGTH_SHORT).show()
            }
        }
    }*/


}
