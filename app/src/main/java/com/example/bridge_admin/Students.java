package com.example.bridge_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;


import com.example.bridge_admin.ShowData.dlist;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Students extends AppCompatActivity implements OnClickListener{
    Button astu, mstu,rstu,sdbl;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        astu = findViewById(R.id.addstu);
        mstu = findViewById(R.id.modistu);
        rstu = findViewById(R.id.remstu);
        sdbl =findViewById(R.id.slist);
        astu.setOnClickListener(this);
        mstu.setOnClickListener(this);
        rstu.setOnClickListener(this);
        sdbl.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case (R.id.addstu):
                add_student();
                break;
            case (R.id.modistu):
                modify_student();
                break;
            case (R.id.remstu):
                remove_student();
                break;
            case (R.id.slist):
                list();
                break;
        }
    }

    private void add_student(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Students.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_student,null);
        builder.setView(dialogView);
        boolean ib = false;
        Button one = dialogView.findViewById(R.id.add_S);



        final AlertDialog dialog = builder.create();
        final EditText adno = dialogView.findViewById(R.id.sadno);
        final EditText sname = dialogView.findViewById(R.id.sname);
        final EditText clas = dialogView.findViewById(R.id.sclass);
        final EditText sec = dialogView.findViewById(R.id.ssec);
        final EditText bno = dialogView.findViewById(R.id.sbno);
        final EditText rno = dialogView.findViewById(R.id.srno);


        one.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String s= adno.getText().toString().trim();
                Map<String, Object> student = new HashMap<>();
                student.put("Admission_no",s);
                student.put("Name", sname.getText().toString().trim());
                student.put("Standard", clas.getText().toString().trim());
                student.put("Sec", sec.getText().toString().trim());
                student.put("Roll_no", rno.getText().toString().trim());
                student.put("Bus_no", bno.getText().toString().trim());
                boolean ib = false;
                student.put("InBus",ib);

// Add a new document with a generated ID

                if (s.isEmpty()) {
                    adno.setError("Enter Admission Number to continue");
                }
                else {
                    db.collection("Students").document(s)
                            .set(student)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("1 new entry ", "DocumentSnapshot added with ID: ");
                                    Toast.makeText(Students.this, "Student Added", Toast.LENGTH_LONG).show();
                                    dialog.cancel();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("Entry failed", "Error adding document", e);
                                    Toast.makeText(Students.this, "Error!", Toast.LENGTH_LONG).show();
                                }
                            });
                    dialog.cancel();
                }
            }
        });
        dialog.show();

    }
    private void modify_student(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Students.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.modi_student,null);

        builder.setView(dialogView);

        final Button fetch = dialogView.findViewById(R.id.btfetch);
        final EditText adno = dialogView.findViewById(R.id.sadno);
        final Button bupdate = dialogView.findViewById(R.id.btupdate);
        final EditText sname = dialogView.findViewById(R.id.sname);
        final EditText clas = dialogView.findViewById(R.id.sclass);
        final EditText sec = dialogView.findViewById(R.id.ssec);
        final EditText bno = dialogView.findViewById(R.id.sbno);
        final EditText rno = dialogView.findViewById(R.id.srno);
        sname.setVisibility(View.INVISIBLE);
        clas.setVisibility(View.INVISIBLE);
        sec.setVisibility(View.INVISIBLE);
        bno.setVisibility(View.INVISIBLE);
        rno.setVisibility(View.INVISIBLE);
        bupdate.setVisibility(View.INVISIBLE);



        final AlertDialog dialog = builder.create();


        fetch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String s = adno.getText().toString().trim();
                if (s.isEmpty()) {
                    adno.setError("Enter Admission Number to continue");
                }
                else{

                db.collection("Students")
                        .whereEqualTo("Admission_no", adno.getText().toString().trim())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.getResult().size() == 0){
                                    adno.setError("Enter correct number");
                                }
                                else if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d("+1 Fetch", document.getId() + " => " + document.getData());
                                        String nm= (document.getString("Name"));
                                        sname.setText(nm);
                                        String cl= (document.getString("Standard"));
                                        clas.setText(cl);
                                        String se= (document.getString("Sec"));
                                        sec.setText(se);
                                        String rn= (document.getString("Roll_no"));
                                        rno.setText(rn);
                                        String bn= (document.getString("Bus_no"));
                                        bno.setText(bn);
                                        sname.setVisibility(View.VISIBLE);
                                        clas.setVisibility(View.VISIBLE);
                                        sec.setVisibility(View.VISIBLE);
                                        bno.setVisibility(View.VISIBLE);
                                        rno.setVisibility(View.VISIBLE);
                                        bupdate.setVisibility(View.VISIBLE);
                                        Toast.makeText(Students.this, "Edit the details you want to change", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Log.d("Fetch error", "Error getting documents: ", task.getException());
                                    Toast.makeText(Students.this, "Error!", Toast.LENGTH_LONG).show();
                            }
                            }
                        });


            }}
        });

        bupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("Students").document(adno.getText().toString().trim())
                        .update(
                                "Name", sname.getText().toString().trim(),
                                "Standard", clas.getText().toString().trim(),
                                "Sec", sec.getText().toString().trim(),
                                "Roll_no", rno.getText().toString().trim(),
                                "Bus_no", bno.getText().toString().trim()
                        );
                Toast.makeText(Students.this, "Record Updated", Toast.LENGTH_LONG).show();
                dialog.cancel();
            }
        });
        dialog.show();

    }
    private void remove_student(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Students.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.rem_student,null);

        builder.setView(dialogView);

        Button rem = dialogView.findViewById(R.id.btremove);
        final EditText adno = dialogView.findViewById(R.id.sadno);


        final AlertDialog dialog = builder.create();


        rem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final String s= adno.getText().toString().trim();
                if (s.isEmpty()) {
                    adno.setError("Enter Admission Number to continue");
                }
                else{

                    db.collection("Students")
                            .whereEqualTo("Admission_no", s)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.getResult().size() == 0){
                                        adno.setError("Enter correct number");
                                    }
                                    else if (task.isSuccessful()) {
                                        db.collection("Students").document(s)
                                                .delete()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d("1 record deleted", "DocumentSnapshot successfully deleted!");
                                                        Toast.makeText(Students.this, "Record removed", Toast.LENGTH_LONG).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w("delete nahi ho raha", "Error deleting document", e);
                                                    }
                                                });

                                        dialog.cancel();
                                    } else {
                                        Log.d("Fetch error", "Error getting documents: ", task.getException());
                                        Toast.makeText(Students.this, "Error!", Toast.LENGTH_LONG).show();
                                    }

            }
        });


    }
}
        });
        dialog.show();
    }

    private void list() {
        startActivity(new Intent(Students.this, dlist.class));
}
}
