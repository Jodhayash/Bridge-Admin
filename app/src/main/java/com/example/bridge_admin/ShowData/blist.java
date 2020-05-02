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

import com.example.bridge_admin.Model.BusModel;
import com.example.bridge_admin.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class blist extends AppCompatActivity {
    private RecyclerView dataList;
    private RecyclerView.LayoutManager layoutManager;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blist);
        dataList = findViewById(R.id.bdlist);
        layoutManager = new LinearLayoutManager(this);
        dataList.setLayoutManager(layoutManager);

        getDataList();
    }
    private void getDataList(){
        Query query = db.collection("School_bus")
                .orderBy("Bus_no");
        FirestoreRecyclerOptions<BusModel> options = new FirestoreRecyclerOptions.Builder<BusModel>()
                .setQuery(query, BusModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<BusModel, BusViewHolder>(options) {
            @Override
            protected void onBindViewHolder(BusViewHolder holder, int i, BusModel busModel) {
                holder.setBusno(busModel.getBus_no());
                holder.setDName(busModel.getDriver_Name());
            }

            @NonNull
            @Override
            public BusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buslist, parent, false);
                return new BusViewHolder(view);
            }
        };
        dataList.setAdapter(adapter);
    }
    private class BusViewHolder extends RecyclerView.ViewHolder {
        private View view;

        BusViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        void setBusno(String Bus_no) {
            TextView textView = view.findViewById(R.id.busno);
            textView.setText(Bus_no);
        }
        void setDName(String DriverName) {
            TextView textView = view.findViewById(R.id.dname);
            textView.setText(DriverName);
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
