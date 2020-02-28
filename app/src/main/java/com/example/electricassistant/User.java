package com.example.electricassistant;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AlignmentSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.electricassistant.data.FinalData;
import com.example.electricassistant.ui.login.LoginActivity;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUILoadingView;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class User extends Fragment {


    @BindView(R.id.ListView)
    QMUIGroupListView mGroupListView;
    private UserViewModel mViewModel;
    public static User newInstance() {
        return new User();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.user_fragment, null);
        ButterKnife.bind(this, root);
        initGroupListView();
//        return inflater.inflate(R.layout.user_fragment, container, false);

        return root;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        // TODO: Use the ViewModel
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initGroupListView() {


        QMUICommonListItemView itemWithDetailBelow = mGroupListView.createItemView("解绑手机号");
        itemWithDetailBelow.setOrientation(QMUICommonListItemView.VERTICAL);
        itemWithDetailBelow.setDetailText("解除绑定的电话号码");

        QMUICommonListItemView itemWithChevron = mGroupListView.createItemView("更换密码");
        itemWithChevron.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        final QMUICommonListItemView itemWithSwitch = mGroupListView.createItemView("自动登录");
        itemWithSwitch.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_SWITCH);
        itemWithSwitch.getSwitch().setChecked(!FinalData.remember_status);
        itemWithSwitch.getSwitch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences sp;
                sp = getActivity().getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("isfirst", !itemWithSwitch.getSwitch().isChecked());
                editor.commit();
                Toast.makeText(getActivity(), "checked = " + isChecked, Toast.LENGTH_SHORT).show();
            }
        });

        QMUICommonListItemView itemWithDetailBelowWithChevron = mGroupListView.createItemView("其他设置");
        itemWithDetailBelowWithChevron.setOrientation(QMUICommonListItemView.VERTICAL);
        itemWithDetailBelowWithChevron.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        itemWithDetailBelowWithChevron.setDetailText("APP设置");

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof QMUICommonListItemView) {
                    CharSequence text = ((QMUICommonListItemView) v).getText();
                    Toast.makeText(getActivity(), text + " is Clicked", Toast.LENGTH_SHORT).show();
                    if (((QMUICommonListItemView) v).getAccessoryType() == QMUICommonListItemView.ACCESSORY_TYPE_SWITCH) {
                        ((QMUICommonListItemView) v).getSwitch().toggle();
                    }
                }
            }
        };

        QMUICommonListItemView quit = mGroupListView.createItemView("退出登录");
        SpannableString spannableString = new SpannableString("退出登录");
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.RED);
        spannableString.setSpan(colorSpan, 0, spannableString.length(), spannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        AlignmentSpan alignmentSpan = new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER);
        spannableString.setSpan(alignmentSpan, 0, spannableString.length(), spannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        Drawable exit = getContext().getResources().getDrawable(R.drawable.ic_exit_to_app_black_24dp);
        exit.setTint(Color.RED);
        ImageSpan exitimage = new ImageSpan(exit);
        spannableString.setSpan(exitimage, 0, 0, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        quit.setText(spannableString);
        int size = QMUIDisplayHelper.dp2px(getContext(), 20);
        View.OnClickListener listenner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp;
                sp = getActivity().getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("isfirst", true);
                editor.commit();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);//打开新的activity
            }
        };

        QMUIGroupListView.newSection(getContext())
                .setTitle("用户设定")

                .setLeftIconSize(size, ViewGroup.LayoutParams.WRAP_CONTENT)
                .addItemView(itemWithDetailBelow, onClickListener)
                .addItemView(itemWithChevron, onClickListener)
                .addItemView(itemWithSwitch, onClickListener)
                .addItemView(itemWithDetailBelowWithChevron, onClickListener)
                .setMiddleSeparatorInset(QMUIDisplayHelper.dp2px(getContext(), 16), 0)
                .addTo(mGroupListView);

        QMUIGroupListView.newSection(getContext())
                .setTitle("")

                .setLeftIconSize(size, ViewGroup.LayoutParams.WRAP_CONTENT)
                .addItemView(quit, listenner)
                .setMiddleSeparatorInset(QMUIDisplayHelper.dp2px(getContext(), 16), 0)
                .addTo(mGroupListView);

    }

}
