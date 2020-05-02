package com.example.bridge_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class Parents extends AppCompatActivity implements OnClickListener{
    Button ap, mp,rp;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parents);
        ap = findViewById(R.id.addp);
        mp = findViewById(R.id.modip);
        rp = findViewById(R.id.remp);
        ap.setOnClickListener(this);
        mp.setOnClickListener(this);
        rp.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case (R.id.addp):
                add_parent();
                break;
            case (R.id.modip):
                modify_parent();
                break;
            case (R.id.remp):
                remove_parent();
                break;
        }
    }
    private void add_parent(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Parents.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_parent,null);
        builder.setView(dialogView);

        Button add = dialogView.findViewById(R.id.add_p);

        final AlertDialog dialog = builder.create();
        final EditText adno = dialogView.findViewById(R.id.sid);
        final EditText name = dialogView.findViewById(R.id.pname);
        final EditText pass = dialogView.findViewById(R.id.ppass);
        final EditText mno = dialogView.findViewById(R.id.pmno);


        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final String idn=adno.getText().toString().trim();
                final Map<String, Object> parent = new HashMap<>();
                parent.put("ID",idn);
                parent.put("Name", name.getText().toString().trim());
                parent.put("Password", pass.getText().toString().trim());
                parent.put("Mobile_no", mno.getText().toString().trim());


                if (idn.isEmpty()) {
                    adno.setError("Enter Admission Number to continue");
                }
                else{
                db.collection("Students")
                        .whereEqualTo("Admission_no", idn)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.getResult().size() == 0){
                                    adno.setError("Admission Number does not exist");
                                }
                                else if (task.isSuccessful()) {
                                    db.collection("Parents").document(idn)
                                            .set(parent)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d("1 new entry ", "DocumentSnapshot added with ID: ");
                                                    Toast.makeText(Parents.this, "Parent Added", Toast.LENGTH_LONG).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w("Entry failed", "Error adding document", e);
                                                    Toast.makeText(Parents.this, "Error!!!", Toast.LENGTH_LONG).show();
                                                }
                                            });
                                    dialog.cancel();
                                } else {
                                    Log.d("Fetch error", "Error getting documents: ", task.getException());
                                    Toast.makeText(Parents.this, "Error!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }}
        });
        dialog.show();
    }
    private void modify_parent(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Parents.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.modi_parent,null);

        builder.setView(dialogView);

        final Button fetch = dialogView.findViewById(R.id.mpfetch);
        final EditText idno = dialogView.findViewById(R.id.mpidno);
        final Button bupdate = dialogView.findViewById(R.id.mpupdate);
        final EditText name = dialogView.findViewById(R.id.mpname);
        final EditText pass = dialogView.findViewById(R.id.mppass);
        final EditText mno = dialogView.findViewById(R.id.mpmno);
        name.setVisibility(View.INVISIBLE);
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
                    idno.setError("Enter Admission Number to continue");
                }
                else{
                    db.collection("Parents")
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
                                            String rn= (document.getString("Password"));
                                            pass.setText(rn);
                                            String bn= (document.getString("Mobile_no"));
                                            mno.setText(bn);
                                            name.setVisibility(View.VISIBLE);
                                            pass.setVisibility(View.VISIBLE);
                                            mno.setVisibility(View.VISIBLE);
                                            bupdate.setVisibility(View.VISIBLE);
                                            Toast.makeText(Parents.this, "Edit the details you want to change", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Log.d("Fetch error", "Error getting documents: ", task.getException());
                                        Toast.makeText(Parents.this, "Error!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });


                }}
        });

        bupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("Parents").document(idno.getText().toString().trim())
                        .update(
                                "Name", name.getText().toString().trim(),
                                "Password", pass.getText().toString().trim(),
                                "Mobile_no", mno.getText().toString().trim()
                        );
                Toast.makeText(Parents.this, "Record Updated", Toast.LENGTH_LONG).show();
                dialog.cancel();
            }
        });
        dialog.show();
    }
    private void remove_parent(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Parents.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.rem_parent,null);
        builder.setView(dialogView);
        Button rem = dialogView.findViewById(R.id.btpremove);
        final EditText idno = dialogView.findViewById(R.id.rpidno);

        final AlertDialog dialog = builder.create();
        rem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String s= idno.getText().toString().trim();
                if (s.isEmpty()) {
                    idno.setError("Enter Admission Number to continue");
                }
                else{
                    db.collection("Parents")
                        .whereEqualTo("ID", s)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.getResult().size() == 0){
                                    idno.setError("Enter correct number");
                                }
                                else if (task.isSuccessful()) {
                                    db.collection("Parents").document(idno.getText().toString().trim())
                                            .delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d("1 record deleted", "DocumentSnapshot successfully deleted!");
                                                    Toast.makeText(Parents.this, "Record removed", Toast.LENGTH_LONG).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w("delete nahi ho raha", "Error deleting document", e);
                                                }
                                            });
                                    }
                                else {
                                    Log.d("Fetch error", "Error getting documents: ", task.getException());
                                    Toast.makeText(Parents.this, "Error!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });


                dialog.cancel();
            }}
        });
        dialog.show();

    }
    }

