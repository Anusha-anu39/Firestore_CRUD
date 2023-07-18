package com.example.firestorecrud;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(MainActivity.this);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        RecyclerView recyclerView = findViewById(R.id.recycler);

        FloatingActionButton add = findViewById(R.id.addUser);
        add.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AddUserActivity.class)));

        try {
            db.collection("users").get().addOnCompleteListener(task -> {
                try {
                    if (task.isSuccessful()) {
                        ArrayList<User> arrayList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            User user = document.toObject(User.class);
                            user.setId(document.getId());
                            arrayList.add(user);
                        }
                        UserAdapter adapter = new UserAdapter(MainActivity.this, arrayList);
                        recyclerView.setAdapter(adapter);

                        adapter.setOnItemClickListener(user -> {
                            App.user = user;
                            startActivity(new Intent(MainActivity.this, EditUserActivity.class));
                        });
                    } else {
                        Toast.makeText(MainActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "An error occurred.", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "An error occurred.", Toast.LENGTH_SHORT).show();
        }

        FloatingActionButton refresh = findViewById(R.id.refresh);
        refresh.setOnClickListener(view -> {
            try {
                db.collection("users").get().addOnCompleteListener(task -> {
                    try {
                        if (task.isSuccessful()) {
                            ArrayList<User> arrayList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User user = document.toObject(User.class);
                                user.setId(document.getId());
                                arrayList.add(user);
                            }
                            UserAdapter adapter = new UserAdapter(MainActivity.this, arrayList);
                            recyclerView.setAdapter(adapter);

                            adapter.setOnItemClickListener(user -> {
                                App.user = user;
                                startActivity(new Intent(MainActivity.this, EditUserActivity.class));
                            });
                        } else {
                            Toast.makeText(MainActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "An error occurred.", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "An error occurred.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
