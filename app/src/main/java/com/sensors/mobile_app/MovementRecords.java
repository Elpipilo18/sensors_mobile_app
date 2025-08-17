package com.sensors.mobile_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import adapters.MovementRecordAdapter;
import model.MovementRecord;

public class MovementRecords extends AppCompatActivity {

    Button btnGoBack;

    RecyclerView mRecyclerView;

    FirebaseFirestore mfirestore;
    MovementRecordAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movement_records);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnGoBack = findViewById(R.id.btnGoBack);

        mfirestore = FirebaseFirestore.getInstance();
        mRecyclerView = findViewById(R.id.rvMovement);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Query query = mfirestore.collection("movement").orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<MovementRecord> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<MovementRecord>().setQuery(query, MovementRecord.class).build();
        mAdapter = new MovementRecordAdapter(firestoreRecyclerOptions, this);
        mRecyclerView.setAdapter(mAdapter);


        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(MovementRecords.this, MainActivity.class));
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