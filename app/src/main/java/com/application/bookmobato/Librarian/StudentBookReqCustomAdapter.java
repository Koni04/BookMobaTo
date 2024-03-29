package com.application.bookmobato.Librarian;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.application.bookmobato.R;
import com.application.bookmobato.Student.BorrowStudentClasses;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;

public class StudentBookReqCustomAdapter extends RecyclerView.Adapter<StudentBookReqCustomAdapter.MyViewHolder> {

    Context context;
    ArrayList<BorrowStudentClasses> list;

    public StudentBookReqCustomAdapter(Context context, ArrayList<BorrowStudentClasses> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public StudentBookReqCustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.myrow_studentbookreq, parent, false);
        return new StudentBookReqCustomAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentBookReqCustomAdapter.MyViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImage()).into(holder.bookImage);
        BorrowStudentClasses studentBookReq = list.get(position);
        holder.title.setText(studentBookReq.getTitle());
        holder.name.setText(studentBookReq.getName());
        holder.setBorrowedDate.setText(studentBookReq.getSetBorrowedDate());

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /*
               Intent intent = new Intent(context, BorrowStudentDetails.class);
               intent.putExtra("image", list.get(holder.getAdapterPosition()).getImage());
               intent.putExtra("title", list.get(holder.getAdapterPosition()).getTitle());
               intent.putExtra("name", list.get(holder.getAdapterPosition()).getName());
               intent.putExtra("borrowedDate", list.get(holder.getAdapterPosition()).getSetBorrowedDate());
               context.startActivity(intent);
               * */

                //Progressing...

                requestDialog();
            }
        });

        holder.mainLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                deleteOneRow();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void searchData(ArrayList<BorrowStudentClasses> searchlist) {
        list = searchlist;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, name, setBorrowedDate;
        ImageView bookImage;

        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.studentReq_title);
            name = itemView.findViewById(R.id.studentReq_name);
            setBorrowedDate = itemView.findViewById(R.id.studentReq_dateBorrowed);
            bookImage = itemView.findViewById(R.id.studentReq_image);
            mainLayout = itemView.findViewById(R.id.studentBookReqLayout);
        }
    }

    private void deleteOneRow() {

        //Simple Dialog box
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setMessage("Do you want to delete this book?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("BookInformationReq");
                ref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Successfully, Book Request Deleted!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, StudentBookReq.class);
                            context.startActivity(intent);
                        }
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

    private void requestDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage("Do you want to approved this book request?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                builder.setCancelable(true);
            }
        });
        builder.create().show();
    }
}