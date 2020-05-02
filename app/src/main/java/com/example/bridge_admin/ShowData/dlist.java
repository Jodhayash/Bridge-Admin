package com.example.bridge_admin.ShowData;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import com.example.bridge_admin.Model.StudentModel;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.bridge_admin.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class dlist extends AppCompatActivity {

    private RecyclerView dataList;
    private RecyclerView.LayoutManager layoutManager;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirestoreRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dlist);
        dataList = findViewById(R.id.dlist);
        layoutManager = new LinearLayoutManager(this);
        dataList.setLayoutManager(layoutManager);

        getDataList();
    }

    private void getDataList(){
        Query query = db.collection("Students")
                .orderBy("Admission_no");
        FirestoreRecyclerOptions<StudentModel> options = new FirestoreRecyclerOptions.Builder<StudentModel>()
                .setQuery(query, StudentModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<StudentModel, StudentViewHolder>(options) {
            @Override
            protected void onBindViewHolder(StudentViewHolder holder, int i, StudentModel studentModel) {
                holder.setName(studentModel.getName());
                holder.setAdno(studentModel.getAdmission_no());
                holder.setStandard(studentModel.getStandard());
                holder.setSec(studentModel.getSec());
            }

            @NonNull
            @Override
            public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dblist, parent, false);
                return new StudentViewHolder(view);
            }
        };
        dataList.setAdapter(adapter);
    }
    private class StudentViewHolder extends RecyclerView.ViewHolder {
        private View view;

        StudentViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        void setAdno(String adno) {
            TextView textView = view.findViewById(R.id.dadno);
            textView.setText(adno);
        }
        void setName(String Name) {
            TextView textView = view.findViewById(R.id.dname);
            textView.setText(Name);
        }
        void setStandard(String Standard) {
            TextView textView = view.findViewById(R.id.dclas);
            textView.setText(Standard);
        }
        void setSec(String Sec) {
            TextView textView = view.findViewById(R.id.dsec);
            textView.setText(Sec);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (adapter != null) {
            adapter.stopListening();
        }
    }
}
