package com.example.roomdatabasedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //inicializamos variables
    EditText editText;
    Button btnAgregar, btnReset;
    RecyclerView recyclerView;

    List<MainData> dataList = new ArrayList<>(); //  en Maindata estan las entidades
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Asignamos variables
        editText = findViewById(R.id.edit_text);
        btnAgregar = findViewById(R.id.btn_agregar);
        btnReset = findViewById(R.id.btn_reset);
        recyclerView = findViewById(R.id.recycler_view);

        // Inicializamos la base de datos
        database = RoomDB.getInstance(this);
        // Store database value in data list
        dataList = database.mainDao().getAll();

        // Inicializamos linear layout manager
        linearLayoutManager = new LinearLayoutManager(this);
        // Set layout manager
        recyclerView.setLayoutManager(linearLayoutManager);
        // Inicializamos el adapter
        adapter = new MainAdapter(MainActivity.this,dataList);
        // Set adapter
        recyclerView.setAdapter(adapter);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el String del edit text
                String sText = editText.getText().toString().trim();
                // Verificamos condicion
                if (!sText.equals("")){
                    // Cuando text no está vacío
                    // Inicializamos main data
                    MainData data = new MainData();
                    // Set text en Main data
                    data.setText(sText);
                    // Insertamos el texto en la base de datos
                    database.mainDao().insert(data);
                    //limpiamos el edit text
                    editText.setText("");
                    // Notificamos cuando los datos sean insertados
                    dataList.clear();
                    dataList.addAll(database.mainDao().getAll());
                    adapter.notifyDataSetChanged();
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Borrar/ Vaciar la base de datos
                database.mainDao().reset(dataList);
                // Notificar cuando todos los datos sean borrados
                dataList.clear();
                dataList.addAll(database.mainDao().getAll());
                adapter.notifyDataSetChanged();
            }
        });
    }
}