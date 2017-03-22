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

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
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

        //read items from the file
        readItems();

        //set arrayList view or display
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        //set arrayAdapter object to listView (in array adapter constructor we defined list item design)
        listView.setAdapter(arrayAdapter);

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

            //save item in the file
            saveItems();
        }
    }

    private void setupListRemoveItem() {
        //if list item is long pressed
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long row) {
                arrayList.remove(position); //remove item from list, which we press for long duration
                arrayAdapter.notifyDataSetChanged();

                //save item in the file
                saveItems();
                return true;
            }
        });
    }


    private void readItems() {
        File fileDir = getFilesDir();  //getting file directory
        File todoFile = new File(fileDir, "todo.txt");  //opening file
        try {   //try to read file and store in arrayList object
            arrayList = new ArrayList<String>(FileUtils.readLines(todoFile, "UTF-8"));   //FileUtils is a library, in 'libs' folder  a Jar file
        } catch (IOException e) {   //catch exception if any occur
            arrayList = new ArrayList<String>();
            e.printStackTrace();
        }
    }

    private void saveItems() {
        File fileDir = getFilesDir();  //getting file directory
        File todoFile = new File(fileDir, "todo.txt");  //opening file
        try {   //try to write in file with delimited newline
            FileUtils.writeLines(todoFile, arrayList);
        } catch (IOException e) {   //catch exception if any occur
            e.printStackTrace();
        }
    }

}
