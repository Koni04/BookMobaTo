package com.application.bookmobato.MainLogin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.application.bookmobato.Connection.BaseActivity;
import com.application.bookmobato.Connection.NetworkUtils;
import com.application.bookmobato.R;

public class MainLoginActivity extends AppCompatActivity{

    CardView librarian_btn,student_btn;
    private String Tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        findID();
        Librarian();
        Student();

        //check connection
        BaseActivity baseActivity = new BaseActivity();

        if (!NetworkUtils.isInternetConnected(MainLoginActivity.this)) {
            baseActivity.showNoInternetDialog(this);
            Log.d(Tag,"No Internet Connection");
        } else {
            baseActivity.dismissNoInternetDialog();
            Log.d(Tag,"Internet Connection Restored");
        }
    }
    private void Student() {
        student_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent studentLogin = new Intent(MainLoginActivity.this, StudentLoginActivity.class);
                startActivity(studentLogin);
            }
        });
    }

    private void Librarian() {
        librarian_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent librarianLogin = new Intent(MainLoginActivity.this, LibrarianLoginActivity.class);
                startActivity(librarianLogin);
            }
        });
    }

    private void findID() {
        librarian_btn = findViewById(R.id.librarian_btn);
        student_btn = findViewById(R.id.student_btn);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to exit an app");
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainLoginActivity.this, "Thank your for using the application -By: Koni", Toast.LENGTH_SHORT).show();
                finishAffinity();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.setCancelable(true);
            }
        });
        builder.create().show();
    }
}
