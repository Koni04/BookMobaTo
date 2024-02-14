package com.application.bookmobato.Librarian;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.application.bookmobato.Connection.BaseActivity;
import com.application.bookmobato.Connection.NetworkUtils;
import com.application.bookmobato.Dashboard.DashboardLibrarian;
import com.application.bookmobato.MainLogin.LibrarianLoginActivity;
import com.application.bookmobato.MainLogin.MainLoginActivity;
import com.application.bookmobato.R;
import com.application.bookmobato.Student.StudentAccountInformationX;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class StudentAccountInformation extends AppCompatActivity {

    ImageView studentImage;
    TextView id, name, level, section, strand, pass;

    String key = "";
    String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_account_information);

        findID();
        connectionCheck();

        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {
            key = bundle.getString("Key");
            imageUrl = bundle.getString("image");
            //
            Glide.with(this).load(bundle.getString("image")).into(studentImage);
            id.setText(bundle.getString("id"));
            name.setText(bundle.getString("name"));
            level.setText(bundle.getString("level"));
            section.setText(bundle.getString("section"));
            strand.setText(bundle.getString("strand"));
            pass.setText(bundle.getString("pass"));
        }
    }

    private void findID() {
        id = findViewById(R.id.student_id_txt);
        name = findViewById(R.id.student_name_txt);
        level = findViewById(R.id.student_level_txt);
        section = findViewById(R.id.student_section_txt);
        strand = findViewById(R.id.student_strand_txt);
        pass = findViewById(R.id.student_pass_txt);
        studentImage = findViewById(R.id.imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.delete_onerow, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.delete_row) {
            deleteOneRow();
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteOneRow() {

        String title = name.getText().toString();

        //Simple Dialog box
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Name: " + title);
        builder.setCancelable(false);
        builder.setMessage("Do you want to delete this user?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("UserInformation");
                FirebaseStorage storage = FirebaseStorage.getInstance();

                StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        databaseReference.child(key).removeValue();
                        Toast.makeText(StudentAccountInformation.this, "Successfully, User Deleted!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(StudentAccountInformation.this, StudentActivityList.class);
                        startActivity(intent);
                    }
                });
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.setCancelable(true);
            }
        });
        builder.create().show();
    }

    public void connectionCheck() {
        //check connection
        BaseActivity baseActivity = new BaseActivity();

        if (!NetworkUtils.isInternetConnected(StudentAccountInformation.this)) {
            baseActivity.showNoInternetDialog(this);
        } else {
            baseActivity.dismissNoInternetDialog();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(StudentAccountInformation.this, StudentActivityList.class);
        startActivity(intent);
        super.onBackPressed();
    }
}