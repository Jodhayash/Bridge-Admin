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

import com.example.bridge_admin.ShowData.blist;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Buses extends AppCompatActivity implements OnClickListener{
    Button ab, mb,rb,bl;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buses);
        ab = findViewById(R.id.addb);
        mb = findViewById(R.id.modib);
        rb = findViewById(R.id.remb);
        bl = findViewById(R.id.blist);
        ab.setOnClickListener(this);
        mb.setOnClickListener(this);
        rb.setOnClickListener(this);
        bl.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case (R.id.addb):
                add_bus();
                break;
            case (R.id.modib):
                modify_bus();
                break;
            case (R.id.remb):
                remove_bus();
                break;
            case (R.id.blist):
                list();
                break;
        }
}
    private void add_bus(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Buses.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_bus,null);

        builder.setView(dialogView);

        Button one = dialogView.findViewById(R.id.add_b);



        final AlertDialog dialog = builder.create();
        final EditText bno = dialogView.findViewById(R.id.bid);
        final EditText name = dialogView.findViewById(R.id.dname);
        final EditText pass = dialogView.findViewById(R.id.dpass);
        final EditText mno = dialogView.findViewById(R.id.dmno);


        one.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final String s = bno.getText().toString().trim();
                Map<String, Object> bus = new HashMap<>();
                bus.put("Bus_no",bno.getText().toString().trim());
                bus.put("Driver_Name", name.getText().toString().trim());
                bus.put("Driver_Password", pass.getText().toString().trim());
                bus.put("Driver_Number", mno.getText().toString().trim());

                if (s.isEmpty()) {
                    bno.setError("Enter Bus Number to continue");
                }
                else {
                    db.collection("School_bus").document(s)
                            .set(bus)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("1 new entry ", "DocumentSnapshot added with ID: ");
                                    Toast.makeText(Buses.this, "Bus Added", Toast.LENGTH_LONG).show();
                                    dialog.cancel();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("Entry failed", "Error adding bus", e);
                                    Toast.makeText(Buses.this, "Error!", Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });
        dialog.show();
    }
    private void modify_bus(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Buses.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.modi_bus,null);

        builder.setView(dialogView);

        final Button fetch = dialogView.findViewById(R.id.mbfetch);
        final EditText bno = dialogView.findViewById(R.id.mbno);
        final Button bupdate = dialogView.findViewById(R.id.mbupdate);
        final EditText name = dialogView.findViewById(R.id.mdname);
        final EditText pass = dialogView.findViewById(R.id.mdpass);
        final EditText mno = dialogView.findViewById(R.id.mdmno);
        name.setVisibility(View.INVISIBLE);
        pass.setVisibility(View.INVISIBLE);
        mno.setVisibility(View.INVISIBLE);
        bupdate.setVisibility(View.INVISIBLE);



        final AlertDialog dialog = builder.create();


        fetch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String s = bno.getText().toString().trim();
                if (s.isEmpty()) {
                    bno.setError("Enter Bus_no to continue");
                }
                else{
                    db.collection("School_bus")
                            .whereEqualTo("Bus_no", bno.getText().toString().trim())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.getResult().size() == 0){
                                        bno.setError("Enter correct number");
                                    }
                                    else if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Log.d("+1 Fetch", document.getId() + " => " + document.getData());
                                            String nm= (document.getString("Driver_Name"));
                                            name.setText(nm);
                                            String rn= (document.getString("Driver_Password"));
                                            pass.setText(rn);
                                            String bn= (document.getString("Driver_Number"));
                                            mno.setText(bn);
                                            name.setVisibility(View.VISIBLE);
                                            pass.setVisibility(View.VISIBLE);
                                            mno.setVisibility(View.VISIBLE);
                                            bupdate.setVisibility(View.VISIBLE);
                                            Toast.makeText(Buses.this, "Edit the details you want to change", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Log.d("Fetch error", "Error getting documents: ", task.getException());
                                        Toast.makeText(Buses.this, "Error!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });


                }}
        });

        bupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("School_bus").document(bno.getText().toString().trim())
                        .update(
                                "Driver_Name", name.getText().toString().trim(),
                                "Driver_Password", pass.getText().toString().trim(),
                                "Driver_Number", mno.getText().toString().trim()
                        );
                Toast.makeText(Buses.this, "Record Updated", Toast.LENGTH_LONG).show();
                dialog.cancel();
            }
        });
        dialog.show();
    }
    private void remove_bus(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Buses.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.rem_bus,null);
        builder.setView(dialogView);
        Button rem = dialogView.findViewById(R.id.btbremove);
        final EditText bno = dialogView.findViewById(R.id.rbno);
        final AlertDialog dialog = builder.create();
        rem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final String s = bno.getText().toString().trim();
                if (s.isEmpty()) {
                    bno.setError("Enter Bus_no to continue");
                }
                else{
                    db.collection("School_bus")
                            .whereEqualTo("Bus_no", s)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.getResult().size() == 0){
                                        bno.setError("Enter correct number");
                                    }
                                    else if (task.isSuccessful()) {
                                        db.collection("School_bus").document(s)
                                                .delete()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d("1 record deleted", "DocumentSnapshot successfully deleted!");
                                                        Toast.makeText(Buses.this, "Record removed", Toast.LENGTH_LONG).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w("delete nahi ho raha", "Error deleting document", e);
                                                        Toast.makeText(Buses.this, "Unable to delete", Toast.LENGTH_LONG).show();
                                                    }
                                                });

                                        dialog.cancel();
                                        }
                                    else {
                                        Log.d("Fetch error", "Error getting documents: ", task.getException());
                                        Toast.makeText(Buses.this, "Error!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });


                }

                /**/
            }
        });
        dialog.show();
    }
    private void list() {
        startActivity(new Intent(Buses.this, blist.class));
    }
}
