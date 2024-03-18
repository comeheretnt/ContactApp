package com.example.labcontactapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.example.labcontactapp.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    ArrayList<Contact> contactList;
    protected ContactAdapter contactAdapter;

    private AppDatabase appDatabase;
    ContactDAO contactDAO;
    private static MainActivity instance;

    public MainActivity(){
        instance = this;
    }
    public static MainActivity getInstance(){
        return instance;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        binding.rvContact.setLayoutManager(new LinearLayoutManager(this));
        contactList = new ArrayList<Contact>();
        contactAdapter = new ContactAdapter(contactList, MainActivity.this);
        binding.rvContact.setAdapter(contactAdapter);

        contactList.add(new Contact("Nguyen Van A", "0123","ashvdahjsf@gmail.com"));
        contactList.add(new Contact("Nguyen Van B", "0123","ashvdahjsf@gmail.com"));
//        contactAdapter.notifyDataSetChanged();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase = AppDatabase.getInstance(getApplicationContext());
                contactDAO = appDatabase.contactDao();

//                contactDAO.insertAll(new Contact("Pham Nguyen Huy Minh", "012323", "minh.pham@vnuk.edu.vn"));
//                contactDAO.insertAll(new Contact("Pham Quang Vinh", "0123233", "vinh.pham@vnuk.edu.vn"));
//                contactDAO.insertAll(new Contact("Nguyen Minh Nhan", "01232334", "nhan.nguyen@vnuk.edu.vn"));
//                contactDAO.insertAll(new Contact("Le Quoc An", "012323321", "anle.quoc@vnuk.edu.vn"));

                final ArrayList<Contact> contacts = new ArrayList<>(contactDAO.getAll());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        contacts.clear();
                        contactList.addAll(contacts);
                        contactAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
//        binding.btnClearSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                binding.tvSearch.setText("");
//                contactAdapter = new ContactAdapter(contactList, MainActivity.this);
//                binding.rvContact.setAdapter(contactAdapter);
//                contactAdapter.notifyDataSetChanged();
//            }
//        });
//        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String searchText = binding.tvSearch.getText().toString().toLowerCase();
//                ArrayList<Contact> filteredList = new ArrayList<>();
//                for(Contact contact: contactList){
//                    if(contact.getName().toLowerCase().contains(searchText) ||
//                       contact.getMobile().toLowerCase().contains(searchText) ||
//                       contact.getEmail().toLowerCase().contains(searchText)){
//                        filteredList.add(contact);
//                    }
//
//                }
//                contactAdapter = new ContactAdapter(filteredList, MainActivity.this);
//                binding.rvContact.setAdapter(contactAdapter);
//                contactAdapter.notifyDataSetChanged();
//
//            }
//        });

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailContact.class);
                startActivity(intent);
            }
        });
    }
}