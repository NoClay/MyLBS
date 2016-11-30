package andfans.com.mylbs.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import andfans.com.mylbs.MainActivity;
import andfans.com.mylbs.R;
import andfans.com.mylbs.util.Utils;

/*
 * Created by 兆鹏 on 2016/11/28.
 */
public class LoginActivity extends Activity {
    private Button btLogin;
    private EditText edTelephone,edPassword;
    private TextView tvRegister,tvForget,tvPic;
    private Context context;
    private ImageView imPic;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        context = this;
        findView();
    }

    private void findView() {
        tvPic = (TextView) findViewById(R.id.id_login_tv);
        imPic = (ImageView) findViewById(R.id.id_login_pic);
        btLogin = (Button) findViewById(R.id.id_login_login);
        edTelephone = (EditText) findViewById(R.id.id_login_tele);
        edPassword = (EditText) findViewById(R.id.id_login_password);
        tvRegister = (TextView) findViewById(R.id.id_login_register);
        tvForget = (TextView) findViewById(R.id.id_login_forget);
        tvPic.setOnClickListener(view -> {
            intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        });
        imPic.setOnClickListener(view -> {
            intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        });
        btLogin.setOnClickListener(view -> {
            Utils.showToast(context,"点击了登录按钮");
        });
        tvForget.setOnClickListener(view -> Utils.showToast(context,"点击了忘记密码"));
        tvRegister.setOnClickListener(view -> {
            intent = new Intent(context,RegisterActivity.class);
            startActivity(intent);
        });
    }

}
