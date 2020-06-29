package hozorghiyab.cityDetail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Timer;

import com.hozorghiyab.R;

import hozorghiyab.activities.ErsalVaziyatDarsiStudent;
import hozorghiyab.activities.HozorGhiyab;
import hozorghiyab.activities.NamayeshVaziyatDarsiStudent;
import hozorghiyab.activities.WriteNewMessage;
import hozorghiyab.customClasses.EnglishNumberToPersian;
import hozorghiyab.customClasses.SharedPrefClass;

public class RecyclerAdapterYouHaveKnow extends RecyclerView.Adapter<RecyclerAdapterYouHaveKnow.MyViewHolder> {

    private ArrayList<RecyclerModel> recyclerModels; // this data structure carries our title and description
    Context c;
    String rowLayoutType,className;
    RecyclerAdapterYouHaveKnow recyclerAdapterYouHaveKnow;
    ImageView img_refresh;
    WebView webView;
    Timer timer;
    TextView tx_state,txReciversList,txEntekhabHame;
    RecyclerView rvAddJalase;
    String jalaseId,sepordanKar, result;
    ConstraintLayout clShowErsal;
    final ArrayList<String> list_family = new ArrayList<String>();
    final ArrayList<String> list_id = new ArrayList<String>();

    public RecyclerAdapterYouHaveKnow(ArrayList<RecyclerModel> recyclerModels, String rowLayoutType, Context c,
                                      RecyclerAdapterYouHaveKnow recyclerAdapterYouHaveKnow,String className,TextView txReciversList,ConstraintLayout clShowErsal,TextView txEntekhabHame,String sepordanKar) {
        this.recyclerModels = recyclerModels;
        this.rowLayoutType = rowLayoutType;
        this.c = c;
        this.recyclerAdapterYouHaveKnow = recyclerAdapterYouHaveKnow;
        this.className = className;
        this.txReciversList = txReciversList;
        this.clShowErsal = clShowErsal;
        this.txEntekhabHame = txEntekhabHame;
        this.sepordanKar = sepordanKar;
    }

    public RecyclerAdapterYouHaveKnow(ArrayList<RecyclerModel> recyclerModels, String rowLayoutType, Context c,
                                      RecyclerAdapterYouHaveKnow recyclerAdapterYouHaveKnow,
                                      ImageView img_refresh,WebView webView,Timer timer,TextView tx_state,
                                      RecyclerView rvAddJalase,String className,String jalaseId) {
        this.recyclerModels = recyclerModels;
        this.rowLayoutType = rowLayoutType;
        this.c = c;
        this.recyclerAdapterYouHaveKnow = recyclerAdapterYouHaveKnow;
        this.className = className;
        this.img_refresh = img_refresh;
        this.webView = webView;
        this.timer = timer;
        this.tx_state = tx_state;
        this.rvAddJalase = rvAddJalase;
        this.jalaseId = jalaseId;

    }

