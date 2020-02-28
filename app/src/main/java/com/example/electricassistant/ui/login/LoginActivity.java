package com.example.electricassistant.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.electricassistant.MainActivity;
import com.example.electricassistant.R;
import com.example.electricassistant.data.FinalData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class LoginActivity extends AppCompatActivity {
    CheckBox login_status;
    public void Post_Login( String username,String password){
// @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        System.out.println(username+password);
        OkHttpClient okHttpClient = new OkHttpClient();
        JSONObject json = new JSONObject();
        try {
            json.put("username",username );
            json.put("password", password);
            FinalData.password = password;
            FinalData.phone = username;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , json.toString());
        System.out.println(json.toString());
        Request request = new Request.Builder()
                .url(FinalData.url+"/login")//请求的url
                .post(requestBody)
                .build();
        okhttp3.Call call = okHttpClient.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.i(TAG,call.toString()+e);
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                final String res = response.body().string();
                if (response.code() == 200) {
                    System.out.println(res);
                    if(!res.equals("error")){
                        Loginsuccess();
                    }
                }
            }
        });
    }
    private LoginViewModel loginViewModel;
    public void Loginsuccess(){


        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);//打开新的activity
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
        boolean isfirst = sp.getBoolean("isfirst", true);
        //对这个值进行判断
        if (!isfirst) {
            //如果是第一次登陆，添加登陆过的标记，并显示第一次登陆
            FinalData.phone = sp.getString("username","");
            FinalData.password = sp.getString("password","");
            Loginsuccess();
//            Toast.makeText(this, "第一次登陆", Toast.LENGTH_SHORT).show();
        }


        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        login_status = findViewById(R.id.RememberLoginStatus);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
//                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                System.out.println("aishhdauihsd");
                if (login_status.isChecked()){
                    System.out.println("aishdoaushsd");
                    FinalData.phone = usernameEditText.getText().toString();
                    FinalData.password = passwordEditText.getText().toString();
                    FinalData.remember_status = true;

                }
                Post_Login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
