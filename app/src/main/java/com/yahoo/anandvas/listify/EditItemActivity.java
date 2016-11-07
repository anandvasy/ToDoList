package com.yahoo.anandvas.listify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    EditText editText;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        String item = getIntent().getStringExtra("item");
        pos = getIntent().getIntExtra("position", 0);

        editText = (EditText) findViewById(R.id.editTxt);
        editText.setText(item);
    }

    public void onSave(View view) {

        Intent editIntent = new Intent();
        editIntent.putExtra("item", editText.getText().toString());
        editIntent.putExtra("position", pos);

        setResult(101, editIntent);

        finish();

    }
}
