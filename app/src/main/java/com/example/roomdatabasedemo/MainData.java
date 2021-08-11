package com.example.roomdatabasedemo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//Definimos el nombre de la tabla
@Entity(tableName = "table_name")
public class MainData implements Serializable {
    // creamos la columna id
    @PrimaryKey(autoGenerate = true)
    private int ID;

    // Creamos la columna text
    @ColumnInfo(name = "text")
    private  String text;

    //Generamos los getters y setters

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
