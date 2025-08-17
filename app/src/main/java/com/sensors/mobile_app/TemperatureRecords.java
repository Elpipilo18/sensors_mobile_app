package com.sensors.mobile_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import adapters.TemperatureRecordAdapter;
import model.TemperatureRecord;

public class TemperatureRecords extends AppCompatActivity {

    Button btnGoBack;

    FirebaseFirestore mfirestore;

    RecyclerView mRecyclerView;

    TemperatureRecordAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_temperature_records);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnGoBack = findViewById(R.id.btnGoBack);

        mfirestore = FirebaseFirestore.getInstance();
        mRecyclerView = findViewById(R.id.rvTemperature);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Query query = mfirestore.collection("temperature").orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<TemperatureRecord> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<TemperatureRecord>().setQuery(query, TemperatureRecord.class).build();
        mAdapter = new TemperatureRecordAdapter(firestoreRecyclerOptions, this);
        mRecyclerView.setAdapter(mAdapter);

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(TemperatureRecords.this, MainActivity.class));
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }
}