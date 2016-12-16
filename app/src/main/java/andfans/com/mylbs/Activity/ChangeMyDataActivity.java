package andfans.com.mylbs.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import andfans.com.mylbs.LoginAndSign.ChangePassWord;
import andfans.com.mylbs.R;
import andfans.com.mylbs.util.MyCircleImageView;

public class ChangeMyDataActivity extends AppCompatActivity
        implements View.OnClickListener{

    EditText editName;
    MyCircleImageView editUserImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_my_data);
        findView();
    }

    private void findView() {
        editName = (EditText) findViewById(R.id.nameEdit);
        editUserImage = (MyCircleImageView) findViewById(R.id.editUserImage);
        editUserImage.setOnClickListener(this);
        findViewById(R.id.passWordEdit).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.saveData).setOnClickListener(this);
        findViewById(R.id.phoneNumberEdit).setOnClickListener(this);
        findViewById(R.id.exitButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.passWordEdit:{
                //修改密码
                Intent intent = new Intent(this, ChangePassWord.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.back:{
                setResult(RESULT_CANCELED);
                finish();
                break;
            }
            case R.id.saveData:{
                //ps:尝试修改数据，成功后返回，失败保持
                break;
            }

            case R.id.phoneNumberEdit:{
                //修改手机号，弹出一个dialog， 验证？or 弹出一个PopWindow

                break;
            }
            case R.id.exitButton:{
                SharedPreferences.Editor editor = getSharedPreferences("LoginState", MODE_PRIVATE).edit();
                editor.putBoolean("LoginStatus", false);
                editor.commit();
                setResult(RESULT_OK);
                finish();
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
        finish();
    }
}
