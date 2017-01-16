package com.example.usuario.apppruebatecnicatekus.Beans;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.usuario.apppruebatecnicatekus.R;

import java.util.ArrayList;

/**
 * Created by Usuario on 15/01/2017.
 */

public class Notification  extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> itemname;
    private final Integer[] integers;


    public Notification(Activity context, ArrayList<String> itemname, Integer [] integers, Context CONTEXT) {
        super(context, R.layout.row_list, itemname);

        this.context=context;
        this.itemname=itemname;
        this.integers=integers;


    }


    public View getView(int posicion, View view, ViewGroup parent){

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.row_list, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.texto_principal);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView etxDescripcion = (TextView) rowView.findViewById(R.id.texto_secundario);
        TextView textoTercero= (TextView) rowView.findViewById(R.id.texto_tercero);

        txtTitle.setText(itemname.get(posicion));
       // imageView.setImageResource(R.drawable.icon_male);
        etxDescripcion.setText("Description " + itemname.get(posicion));
       // textoTercero.setText("Clave "+ user.buscarClave(db,itemname.get(posicion)));

        System.out.println("ingegers "+integers);

        return rowView;
    }
}