    @Override
    public RecyclerAdapterYouHaveKnow.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate your custom row layout here
        if (rowLayoutType.contains("add_class")){
            return new RecyclerAdapterYouHaveKnow.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_add_class, parent, false));

        }else if (rowLayoutType.contains("add_student")) {
            return new RecyclerAdapterYouHaveKnow.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_add_student, parent, false));

        }else if (rowLayoutType.contains("recived_message")) {
            return new RecyclerAdapterYouHaveKnow.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recive_message, parent, false));

        }else if (rowLayoutType.contains("vorod_khoroj")) {
            return new RecyclerAdapterYouHaveKnow.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_vorod_khoroj, parent, false));

        }else if (rowLayoutType.contains("darkhast_morkhasi")) {
            return new RecyclerAdapterYouHaveKnow.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_darkhast_morkhasi, parent, false));

        }else if (rowLayoutType.contains("all_users_message")) {
            return new RecyclerAdapterYouHaveKnow.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recive_all_message, parent, false));

        }else if (rowLayoutType.contains("search")) {
            return new RecyclerAdapterYouHaveKnow.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search, parent, false));

        }else if (rowLayoutType.contains("dars_list")) {
            return new RecyclerAdapterYouHaveKnow.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dars_list, parent, false));

        }else if (rowLayoutType.contains("dars_student_to_teacher_list")) {
            return new RecyclerAdapterYouHaveKnow.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dars_list_for_send_student_message_to_teacher, parent, false));

        }else if (rowLayoutType.contains("add_jalase")) {
            return new RecyclerAdapterYouHaveKnow.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_add_jalase, parent, false));

        }else if (rowLayoutType.contains("add_dars_result_student")) {
            return new RecyclerAdapterYouHaveKnow.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_student_dars_result, parent, false));

        }else if (rowLayoutType.contains("add_hozorghiyab")) {
            return new RecyclerAdapterYouHaveKnow.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_add_hozorghiyab, parent, false));

        }else {
            return new RecyclerAdapterYouHaveKnow.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_city_list, parent, false));

        }

    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(final RecyclerAdapterYouHaveKnow.MyViewHolder holder,final int position) {



          if (rowLayoutType.contains("add_class")){

              holder.txSchoolName.setText(recyclerModels.get(position).getOnvan());
              holder.txClassName.setText(recyclerModels.get(position).getMatn());
              holder.txTedadStudentInClass.setText("("+recyclerModels.get(position).getPosition()+" دانش آموز)");

              SharedPreferences sharedPreferences = c.getSharedPreferences("file", c.MODE_PRIVATE);
              SharedPreferences.Editor editor = sharedPreferences.edit();
              String username = sharedPreferences.getString("user", "user");
              editor.commit();

              if (username.matches("100024") || username.matches("100025") ){
                  holder.imgRemoveClass.setVisibility(View.GONE);

              }

              holder.imgRemoveClass.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                      holder.txSchoolName.setText(recyclerModels.get(position).getOnvan());
                      holder.txClassName.setText(recyclerModels.get(position).getMatn());

                      LoadData.removeClass(c,recyclerModels.get(position).getId());
                      recyclerModels.remove(position);
                      notifyItemRemoved(position);
                      notifyItemRangeChanged(position, recyclerModels.size());

                  }
              });

              Picasso.get()
                      .load(R.drawable.classimage)
                      .centerCrop()
                      .fit()
                      .into(holder.imageView);
          }else if (rowLayoutType.contains("add_student")){


              SharedPreferences sharedPreferences = c.getSharedPreferences("file", c.MODE_PRIVATE);
              SharedPreferences.Editor editor = sharedPreferences.edit();
              String username = sharedPreferences.getString("user", "user");
              editor.commit();

              if (username.matches("100024") || username.matches("100025") ){
                  username = "100010";
                  holder.imgRemoveStudent.setVisibility(View.GONE);
              }


              if (recyclerModels.get(position).getPicture().isEmpty()) {

                  Picasso.get()
                          .load(R.drawable.usericon)
                          .centerInside()
                          .fit()
                          .error(R.drawable.usericon)
                          .placeholder(R.drawable.usericon)
                          .into(holder.imgAddStudent);

              }else{
                  Picasso.get()
                          .load(recyclerModels.get(position).getPicture())
                          .centerInside()
                          .fit()
                          .error(R.drawable.usericon)
                          .placeholder(R.drawable.usericon)
                          .into(holder.imgAddStudent);
              }



              holder.txSchoolName.setText(recyclerModels.get(position).getOnvan());
              holder.txClassName.setText(recyclerModels.get(position).getMatn());

                holder.imgRemoveStudent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        LoadData.removeStudent(c,recyclerModels.get(position).getId());

                        recyclerModels.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, recyclerModels.size());

                    }
                });

          }else if (rowLayoutType.contains("vorod_khoroj")){

              holder.txDate2.setText(recyclerModels.get(position).getOnvan());
              holder.txSaatVorod.setText(recyclerModels.get(position).getMatn());
              holder.txSaatKhoroj.setText(recyclerModels.get(position).getPicture());
              holder.txVaziyat.setText(recyclerModels.get(position).getCity());

          }else if (rowLayoutType.contains("darkhast_morkhasi")){

              holder.txTarikhDarkhast.setText(new EnglishNumberToPersian().convert(recyclerModels.get(position).getOnvan()));
              holder.txAzTarikh.setText(new EnglishNumberToPersian().convert(recyclerModels.get(position).getMatn()));
              holder.txTaTarikh.setText(new EnglishNumberToPersian().convert(recyclerModels.get(position).getPicture()));
              holder.txElat.setText(new EnglishNumberToPersian().convert(recyclerModels.get(position).getCity()));
              holder.TxVaziyatt.setText(recyclerModels.get(position).getPosition());

          }else if (rowLayoutType.contains("recived_message")){

              holder.txOnvanMessageInRecivedMessage.setText(recyclerModels.get(position).getOnvan());
              holder.txMatnMessageInRecivedMessage.setText(recyclerModels.get(position).getMatn());
              holder.txNameFerestandeInRecivedMessage.setText(recyclerModels.get(position).getCity());
              holder.txDate.setText(recyclerModels.get(position).getPicture());

              if (recyclerModels.get(position).getVaziyat() != null){
                  if (recyclerModels.get(position).getVaziyat().equals("تایید شده")){
                      holder.imgVaziyatTaeid.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.taeid_shode));
                  }else if(recyclerModels.get(position).getVaziyat().equals("رد شده")){
                      holder.imgVaziyatTaeid.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.taeid_nashode));
                  }
              }

              if (recyclerModels.get(position).getCountRateAndComment()!=""){
                  holder.imgReadOrNo.setVisibility(View.VISIBLE);

                  if (recyclerModels.get(position).getCountRateAndComment().equals("0")){
                      holder.imgReadOrNo.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.not_read));
                  }else {
                      holder.imgReadOrNo.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.read));
                  }
              }

              if(holder.txDate.getText().toString().isEmpty()){
                  holder.txDate.setVisibility(View.GONE);
                  holder.imgIconDate.setVisibility(View.GONE);
              }

              if(recyclerModels.get(position).getPosition().toString().contains("gozaresh_kar")){

                  final SpannableStringBuilder sb = new SpannableStringBuilder("گزارش کار: " +recyclerModels.get(position).getOnvan());

                  final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
                  sb.setSpan(bss, 0, 10, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make first 4 characters Bold

                  holder.txOnvanMessageInRecivedMessage.setText(sb);
                  holder.txErsalNazar.setVisibility(View.VISIBLE);
                  holder.imgErsalNazarIcon.setVisibility(View.VISIBLE);
                  holder.clVaziyatTaeid.setVisibility(View.VISIBLE);

              }

              String noe = SharedPrefClass.getUserId(c,"noe");
              if (noe.equals("admin")){
                  holder.clVaziyatTaeid.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          showCustomDialog(c,position,"taeid_gozaresh",holder.imgVaziyatTaeid);
                      }
                  });
              }

              if(recyclerModels.get(position).getPosition().toString().contains("ahkam")){

                  final SpannableStringBuilder sb = new SpannableStringBuilder("احکام: " +recyclerModels.get(position).getOnvan());

                  final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
                  sb.setSpan(bss, 0, 6, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make first 4 characters Bold

                  holder.txOnvanMessageInRecivedMessage.setText(sb);
                  holder.txErsalNazar.setVisibility(View.VISIBLE);
                  holder.imgErsalNazarIcon.setVisibility(View.VISIBLE);

              }

              if(recyclerModels.get(position).getPosition().toString().contains("sepordan_kar")){

                  final SpannableStringBuilder sb = new SpannableStringBuilder("کار: " +recyclerModels.get(position).getOnvan());

                  final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
                  sb.setSpan(bss, 0, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make first 4 characters Bold

                  holder.txOnvanMessageInRecivedMessage.setText(sb);
                  holder.txErsalNazar.setVisibility(View.VISIBLE);
                  holder.imgErsalNazarIcon.setVisibility(View.VISIBLE);

              }

              holder.txErsalNazar.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                      list_family.add(recyclerModels.get(position).getCity());
                      list_id.add(recyclerModels.get(position).getRate());

                      String s = "";
                      for (int i = 0; i < list_family.size(); i++) {
                          s += list_family.get(i) + ", ";
                      }


                      Intent intent = new Intent(c, WriteNewMessage.class);
                      intent.putStringArrayListExtra("id",list_id);
                      intent.putStringArrayListExtra("family",list_family);
                      c.startActivity(intent);

                  }
              });

              holder.imgRemoveMessage.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                      showCustomDialog(c,position,"delete",null);

                  }
              });

          }else if (rowLayoutType.contains("all_users_message")){

              holder.txOnvanMessageInRecivedMessage.setText(recyclerModels.get(position).getOnvan());
              holder.txMatnMessageInRecivedMessage.setText(recyclerModels.get(position).getMatn());
              holder.txNameFerestandeInRecivedMessage.setText(recyclerModels.get(position).getCity());
              holder.txNameGirandehInRecivedMessage.setText(recyclerModels.get(position).getPosition());
              holder.txDate1.setText(recyclerModels.get(position).getRate());

              holder.imgRemoveMessage.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                      showCustomDialog(c,position,"delete",null);

                  }
              });

          }else if (rowLayoutType.contains("search")){

              final String stdId = recyclerModels.get(position).getOnvan();
              final String stdFamily = recyclerModels.get(position).getMatn();

              if (recyclerModels.get(position).getPicture().isEmpty()) {

                  Picasso.get()
                          .load(R.drawable.usericon)
                          .centerInside()
                          .fit()
                          .error(R.drawable.usericon)
                          .placeholder(R.drawable.usericon)
                          .into(holder.imgUserPictureForSendMessageInTeacher);

              }else{
                  Picasso.get()
                          .load(recyclerModels.get(position).getPicture())
                          .centerInside()
                          .fit()
                          .error(R.drawable.usericon)
                          .placeholder(R.drawable.usericon)
                          .into(holder.imgUserPictureForSendMessageInTeacher);
              }


              holder.txUserNameInSearchInTeacher.setText(stdFamily);
              holder.imgChoiseUserInSearchInTeacher.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {


                      list_family.add(stdFamily);
                      list_id.add(stdId);

                      String s = "";
                      for (int i = 0; i < list_family.size(); i++) {
                          s += list_family.get(i) + ", ";
                      }
                      txReciversList.setText(s);

                      clShowErsal.setVisibility(View.VISIBLE);
                      holder.imgChoiseReciverSendNewMessageInTeacher.setVisibility(View.VISIBLE);
                      holder.imgChoiseUserInSearchInTeacher.setVisibility(View.GONE);
                  }
              });

              holder.imgChoiseReciverSendNewMessageInTeacher.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                      list_family.remove(stdFamily);
                      list_id.remove(stdId);

                      if (list_family.size() <= 0){
                          clShowErsal.setVisibility(View.GONE);

                      }

                      String s = "";
                      for (int i = 0; i < list_family.size(); i++) {
                          s += list_family.get(i) + ", ";
                      }
                      txReciversList.setText(s);

                      holder.imgChoiseReciverSendNewMessageInTeacher.setVisibility(View.GONE);
                      holder.imgChoiseUserInSearchInTeacher.setVisibility(View.VISIBLE);


                  }
              });

              clShowErsal.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                      Intent intent = new Intent(c, WriteNewMessage.class);
                      intent.putStringArrayListExtra("id",list_id);
                      intent.putStringArrayListExtra("family",list_family);
                      intent.putExtra("ahkam",className);
                      intent.putExtra("sepordan_kar",sepordanKar);
                      c.startActivity(intent);

                      String stdId = recyclerModels.get(position).getOnvan();

                  }
              });

          }else if (rowLayoutType.contains("dars_list")){

              holder.txNameDars.setText(recyclerModels.get(position).getOnvan());
              holder.txCountHazeri.setText(recyclerModels.get(position).getMatn()+" حضور");
              holder.txCountGheybat.setText(recyclerModels.get(position).getCity()+" غیبت");

              holder.txVaziyatDarsiVaAkhlaghi.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      String darsiId = recyclerModels.get(position).getPosition();

                      Intent intent = new Intent(c, NamayeshVaziyatDarsiStudent.class);
                      intent.putExtra("dars_id", darsiId);
                      intent.putExtra("class_name", recyclerModels.get(position).getOnvan());
                      c.startActivity(intent);
                  }
              });

          }else if (rowLayoutType.contains("dars_student_to_teacher_list")){

              final String className = recyclerModels.get(position).getOnvan();
              final String teacherName = recyclerModels.get(position).getRate();
              final String teacherId = recyclerModels.get(position).getCountRateAndComment();
              holder.txNameDars.setText(className + " (" + teacherName +")");

              holder.clVaziyatDarsi.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      Intent intent = new Intent(c, ErsalVaziyatDarsiStudent.class);
                      intent.putExtra("teacher_id", teacherId);
                      intent.putExtra("class_name", className + " (" + teacherName +")");
                      c.startActivity(intent);
                  }
              });

          }else if (rowLayoutType.contains("add_jalase")){


              SharedPreferences sharedPreferences = c.getSharedPreferences("file", c.MODE_PRIVATE);
              SharedPreferences.Editor editor = sharedPreferences.edit();
              String username = sharedPreferences.getString("user", "user");
              editor.commit();

              if (username.matches("100024") || username.matches("100025") ){
                  holder.imgRemoveJalase.setVisibility(View.GONE);
              }

              holder.txSchoolName.setText(recyclerModels.get(position).getOnvan());
              holder.txClassName.setText(recyclerModels.get(position).getMatn());
              holder.txTedadHazerinInLinsJalasat.setText("تعداد حاظران "+recyclerModels.get(position).getPosition()+" نفر");
              holder.txTedadGhayebinInListJalasat.setText("تعداد غایبان "+recyclerModels.get(position).getCountRateAndComment()+" نفر");

              holder.cl.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      String jalaseId = recyclerModels.get(position).getId();
                      String jalaseName = recyclerModels.get(position).getOnvan();
                      String classId = recyclerModels.get(position).getCity();
                      String className = recyclerModels.get(position).getMatn();

                      Intent intent = new Intent(c, HozorGhiyab.class);
                      intent.putExtra("jalase_id", jalaseId);
                      intent.putExtra("jalase_name", jalaseName);

                      intent.putExtra("class_id", classId);
                      intent.putExtra("class_name", className);
                      c.startActivity(intent);
                  }
              });

              holder.imgRemoveJalase.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                      LoadData.removeJalase(c,recyclerModels.get(position).getId(),
                              recyclerModels.get(position).getCity());

                      recyclerModels.remove(position);
                      notifyItemRemoved(position);
                      notifyItemRangeChanged(position, recyclerModels.size());

                  }
              });

          }else if (rowLayoutType.contains("add_dars_result_student")){

              holder.txSchoolName.setText(recyclerModels.get(position).getOnvan());
              if (recyclerModels.get(position).getPicture().matches("1")  || recyclerModels.get(position).getPicture().matches("حاضر")){
                  holder.txClassName.setText("حاضر");
              }else {
                  holder.txClassName.setText("غایب");
              }
              holder.txTedadHazerinInLinsJalasat.setText("وضعیت درسی: "+recyclerModels.get(position).getCity());
              holder.txTedadGhayebinInListJalasat.setText("وضعیت اخلاقی: "+recyclerModels.get(position).getPosition());
              holder.txTaklif.setText("تکلیف: "+recyclerModels.get(position).getRate());


              holder.cl.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      String jalaseId = recyclerModels.get(position).getId();
                      String jalaseName = recyclerModels.get(position).getOnvan();
                      String classId = recyclerModels.get(position).getCity();
                      String className = recyclerModels.get(position).getMatn();

                      Intent intent = new Intent(c, HozorGhiyab.class);
                      intent.putExtra("jalase_id", jalaseId);
                      intent.putExtra("jalase_name", jalaseName);

                      intent.putExtra("class_id", classId);
                      intent.putExtra("class_name", className);
                      c.startActivity(intent);
                  }
              });


          }else if (rowLayoutType.contains("add_hozorghiyab")){


              holder.etTimeTakhirStudent.setText(recyclerModels.get(position).getPosition());
              holder.etVaziyatDarsiStudent.setText(recyclerModels.get(position).getRate());
              holder.etVaziyatAkhlaghiStudent.setText(recyclerModels.get(position).getCountRateAndComment());

              holder.etTimeTakhirStudent.addTextChangedListener(new TextWatcher() {
                  @Override
                  public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                  }

                  @Override
                  public void onTextChanged(CharSequence s, int start, int before, int count) {
                      LoadData.updateStudentInHozorGhiyab_timeTakhir(c,
                              recyclerModels.get(position).getId(),
                              recyclerModels,position,s.toString());


                  }

                  @Override
                  public void afterTextChanged(Editable s) {

                  }
              });
              holder.etVaziyatDarsiStudent.addTextChangedListener(new TextWatcher() {
                  @Override
                  public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                  }
                  @Override
                  public void onTextChanged(CharSequence s, int start, int before, int count) {
                      LoadData.updateStudentInHozorGhiyab_VaziyatDarsi(c,
                              recyclerModels.get(position).getId(),
                              recyclerModels,position,s.toString());
                  }

                  @Override
                  public void afterTextChanged(Editable s) {

                  }
              });

              holder.etVaziyatAkhlaghiStudent.addTextChangedListener(new TextWatcher() {
                  @Override
                  public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                  }

                  @Override
                  public void onTextChanged(CharSequence s, int start, int before, int count) {
                      LoadData.updateStudentInHozorGhiyab_VaziyatAkhlaghi(c,
                              recyclerModels.get(position).getId(),
                              recyclerModels,position,s.toString());


                  }

                  @Override
                  public void afterTextChanged(Editable s) {

                  }
              });

              if (recyclerModels.get(position).getPicture().isEmpty()) {

                  Picasso.get()
                          .load(R.drawable.usericon)
                          .centerInside()
                          .fit()
                          .error(R.drawable.usericon)
                          .placeholder(R.drawable.usericon)
                          .into(holder.imgHozorGhiyab);

              }else{
                  Picasso.get()
                          .load(recyclerModels.get(position).getPicture())
                          .centerInside()
                          .fit()
                          .error(R.drawable.usericon)
                          .placeholder(R.drawable.usericon)
                          .into(holder.imgHozorGhiyab);
              }

              holder.txStudentNameInHozorGhiyab.setText(recyclerModels.get(position).getOnvan());

              if (recyclerModels.get(position).getMatn().contains("1")){
                  holder.imgHazerGhayebTik.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.hazertik));
              }else{
                  holder.imgHazerGhayebTik.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.ghayebtik));
              }

              holder.clHazerGhayebTik.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                        if (recyclerModels.get(position).getMatn().contains("1")){
                            //update hazeri to ghayeb
                            LoadData.updateStudentInHozorGhiyab(c,recyclerModels.get(position).getId(),holder.imgHazerGhayebTik,recyclerModels,position);

                        }else {
                            //update ghayebbi to hazeri
                            LoadData.updateStudentInHozorGhiyabGhayebToHazer(c,recyclerModels.get(position).getId(),holder.imgHazerGhayebTik,recyclerModels,position);
                        }

                  }
              });

         /*     holder.spinnerTakhirStudent.setAdapter(spinnerArrayAdapter);
              holder.spinnerTakhirStudent.setSelection(recyclerModels.get(position).getIdTimeTakhir());
              holder.spinnerTakhirStudent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                  @Override
                  public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                      String selectedItem = adapterView.getItemAtPosition(i).toString();
                          LoadData.updateStudentInHozorGhiyab_timeTakhir(c,
                                  recyclerModels.get(position).getId(),
                                  recyclerModels,position,selectedItem);
                  }

                  @Override
                  public void onNothingSelected(AdapterView<?> adapterView) {
                  }
              });*/

          }else {
              holder.txSchoolName.setText(recyclerModels.get(position).getOnvan());
              holder.txClassName.setText(recyclerModels.get(position).getMatn());

              Picasso.get()
                      .load(R.drawable.usericon)
                      .centerCrop()
                      .fit()
                      .into(holder.imageView);
          }

    }

    @Override
    public int getItemCount() {
        return recyclerModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txSchoolName,txClassName,txStudentNameInHozorGhiyab,
                 txNameDars,txCountHazeri,txCountGheybat,txTedadStudentInClass,
                 txTedadHazerinInLinsJalasat,txTedadGhayebinInListJalasat,
                 txOnvanMessageInRecivedMessage,txMatnMessageInRecivedMessage,
                 txNameFerestandeInRecivedMessage,txNameGirandehInRecivedMessage,
                 txUserNameInSearchInTeacher,txVaziyatDarsiVaAkhlaghi,txTaklif,txDate,txErsalNazar,
                 txDate2,txVaziyat,txSaatVorod,txSaatKhoroj,txDate1,txTarikhDarkhast,txAzTarikh,txTaTarikh,txElat,TxVaziyatt;
        ImageView imageView,imgRemoveStudent,imgRemoveJalase,imgRemoveClass,imgErsalNazarIcon,
        imgHazerGhayebTik,imgChoiseUserInSearchInTeacher,imgChoiseReciverSendNewMessageInTeacher,imgRemoveMessage,imgAddStudent,imgVaziyatTaeid,
        imgHozorGhiyab,imgUserPictureForSendMessageInTeacher,imgIconDate,imgReadOrNo;
        ConstraintLayout cl,clHazerGhayebTik,clVaziyatDarsi,clVaziyatTaeid;
        Spinner spinnerTakhirStudent;
        EditText etTimeTakhirStudent,etVaziyatDarsiStudent,etVaziyatAkhlaghiStudent;
        MyViewHolder(View view) {
            super(view);
            clVaziyatTaeid = itemView.findViewById(R.id.clVaziyatTaeid);
            imgVaziyatTaeid = itemView.findViewById(R.id.imgVaziyatTaeid);

            txTarikhDarkhast = itemView.findViewById(R.id.txTarikhDarkhast);
            txAzTarikh = itemView.findViewById(R.id.txDateAz);
            txTaTarikh = itemView.findViewById(R.id.txDateTa);
            txElat = itemView.findViewById(R.id.txElat);
            TxVaziyatt = itemView.findViewById(R.id.txVaziyat);

            txDate1 = itemView.findViewById(R.id.txDate);
            txDate2 = itemView.findViewById(R.id.txDate);
            txVaziyat = itemView.findViewById(R.id.txVaziyat);
            txSaatVorod = itemView.findViewById(R.id.txSaatVorod);
            txSaatKhoroj = itemView.findViewById(R.id.txSaatKhoroj);

            imgReadOrNo = itemView.findViewById(R.id.imgReadOrNotRead);
            txErsalNazar = itemView.findViewById(R.id.txErsalNazar);
            imgErsalNazarIcon = itemView.findViewById(R.id.imgErsalNazarIcon);

            imgUserPictureForSendMessageInTeacher = itemView.findViewById(R.id.imgUserPictureForSendMessageInTeacher);
            etTimeTakhirStudent = itemView.findViewById(R.id.etTimeTakhirStudent);
            etVaziyatDarsiStudent = itemView.findViewById(R.id.etVaziyatDarsiStudent);
            etVaziyatAkhlaghiStudent = itemView.findViewById(R.id.etVaziyatAkhlaghiStudent);

            imgHozorGhiyab = itemView.findViewById(R.id.imgHozorGhiyab);
            imgAddStudent = itemView.findViewById(R.id.imgAddStudent);

            txDate = itemView.findViewById(R.id.txDate);
            imgIconDate = itemView.findViewById(R.id.imgIconDate);

            imgRemoveMessage = itemView.findViewById(R.id.imgRemoveMessage);
            spinnerTakhirStudent= itemView.findViewById(R.id.spinnerInHozorGhiyab);
            imgChoiseReciverSendNewMessageInTeacher= itemView.findViewById(R.id.imgChoiseReciverSendNewMessageInTeacher);
            imgChoiseUserInSearchInTeacher= itemView.findViewById(R.id.imgChoiseUserForSendNewMessageInTeacher);
            txUserNameInSearchInTeacher= itemView.findViewById(R.id.txUserNameForSendMessageInTeacher);

            txOnvanMessageInRecivedMessage= itemView.findViewById(R.id.txOnvanRecevedMessage);
            txMatnMessageInRecivedMessage= itemView.findViewById(R.id.txMatnRecivedMessage);
            txNameFerestandeInRecivedMessage= itemView.findViewById(R.id.txNameFrestandeInRecivedMessage);
            txNameGirandehInRecivedMessage= itemView.findViewById(R.id.txNameGirandeInRecivedMessage);

            txNameDars= itemView.findViewById(R.id.txDarsNameInStudentDarsList);
            txVaziyatDarsiVaAkhlaghi= itemView.findViewById(R.id.txVaziyatDarsiVaAkhlaghiInStudentDarsList);
            txCountHazeri= itemView.findViewById(R.id.txTedadHozorInStudentDarsList);
            txCountGheybat= itemView.findViewById(R.id.txTedadGheybatInStudentDarsList);
            clVaziyatDarsi= itemView.findViewById(R.id.clVaziyatDarsi);

            txTaklif = itemView.findViewById(R.id.txTaklifInListJalasat2);
            txSchoolName = itemView.findViewById(R.id.txSchoolName);
            txClassName =  itemView.findViewById(R.id.txClassName);
            txTedadHazerinInLinsJalasat = itemView.findViewById(R.id.txTedadHazerinInListJalasat);
            txTedadGhayebinInListJalasat = itemView.findViewById(R.id.txTedadGhayebinInListJalasat);
            txTedadStudentInClass = itemView.findViewById(R.id.txTedadStudentInClass);
            txStudentNameInHozorGhiyab=  itemView.findViewById(R.id.txStudentNameInHozorGhiyab);
            imageView = itemView.findViewById(R.id.imgAddClass);
            imgRemoveStudent = itemView.findViewById(R.id.imgRemoveStudent);
            imgRemoveJalase = itemView.findViewById(R.id.imgRemoveJalase);
            imgRemoveClass = itemView.findViewById(R.id.imgRemoveClass);
            imgHazerGhayebTik = itemView.findViewById(R.id.imgHazerGhayebTik);

            cl = itemView.findViewById(R.id.clClickInRowAddJalase);
            clHazerGhayebTik = itemView.findViewById(R.id.clHazerGhayebTik);
        }
    }
    public void showCustomDialog(final Context context, final int position, final String method, final ImageView imgVaziyatTaeid) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_dialog, null, false);
        TextView txCancel = view.findViewById(R.id.txCancel);
        TextView txRemove = view.findViewById(R.id.txRemove);
        TextView txHazfPayam = view.findViewById(R.id.txHazfPayam);
        TextView txMatnHazfPayam = view.findViewById(R.id.txMatnHazfPayam);
        TextView txRad = view.findViewById(R.id.txRad);

        if (method.equals("taeid_gozaresh")){
            txHazfPayam.setText("تایید گزارش");
            txMatnHazfPayam.setText("آیا گزارش تایید شود؟");
            txRemove.setText("تایید");
            txRemove.setTextColor(Color.parseColor("#008000"));
            txRad.setVisibility(View.VISIBLE);
        }

        txCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        txRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (method.equals("delete")){
                    LoadData.removeMessage(c,recyclerModels.get(position).getId());
                    recyclerModels.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, recyclerModels.size());
                }else {
                    LoadData.updateVaziyatGozaresh(c,recyclerModels.get(position).getId(),imgVaziyatTaeid,"تایید شده");
                }
                dialog.dismiss();
            }
        });

        txRad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadData.updateVaziyatGozaresh(c,recyclerModels.get(position).getId(),imgVaziyatTaeid,"رد شده");
                dialog.dismiss();
            }
        });

        ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.setContentView(view);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        dialog.show();
    }

}