package com.example.firestorecrud;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        TextInputEditText firstNameET = findViewById(R.id.firstNameET);
        TextInputEditText lastNameET = findViewById(R.id.lastNameET);
        TextInputEditText phoneET = findViewById(R.id.phoneET);
        TextInputEditText bioET = findViewById(R.id.bioET);
        MaterialButton save = findViewById(R.id.save);
        MaterialButton delete = findViewById(R.id.delete);

        firstNameET.setText(App.user.getFirstName());
        lastNameET.setText(App.user.getLastName());
        phoneET.setText(App.user.getPhone());
        bioET.setText(App.user.getBio());

        delete.setOnClickListener(view -> {
            try {
                db.collection("users").document(App.user.getId()).delete().addOnSuccessListener(aVoid -> {
                    Toast.makeText(EditUserActivity.this, "User Deleted Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }).addOnFailureListener(e ->
                        Toast.makeText(EditUserActivity.this, "Error while deleting user", Toast.LENGTH_SHORT).show());
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(EditUserActivity.this, "An error occurred.", Toast.LENGTH_SHORT).show();
            }
        });

        save.setOnClickListener(view -> {
            try {
                Map<String, Object> user = new HashMap<>();
                user.put("firstName", Objects.requireNonNull(firstNameET.getText()).toString());
                user.put("lastName", Objects.requireNonNull(lastNameET.getText()).toString());
                user.put("phone", Objects.requireNonNull(phoneET.getText()).toString());
                user.put("bio", Objects.requireNonNull(bioET.getText()).toString());


                db.collection("users").document(App.user.getId()).set(user).addOnSuccessListener(aVoid -> {
                    Toast.makeText(EditUserActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }).addOnFailureListener(e ->
                        Toast.makeText(EditUserActivity.this, "Error while saving users", Toast.LENGTH_SHORT).show());
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(EditUserActivity.this, "An error occurred.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}