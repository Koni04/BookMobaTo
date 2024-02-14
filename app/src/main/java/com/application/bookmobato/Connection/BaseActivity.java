package com.application.bookmobato.Connection;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.application.bookmobato.R;

public class BaseActivity extends AppCompatActivity {

    AlertDialog noInternetDialog;

    public void showNoInternetDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(R.layout.activity_dialog_no_internet);
        builder.setCancelable(false);
        noInternetDialog = builder.create();

        noInternetDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button btnOk = noInternetDialog.findViewById(R.id.btn_ok);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismissNoInternetDialog();
                    }
                });
            }
        });

        noInternetDialog.show();

    }
    public void dismissNoInternetDialog() {
        if (noInternetDialog != null && noInternetDialog.isShowing()) {
            noInternetDialog.dismiss();
            noInternetDialog = null;
        }
    }
}