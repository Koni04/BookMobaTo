package com.application.bookmobato.Librarian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.application.bookmobato.Connection.BaseActivity;
import com.application.bookmobato.Connection.NetworkUtils;
import com.application.bookmobato.Dashboard.DashboardLibrarian;
import com.application.bookmobato.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UpdateBookActivity extends AppCompatActivity {

    String title, author, genre, publishdate, numpages, description;

    EditText updateTitle, updateAuthor, updateGenre ,updatePublishdate ,updatePages , updateDescription;
    Button update_button;
    ImageView images_update;
    String key, oldImageURL;
    DatabaseReference databaseReference;
    String imgURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);

        findID();
        datePickerDialogListener();
        connectionCheck();


        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {
            Glide.with(this).load(bundle.getString("image")).into(images_update);
            updateTitle.setText(bundle.getString("title"));
            updateAuthor.setText(bundle.getString("author"));
            updateGenre.setText(bundle.getString("genre"));
            updatePublishdate.setText(bundle.getString("publishdate"));
            updatePages.setText(bundle.getString("numpages"));
            updateDescription.setText(bundle.getString("description"));

            key = bundle.getString("Key");
            oldImageURL = bundle.getString("image");

            // set the imgUrl (String)
            imgURL = bundle.getString("image");
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("BookInformation").child(key);

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });
    }
    public void connectionCheck() {
        //check connection
        BaseActivity baseActivity = new BaseActivity();

        if (!NetworkUtils.isInternetConnected(UpdateBookActivity.this)) {
            baseActivity.showNoInternetDialog(this);
        } else {
            baseActivity.dismissNoInternetDialog();
        }
    }

    private void datePickerDialogListener() {
        updatePublishdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePicker = new DatePickerDialog(UpdateBookActivity.this);
                datePicker.show();
                datePicker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        updatePublishdate.setText(year +"-"+ month +"-"+ day);

                    }
                });
            }
        });
    }

    private void findID() {
        updateTitle = findViewById(R.id.inputTitle2);
        updateAuthor = findViewById(R.id.inputAuthor2);
        updateGenre = findViewById(R.id.inputGenre2);
        updatePublishdate = findViewById(R.id.inputPublishdate2);
        updatePages = findViewById(R.id.inputNumpages2);
        updateDescription = findViewById(R.id.inputDescription2);
        update_button = findViewById(R.id.updateBtn);
        images_update = findViewById(R.id.book_cover2);
    }

    public void updateData() {

        title = updateTitle.getText().toString().trim();
        author = updateAuthor.getText().toString().trim();
        genre = updateGenre.getText().toString().trim();
        publishdate = updatePublishdate.getText().toString().trim();
        numpages = updatePages.getText().toString().trim();
        description = updateDescription.getText().toString().trim();

        BookClasses bookClasses = new BookClasses(title, author, genre, publishdate, numpages, description, imgURL);

        databaseReference.setValue(bookClasses).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(UpdateBookActivity.this, "Successfully, Book Updated!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateBookActivity.this, BookListActivity.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateBookActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UpdateBookActivity.this, BookListActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}