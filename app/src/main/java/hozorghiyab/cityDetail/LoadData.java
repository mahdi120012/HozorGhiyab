package hozorghiyab.cityDetail;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.hozorghiyab.R;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import hozorghiyab.MySingleton;
import hozorghiyab.activities.ListPayamHayeErsali;
import hozorghiyab.cityDetail.placeComment.RecyclerAdapterPlaceComment;
import hozorghiyab.cityDetail.placeComment.RecyclerModelPlaceComment;
import hozorghiyab.listCityACT.CityAdapter;

public class LoadData {

    public static final int LOAD_LIMIT = 15;
    public static String lastId = "0";
    public static boolean itShouldLoadMore = true;
    public static String tedadPayamKhangeNashodeServise = "";

    public static void firstLoadData(final Context c, final CityAdapter recyclerAdapter,
                                     final ArrayList<RecyclerModel> recyclerModels,
                                     final ImageView img_refresh, final WebView webView,
                                     final Timer timer, final TextView net_state,
                                     final RecyclerView recyclerView, final String city,
                                     final String cat) {


        //String url = "http://robika.ir/ultitled/practice/auto_load_more_for_safarkon.php?limit=" + LOAD_LIMIT;
        String url= "http://robika.ir/ultitled/practice/auto_load_more_for_safarkon.php?action=first&limit=" + LOAD_LIMIT + "&city=" + city + "&cat=" + cat;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                webView.setVisibility(View.GONE);
                //img_refresh.setVisibility(View.GONE);
                net_state.setText("");
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        String onvan = jsonObject.getString("onvan");
                        String matn = jsonObject.getString("matn");
                        String picture = jsonObject.getString("p1");
                        String city = jsonObject.getString("city");
                        String position = jsonObject.getString("position");
                        float rate = Float.valueOf(jsonObject.getString("rate"));
                        String countRateAndComment = jsonObject.getString("count_rate_comment");
                        recyclerModels.add(new RecyclerModel(lastId,onvan, matn,picture,city,"","",countRateAndComment,0));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

                net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        LoadData.firstLoadData(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,cat);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }


    public static void firstLoadDataPlaceAct(final Context c, final RecyclerAdapter recyclerAdapter,
                                             final ArrayList<RecyclerModel> recyclerModels,
                                             final ImageView img_refresh, final WebView webView,
                                             final Timer timer, final TextView net_state,
                                             final RecyclerView recyclerView, final String city,
                                             final String cat) {


        //String url = "http://robika.ir/ultitled/practice/auto_load_more_for_safarkon.php?limit=" + LOAD_LIMIT;
        String url= "http://robika.ir/ultitled/practice/auto_load_more_for_safarkon.php?action=first&limit=" + LOAD_LIMIT + "&city=" + city + "&cat=" + cat;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                webView.setVisibility(View.GONE);
                img_refresh.setVisibility(View.GONE);
                net_state.setText("");
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        String onvan = jsonObject.getString("onvan");
                        String matn = jsonObject.getString("matn");
                        String picture = jsonObject.getString("p1");
                        String city = jsonObject.getString("city");
                        String position = jsonObject.getString("position");
                        //float rate = Float.valueOf(jsonObject.getString("rate"));
                        recyclerModels.add(new RecyclerModel(lastId,onvan, matn,picture,city,"","","",0));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

                net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        LoadData.firstLoadDataPlaceAct(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,cat);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }

