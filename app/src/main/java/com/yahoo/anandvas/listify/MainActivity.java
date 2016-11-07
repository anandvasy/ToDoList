package com.yahoo.anandvas.listify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.todoList);
        readItems();

        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);

//        items.add("1st");
//        items.add("2nd");

        setupListViewListeners();
    }

    public void addClicked(View v) {
        EditText newItem = (EditText)findViewById(R.id.addItem);
        String newItemText = newItem.getText().toString();
        if (newItemText.length() > 0) {
            itemsAdapter.add(newItemText);
            writeItems();
        }
        newItem.setText("");
    }

    private void setupListViewListeners() {
        listView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                        items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                }
        );

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                        Intent editIntent = new Intent(MainActivity.this, EditItemActivity.class);
                        editIntent.putExtra("item", items.get(pos));
                        editIntent.putExtra("position", pos);
                        startActivityForResult(editIntent,101);
                    }
                }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101 && data != null) {
            int pos = data.getIntExtra("position", items.size());
            String value = data.getStringExtra("item");

            items.set(pos, value); //--update item value with edited value
            itemsAdapter.notifyDataSetChanged();
            writeItems();
        }

    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir,"todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir,"todo.txt");
        try {
            FileUtils.writeLines(todoFile,items);
        } catch (IOException e) {

        }
    }
}
