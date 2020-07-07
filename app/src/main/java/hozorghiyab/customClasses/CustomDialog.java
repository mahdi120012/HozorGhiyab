package hozorghiyab.customClasses;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hozorghiyab.R;

import java.lang.reflect.Type;

import hozorghiyab.activities.MainActivity;
import hozorghiyab.cityDetail.LoadData;

public class CustomDialog {

    public static void changePassword(final Context context) {
        final Dialog dialog = new Dialog(context, R.style.customDialog2);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_change_password, null, false);
        final EditText etNewPassword = view.findViewById(R.id.etNewPassword);
        final EditText etTekrarNewPassword = view.findViewById(R.id.etTekrarNewPassword);
        ConstraintLayout clChangePassword = view.findViewById(R.id.clChangePassword);
        ImageView imgBack = view.findViewById(R.id.imgBack);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Keyboard.hideKeyboard(context);
                dialog.dismiss();
            }
        });

        clChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = SharedPrefClass.getUserId(context,"user");

                String newPassword = etNewPassword.getText().toString();
                String tekrarNewPassword = etTekrarNewPassword.getText().toString();

                if (newPassword.length() < 6){
                    Toast.makeText(context, "رمز عبور کوتاه است", Toast.LENGTH_SHORT).show();
                }else if (newPassword.equals(tekrarNewPassword)){
                    LoadData.updatePassword(context,username,newPassword,etNewPassword,etTekrarNewPassword,dialog);
                }else {
                    Toast.makeText(context, "رمز های وارد شده با هم برابر نیستند", Toast.LENGTH_SHORT).show();
                }
            }
        });


        ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.setContentView(view);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        //line zir baraye transparent kardan hashiye haye cardview ee:
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }
}
