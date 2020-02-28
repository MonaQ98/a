package com.example.electricassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.electricassistant.data.FinalData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Payment extends AppCompatActivity {
    Button Num0;
    Button Num1;
    Button Num2;
    Button Num3;
    Button Num4;
    Button Num5;
    Button Num6;
    Button Num7;
    Button Num8;
    Button Num9;
    Button Pay;
    Button Point;
    Button Backspace;
    EditText Input;
    public void Post_Pay_money(Double money) {
// @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient okHttpClient = new OkHttpClient();
        JSONObject json = new JSONObject();
        try {
            json.put("user_id",FinalData.user_id);
            json.put("money",money);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , json.toString());
        System.out.println(json.toString());
        Request request = new Request.Builder()
                .url(FinalData.url + "/user_pay")//请求的url
                .post(requestBody)
                .build();
        okhttp3.Call call = okHttpClient.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.i(TAG, call.toString() + e);
                System.out.println("FFFFFFFFFFFFFFFFFFFFFF");
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                final String res = response.body().string();
                if (response.code() == 200) {
                    System.out.println(res);

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }
    public boolean isNum(String s) {
        if (s == null || s.equals("")) {
            return true;
        } else {
            System.out.println(s);
            if (s.matches("\\d+(.\\d+)?")) {
                if (s.indexOf(".") > 0) {
                    System.out.println("浮点类型");
                    if (s.indexOf(".") >= s.length() - 3) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    System.out.println("整形类型");
                    return true;
                }
            } else {
                System.out.println("不是数值类型");
                return false;

            }
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Num0 = findViewById(R.id.Num0);
        Num1 = findViewById(R.id.Num1);
        Num2 = findViewById(R.id.Num2);
        Num3 = findViewById(R.id.Num3);
        Num4 = findViewById(R.id.Num4);
        Num5 = findViewById(R.id.Num5);
        Num6 = findViewById(R.id.Num6);
        Num7 = findViewById(R.id.Num7);
        Num8 = findViewById(R.id.Num8);
        Num9 = findViewById(R.id.Num9);
        Point = findViewById(R.id.DecimalPoint);
        Pay = findViewById(R.id.Pay_button);
        Backspace = findViewById(R.id.Backspace);
        Input = findViewById(R.id.Input_money);


        ArrayList<Button> buttonArrayList = new ArrayList<Button>();
        buttonArrayList.add(Num0);
        buttonArrayList.add(Num1);
        buttonArrayList.add(Num2);
        buttonArrayList.add(Num3);
        buttonArrayList.add(Num4);
        buttonArrayList.add(Num5);
        buttonArrayList.add(Num6);
        buttonArrayList.add(Num7);
        buttonArrayList.add(Num8);
        buttonArrayList.add(Num9);

        Input.setText("");
        Num1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNum(Input.getText() + "1")) {
                    Input.setText(Input.getText() + "1");
                }
            }
        });
        Num2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNum(Input.getText() + "2")) {
                    Input.setText(Input.getText() + "2");
                }
            }
        });
        Num3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNum(Input.getText() + "3")) {
                    Input.setText(Input.getText() + "3");
                }
            }
        });
        Num4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNum(Input.getText() + "4")) {
                    Input.setText(Input.getText() + "4");
                }
            }
        });
        Num5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNum(Input.getText() + "5")) {
                    Input.setText(Input.getText() + "5");
                }
            }
        });
        Num6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNum(Input.getText() + "6")) {
                    Input.setText(Input.getText() + "6");
                }
            }
        });
        Num7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNum(Input.getText() + "7")) {
                    Input.setText(Input.getText() + "7");
                }
            }
        });
        Num8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNum(Input.getText() + "8")) {
                    Input.setText(Input.getText() + "8");
                }
            }
        });
        Num9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNum(Input.getText() + "9")) {
                    Input.setText(Input.getText() + "9");
                }
            }
        });
        Num0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNum(Input.getText() + "0")) {
                    Input.setText(Input.getText() + "0");
                }
            }
        });
        Point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Input.getText().toString().contains(".")) {
                    Input.setText(Input.getText() + ".");
                }
            }
        });
        Backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(Input.getText());
                String s = Input.getText().toString();
                if (!(s.equals(""))) {
                    System.out.println(s);
                    Input.setText(Input.getText().toString().substring(0, Input.getText().length() - 1));
                }
            }
        });
        Pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double money = Double.parseDouble(Input.getText().toString());
                Post_Pay_money(money);
            }
        });

    }
}
