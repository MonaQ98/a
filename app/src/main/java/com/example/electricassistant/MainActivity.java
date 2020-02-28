package com.example.electricassistant;

//import android.support.v7.app.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.ActionMode;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.electricassistant.data.FinalData;
import com.example.electricassistant.data.model.PaymentItem;
import com.example.electricassistant.ui.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private ViewPager viewPager;
    private MenuItem menuItem;
    private BottomNavigationView navigation;
    private PagerAdapter viewPagerAdapter;
    TextView amount_month;
    TextView money_month;
    TextView amount_year;
    TextView money_year;

    private static final int PERMISSION_REQUEST = 1;
    public static List<String> logList = new CopyOnWriteArrayList<String>();

    public void HandleUserInfo(JSONObject json) throws JSONException {
        System.out.println(json);
        FinalData.amount_month = json.getDouble("amount_month");
        FinalData.amount_year = json.getDouble("amount_year");
        FinalData.money_month = json.getDouble("money_month");
        FinalData.money_year = json.getDouble("money_year");
        JSONArray payments = json.getJSONArray("user_payments");
        ArrayList<PaymentItem> result = new ArrayList<PaymentItem>();
        FinalData.payments = result;
        for (int i = 0; i < payments.length(); i++) {
            PaymentItem item = new PaymentItem(null, null, null);
            ;
            Timestamp time = Timestamp.valueOf(payments.getJSONObject(i).getString("time"));
            item.setTime(time);
            item.setAmount(payments.getJSONObject(i).getDouble("amount"));
            item.setMoney(payments.getJSONObject(i).getDouble("money"));
            result.add(item);
        }
        FinalData.payments = result;
        JSONObject user = json.getJSONObject("user");
        FinalData.electricity_month = user.getDouble("electricity_month");
        FinalData.wallet = user.getDouble("wallet");
        FinalData.status = user.getString("status");
//        FinalData.user_id = user.getInt("user_id");
        FinalData.username = user.getString("username");
    }

    public void Post_UserData_request(int user_id) {
// @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        System.out.println(user_id);
        OkHttpClient okHttpClient = new OkHttpClient();
        JSONObject json = new JSONObject();
        try {
            json.put("user_id", user_id);
            FinalData.user_id = user_id;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , json.toString());
        System.out.println(json.toString());
        Request request = new Request.Builder()
                .url(FinalData.url + "/user_info")//请求的url
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
                    if (!res.equals("error")) {
                        try {
                            HandleUserInfo(new JSONObject(res));

                            MainActivity.this.runOnUiThread(new Runnable() {
                                // 耗时操作
                                @Override
                                public void run() {
//                                    System.out.println("ddddddddddddddd");
                                    amount_year = findViewById(R.id.amount_year);
                                    amount_month = findViewById(R.id.amount_month);
                                    money_year = findViewById(R.id.money_year);
                                    money_month = findViewById(R.id.money_month);


                                    amount_year.setText(FinalData.amount_year.toString());
                                    amount_month.setText(FinalData.amount_month.toString());
                                    money_month.setText(FinalData.money_month.toString());
                                    money_year.setText(FinalData.money_year.toString());
                                    if (FinalData.remember_status){
                                        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                                        SharedPreferences.Editor et = sp.edit();
                                        et.putBoolean("isfirst", false);
                                        et.putString("password", FinalData.password);
                                        et.putString("username", FinalData.phone);
                                        et.commit();
                                        System.out.println("aishdoaushdauihsd");
                                    }

                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
//                case R.id.navigation_home:
//                    viewPager.setCurrentItem(0);
//                    return true;
                case R.id.navigation_electricity:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_services:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_user:
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //得到了授权
//                    Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
                } else {
                    //未授权
//                    Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mfragmentList;

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.mfragmentList = fragmentList;
        }

        //获取集合中的某个项
        @Override
        public Fragment getItem(int position) {
            return mfragmentList.get(position);
        }

        //返回绘制项的数目
        @Override
        public int getCount() {
            return mfragmentList.size();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Post_UserData_request(1);
        navigation = findViewById(R.id.navigation);
//        navigation.getMenu().getItem(0).setChecked(true);


        viewPager = (ViewPager) findViewById(R.id.vp);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    navigation.getMenu().getItem(0).setChecked(false);
                }
                menuItem = navigation.getMenu().getItem(position);
                menuItem.setChecked(true);
                switch (position) {
                    case 0:
                        break;
                    case 1:
//
                        break;
                    case 2:
//                        UserPanel.update();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        List<Fragment> fragments = new ArrayList<>();
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
//        fragments.add(new home());
        fragments.add(new Electricity());
        fragments.add(new Services());
        fragments.add(new User());
        viewPager.setAdapter(myFragmentPagerAdapter);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        navigation.setSelectedItemId(R.id.navigation_notifications);
        viewPager.setCurrentItem(0);



    }


}