    public static void firstLoadDataRate(final Context c,
                                         final ImageView img_refresh, final WebView webView,
                                         final Timer timer, final TextView net_state, final String id,
                                         final SimpleRatingBar ratingBar, final TextView numberOfRate) {

        String url= "http://robika.ir/ultitled/practice/auto_load_more_for_safarkon.php?action=loadrate&limit=" + LOAD_LIMIT + "&id=" + id;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                webView.setVisibility(View.GONE);
                img_refresh.setVisibility(View.GONE);
                net_state.setText("");
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                float rate;

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        //line zir bara ine ke float null nmigire va age null begire error mide, pass avval baresi mikonim
                        //null ee ya na:
                        if (jsonObject.has("rate") && !jsonObject.isNull("rate")) {
                            rate = Float.valueOf(jsonObject.getString("rate"));
                            ratingBar.setRating(rate);
                        }

                        String numberOfAllRate = (jsonObject.getString("all_rate_count"));
                        numberOfRate.setText(numberOfAllRate + " نظر");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

                net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);




                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }
    public static void sendRate(final Context c, final RecyclerAdapterPlaceComment recyclerAdapter,
                                final ArrayList<RecyclerModelPlaceComment> recyclerModels,
                                final ImageView img_refresh, final WebView webView,
                                final Timer timer, final TextView net_state,
                                final RecyclerView recyclerView,String username,String post_id,String rate,
                                final SimpleRatingBar ratingBar, final TextView numberOfRate) {

        String url= "http://robika.ir/ultitled/practice/auto_load_more_for_safarkon.php?action=sendrate&limit=" + LOAD_LIMIT + "&username=" + username + "&post_id=" + post_id + "&rate=" + rate;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                webView.setVisibility(View.GONE);
                img_refresh.setVisibility(View.GONE);
                net_state.setText("");
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        float rate = Float.valueOf(jsonObject.getString("rate"));
                        String numberOfAllRate = (jsonObject.getString("all_rate_count"));
                        ratingBar.setRating(rate);
                        numberOfRate.setText(numberOfAllRate + " نظر");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                //Toast.makeText(c, response, Toast.LENGTH_SHORT).show();





            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

                net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        //LoadData.firstLoadDataPlaceComment(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,id);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }


    public static void removeMessage(final Context c, String messageId) {

        String messageIdEncode="";
        try {
            messageIdEncode = URLEncoder.encode(messageId,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=remove_message&message_id=" + messageIdEncode;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        //etComment.setText("");
                        if (response.equals("send_shod")){
                            //Toast.makeText(c, "ارسال شد", Toast.LENGTH_SHORT).show();
                            //Line Zir Baraye neshon dadan comment pas az ersal comment be server va namayesh to recyclerviewee.
                            //LoadData.loadMoreStudent(c,rAdapterYouHaveKnow,recyclerModels,progressBar);
                            Toast.makeText(c, "حذف شد", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(c, "حذف نشد", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

               /* net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        //LoadData.firstLoadDataPlaceComment(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,id);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });*/


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }


    public static void removeJalase(final Context c, String jalase_id,String classId) {

        String jalaseIdEncode="";
        String classIdEncode="";
        try {
            jalaseIdEncode = URLEncoder.encode(jalase_id,"UTF-8");
            classIdEncode = URLEncoder.encode(classId,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=remove_jalase&jalase_id=" + jalaseIdEncode + "&class_id=" + classIdEncode;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        //etComment.setText("");
                        if (response.equals("send_shod")){
                            //Toast.makeText(c, "ارسال شد", Toast.LENGTH_SHORT).show();
                            //Line Zir Baraye neshon dadan comment pas az ersal comment be server va namayesh to recyclerviewee.
                            //LoadData.loadMoreStudent(c,rAdapterYouHaveKnow,recyclerModels,progressBar);
                            Toast.makeText(c, "حذف شد", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(c, "حذف نشد", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

               /* net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        //LoadData.firstLoadDataPlaceComment(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,id);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });*/


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);;


    }


    public static void removeClass(final Context c, String classId) {

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=remove_class&class_id=" + classId;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        //etComment.setText("");
                        if (response.equals("send_shod")){
                            //Toast.makeText(c, "ارسال شد", Toast.LENGTH_SHORT).show();
                            //Line Zir Baraye neshon dadan comment pas az ersal comment be server va namayesh to recyclerviewee.
                            //LoadData.loadMoreStudent(c,rAdapterYouHaveKnow,recyclerModels,progressBar);
                            Toast.makeText(c, "حذف شد", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(c, "حذف نشد", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

               /* net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        //LoadData.firstLoadDataPlaceComment(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,id);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });*/


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }

    public static void removeStudent(final Context c, String student_id) {

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=remove_student&student_id=" + student_id;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        //etComment.setText("");
                        if (response.equals("send_shod")){
                            //Toast.makeText(c, "ارسال شد", Toast.LENGTH_SHORT).show();
                            //Line Zir Baraye neshon dadan comment pas az ersal comment be server va namayesh to recyclerviewee.
                            //LoadData.loadMoreStudent(c,rAdapterYouHaveKnow,recyclerModels,progressBar);
                            Toast.makeText(c, "حذف شد", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(c, "حذف نشد", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

               /* net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        //LoadData.firstLoadDataPlaceComment(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,id);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });*/


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }

    public static void sendStudent(final Context c, final RecyclerAdapterYouHaveKnow rAdapterYouHaveKnow,
                                   final ArrayList<RecyclerModel> recyclerModels,
                                   final ImageView img_refresh, final WebView webView,
                                   final Timer timer, final TextView net_state,
                                   final RecyclerView recyclerView, String studentName,
                                   final String className, final String username, final ProgressBar progressBar) {
        String classNameEncode="";
        String studentNameEncode="";
        try {
            classNameEncode = URLEncoder.encode(className,"UTF-8");
            studentNameEncode = URLEncoder.encode(studentName,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=send_student&limit=" + LOAD_LIMIT + "&student_name=" + studentNameEncode + "&class=" + classNameEncode + "&user1=" + username;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        webView.setVisibility(View.GONE);
                        img_refresh.setVisibility(View.GONE);
                        net_state.setText("");
                        progressDialog.dismiss();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "دانش آموزی پیدا نشد", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        //etComment.setText("");
                        if (response.equals("send_shod")){
                            //Toast.makeText(c, "ارسال شد", Toast.LENGTH_SHORT).show();
                            //Line Zir Baraye neshon dadan comment pas az ersal comment be server va namayesh to recyclerviewee.
                            LoadData.loadMoreStudent(c,rAdapterYouHaveKnow,recyclerModels,progressBar,username,className);

                        }else {
                            Toast.makeText(c, "ارسال نشد", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

                net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        //LoadData.firstLoadDataPlaceComment(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,id);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }

    public static void sendJalase(final Context c, final RecyclerAdapterYouHaveKnow rAdapterYouHaveKnow,
                                  final ArrayList<RecyclerModel> recyclerModels,
                                  final ImageView img_refresh, final WebView webView,
                                  final Timer timer, final TextView net_state,
                                  final RecyclerView recyclerView, final ProgressBar progressBar,
                                  String jalaseName, final String className,
                                  final String username) {

        String classNameEncode="";
        String jalaseNameEncode="";
        try {
            classNameEncode = URLEncoder.encode(className,"UTF-8");
            jalaseNameEncode = URLEncoder.encode(jalaseName,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        final String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=send_jalase&limit=" + LOAD_LIMIT + "&name=" + jalaseNameEncode + "&class=" + classNameEncode + "&user1=" + username;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        webView.setVisibility(View.GONE);
                        img_refresh.setVisibility(View.GONE);
                        net_state.setText("");
                        progressDialog.dismiss();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        //etComment.setText("");
                        if (response.equals("send_shod")){
                            //Toast.makeText(c, "ارسال شد", Toast.LENGTH_SHORT).show();
                            //Line Zir Baraye neshon dadan comment pas az ersal comment be server va namayesh to recyclerviewee.
                            LoadData.loadMorejalase(c,rAdapterYouHaveKnow,recyclerModels,progressBar,username,className);

                        }else {
                            Toast.makeText(c, "ارسال نشد", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

                net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        //LoadData.firstLoadDataPlaceComment(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,id);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }

    public static void loadMorejalase(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                       final ArrayList<RecyclerModel> recyclerModels,
                                       final ProgressBar progressBar,String username,String className) {

        String classNameEncode="";
        try {
            classNameEncode = URLEncoder.encode(className,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=loadmore_jalase&lastId=" + lastId + "&limit=" + LOAD_LIMIT + "&user1=" + username + "&class=" + classNameEncode ;

        itShouldLoadMore = false;
        progressBar.setVisibility(View.VISIBLE);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);

                itShouldLoadMore = true;
                if (response.length() <= 0) {
                    // we need to check this, to make sure, our dataStructure JSonArray contains
                    // something
                    //Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();
                    return; // return will end the program at this point
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        lastId = jsonObject.getString("jalase_id");
                        String jalaseName = jsonObject.getString("name");
                        String className = jsonObject.getString("class_name");
                        String classId = jsonObject.getString("class_id");
                        String tedadHazerin = jsonObject.getString("tedad_hazerin");
                        String tedadGhayebin = jsonObject.getString("tedad_ghayebin");
                        recyclerModels.add(new RecyclerModel(lastId,jalaseName, className,"",classId,tedadHazerin,"",tedadGhayebin,0));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                itShouldLoadMore = true;
                Toast.makeText(c, "خطار در بارگزاری . دسترسی به اینترنت موجود نیست", Toast.LENGTH_SHORT).show();

            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);

    }

    public static void loadMoreStudent(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                     final ArrayList<RecyclerModel> recyclerModels,
                                     final ProgressBar progressBar,String username,String className) {

        String classNameEncode="";
        try {
            classNameEncode = URLEncoder.encode(className,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=loadmore_student&lastId=" + lastId + "&limit=" + LOAD_LIMIT + "&user1=" + username + "&class=" + classNameEncode;

        itShouldLoadMore = false;
        progressBar.setVisibility(View.VISIBLE);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);

                itShouldLoadMore = true;
                if (response.length() <= 0) {
                    // we need to check this, to make sure, our dataStructure JSonArray contains
                    // something
                    //Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();
                    return; // return will end the program at this point
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        String std_id = jsonObject.getString("std_id");
                        String classId = jsonObject.getString("class_id");
                        String teacherId = jsonObject.getString("teacher_id");
                        String studentName = jsonObject.getString("std_name");
                        String className = jsonObject.getString("class_name");
                        String studentPicture = jsonObject.getString("std_picture");
                        recyclerModels.add(new RecyclerModel(lastId,studentName, className,studentPicture,"","","","",0));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                itShouldLoadMore = true;
                Toast.makeText(c, "خطار در بارگزاری . دسترسی به اینترنت موجود نیست", Toast.LENGTH_SHORT).show();

            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);

    }
    public static void sendMessageStudent(final Context c, final RecyclerAdapterYouHaveKnow rAdapterYouHaveKnow,
                                          final ArrayList<RecyclerModel> recyclerModels,
                                          final RecyclerView recyclerView,
                                          final String username, String stdId,
                                          String onvan, String matn,String nowTime) {
        String userNameEncode="";
        String stdIdEncode="";
        String onvanEncode="";
        String matnEncode="";
        String nowTimeEncode="";
        try {
            userNameEncode = URLEncoder.encode(username,"UTF-8");
            stdIdEncode = URLEncoder.encode(stdId,"UTF-8");
            onvanEncode = URLEncoder.encode(onvan,"UTF-8");
            matnEncode = URLEncoder.encode(matn,"UTF-8");
            nowTimeEncode = URLEncoder.encode(nowTime,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=send_message_student&username1=" + userNameEncode + "&std_id=" + stdIdEncode + "&onvan=" + onvanEncode + "&matn=" + matnEncode + "&now_time=" + nowTimeEncode;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        /*webView.setVisibility(View.GONE);
                        img_refresh.setVisibility(View.GONE);
                        net_state.setText("");*/
                        progressDialog.dismiss();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        //etComment.setText("");
                        if (response.equals("send_shod")){
                            Toast.makeText(c, "ارسال شد", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(c, ListPayamHayeErsali.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            c.startActivity(intent);
                            //Line Zir Baraye neshon dadan comment pas az ersal comment be server va namayesh to recyclerviewee.
                            //LoadData.loadMoreClass(c,rAdapterYouHaveKnow,recyclerModels,progressBar,username);

                        }else {
                            Toast.makeText(c, "ارسال نشد", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

                /*net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        //LoadData.firstLoadDataPlaceComment(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,id);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });
*/

            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }


    public static void sendVorodKhoroj(final Context c, final String username,
                                       String saatVorod, String saatKhoroj, String date, final ConstraintLayout clWifi) {

        String userNameEncode= UrlEncoderClass.urlEncoder(username);
        String saatVorodEncode= UrlEncoderClass.urlEncoder(saatVorod);
        String saatKhorojEncode= UrlEncoderClass.urlEncoder(saatKhoroj);
        String dateEncode= UrlEncoderClass.urlEncoder(date);

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=send_vorod_khoroj&username1=" + userNameEncode + "&saat_vorod=" + saatVorodEncode + "&saat_khoroj=" + saatKhorojEncode + "&date=" + dateEncode;
        itShouldLoadMore = false;
        ProgressDialogClass.showProgress(c);

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        clWifi.setVisibility(View.GONE);
                        ProgressDialogClass.dismissProgress();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        if (response.equals("send_shod")){
                            Toast.makeText(c, "ارسال شد", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(c, ListPayamHayeErsali.class);
                            intent.putExtra("vorod_khoroj", "vorod_khoroj");
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            c.startActivity(intent);

                            //Line Zir Baraye neshon dadan comment pas az ersal comment be server va namayesh to recyclerviewee.
                            //LoadData.loadMoreClass(c,rAdapterYouHaveKnow,recyclerModels,progressBar,username);

                        }else {
                            Toast.makeText(c, "ارسال نشد", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                ProgressDialogClass.dismissProgress();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();
                clWifi.setVisibility(View.VISIBLE);

            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }

    public static void sendGozareshKar(final Context c, final RecyclerAdapterYouHaveKnow rAdapterYouHaveKnow,
                                          final ArrayList<RecyclerModel> recyclerModels,
                                          final String username, String idDaryaftKonande,
                                          String gozaresh, String natige, String date, final ConstraintLayout clWifi) {

        String userNameEncode= UrlEncoderClass.urlEncoder(username);
        String idDaryaftKonandeEncode= UrlEncoderClass.urlEncoder(idDaryaftKonande);
        String gozareshEncode= UrlEncoderClass.urlEncoder(gozaresh);
        String natigeEncode= UrlEncoderClass.urlEncoder(natige);
        String dateEncode= UrlEncoderClass.urlEncoder(date);

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=send_gozaresh_kar&username1=" + userNameEncode + "&id_daryaft_konande=" + idDaryaftKonandeEncode + "&gozaresh=" + gozareshEncode + "&natige=" + natigeEncode + "&date=" + dateEncode;
        itShouldLoadMore = false;
        ProgressDialogClass.showProgress(c);

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        clWifi.setVisibility(View.GONE);
                        ProgressDialogClass.dismissProgress();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        if (response.equals("send_shod")){
                            Toast.makeText(c, "ارسال شد", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(c, ListPayamHayeErsali.class);
                            intent.putExtra("gozaresh_kar", "gozaresh_kar");
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            c.startActivity(intent);

                            //Line Zir Baraye neshon dadan comment pas az ersal comment be server va namayesh to recyclerviewee.
                            //LoadData.loadMoreClass(c,rAdapterYouHaveKnow,recyclerModels,progressBar,username);

                        }else {
                            Toast.makeText(c, "ارسال نشد", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                ProgressDialogClass.dismissProgress();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();
                clWifi.setVisibility(View.VISIBLE);

            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }

    public static void sendMessageTeacher(final Context c, final RecyclerAdapterYouHaveKnow rAdapterYouHaveKnow,
                                          final ArrayList<RecyclerModel> recyclerModels,
                                          final String username, String stdId,
                                          String onvan, String matn, final ConstraintLayout clWifi,String nowTime,String noe) {

        String userNameEncode= UrlEncoderClass.urlEncoder(username);
        String stdIdEncode= UrlEncoderClass.urlEncoder(stdId);
        String onvanEncode= UrlEncoderClass.urlEncoder(onvan);
        String matnEncode= UrlEncoderClass.urlEncoder(matn);
        String nowTimeEncode= UrlEncoderClass.urlEncoder(nowTime);
        String noeEncode= UrlEncoderClass.urlEncoder(noe);

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=send_message_teacher&username1=" + userNameEncode + "&std_id=" + stdIdEncode + "&onvan=" + onvanEncode + "&matn=" + matnEncode + "&now_time=" + nowTimeEncode + "&noe=" + noeEncode;
        itShouldLoadMore = false;
        ProgressDialogClass.showProgress(c);

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        clWifi.setVisibility(View.GONE);
                        ProgressDialogClass.dismissProgress();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        if (response.equals("send_shod")){
                            Toast.makeText(c, "ارسال شد", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(c, ListPayamHayeErsali.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            c.startActivity(intent);
                            //Line Zir Baraye neshon dadan comment pas az ersal comment be server va namayesh to recyclerviewee.
                            //LoadData.loadMoreClass(c,rAdapterYouHaveKnow,recyclerModels,progressBar,username);

                        }else {
                            Toast.makeText(c, "ارسال نشد", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                ProgressDialogClass.dismissProgress();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();
                clWifi.setVisibility(View.VISIBLE);

            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }


    public static void sendClass(final Context c, final RecyclerAdapterYouHaveKnow rAdapterYouHaveKnow,
                                 final ArrayList<RecyclerModel> recyclerModels,
                                 final ImageView img_refresh, final WebView webView,
                                 final Timer timer, final TextView net_state,
                                 final RecyclerView recyclerView, String schoolName,
                                 String className, final String username, final ProgressBar progressBar) {
        String classNameEncode="";
        String schoolNameEncode="";
        try {
            classNameEncode = URLEncoder.encode(className,"UTF-8");
            schoolNameEncode = URLEncoder.encode(schoolName,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=sendclass&limit=" + LOAD_LIMIT + "&school=" + schoolNameEncode + "&class=" + classNameEncode + "&user1=" + username;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        webView.setVisibility(View.GONE);
                        img_refresh.setVisibility(View.GONE);
                        net_state.setText("");
                        progressDialog.dismiss();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        //etComment.setText("");
                        if (response.equals("send_shod")){
                            //Toast.makeText(c, "ارسال شد", Toast.LENGTH_SHORT).show();
                            //Line Zir Baraye neshon dadan comment pas az ersal comment be server va namayesh to recyclerviewee.
                            LoadData.loadMoreClass(c,rAdapterYouHaveKnow,recyclerModels,progressBar,username);

                        }else {
                            Toast.makeText(c, "ارسال نشد", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

                net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        //LoadData.firstLoadDataPlaceComment(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,id);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }


    public static void firstLoadDataPlaceComment(final Context c, final RecyclerAdapterPlaceComment recyclerAdapter,
                                                 final ArrayList<RecyclerModelPlaceComment> recyclerModels,
                                                 final ImageView img_refresh, final WebView webView,
                                                 final Timer timer, final TextView net_state,
                                                 final RecyclerView recyclerView, final String city,
                                                 final String cat) {

        String url= "http://robika.ir/ultitled/practice/auto_load_more_for_safarkon.php?action=comment&limit=" + LOAD_LIMIT + "&city=" + city + "&cat=" + cat;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                webView.setVisibility(View.GONE);
                img_refresh.setVisibility(View.GONE);
                net_state.setText("");
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    //Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        String username = jsonObject.getString("username");
                        String name = jsonObject.getString("name");
                        String picture = jsonObject.getString("picture");
                        String comment = jsonObject.getString("comment");
                        recyclerModels.add(new RecyclerModelPlaceComment(username, name,picture,comment));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

                net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        LoadData.firstLoadDataPlaceComment(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,cat);

                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);
                    }
                });
            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }



    public static void loadCountMessageNotRead(final Context c, final TextView txCountNotReadMessage,
                                                             String username) {

        String usernameEncode = UrlEncoderClass.urlEncoder(username);

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_count_not_read_message&user1=" + usernameEncode;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                if (response.length() <= 0) {
                    //Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }
                String tedadPayamKhangeNashode = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        tedadPayamKhangeNashode = jsonObject.getString("tedad_payam_khande_nashode");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                txCountNotReadMessage.setText(tedadPayamKhangeNashode);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }

    public static void loadVorodKhorojGhabli(final Context c,final EditText etSaatVorod,
                                                             final EditText etSaatKhoroj,
                                                             String tarikh,
                                                             String username, final ConstraintLayout clWifi) {

        String usernameEncode = UrlEncoderClass.urlEncoder(username);
        String tarikhEncode = UrlEncoderClass.urlEncoder(tarikh);
        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_saat_vorod_khoroj_ghabli&user1=" + usernameEncode + "&tarikhEncode=" + tarikhEncode;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                clWifi.setVisibility(View.GONE);

                if (response.length() <= 0) {
                    //Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();
                    return;
                }
                String tarikh = null;
                String saatVorod = null;
                String saatKhoroj = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        tarikh = jsonObject.getString("tarikh");
                        saatVorod = jsonObject.getString("saat_vorod");
                        saatKhoroj = jsonObject.getString("saat_khoroj");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                etSaatVorod.setText(saatVorod);
                etSaatKhoroj.setText(saatKhoroj);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                clWifi.setVisibility(View.VISIBLE);
            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }

    public static String loadTeacherNameAndCountMessageNotReadForServise(final Context c, String username) {


        String usernameEncode = UrlEncoderClass.urlEncoder(username);
        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_teacher_name&user1=" + usernameEncode;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {


                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();
                    return;
                }

                String studentName = null;
                String teacherPicture = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        studentName = jsonObject.getString("family");
                        tedadPayamKhangeNashodeServise = jsonObject.getString("tedad_payam_khande_nashode");
                        teacherPicture = jsonObject.getString("picture");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
        return tedadPayamKhangeNashodeServise;
    }


    public static void loadTeacherNameAndCountMessageNotRead(final Context c, final TextView txStudentName,
                                                             final TextView txCountNotReadMessage,
                                                             String username, final ImageView imgTeacherPicture,
                                                             final ConstraintLayout clWifi) {

        String usernameEncode = UrlEncoderClass.urlEncoder(username);
        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_teacher_name&user1=" + usernameEncode;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                clWifi.setVisibility(View.GONE);

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();
                    return;
                }

                String studentName = null;
                String tedadPayamKhangeNashode = null;
                String teacherPicture = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        studentName = jsonObject.getString("family");
                        tedadPayamKhangeNashode = jsonObject.getString("tedad_payam_khande_nashode");
                        teacherPicture = jsonObject.getString("picture");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                txStudentName.setText(studentName);
                txCountNotReadMessage.setText(tedadPayamKhangeNashode);

                if (teacherPicture.isEmpty()) {

                    Picasso.get()
                            .load(R.drawable.usericon)
                            .centerInside()
                            .fit()
                            .error(R.drawable.usericon)
                            .placeholder(R.drawable.usericon)
                            .into(imgTeacherPicture);

                }else{
                    Picasso.get()
                            .load(teacherPicture)
                            .centerInside()
                            .fit()
                            .error(R.drawable.usericon)
                            .placeholder(R.drawable.usericon)
                            .into(imgTeacherPicture);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                clWifi.setVisibility(View.VISIBLE);
            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }


    public static String loadStudentNameAndCountMessageNotRead(final Context c, final String urlAppend,
                                                             final TextView net_state,
                                                             final TextView txStudentName, final TextView txCountNotReadMessage,
                                                             final ImageView imgStudentPicture, final ConstraintLayout clWifi) {
        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php"+urlAppend;
        itShouldLoadMore = false;
        //ProgressDialogClass.showProgress(c);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                clWifi.setVisibility(View.GONE);
                net_state.setVisibility(View.GONE);
                //ProgressDialogClass.dismissProgress();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    //Toast.makeText(c, "اطلاعاتی موجود نیست.", Toast.LENGTH_SHORT).show();
                    return;
                }
                String studentName = null;
                String tedadPayamKhangeNashode = null;
                String studentPicture = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        studentName = jsonObject.getString("family");
                        tedadPayamKhangeNashode = jsonObject.getString("tedad_payam_khande_nashode");
                        studentPicture = jsonObject.getString("picture");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                txStudentName.setText(studentName);
                txCountNotReadMessage.setText(tedadPayamKhangeNashode);

                if (studentPicture.isEmpty()) {

                    Picasso.get()
                            .load(R.drawable.usericon)
                            .centerInside()
                            .fit()
                            .error(R.drawable.usericon)
                            .placeholder(R.drawable.usericon)
                            .into(imgStudentPicture);

                }else{
                    Picasso.get()
                            .load(studentPicture)
                            .centerInside()
                            .fit()
                            .error(R.drawable.usericon)
                            .placeholder(R.drawable.usericon)
                            .into(imgStudentPicture);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                //ProgressDialogClass.dismissProgress();
                //Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();
                clWifi.setVisibility(View.VISIBLE);
                net_state.setVisibility(View.VISIBLE);

                clWifi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        LoadData.loadStudentNameAndCountMessageNotRead(c,urlAppend,
                                net_state,txStudentName,txCountNotReadMessage,imgStudentPicture,clWifi);

                    }
                });
            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);

         return txCountNotReadMessage.getText().toString();

    }

    public static void loadTimeTakhirList(final Context c, final List<String> list,
                                     final ArrayAdapter<String> spinnerArrayAdapter,
                                     final ImageView img_refresh, final WebView webView,
                                     final Timer timer, final TextView net_state,
                                     final RecyclerAdapterYouHaveKnow rAdapterYouHaveKnow,
                                     final ArrayList<RecyclerModel> rModelsYouHaveKnow) {

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_time_takhir&limit=" + LOAD_LIMIT;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                webView.setVisibility(View.GONE);
                img_refresh.setVisibility(View.GONE);
                net_state.setText("");
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        String className = jsonObject.getString("time_takhir");

                        rModelsYouHaveKnow.add(new RecyclerModel(lastId,className, "","","","","","",0));
                        rAdapterYouHaveKnow.notifyDataSetChanged();

                        list.add(className);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                spinnerArrayAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

                net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        //LoadData.firstLoadDataYH(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,cat);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }

    public static void loadClassList(final Context c, final List<String> list,
                                     final ArrayAdapter<String> spinnerArrayAdapter,
                                     final ImageView img_refresh, final WebView webView,
                                     final Timer timer, final TextView net_state, String username,
                                     final RecyclerAdapterYouHaveKnow rAdapterYouHaveKnow,
                                     final ArrayList<RecyclerModel> rModelsYouHaveKnow) {

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=loadclass&limit=" + LOAD_LIMIT + "&user1=" + username;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                webView.setVisibility(View.GONE);
                img_refresh.setVisibility(View.GONE);
                net_state.setText("");
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("class_id");
                        String className = jsonObject.getString("class_name");

                        rModelsYouHaveKnow.add(new RecyclerModel(lastId,className, "","","","","","",0));
                        rAdapterYouHaveKnow.notifyDataSetChanged();

                        list.add(className);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                spinnerArrayAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

                net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        //LoadData.firstLoadDataYH(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,cat);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }

    public static void updateStudentInHozorGhiyabGhayebToHazer(final Context c, String student_id,final ImageView imgTik,
                                                               final ArrayList<RecyclerModel> recyclerModels, final int position) {

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=update_student_ghtoh&student_id=" + student_id;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        //etComment.setText("");
                        if (response.equals("send_shod")){
                            //Toast.makeText(c, "ارسال شد", Toast.LENGTH_SHORT).show();
                            //Line Zir Baraye neshon dadan comment pas az ersal comment be server va namayesh to recyclerviewee.
                            //LoadData.loadMoreStudent(c,rAdapterYouHaveKnow,recyclerModels,progressBar);
                            recyclerModels.get(position).setMatn("1");
                            imgTik.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.hazertik));
                            Toast.makeText(c, "ثبت شد", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(c, "ثبت نشد", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

               /* net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        //LoadData.firstLoadDataPlaceComment(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,id);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });*/


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }

    public static void updateHozorGhiyabTozihat(final Context c, String jalaseId,
                                                             String tozihat,String taklif) {
        String tozihatEncode="";
        String taklifEncode="";
        try {
            tozihatEncode = URLEncoder.encode(tozihat,"UTF-8");
            taklifEncode = URLEncoder.encode(taklif,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=update_jalase_tozihat&jalase_id=" + jalaseId + "&tozihat=" + tozihatEncode + "&taklif=" + taklifEncode;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        //etComment.setText("");
                        if (response.equals("send_shod")){
                            //Toast.makeText(c, "ارسال شد", Toast.LENGTH_SHORT).show();
                            //Line Zir Baraye neshon dadan comment pas az ersal comment be server va namayesh to recyclerviewee.
                            //LoadData.loadMoreStudent(c,rAdapterYouHaveKnow,recyclerModels,progressBar);
                            //recyclerModels.get(position).setMatn("0");
                            //imgTik.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.ghayebtik));
                            Toast.makeText(c, "ثبت شد", Toast.LENGTH_SHORT).show();


                        }else {
                            Toast.makeText(c, "ثبت نشد", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

               /* net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        //LoadData.firstLoadDataPlaceComment(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,id);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });*/


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }


    public static void updateStudentInHozorGhiyab_timeTakhir(final Context c, String student_id,
                                                  final ArrayList<RecyclerModel> recyclerModels,
                                                  final int position,final String timeTakhir) {
        String timeTakhirEncode="";
        try {
            timeTakhirEncode = URLEncoder.encode(timeTakhir,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=update_student_time_takhir&student_id=" + student_id + "&time_takhir=" + timeTakhirEncode;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        if (response.equals("send_shod")){
                            Toast.makeText(c, "ثبت شد", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(c, "ثبت نشد", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();



            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }
    public static void updateStudentInHozorGhiyab_VaziyatDarsi(final Context c, String student_id,
                                                             final ArrayList<RecyclerModel> recyclerModels,
                                                             final int position,final String timeTakhir) {
        String timeTakhirEncode="";
        try {
            timeTakhirEncode = URLEncoder.encode(timeTakhir,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=update_student_vaziyat_darsi&student_id=" + student_id + "&time_takhir=" + timeTakhirEncode;
        itShouldLoadMore = false;
        /*final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();*/

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        //progressDialog.dismiss();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        if (response.equals("send_shod")){
                            Toast.makeText(c, "ثبت شد", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(c, "ثبت نشد", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                //progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();



            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }
    public static void updateStudentInHozorGhiyab_VaziyatAkhlaghi(final Context c, String student_id,
                                                             final ArrayList<RecyclerModel> recyclerModels,
                                                             final int position,final String timeTakhir) {
        String timeTakhirEncode="";
        try {
            timeTakhirEncode = URLEncoder.encode(timeTakhir,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=update_student_vaziyat_akhlaghi&student_id=" + student_id + "&time_takhir=" + timeTakhirEncode;
        itShouldLoadMore = false;
        /*final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();*/

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        //progressDialog.dismiss();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        if (response.equals("send_shod")){
                            Toast.makeText(c, "ثبت شد", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(c, "ثبت نشد", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                //progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();



            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }

    public static void updateStudentInHozorGhiyab(final Context c, String student_id, final ImageView imgTik,
                                                  final ArrayList<RecyclerModel> recyclerModels, final int position) {

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=update_student&student_id=" + student_id;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest jsonArrayRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        itShouldLoadMore = true;

                        if (response.length() <= 0) {
                            Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        //etComment.setText("");
                        if (response.equals("send_shod")){
                            //Toast.makeText(c, "ارسال شد", Toast.LENGTH_SHORT).show();
                            //Line Zir Baraye neshon dadan comment pas az ersal comment be server va namayesh to recyclerviewee.
                            //LoadData.loadMoreStudent(c,rAdapterYouHaveKnow,recyclerModels,progressBar);
                            recyclerModels.get(position).setMatn("0");
                            imgTik.setImageDrawable(ContextCompat.getDrawable(c, R.drawable.ghayebtik));
                            Toast.makeText(c, "ثبت شد", Toast.LENGTH_SHORT).show();


                        }else {
                            Toast.makeText(c, "ثبت نشد", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

               /* net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        //LoadData.firstLoadDataPlaceComment(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,id);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });*/


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }


    public static void firstLoadDataStudentForHozorGhiyab(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                                          final ArrayList<RecyclerModel> recyclerModels,
                                                          final ImageView img_refresh, final WebView webView,
                                                          final Timer timer, final TextView net_state,
                                                          final RecyclerView recyclerView, final String selectedClass,
                                                          String jalaseName, String username, final EditText etTozihat,
                                                          final EditText etTaklif) {
        String classNameEncode="";
        String jalaseNameEncode="";
        try {
            classNameEncode = URLEncoder.encode(selectedClass,"UTF-8");
            jalaseNameEncode = URLEncoder.encode(jalaseName,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_student_hozorghiyab&limit=" + LOAD_LIMIT + "&class=" + classNameEncode + "&jalase=" + jalaseNameEncode + "&user1=" + username;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                webView.setVisibility(View.GONE);
                img_refresh.setVisibility(View.GONE);
                net_state.setText("");
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }
                String tozihatJalase = null;
                String taklifJalase = null;
                String vaziyatDarsi = null;
                String vaziyatAkhlaghi = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        String schoolName = jsonObject.getString("std_name");
                        String className = jsonObject.getString("hazer_ya_na");
                        String timeTakhir= jsonObject.getString("time_takhir");
                        String idTimeTakhir= jsonObject.getString("id_time_takhir");
                        String studentPicture = jsonObject.getString("std_picture");
                        tozihatJalase = jsonObject.getString("tozihat");
                        taklifJalase = jsonObject.getString("taklif");
                        vaziyatDarsi = jsonObject.getString("vaziyat_darsi");
                        vaziyatAkhlaghi = jsonObject.getString("vaziyat_akhlaghi");
                        int idTimeTakhirConvert = Integer.parseInt(idTimeTakhir);

                        recyclerModels.add(new RecyclerModel(lastId,schoolName, className,studentPicture,tozihatJalase,timeTakhir,vaziyatDarsi,vaziyatAkhlaghi,idTimeTakhirConvert));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                
                etTozihat.setText(tozihatJalase);
                etTaklif.setText(taklifJalase);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

                net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        //LoadData.firstLoadDataYH(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,cat);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }

    public static void firstLoadDataStudent(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                       final ArrayList<RecyclerModel> recyclerModels,
                                       final ImageView img_refresh, final WebView webView,
                                       final Timer timer, final TextView net_state,
                                       final RecyclerView recyclerView, final String selectedClass,String username) {
        //below line for send persian sting to server dar android 4.4 bekar mire
        String selectedClassEncode="";
        try {
            selectedClassEncode = URLEncoder.encode(selectedClass,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_student&limit=" + LOAD_LIMIT + "&class=" + selectedClassEncode + "&user1=" + username;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                webView.setVisibility(View.GONE);
                img_refresh.setVisibility(View.GONE);
                net_state.setText("");
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        String std_id = jsonObject.getString("std_id");
                        String classId = jsonObject.getString("class_id");
                        String teacherId = jsonObject.getString("teacher_id");
                        String studentName = jsonObject.getString("std_name");
                        String className = jsonObject.getString("class_name");
                        String studentPicture = jsonObject.getString("std_picture");

                        recyclerModels.add(new RecyclerModel(lastId,studentName, className,studentPicture,"","","","",0));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

                net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        //LoadData.firstLoadDataYH(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,cat);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }


    public static void loadDarsResultForStudent(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                            final ArrayList<RecyclerModel> recyclerModels,
                                            final ImageView img_refresh, final WebView webView,
                                            final Timer timer, final TextView net_state,
                                            final RecyclerView recyclerView, final String selectedClass,String username) {
        String selectedClassEncode="";
        try {
            selectedClassEncode = URLEncoder.encode(selectedClass,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_dars_result_student&limit=" + LOAD_LIMIT + "&dars_id=" + selectedClassEncode + "&user1=" + username;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                webView.setVisibility(View.GONE);
                img_refresh.setVisibility(View.GONE);
                net_state.setText("");
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("jalase_id");
                        String jalaseName = jsonObject.getString("jalase_name");
                        //String className = jsonObject.getString("class_name");
                        String classId = jsonObject.getString("class_id");
                        String hazer_ya_na = jsonObject.getString("hazer_ya_na");
                        String vaziyat_darsi = jsonObject.getString("vaziyat_darsi");
                        String vaziyat_akhlaghi = jsonObject.getString("vaziyat_akhlaghi");
                        String taklif = jsonObject.getString("taklif");

                        recyclerModels.add(new RecyclerModel(lastId,jalaseName, classId,hazer_ya_na,vaziyat_darsi,vaziyat_akhlaghi,taklif,"",0));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

                net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        //LoadData.firstLoadDataYH(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,cat);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }



    public static void firstLoadDataJalasat(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                            final ArrayList<RecyclerModel> recyclerModels,
                                            final ImageView img_refresh, final WebView webView,
                                            final Timer timer, final TextView net_state,
                                            final RecyclerView recyclerView, final String selectedClass,String username) {
        String selectedClassEncode="";
        try {
            selectedClassEncode = URLEncoder.encode(selectedClass,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_jalasat&limit=" + LOAD_LIMIT + "&class=" + selectedClassEncode + "&user1=" + username;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                webView.setVisibility(View.GONE);
                img_refresh.setVisibility(View.GONE);
                net_state.setText("");
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("jalase_id");
                        String jalaseName = jsonObject.getString("name");
                        String className = jsonObject.getString("class_name");
                        String classId = jsonObject.getString("class_id");
                        String tedadHazerin = jsonObject.getString("tedad_hazerin");
                        String tedadGhayebin = jsonObject.getString("tedad_ghayebin");
                        recyclerModels.add(new RecyclerModel(lastId,jalaseName, className,"",classId,tedadHazerin,"",tedadGhayebin,0));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

                net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        //LoadData.firstLoadDataYH(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,city,cat);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }

    public static void firstLoadDataDarsList(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                       final ArrayList<RecyclerModel> recyclerModels,
                                       final ImageView img_refresh, final WebView webView,
                                       final Timer timer, final TextView net_state,
                                       final RecyclerView recyclerView,
                                       final String username) {
        String usernameEncode="";
        try {
            usernameEncode = URLEncoder.encode(username,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_dars_list&limit=" + LOAD_LIMIT + "&user1=" + usernameEncode;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                webView.setVisibility(View.GONE);
                img_refresh.setVisibility(View.GONE);
                net_state.setText("");
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        String className = jsonObject.getString("class_name");
                        String teacherId = jsonObject.getString("teacher_id");
                        String teacherName = jsonObject.getString("teacher_name");
                        String tedadHazeri = jsonObject.getString("tedad_hazeri");
                        String tedadGheybat = jsonObject.getString("tedad_gheybat");
                        String classId = jsonObject.getString("class_id");
                        recyclerModels.add(new RecyclerModel(lastId, className,tedadHazeri,"",tedadGheybat,classId,teacherName,teacherId,0));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

                net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        LoadData.firstLoadDataYH(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,username);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }

    public static void firstLoadDataRecivedMessageTeacher(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                                          final ArrayList<RecyclerModel> recyclerModels,
                                                          final RecyclerView recyclerView,
                                                          final String username, final String noe, final ConstraintLayout clWifi) {

        String usernameEncode = UrlEncoderClass.urlEncoder(username);
        String noeEncode = UrlEncoderClass.urlEncoder(noe);
        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_recived_message_teacher&limit=" + LOAD_LIMIT + "&user1=" + usernameEncode + "&noe=" + noeEncode;
        itShouldLoadMore = false;
       /* final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();*/

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                clWifi.setVisibility(View.GONE);
                //progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        String onvan = jsonObject.getString("onvan");
                        String matn = jsonObject.getString("message");
                        String idFerestande = jsonObject.getString("id_ersal_konande");
                        String nameFerestande = jsonObject.getString("name_ferestande");
                        String tarikh = jsonObject.getString("tarikh");
                        String noe = jsonObject.getString("noe");

                        recyclerModels.add(new RecyclerModel(lastId,onvan, matn,tarikh,nameFerestande,noe,idFerestande,"",0));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                //progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();
                clWifi.setVisibility(View.VISIBLE);
                clWifi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoadData.firstLoadDataRecivedMessageTeacher(c, recyclerAdapter, recyclerModels,
                                recyclerView, username,noe,clWifi);
                    }
                });
            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }


    public static void LoadSearchResult(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                               final ArrayList<RecyclerModel> recyclerModels,
                                               final RecyclerView recyclerView,
                                               final String username, String query, final ConstraintLayout clWifi, final String noe) {

        String usernameEncode= UrlEncoderClass.urlEncoder(username);
        String queryEncode= UrlEncoderClass.urlEncoder(query);
        String noeEncode= UrlEncoderClass.urlEncoder(noe);

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_search_result&limit=" + LOAD_LIMIT + "&user1=" + usernameEncode + "&query=" + queryEncode + "&noe=" + noeEncode;
        itShouldLoadMore = false;
        ProgressDialogClass.showProgress(c);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                clWifi.setVisibility(View.GONE);
                ProgressDialogClass.dismissProgress();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        String studentId = jsonObject.getString("id");
                        String nameStudent = jsonObject.getString("family");
                        String std_picture = jsonObject.getString("picture");

                        recyclerModels.add(new RecyclerModel(lastId,studentId, nameStudent,std_picture,"","","","",0));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                ProgressDialogClass.dismissProgress();
                clWifi.setVisibility(View.VISIBLE);
                clWifi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoadData.LoadSearchResult(c, recyclerAdapter, recyclerModels,
                                recyclerView, username, "",clWifi,noe);
                    }
                });

            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }


    public static void ListVorodKhorojErsali(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                                               final ArrayList<RecyclerModel> recyclerModels,
                                                               final RecyclerView recyclerView,
                                                               final String username, final ConstraintLayout clWifi) {

        String usernameEncode= UrlEncoderClass.urlEncoder(username);

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=list_vorod_khoroj_ersali&limit=" + LOAD_LIMIT + "&user1=" + usernameEncode;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                clWifi.setVisibility(View.GONE);
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        String tarikh = jsonObject.getString("tarikh");
                        String saat_vorod = jsonObject.getString("saat_vorod");
                        String saat_khoroj = jsonObject.getString("saat_khoroj");
                        String vaziyat_taeid = jsonObject.getString("vaziyat_taeid");
                        recyclerModels.add(new RecyclerModel(lastId,tarikh, saat_vorod,saat_khoroj,vaziyat_taeid,"noe","","",0));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                clWifi.setVisibility(View.VISIBLE);
                clWifi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoadData.firstLoadDataListPayamHayeErsaliTeacher(c, recyclerAdapter, recyclerModels,
                                recyclerView, username,"",clWifi);
                    }
                });
            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
    }


    public static void firstLoadDataListPayamHayeErsaliTeacher(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                                               final ArrayList<RecyclerModel> recyclerModels,
                                                               final RecyclerView recyclerView,
                                                               final String username, final String noe, final ConstraintLayout clWifi) {

        String usernameEncode= UrlEncoderClass.urlEncoder(username);
        String noeEncode= UrlEncoderClass.urlEncoder(noe);

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_list_payamhaye_ersali_teacher&limit=" + LOAD_LIMIT + "&user1=" + usernameEncode + "&noe=" + noeEncode;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                clWifi.setVisibility(View.GONE);
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        String onvan = jsonObject.getString("onvan");
                        String matn = jsonObject.getString("message");
                        String nameFerestande = jsonObject.getString("name_daryaft_konande");
                        String tarikh = jsonObject.getString("tarikh");
                        String noe = jsonObject.getString("noe");
                        String readOrNo = jsonObject.getString("read_or_no");
                        recyclerModels.add(new RecyclerModel(lastId,onvan, matn,tarikh,nameFerestande,noe,"",readOrNo,0));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                clWifi.setVisibility(View.VISIBLE);
                clWifi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoadData.firstLoadDataListPayamHayeErsaliTeacher(c, recyclerAdapter, recyclerModels,
                                recyclerView, username,noe,clWifi);
                    }
                });
            }
        });

        //class mySingleton baes misheh ke yek tread bishtar az yek bar sakhte nashe v roye sorat
        //va performance barname tasir ziyadi dare makhsosan vaghti bekhahim yek code ro hamash
        //refresh konim
        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);
        //Volley.newRequestQueue(c).add(jsonArrayRequest);


    }

    public static void firstLoadDataAllMessage(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                                               final ArrayList<RecyclerModel> recyclerModels,
                                                               final RecyclerView recyclerView,
                                                               final String username, final ConstraintLayout clWifi) {

        String usernameEncode="";
        try {
            usernameEncode = URLEncoder.encode(username,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_list_hameye_payam_ha&limit=" + LOAD_LIMIT + "&user1=" + usernameEncode;
        itShouldLoadMore = false;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                clWifi.setVisibility(View.GONE);
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        String onvan = jsonObject.getString("onvan");
                        String matn = jsonObject.getString("message");
                        String nameDaryaftKonandeh = jsonObject.getString("name_daryaft_konande");
                        String nameErsalKonandeh = jsonObject.getString("name_ersal_konande");
                        String tarikh = jsonObject.getString("tarikh");
                        recyclerModels.add(new RecyclerModel(lastId,onvan, matn,"",nameErsalKonandeh,nameDaryaftKonandeh,tarikh,"",0));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                clWifi.setVisibility(View.VISIBLE);

                clWifi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        LoadData.firstLoadDataListPayamHayeErsaliStudent(c,recyclerAdapter,recyclerModels,recyclerView,username,clWifi);


                    }
                });


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);

    }


    public static void firstLoadDataListPayamHayeErsaliStudent(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                                               final ArrayList<RecyclerModel> recyclerModels,
                                                               final RecyclerView recyclerView,
                                                               final String username, final ConstraintLayout clWifi) {

        String usernameEncode="";
        try {
            usernameEncode = URLEncoder.encode(username,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_list_payamhaye_ersali_student&limit=" + LOAD_LIMIT + "&user1=" + usernameEncode;
        itShouldLoadMore = false;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                //clWifi.setVisibility(View.GONE);
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        String onvan = jsonObject.getString("onvan");
                        String matn = jsonObject.getString("message");
                        String nameFerestande = jsonObject.getString("name_daryaft_konande");
                        recyclerModels.add(new RecyclerModel(lastId,onvan, matn,"",nameFerestande,"","","",0));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                /*clWifi.setVisibility(View.VISIBLE);

                clWifi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        LoadData.firstLoadDataListPayamHayeErsaliStudent(c,recyclerAdapter,recyclerModels,recyclerView,username,clWifi);


                    }
                });*/


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);

    }

    public static void firstLoadDataRecivedMessageStudent(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                                          final ArrayList<RecyclerModel> recyclerModels,
                                                          final RecyclerView recyclerView,
                                                          final String username, final ConstraintLayout clWifi) {

        String usernameEncode="";
        try {
            usernameEncode = URLEncoder.encode(username,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=load_recived_message_student&limit=" + LOAD_LIMIT + "&user1=" + usernameEncode;
        itShouldLoadMore = false;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                clWifi.setVisibility(View.GONE);
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        String onvan = jsonObject.getString("onvan");
                        String matn = jsonObject.getString("message");
                        String nameFerestande = jsonObject.getString("name_ferestande");
                        recyclerModels.add(new RecyclerModel(lastId,onvan, matn,"",nameFerestande,"","","",0));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;

                clWifi.setVisibility(View.VISIBLE);

                clWifi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        LoadData.firstLoadDataRecivedMessageStudent(c,recyclerAdapter,recyclerModels,recyclerView,username,clWifi);


                    }
                });

            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }

    public static void firstLoadDataYH(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                       final ArrayList<RecyclerModel> recyclerModels,
                                       final ImageView img_refresh, final WebView webView,
                                       final Timer timer, final TextView net_state,
                                       final RecyclerView recyclerView,
                                       final String username) {

        String usernameEncode="";
        try {
            usernameEncode = URLEncoder.encode(username,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url= "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=loadclass&limit=" + LOAD_LIMIT + "&user1=" + usernameEncode;
        itShouldLoadMore = false;
        final ProgressDialog progressDialog = new ProgressDialog(c);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                webView.setVisibility(View.GONE);
                img_refresh.setVisibility(View.GONE);
                net_state.setText("");
                progressDialog.dismiss();
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();

                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("class_id");
                        String className = jsonObject.getString("class_name");
                        String schoolName = jsonObject.getString("school_name");
                        String teacherId = jsonObject.getString("teacher_id");
                        String tedad_student = jsonObject.getString("tedad_student");
                        recyclerModels.add(new RecyclerModel(lastId,schoolName, className,"",teacherId,tedad_student,"","",0));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itShouldLoadMore = true;
                progressDialog.dismiss();
                Toast.makeText(c, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show();

                net_state.setText("دسترسی به اینترنت موجود نیست.");
                net_state.setTextSize(18);
                img_refresh.setVisibility(View.GONE);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/refresh_gif.gif");


                webView.postDelayed(new Runnable() {
                    public void run() {
                        webView.onPause();

                        //------>توضیح مهم، درصورت فعالسازی کد زیر وب ویو پرداخت همراه پی با مشکل مواجه میشود<-----
                        //webView.pauseTimers();
                    }
                }, 5000);

                //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);



                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //webView.onResume();
                        //webView.reload();
                        webView.onResume();
                        webView.resumeTimers();

                        webView.postDelayed(new Runnable() {
                            public void run() {
                                webView.onPause();
                                webView.pauseTimers();
                            }
                        }, 10000);

                        LoadData.firstLoadDataYH(c,recyclerAdapter,recyclerModels,img_refresh,webView,timer,net_state,recyclerView,username);



                        return false;
                    }
                });
                img_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //new JSONDownloader(c,jsonURL, rv,rv2,img_refresh).execute(net_state);

                    }
                });


            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);


    }


    public static void loadMoreClass(final Context c, final RecyclerAdapterYouHaveKnow recyclerAdapter,
                                            final ArrayList<RecyclerModel> recyclerModels,
                                            final ProgressBar progressBar,String username) {


        String url = "http://robika.ir/ultitled/practice/tavasi_load_data.php?action=loadmore_class&lastId=" + lastId + "&limit=" + LOAD_LIMIT + "&user1=" + username;

        itShouldLoadMore = false;
        progressBar.setVisibility(View.VISIBLE);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);

                itShouldLoadMore = true;
                if (response.length() <= 0) {
                    // we need to check this, to make sure, our dataStructure JSonArray contains
                    // something
                    //Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();
                    return; // return will end the program at this point
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("class_id");
                        String schoolName = jsonObject.getString("school_name");
                        String className = jsonObject.getString("class_name");
                        String teacher_id = jsonObject.getString("teacher_id");
                        String tedad_student = jsonObject.getString("tedad_student");
                        recyclerModels.add(new RecyclerModel(lastId,schoolName, className,"",teacher_id,tedad_student,"","",0));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                itShouldLoadMore = true;
                Toast.makeText(c, "خطار در بارگزاری . دسترسی به اینترنت موجود نیست", Toast.LENGTH_SHORT).show();

            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);

    }


    //Load more below :

    public static void loadMore(final Context c, final RecyclerAdapter recyclerAdapter, final ArrayList<RecyclerModel> recyclerModels, final ProgressBar progressBar) {


        String url = "http://robika.ir/ultitled/practice/auto_load_more_for_safarkon.php?action=loadmore&lastId=" + lastId + "&limit=" + LOAD_LIMIT;

        itShouldLoadMore = false;
        progressBar.setVisibility(View.VISIBLE);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);

                itShouldLoadMore = true;
                if (response.length() <= 0) {
                    // we need to check this, to make sure, our dataStructure JSonArray contains
                    // something
                    Toast.makeText(c, "اطلاعاتی موجود نیست", Toast.LENGTH_SHORT).show();
                    return; // return will end the program at this point
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        lastId = jsonObject.getString("id");
                        String onvan = jsonObject.getString("onvan");
                        String matn = jsonObject.getString("matn");
                        String picture = jsonObject.getString("p1");
                        String city = jsonObject.getString("city");
                        String position = jsonObject.getString("position");
                        float rate = jsonObject.getInt("rate");
                        recyclerModels.add(new RecyclerModel(lastId,onvan, matn,picture,city,"","","",0));
                        recyclerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                itShouldLoadMore = true;
                Toast.makeText(c, "خطار در بارگزاری . دسترسی به اینترنت موجود نیست", Toast.LENGTH_SHORT).show();

            }
        });

        MySingleton.getInstance(c).addToRequestQueue(jsonArrayRequest);

    }




}
