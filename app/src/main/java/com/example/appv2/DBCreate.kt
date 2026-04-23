package com.example.appv2


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBCreate(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "MyDB.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createNotes = "CREATE TABLE notes (id INTEGER PRIMARY KEY , title varChar(30), content TEXT);"
        val createToDo = "CREATE TABLE todo (id INTEGER PRIMARY KEY , task TEXT,complete BOOLEAN);"

        db.execSQL(createNotes)
        db.execSQL(createToDo)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Simple policy: drop existing tables and start over
        db.execSQL("DROP TABLE IF EXISTS notes")
        db.execSQL("DROP TABLE IF EXISTS todo")
        onCreate(db)
    }
}