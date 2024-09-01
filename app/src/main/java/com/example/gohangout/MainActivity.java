package com.example.gohangout;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private TextView myPagePolicybool;
    private Button myPageStorageButton;
    private BottomNavigationView bottomNavigationView;
    private BadgeDrawable badgeDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myPagePolicybool = findViewById(R.id.MyPage_policybool);
        myPageStorageButton = findViewById(R.id.MyPage_Storagebutton);

        setupBadge();

        myPagePolicybool.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    myPageStorageButton.setVisibility(View.VISIBLE);
                } else {
                    myPageStorageButton.setVisibility(View.GONE);
                }
            }
        });

        myPagePolicybool.setOnClickListener(v -> showPolicyDialog());

        myPageStorageButton.setOnClickListener(v -> showLogoutDialog());
    }

    private void setupBadge() {
        badgeDrawable.setVerticalOffset(DpToPx(this, 3));
        badgeDrawable.setHorizontalOffset(DpToPx(this, 1));
        badgeDrawable.setNumber(3);
        badgeDrawable.setBackgroundColor(ContextCompat.getColor(this, R.color.black));
        badgeDrawable.setBadgeTextColor(ContextCompat.getColor(this, R.color.white));
    }

    private void showPolicyDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.policy_diallog, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.logout_diallog, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        Button yesButton = dialogView.findViewById(R.id.Mypage_yesbool);
        Button noButton = dialogView.findViewById(R.id.Mypage_nobool);

        yesButton.setOnClickListener(v -> {
            dialog.dismiss();
        });

        noButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private int DpToPx(Context context, int dp) {
        Resources resources = context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics()));
    }
}
