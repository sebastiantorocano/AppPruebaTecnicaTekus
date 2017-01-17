package com.example.usuario.apppruebatecnicatekus;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Icon;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.apppruebatecnicatekus.Beans.NotificationBean;
import com.example.usuario.apppruebatecnicatekus.Connection.DataBase;
import com.example.usuario.apppruebatecnicatekus.Controllers.NotificationsController;
import com.example.usuario.apppruebatecnicatekus.Models.NotificationsModel;
import com.example.usuario.apppruebatecnicatekus.Util.AsyncTasks.DeleteNotificationAsync;
import com.example.usuario.apppruebatecnicatekus.Util.AsyncTasks.DownloadNotificationAsync;
import com.example.usuario.apppruebatecnicatekus.Util.Useful;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class AdministrationActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    DataBase dataBase;
    NotificationsController notificationsController;
    Useful useful;
    NotificationsModel notificationsModel;


    private ArrayList<NotificationBean> m_localsNotifications = null;
    private IconListViewAdapter adapterNotfication;


    ListView NotificationList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administration);



        dataBase = new DataBase(AdministrationActivity.this);
        db = dataBase.getReadableDatabase();
        notificationsModel = new NotificationsModel(db);
        notificationsController = new NotificationsController(notificationsModel);
        useful = new Useful();


        m_localsNotifications = new ArrayList<NotificationBean>();
        this.adapterNotfication = new IconListViewAdapter(this, R.layout.row_list, m_localsNotifications);

        m_localsNotifications.clear();
        NotificationList = (ListView) findViewById(R.id.NotificationList);
        NotificationList.setAdapter((ListAdapter) this.adapterNotfication);

        InitializeNotifications();

        onListClick(NotificationList);

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_administration, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.btnReturn:
                Return();
                return true;

            case R.id.btnRefresh:
                Refresh();

                return true;
        }
        return true;
    }

    public void Refresh(){
        AlertDialog.Builder ad= new AlertDialog.Builder(AdministrationActivity.this);
        ad.setTitle("Alerta");
        ad.setMessage("¿Esta seguro de refrescar la lista en este momento?");
        ad.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DownloadNotificationAsync downloadNotificationAsync= new DownloadNotificationAsync(AdministrationActivity.this,db);
                downloadNotificationAsync.execute();
            }
        });
        ad.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        ad.show();
    }

    public void Return() {

    }


    public class IconListViewAdapter extends ArrayAdapter<NotificationBean> {

        private ArrayList<NotificationBean> items;

        public IconListViewAdapter(Context context, int resource, ArrayList<NotificationBean> items) {
            super(context, resource, items);

            this.items = items;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.row_list, null);
            }

            NotificationBean Nb = items.get(position);
            if (Nb != null) {
                TextView ttPrincipal = (TextView) v.findViewById(R.id.lblPrincipalText);
                TextView ttSecondary = (TextView) v.findViewById(R.id.lblSecondaryText);
                TextView ttTertiary = (TextView) v.findViewById(R.id.lblTertiaryText);


                System.out.println("Notificationid " + Nb.getNotificationId());
                System.out.println("DATE " + Nb.getDate().toString());

                ttPrincipal.setText("Notification Id :" + Integer.toString(Nb.getNotificationId()));
                ttSecondary.setText("Date :" + Nb.getDate().toString());
                ttTertiary.setText("Duration :" + Integer.toString(Nb.getDuration()));

            }
            return v;
        }
    }


    private void onListClick(final ListView listNotification) {
        listNotification.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final NotificationBean nb = m_localsNotifications.get(position);

                final int NotificationId=nb.getNotificationId();
                System.out.println("bnnotification id " + NotificationId);

                AlertDialog.Builder ad = new AlertDialog.Builder(AdministrationActivity.this);
                ad.setTitle("Información");
                ad.setMessage("Presione el boton eliminar para eliminar la notificación seleccionada");
                ad.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final AlertDialog.Builder alert=new AlertDialog.Builder(AdministrationActivity.this);
                        alert.setTitle("Alerta");
                        alert.setMessage("¿Esta seguro que desea eliminar la notificación con id "+NotificationId);
                        alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DeleteNotificationAsync deleteNotificationAsync= new DeleteNotificationAsync(AdministrationActivity.this,db,NotificationId);
                                deleteNotificationAsync.execute();

                            }
                        });
                        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        alert.show();
                    }
                });
                ad.show();
            }
        });
    }

    private void InitializeNotifications() {
        m_localsNotifications.clear();
        adapterNotfication.clear();

        m_localsNotifications = new ArrayList<NotificationBean>();
        Hashtable result = notificationsController.getAllNotifications();
        for (int i = 0; i < result.size(); i++) {
            Hashtable data = (Hashtable) result.get(i);

            NotificationBean nb = new NotificationBean();
            int _id = Integer.parseInt(data.get("_id").toString());
            String date = data.get("Date").toString();
            int duration = Integer.parseInt(data.get("Duration").toString());
            int sendState = Integer.parseInt(data.get("SendState").toString());
            int notificacion_Id = Integer.parseInt(data.get("NotificationId").toString());


            nb.Local(_id, date, duration, sendState, notificacion_Id);

            m_localsNotifications.add(nb);


        }


        if (m_localsNotifications != null && m_localsNotifications.size() > 0) {
            for (int i = 0; i < m_localsNotifications.size(); i++) {
                adapterNotfication.add(m_localsNotifications.get(i));
            }
        }

        adapterNotfication.notifyDataSetChanged();


    }


}
