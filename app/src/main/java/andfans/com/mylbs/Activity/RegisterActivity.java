package andfans.com.mylbs.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import andfans.com.mylbs.R;
import andfans.com.mylbs.util.Utils;

/*
 * Created by 兆鹏 on 2016/11/30.
 */
public class RegisterActivity extends Activity {
    private ImageView imBack;
    private EditText edUser,edTelephone,edBrand,edPassword,edConfirmCode;
    private Button btRegister,btGetConfirm;
    private boolean isRegister = false;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        context = this;
        findView();
    }

    private void findView() {
        imBack = (ImageView) findViewById(R.id.id_register_back);
        edConfirmCode = (EditText) findViewById(R.id.id_register_confirmCode);
        edUser = (EditText) findViewById(R.id.id_register_user);
        edTelephone = (EditText) findViewById(R.id.id_register_telephone);
        edBrand = (EditText) findViewById(R.id.id_register_brand);
        edPassword = (EditText) findViewById(R.id.id_register_password);
        btRegister = (Button) findViewById(R.id.id_register_register);
        btGetConfirm = (Button) findViewById(R.id.id_register_getConfirmCode);
        imBack.setOnClickListener(view -> {
            if(isRegister){

            }else {
                finish();
            }
        });
        btRegister.setOnClickListener(view -> Utils.showToast(context,"点击了注册按钮"));
        btGetConfirm.setOnClickListener(view -> Utils.showToast(context,"点击了获取验证码"));
    }
}
