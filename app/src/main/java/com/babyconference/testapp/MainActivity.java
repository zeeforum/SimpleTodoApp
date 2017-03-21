package com.babyconference.testapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    ListView listView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find list view element
        listView = (ListView) findViewById(R.id.todo_list);

        arrayList = new ArrayList<String>();
        //set arrayList view or display
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        //set arrayAdapter object to listView (in array adapter constructor we defined list item design)
        listView.setAdapter(arrayAdapter);
        //add items or strings to arrayList object
        arrayList.add("First Item");
        arrayList.add("Second Item");

        button = (Button) findViewById(R.id.todo_add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTodo();
            }
        });

        //setup list item long press/click
        setupListRemoveItem();
    }

    private void addTodo() {
        //get textbox reference
        EditText todoTask = (EditText) findViewById(R.id.todo_new_item);
        //convert value into string
        String my_todo = todoTask.getText().toString();
        //show toast message that edit text should not empty
        if (my_todo.equals("")) {
            Toast.makeText(this, "Please add some text to save.", Toast.LENGTH_SHORT).show();
        } else {    //if value not empty, add it to list
            arrayList.add(my_todo);
            todoTask.setText("");
            arrayAdapter.notifyDataSetChanged();

        }
    }

    private void setupListRemoveItem() {
        //if list item is long pressed
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long row) {
                arrayList.remove(position); //remove item from list, which we press for long duration
                arrayAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }

}
