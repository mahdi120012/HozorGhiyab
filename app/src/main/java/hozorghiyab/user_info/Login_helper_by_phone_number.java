package hozorghiyab.user_info;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import hozorghiyab.activities.MainActivity;
import hozorghiyab.activities.StudentPanelMainKt;

public class Login_helper_by_phone_number extends AsyncTask<Void, Void, String> {

    Context        c;
    String         urlAddress;
    String       usernameTxt1, passwordTxt1;

    ProgressDialog pd;
    User_login     user;


    public Login_helper_by_phone_number(Context c, String urlAddress, String usernameTxt1, String passwordTxt1) {

        this.c = c;
        this.urlAddress = urlAddress;
        this.usernameTxt1 = usernameTxt1;
        this.passwordTxt1 = passwordTxt1;

        user = new User_login();
        user.setUsernameOrEmail(usernameTxt1);
        user.setPassword(passwordTxt1);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(c);
        pd.setTitle("ورود");
        pd.setMessage("درحال ورود ... لطفا صبر نمایید");
        pd.show();
    }


    @Override
    protected String doInBackground(Void... params) {
        return this.Login();
    }


    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        pd.dismiss();
        if (response.startsWith("Error")) {
            display(response);

        } else {


            String message = "",
                   id = "",
                   first_name = "",
                   last_name = "",
                   user_type = "",
                   picture = "";
            try {
                JSONObject jsonObject = new JSONObject(response);
                message = jsonObject.getString("message");
                id = jsonObject.getString("id");
                first_name = jsonObject.getString("first_name");
                last_name = jsonObject.getString("last_name");
                user_type = jsonObject.getString("user_type");
                picture = jsonObject.getString("picture");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (message.startsWith(ErrorTracker.LOGIN_SUCCESS)) {

                /*//Line below For Remove String Logged Successfully from string and get only name:
                //String name = response.replace("Logged Successfully", "");
                String name = response.substring(response.indexOf("[") + 1, response.indexOf("]"));
                //Line below for remove only the spaces at the beginning or end of the String:
                name = name.trim();

                String noe = response.substring(response.indexOf("(") + 1, response.indexOf(")"));
                noe = noe.trim();*/

                SharedPreferences sharedPreferences = Login_helper_by_phone_number.this.c.getSharedPreferences("file", c.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("user", id);
                editor.putString("pass", passwordTxt1);
                editor.putString("name", first_name + " " + last_name );
                editor.putString("noe", user_type);
                editor.putString("picture", picture);
                editor.commit();

                //usernameTxt1.setText("");
                //passwordTxt1.setText("");
                display(response);

                if (user_type.matches("student")){
                    Intent intent = new Intent(c, StudentPanelMainKt.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    c.startActivity(intent);
                }else {
                    Intent intent = new Intent(c, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    c.startActivity(intent);
                }

            }

            else {
                display(response);

            }
        }
    }


    private void display(String txt) {
        if (txt.startsWith("Wrong details")){
            Toast.makeText(c, "نام كاربري يا رمز عبور اشتباه است.", Toast.LENGTH_LONG).show();
        }
        //Toast.makeText(c, txt, Toast.LENGTH_LONG).show();

    }


    private String Login() {
        Object connection = Connector.connect(urlAddress);
        if (connection.toString().startsWith("Error")) {
            return connection.toString();

        }

        try {

            HttpURLConnection con = (HttpURLConnection) connection;
            OutputStream os = new BufferedOutputStream(con.getOutputStream());
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));

            String LoginData = new Login_data(user).packLoginData();
            if (LoginData.startsWith("Error")) {
                return LoginData;

            }

            bw.write(LoginData);
            bw.flush();
            bw.close();
            os.close();

            int responseCode = con.getResponseCode();
            if (responseCode == con.HTTP_OK) {
                InputStream is = new BufferedInputStream(con.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                String line;
                StringBuffer response = new StringBuffer();

                while ((line = br.readLine()) != null) {
                    response.append(line + "\n");
                }

                br.close();
                is.close();

                return response.toString();
            } else {

                return ErrorTracker.RESPONSE_ERROR + String.valueOf(responseCode);

            }

        }
        catch (IOException e) {
            e.printStackTrace();
            return ErrorTracker.IO_ERROR;

        }

    }
}
