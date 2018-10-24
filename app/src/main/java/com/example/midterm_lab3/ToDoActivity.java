package com.example.midterm_lab3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ToDoActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String ITEMS = "items";

    private EditText mTitleInput;
    private EditText mContentInput;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mUser;

    private String mSelectedItems;
    private String uId;

    private Spinner mSpinner;
    private Button mAddBtn;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        getSupportActionBar().hide();

        initUI();
    }

    private void initUI() {
        mTitleInput = findViewById(R.id.titleInput);
        mContentInput = findViewById(R.id.contentInput);
        mSpinner = findViewById(R.id.spinner);
        mAddBtn = findViewById(R.id.addItemsBtn);

        mAddBtn.setOnClickListener(this);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mReference = mFirebaseDatabase.getReference(ITEMS);
        mUser = mFirebaseAuth.getCurrentUser();
        uId = mUser.getUid();

        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(this, R.array.priorityList,
                        android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//        mSpinner.setSelection(1);
        mSpinner.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.addItemsBtn):
                mSelectedItems = mSpinner.getSelectedItem().toString();
                saveNewItem(uId);
                break;
        }
    }

    private void saveNewItem(String uId) {
        String title = mTitleInput.getText().toString();
        String content = mContentInput.getText().toString();
        String priority = mSelectedItems;

        if (!(title.isEmpty() && content.isEmpty())) {
            String itemId = mReference.push().getKey();
            Item item = new Item(uId, title, content, priority);
            mReference.child(itemId).setValue(item);
            Toast.makeText(this, "New item added", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, HomeActivity.class));
            finish();
            return;
        }

        Toast.makeText(this, "Fill the gaps", Toast.LENGTH_LONG).show();

    }
}
