package com.yl.imxmppdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yl.imxmppdemo.server.Imservice;
import com.yl.imxmppdemo.utils.ThreadUtils;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/27 0027.
 */
public class LoginActivity extends Activity implements View.OnClickListener{
    private static final String TAG = "LoginActivity";
    @BindView(R.id.et_acount)
    EditText etAcount;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    private String username;
    private String password;
    private XMPPConnection conn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        btnLogin.setOnClickListener(this);
        ThreadUtils.runInThread(new Runnable() {
            @Override
            public void run() {
                //1.建立通道
                ConnectionConfiguration configuration = new ConnectionConfiguration(MyApp.HOST, MyApp.PORT);
                //设置debug信息
                configuration.setDebuggerEnabled(true);
                //使用明文
                configuration.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
                conn = new XMPPConnection(configuration);
                try {
                    conn.connect();
                } catch (XMPPException e) {
                    e.printStackTrace();
                }
                //
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        username = etAcount.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        ThreadUtils.runInThread(new Runnable() {

            private boolean flag;

            @Override
            public void run() {
                try {
                    conn.login(username,password);
                    flag = true;
                } catch (XMPPException e) {
                    e.printStackTrace();
                    flag = false;
                }
                
                ThreadUtils.runInUIThread(new Runnable() {
                    @Override
                    public void run() {
                        if (flag) {

                            Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();

                            MyApp.conn = conn;
                            MyApp.username = username;
                            MyApp.account = username + "@" + MyApp.SERVICE_NAME;
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            startService(new Intent(LoginActivity.this, Imservice.class));
                            finish();

                        } else {
                            Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }
}
