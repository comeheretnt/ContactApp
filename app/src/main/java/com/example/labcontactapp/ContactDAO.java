package com.example.labcontactapp;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Insert;

import java.util.List;

@Dao
public interface ContactDAO {
    @Query("SELECT * FROM Contact")
    List<Contact> getAll();

    @Query("SELECT * FROM Contact WHERE contactId IN (:contactIds)")
    List<Contact> loadAllByIds(int[] contactIds);

    @Query("SELECT * FROM Contact WHERE name LIKE :name LIMIT 1")
    Contact findByName(String name);

    @Insert
    void insertAll(Contact... contacts);
    @Query("UPDATE Contact SET name = :name, mobile = :mobile, email = :email WHERE contactId = :id")
    void updateContact(int id, String name, String mobile, String email);

    @Delete
    void delete(Contact contact);
}
