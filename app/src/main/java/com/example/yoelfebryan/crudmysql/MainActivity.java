package com.example.yoelfebryan.crudmysql;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yoelfebryan.crudmysql.api.ApiRequestBiodata;
import com.example.yoelfebryan.crudmysql.api.Retroserver;
import com.example.yoelfebryan.crudmysql.model.ResponsModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText nama, usia, domisili;
    Button save,tampil,update,delete;
    private RecyclerView.Adapter mAdapter;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nama = (EditText) findViewById(R.id.etnama);
        usia = (EditText) findViewById(R.id.etusia);
        domisili = (EditText) findViewById(R.id.etdomisili);
        save = (Button) findViewById(R.id.btnsave);
        tampil = (Button) findViewById(R.id.btntampil);
        delete = (Button) findViewById(R.id.btndelete);
        update = (Button) findViewById(R.id.btnupdate);
        pd = new ProgressDialog(this);

        Intent data = getIntent();
        final String iddata = data.getStringExtra("id");
        if (iddata != null) {
            save.setVisibility(View.GONE);
            tampil.setVisibility(View.GONE);
            update.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);
            nama.setText(data.getStringExtra("nama"));
            usia.setText(data.getStringExtra("usia"));
            domisili.setText(data.getStringExtra("domisili"));

        }

        tampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TampilData.class);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setMessage("Loading Hapus.....");
                pd.setCancelable(false);
                pd.show();

                ApiRequestBiodata api = Retroserver.getClient().create(ApiRequestBiodata.class);
                Call<ResponsModel> delete = api.deleteData(iddata);
                delete.enqueue(new Callback<ResponsModel>() {
                    @Override
                    public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                        pd.hide();
                        Log.d("Retro", "onResponse");
                        Toast.makeText(MainActivity.this, response.body().getPesan(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponsModel> call, Throwable t) {
                    }
                });
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setMessage("update data .......");
                pd.setCancelable(false);
                pd.show();

                ApiRequestBiodata api = Retroserver.getClient().create(ApiRequestBiodata.class);
                Call<ResponsModel> update = api.updateData(iddata, nama.getText().toString(), usia.getText().toString(), domisili.getText().toString());
                update.enqueue(new Callback<ResponsModel>() {
                    @Override
                    public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                        pd.hide();
                        Log.d("Retro", "Response");
                        Toast.makeText(MainActivity.this, response.body().getPesan(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponsModel> call, Throwable t) {
                    }
                });
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setMessage("send data .......");
                pd.setCancelable(false);
                pd.show();

                final String snama = nama.getText().toString();
                final String susia = usia.getText().toString();
                final String sdomisili = domisili.getText().toString();

                ApiRequestBiodata api = Retroserver.getClient().create(ApiRequestBiodata.class);

                Call<ResponsModel> sendbio = api.sendBiodata(snama, susia, sdomisili);
                sendbio.enqueue(new Callback<ResponsModel>() {
                    @Override
                    public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                        pd.hide();
                        Log.d("RETRO", "response : " + response.body().toString());
                        String kode = response.body().getKode();

                        if (kode == "1") {
                            Toast.makeText(MainActivity.this, "Data berhasil disimpan", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Data Error, tidak berhasil disimpan", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponsModel> call, Throwable t) {
                        pd.hide();
                        Log.d("RETRO", "Failure : " + "Gagal Mengirim Request");
                    }
                });
                nama.setText("");
                usia.setText("");
                domisili.setText("");
            }
        });
    }
}
