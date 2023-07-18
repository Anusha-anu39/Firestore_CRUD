package com.example.firestorecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        TextInputEditText firstNameET = findViewById(R.id.firstNameET);
        TextInputEditText lastNameET = findViewById(R.id.lastNameET);
        TextInputEditText phoneET = findViewById(R.id.phoneET);
        TextInputEditText bioET = findViewById(R.id.bioET);
        MaterialButton addUser = findViewById(R.id.addUser);

        addUser.setOnClickListener(view -> {
            try {
                Map<String, Object> user = new HashMap<>();
                user.put("firstName", Objects.requireNonNull(firstNameET.getText()).toString());
                user.put("lastName", Objects.requireNonNull(lastNameET.getText()).toString());
                user.put("phone", Objects.requireNonNull(phoneET.getText()).toString());
                user.put("bio", Objects.requireNonNull(bioET.getText()).toString());

                db.collection("users").add(user).addOnSuccessListener(documentReference -> {
                    Toast.makeText(AddUserActivity.this, "User Added Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }).addOnFailureListener(e ->
                        Toast.makeText(AddUserActivity.this, "There was an error while adding user", Toast.LENGTH_SHORT).show());
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(AddUserActivity.this, "An error occurred.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
