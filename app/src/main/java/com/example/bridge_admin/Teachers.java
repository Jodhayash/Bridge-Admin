package com.example.bridge_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bridge_admin.ShowData.tlist;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Teachers extends AppCompatActivity implements OnClickListener {
    Button at, mt,rt, sdbl;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers);
        at = findViewById(R.id.addt);
        mt = findViewById(R.id.modit);
        rt = findViewById(R.id.remt);
        sdbl =findViewById(R.id.tlist);
        at.setOnClickListener(this);
        mt.setOnClickListener(this);
        rt.setOnClickListener(this);
        sdbl.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case (R.id.addt):
                add_teacher();
                break;
            case (R.id.modit):
                modify_teacher();
                break;
            case (R.id.remt):
                remove_teacher();
                break;
            case (R.id.tlist):
                list();
                break;
        }
    }
    private void add_teacher(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Teachers.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_teacher,null);

        builder.setView(dialogView);

        Button add = dialogView.findViewById(R.id.add_t);

        final AlertDialog dialog = builder.create();
        final EditText idno = dialogView.findViewById(R.id.tid);
        final EditText name = dialogView.findViewById(R.id.tname);
        final EditText clas = dialogView.findViewById(R.id.tclass);
        final EditText sec = dialogView.findViewById(R.id.tsec);
        final EditText pass = dialogView.findViewById(R.id.tpass);
        final EditText mno = dialogView.findViewById(R.id.tmno);


        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String cl =clas.getText().toString().trim();
                String se=sec.getText().toString().trim();
                final String idn=idno.getText().toString().trim();
                final Map<String, Object> teacher = new HashMap<>();
                teacher.put("ID",idn);
                teacher.put("Name", name.getText().toString().trim());
                teacher.put("Class_Handled",cl);
                teacher.put("Sec_Handled",se);
                teacher.put("Password", pass.getText().toString().trim());
                teacher.put("Mobile_no", mno.getText().toString().trim());
                if (idn.isEmpty()) {
                    idno.setError("Enter Teacher ID to continue");
                }
                else {
                db.collection("Teachers").document(idn)
                        .set(teacher)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("1 new entry ", "DocumentSnapshot added with ID: ");
                                Toast.makeText(Teachers.this, "Teacher Added", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("Entry failed", "Error adding document", e);
                                Toast.makeText(Teachers.this, "Error!!!", Toast.LENGTH_LONG).show();
                            }
                        });
                dialog.cancel();
                }
            }
        });
        dialog.show();

    }
    private void modify_teacher(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Teachers.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.modi_teacher,null);

        builder.setView(dialogView);

        final Button fetch = dialogView.findViewById(R.id.mtfetch);
        final EditText idno = dialogView.findViewById(R.id.mtidno);
        final Button bupdate = dialogView.findViewById(R.id.mtupdate);
        final EditText name = dialogView.findViewById(R.id.mtname);
        final EditText clas = dialogView.findViewById(R.id.mtclass);
        final EditText sec = dialogView.findViewById(R.id.mtsec);
        final EditText pass = dialogView.findViewById(R.id.mtpass);
        final EditText mno = dialogView.findViewById(R.id.mtmno);
        name.setVisibility(View.INVISIBLE);
        clas.setVisibility(View.INVISIBLE);
        sec.setVisibility(View.INVISIBLE);
        pass.setVisibility(View.INVISIBLE);
        mno.setVisibility(View.INVISIBLE);
        bupdate.setVisibility(View.INVISIBLE);



        final AlertDialog dialog = builder.create();


        fetch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String s = idno.getText().toString().trim();
                if (s.isEmpty()) {
                    idno.setError("Enter Teacher Id to continue");
                }
                else{
                    db.collection("Teachers")
                            .whereEqualTo("ID", idno.getText().toString().trim())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.getResult().size() == 0){
                                        idno.setError("Enter correct number");
                                    }
                                    else if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Log.d("+1 Fetch", document.getId() + " => " + document.getData());
                                            String nm= (document.getString("Name"));
                                            name.setText(nm);
                                            String cl= (document.getString("Class_Handled"));
                                            clas.setText(cl);
                                            String se= (document.getString("Sec_Handled"));
                                            sec.setText(se);
                                            String rn= (document.getString("Password"));
                                            pass.setText(rn);
                                            String bn= (document.getString("Mobile_no"));
                                            mno.setText(bn);
                                            name.setVisibility(View.VISIBLE);
                                            clas.setVisibility(View.VISIBLE);
                                            sec.setVisibility(View.VISIBLE);
                                            pass.setVisibility(View.VISIBLE);
                                            mno.setVisibility(View.VISIBLE);
                                            bupdate.setVisibility(View.VISIBLE);
                                            Toast.makeText(Teachers.this, "Edit the details you want to change", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Log.d("Fetch error", "Error getting documents: ", task.getException());
                                        Toast.makeText(Teachers.this, "Error!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });


                }}
        });

        bupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("Teachers").document(idno.getText().toString().trim())
                        .update(
                                "Name", name.getText().toString().trim(),
                                "Class_Handled", clas.getText().toString().trim(),
                                "Sec_Handled", sec.getText().toString().trim(),
                                "Password", pass.getText().toString().trim(),
                                "Mobile_no", mno.getText().toString().trim()
                        );
                Toast.makeText(Teachers.this, "Record Updated", Toast.LENGTH_LONG).show();
                dialog.cancel();
            }
        });
        dialog.show();


    }

    private void remove_teacher(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Teachers.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.rem_teacher,null);
        builder.setView(dialogView);
        Button rem = dialogView.findViewById(R.id.btremove);
        final EditText idno = dialogView.findViewById(R.id.ridno);
        final String s=idno.getText().toString().trim();
        final AlertDialog dialog = builder.create();
        rem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final String s=idno.getText().toString().trim();
                if (s.isEmpty()) {
                    idno.setError("Enter Teacher Id continue");
                }
                else{
                    db.collection("Teachers")
                            .whereEqualTo("ID", idno.getText().toString().trim())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.getResult().size() == 0){
                                        idno.setError("Enter correct ID");
                                    }
                                    else if (task.isSuccessful()) {
                                        db.collection("Teachers").document(idno.getText().toString().trim())
                                                .delete()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d("1 record deleted", "DocumentSnapshot successfully deleted!");
                                                        Toast.makeText(Teachers.this, "Record removed", Toast.LENGTH_LONG).show();
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
                                        Toast.makeText(Teachers.this, "Error!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

            }}
        });

        dialog.show();

    }
    private void list() {
        startActivity(new Intent(Teachers.this, tlist.class));
    }
 }
