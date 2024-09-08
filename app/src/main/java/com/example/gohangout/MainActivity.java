package com.example.gohangout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText mypageNameString, mypageHomeString, mypageFavorString;
    private Button myPageStorageButton, checkDuplicateButton;
    private TextView myPageLogoutTextView, myPageSecessionTextView;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);

        mypageNameString = findViewById(R.id.Mypage_namestring);
        mypageHomeString = findViewById(R.id.Mypage_homestring);
        mypageFavorString = findViewById(R.id.Mypage_favorstring);

        myPageStorageButton = findViewById(R.id.MyPage_Storagebutton);
        checkDuplicateButton = findViewById(R.id.check);

        myPageLogoutTextView = findViewById(R.id.MyPage_logoutbool);
        myPageSecessionTextView = findViewById(R.id.MyPage_secessionbool);

        loadSavedData();

        mypageNameString.addTextChangedListener(textWatcher);
        mypageHomeString.addTextChangedListener(textWatcher);
        mypageFavorString.addTextChangedListener(textWatcher);

        myPageStorageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        checkDuplicateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNameDuplicate();
            }
        });

        myPageLogoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutDialog();
            }
        });

        myPageSecessionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPolicyDialog();
            }
        });
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            myPageStorageButton.setVisibility(View.VISIBLE);
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    };

    private void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", mypageNameString.getText().toString());
        editor.putString("home", mypageHomeString.getText().toString());
        editor.putString("favor", mypageFavorString.getText().toString());
        editor.apply();

        Toast.makeText(this, "데이터가 저장되었습니다.", Toast.LENGTH_SHORT).show();
        myPageStorageButton.setVisibility(View.INVISIBLE);
    }

    private void loadSavedData() {
        String name = sharedPreferences.getString("name", "");
        String home = sharedPreferences.getString("home", "");
        String favor = sharedPreferences.getString("favor", "");

        mypageNameString.setText(name);
        mypageHomeString.setText(home);
        mypageFavorString.setText(favor);
    }

    private void checkNameDuplicate() {
        String name = mypageNameString.getText().toString();

        if (name.isEmpty()) {
            Toast.makeText(this, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = RetrofitClient.getInstance();
        Call<Boolean> call = apiService.checkDuplicate(name);

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null) {
                    boolean isDuplicate = response.body();
                    if (isDuplicate) {
                        Toast.makeText(MainActivity.this, "아이디가 이미 사용 중입니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "서버 응답 오류: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(MainActivity.this, "네트워크 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void performSecession() {
        ApiService apiService = RetrofitClient.getInstance();
        Call<Void> call = apiService.deleteAccount(); // 서버에 회원탈퇴 요청

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    Toast.makeText(MainActivity.this, "회원탈퇴가 완료되었습니다.", Toast.LENGTH_LONG).show();
                    performLogout(false); // 로그아웃 처리 (메시지 표시 없이)
                } else {
                    Toast.makeText(MainActivity.this, "서버 응답 오류: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "네트워크 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void performLogout(boolean showToast) {
        ApiService apiService = RetrofitClient.getInstance();
        Call<Void> call = apiService.logout(); // 서버에 로그아웃 요청

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    if (showToast) {
                        Toast.makeText(MainActivity.this, "로그아웃되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "서버 응답 오류: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "네트워크 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showLogoutDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.logout_diallog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        Button cancelButton = dialogView.findViewById(R.id.Mypage_nobool);
        Button logoutButton = dialogView.findViewById(R.id.Mypage_yesbool);

        final AlertDialog dialog = builder.create();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogout(true); // 로그아웃 로직 호출 (메시지 표시)
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showPolicyDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.policy_diallog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        Button cancelButton = dialogView.findViewById(R.id.Mypage_nobool1);
        Button agreeButton = dialogView.findViewById(R.id.Mypage_yesbool1);

        final AlertDialog dialog = builder.create();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        agreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSecession(); // 회원탈퇴 로직 호출
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
