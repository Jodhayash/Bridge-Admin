package com.example.bridge_admin.ShowData;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bridge_admin.Model.TeacherModel;
import com.example.bridge_admin.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class tlist extends AppCompatActivity {

        private RecyclerView dataList;
        private RecyclerView.LayoutManager layoutManager;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        private FirestoreRecyclerAdapter adapter;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tlist);
            dataList = findViewById(R.id.dlist);
            layoutManager = new LinearLayoutManager(this);
            dataList.setLayoutManager(layoutManager);

            getDataList();
        }

        private void getDataList(){
            Query query = db.collection("Teachers")
                    .orderBy("ID");
            FirestoreRecyclerOptions<TeacherModel> options = new FirestoreRecyclerOptions.Builder<TeacherModel>()
                    .setQuery(query, TeacherModel.class)
                    .build();

            adapter = new FirestoreRecyclerAdapter<TeacherModel, TeacherViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull TeacherViewHolder holder, int position, @NonNull TeacherModel model) {
                    holder.setName(model.getName());
                    holder.setAdno(model.getID());
                    holder.setStandard(model.getClass_Handled());
                    holder.setSec(model.getSec_Handled());
                }


                @NonNull
                @Override
                public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dblist, parent, false);
                    return new TeacherViewHolder(view);
                }
            };
            dataList.setAdapter(adapter);
        }
        private class TeacherViewHolder extends RecyclerView.ViewHolder {
            private View view;

            TeacherViewHolder(View itemView) {
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
