package com.example.gohangout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText mypageNameString, mypageHomeString, mypageFavorString;
    private Button myPageStorageButton;
    private TextView myPageLogoutTextView, myPageSecessionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // EditText 필드 초기화
        mypageNameString = findViewById(R.id.Mypage_namestring);
        mypageHomeString = findViewById(R.id.Mypage_homestring);
        mypageFavorString = findViewById(R.id.Mypage_favorstring);

        // 버튼 초기화
        myPageStorageButton = findViewById(R.id.MyPage_Storagebutton);

        // TextView 초기화
        myPageLogoutTextView = findViewById(R.id.MyPage_logoutbool);
        myPageSecessionTextView = findViewById(R.id.MyPage_secessionbool);

        // EditText 필드에 TextWatcher 추가
        mypageNameString.addTextChangedListener(textWatcher);
        mypageHomeString.addTextChangedListener(textWatcher);
        mypageFavorString.addTextChangedListener(textWatcher);

        // 로그아웃 TextView에 클릭 리스너 설정
        myPageLogoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutDialog();
            }
        });

        // 회원탈퇴 TextView에 클릭 리스너 설정
        myPageSecessionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPolicyDialog();
            }
        });
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
            // 텍스트 변경 전에는 아무 작업도 필요 없음
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            // 텍스트가 변경되면 버튼을 보이게 함
            myPageStorageButton.setVisibility(View.VISIBLE);
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // 텍스트 변경 후에는 아무 작업도 필요 없음
        }
    };

    private void showLogoutDialog() {
        // 다이얼로그 레이아웃을 인플레이트
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.logout_diallog, null);

        // AlertDialog 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        // 다이얼로그 내 버튼을 찾기
        Button cancelButton = dialogView.findViewById(R.id.Mypage_nobool);
        Button logoutButton = dialogView.findViewById(R.id.Mypage_yesbool);

        final AlertDialog dialog = builder.create();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        // 취소 버튼 클릭 리스너 설정
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // 다이얼로그 닫기
            }
        });

        // 로그아웃 버튼 클릭 리스너 설정
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 로그아웃 작업 처리 (여기에 로그아웃 로직 추가)
                dialog.dismiss(); // 다이얼로그 닫기
            }
        });

        // 다이얼로그 표시
        dialog.show();
    }

    private void showPolicyDialog() {
        // 다이얼로그 레이아웃을 인플레이트

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.policy_diallog, null);

        // AlertDialog 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        // 다이얼로그 내 버튼을 찾기
        Button cancelButton = dialogView.findViewById(R.id.Mypage_nobool);
        Button agreeButton = dialogView.findViewById(R.id.Mypage_yesbool);

        final AlertDialog dialog = builder.create();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        // 취소 버튼 클릭 리스너 설정
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // 다이얼로그 닫기
            }
        });

        // 동의 버튼 클릭 리스너 설정
        agreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 동의 작업 처리 (여기에 동의 로직 추가)
                dialog.dismiss(); // 다이얼로그 닫기
            }
        });

        // 다이얼로그 표시
        dialog.show();
    }
}
