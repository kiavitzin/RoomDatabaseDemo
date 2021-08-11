package com.example.roomdatabasedemo;

import android.app.Activity;
import android.app.Dialog;
import android.app.WallpaperManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    //Inicializamos variables
    private List<MainData> dataList;
    private Activity context;
    private RoomDB database;

    //Creamos el constructor
    public MainAdapter(Activity context,List<MainData> dataList){
        this.context = context;
        this.dataList = dataList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inicializamos view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lis_row_main,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Inicializar main data
        MainData data = dataList.get(position);
        // Inicializamos database
        database = RoomDB.getInstance(context);
        //Set text en text view
        holder.textView.setText(data.getText());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicializamos main data
                MainData d = dataList.get(holder.getAdapterPosition());
                // Get id
                int sID = d.getID();
                // Get text
                String sText = d.getText();

                // Create dialog
                Dialog dialog = new Dialog(context);
                // Set content view
                dialog.setContentView(R.layout.dialog_update);
                //Inicializamos width
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                // Inicializamos height
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                // set Layout
                dialog.getWindow().setLayout(width,height);
                // Mostrar dialog
                dialog.show();

                // Inicializamos y asigmnamos variables
                EditText editText = dialog.findViewById(R.id.edit_text);
                Button btnUpdate = dialog.findViewById(R.id.btn_update);

                // Set text en edit text
                editText.setText(sText);

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Dismiss dialog
                        dialog.dismiss();
                        // Obtener el texto actualizado del edit text
                        String uText = editText.getText().toString().trim();
                        // Actualizar el texto en la base de datos
                        database.mainDao().update(sID,uText);
                        // Notificar cuando los datos sean actualizados
                        dataList.clear();
                        dataList.addAll(database.mainDao().getAll());
                        notifyDataSetChanged();
                    }
                });
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicialozamos el main data
                MainData d = dataList.get(holder.getAdapterPosition());
                // Borramos texto de la bnase de datos
                database.mainDao().delete(d);
                // Notificar cuando la base sea borrada
                int position = holder.getAdapterPosition();
                dataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,dataList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //Inicialiamos variables
        TextView textView;
        ImageView btnEdit, btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Asignamos variables
            textView = itemView.findViewById(R.id.text_view);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
