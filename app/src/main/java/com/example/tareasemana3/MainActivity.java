package com.example.tareasemana3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText et_nombre,et_costo,et_stock;
    BD admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_nombre = findViewById(R.id.txt_nombre);
        et_costo = findViewById(R.id.txt_costo);
        et_stock = findViewById(R.id.txt_stock);
        admin = new BD(this,"bd1",null,1);
    }

    public void agregar(View v){
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("nombre",et_nombre.getText().toString());
        registro.put("costo",et_costo.getText().toString());
        registro.put("stock",et_stock.getText().toString());
        bd.insert("producto",null,registro);
        limpiar();
        Toast.makeText(this,"Se almaceno el producto",Toast.LENGTH_LONG).show();
        bd.close();
    }

    public void buscar(View v){
        SQLiteDatabase bd = admin.getWritableDatabase();
        String producto  = et_nombre.getText().toString();
        Cursor fila = bd.rawQuery("select nombre, costo,stock from producto where nombre = '"+producto+"'",null);
        if (fila.moveToFirst()){
            et_costo.setText(fila.getString(1));
            et_stock.setText(fila.getString(2));
        }else{
            Toast.makeText(this,"No Existe el producto",Toast.LENGTH_LONG).show();
            limpiar();
        }
    }

    public void limpiar(){
        et_nombre.setText("");
        et_costo.setText("");
        et_stock.setText("");
    }

    public void eliminar(View v){
        SQLiteDatabase bd = admin.getWritableDatabase();
        String nombre= et_nombre.getText().toString();
        int cant = bd.delete("producto", "nombre='" + nombre+"'", null);
        bd.close();
        if (cant == 1) {
            Toast.makeText(this, "Se borró el producto con dicho nombre",
                    Toast.LENGTH_SHORT).show();
            limpiar();
        }else {
            Toast.makeText(this, "No existe el producto con dicho nombre",
                    Toast.LENGTH_SHORT).show();
            limpiar();
        }
    }

    public void actualizar(View v){
        if (et_nombre.getText().toString() != "" && et_costo.getText().toString() != "" && et_stock.getText().toString() != ""){
            SQLiteDatabase bd = admin.getWritableDatabase();
            ContentValues registro = new ContentValues();
            registro.put("nombre", et_nombre.getText().toString());
            registro.put("costo", et_costo.getText().toString());
            registro.put("stock", et_stock.getText().toString());
            int cant = bd.update("producto", registro, "nombre= '" + et_nombre.getText().toString()+"'", null);
            bd.close();
            if (cant == 1) {
                Toast.makeText(this, "se modificaron los datos", Toast.LENGTH_SHORT)
                        .show();
                limpiar();
            }else {
                Toast.makeText(this, "no existe un artículo con el nombre ingresado",
                        Toast.LENGTH_SHORT).show();
                limpiar();
            }
        }
    }
}