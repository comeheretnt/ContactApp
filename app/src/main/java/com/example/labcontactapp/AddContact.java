package com.example.labcontactapp;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.labcontactapp.databinding.DetailContactBinding;

public class AddContact extends AppCompatActivity {
    private ContactDAO contactDAO;
    private DetailContactBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_contact);

        binding = DetailContactBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        binding.btnDelete.setVisibility(View.INVISIBLE);

        contactDAO = MainActivity.getInstance().contactDAO;

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        String name = binding.tvName.getText().toString();
                        String phone = binding.tvPhone.getText().toString();
                        String email = binding.tvEmail.getText().toString();
                        contactDAO.insertAll(new Contact(name, phone, email));
                        MainActivity.getInstance().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MainActivity.getInstance().contactList.add(new Contact(name, phone, email));
                                MainActivity.getInstance().contactAdapter.notifyDataSetChanged();
                                finish();
                            }
                        });
                    }
                });

            }
        });
    }
}

