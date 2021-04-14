package com.example.mytaskinfocom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    public  EditText et_email,et_pwd;
    Button save;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        et_email=(EditText)findViewById(R.id.et_email);
        et_pwd=(EditText)findViewById(R.id.et_password);
        save=(Button) findViewById(R.id.btn_save);
        recyclerView=findViewById(R.id.rv_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask();
            }
        });
        getUsers();

    }

    private void getUsers() {

        SharedPreferences sharedPreferences = getSharedPreferences("MyUserPreferences", MODE_PRIVATE);

        String storedVal=sharedPreferences.getString("list","");
        Type type = new TypeToken<List<Task>>(){}.getType();
        Gson gson = new Gson();
        ArrayList<Task>   arrayItems = gson.fromJson(storedVal, type);
        TaskAdapter adapter=new TaskAdapter(MainActivity.this,arrayItems);
        recyclerView.setAdapter(adapter);



    }

    private void saveTask() {

        final String email = et_email.getText().toString().trim();
        final String pwd = et_pwd.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (email.isEmpty() || !email.matches(emailPattern)) {
            et_email.setError("Enter valid email");
            et_email.requestFocus();
            return;
        }

        else if (pwd.isEmpty()) {
            et_pwd.setError("password required");
            et_pwd.requestFocus();
            return;
        }else {

            SharedPreferences sharedPreferences = getSharedPreferences("MyUserPreferences", MODE_PRIVATE);
            SharedPreferences.Editor prefEditor = sharedPreferences.edit();
            String storedVal=sharedPreferences.getString("list","");


            Gson gson = new Gson();
            if (storedVal.isEmpty()){
                ArrayList<Task> tempList=new ArrayList<>();
                tempList.add(new Task(email,pwd));
                String json = gson.toJson(tempList);
                prefEditor.putString("list",json);
                prefEditor.commit();
                getUsers();
                et_email.setText("");
                et_pwd.setText("");
            }else {


                Type type = new TypeToken<List<Task>>(){}.getType();
               ArrayList<Task>   arrayItems = gson.fromJson(storedVal, type);
               if (arrayItems!=null && !arrayItems.isEmpty()){
                   boolean isExist=false;
                   for (int i=0;i<arrayItems.size(); i++){
                       Task newTask=arrayItems.get(i);
                       if (newTask.getEmail().equals(email)||newTask.pwd.equals(pwd)){
                           isExist=true;
                           break;
                       }
                   }

                   if (isExist){
                       Toast.makeText(MainActivity.this,"Email or number already exist",Toast.LENGTH_LONG).show();
                   }else {
                       arrayItems.add(new Task(email,pwd));
                       String json = gson.toJson(arrayItems);
                       prefEditor.putString("list",json);
                       prefEditor.commit();
                       getUsers();
                       et_email.setText("");
                       et_pwd.setText("");
                   }


               }
            }

            Log.d("values...",sharedPreferences.getString("list","list is emty"));


        }

    }

}