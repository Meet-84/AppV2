package com.example.appv2

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import java.util.prefs.Preferences

public class dbMethods(context: Context) {

    private val dbhelper: DBCreate = DBCreate(context.applicationContext)
    val sp: SharedPreferences = context.getSharedPreferences("app_count",Context.MODE_PRIVATE)

    public var countNote: Int = sp.getInt("cNote",0)
    public var countList: Int = sp.getInt("cList",0)

    fun addNote(title: String,content: String) {

        val db = dbhelper.writableDatabase
        val valuesN: ContentValues = ContentValues()

        valuesN.put("id", ++countNote)
        valuesN.put("title", title)
        valuesN.put("content", content)
        db.insert("notes", null, valuesN)
        sp.edit().putInt("cNote",countNote).apply()
    }



    fun getNotesData(): Cursor {
        val db: SQLiteDatabase = dbhelper.readableDatabase
        var cursor: Cursor = db.rawQuery("SELECT * FROM notes",null)
        return cursor
    }




    fun deleteNotes(id: Int){
        val db = dbhelper.writableDatabase
        db.execSQL("DELETE FROM notes WHERE id = $id")
        for (i in id..countNote)
            db.execSQL("UPDATE  notes SET id = $id-1 WHERE id = $i")
        countNote--
        sp.edit().putInt("cNote",countNote).apply()
    }

    fun updateNotes(title: String,content: String,id:Int) {
        val db = dbhelper.writableDatabase
        db.execSQL("UPDATE notes SET title = ?, content = ? WHERE id = $id",arrayOf(title,content));
    }




    fun addList(task: String) {

        val db = dbhelper.writableDatabase
        val valuesN: ContentValues = ContentValues()

        valuesN.put("id", ++countList)
        valuesN.put("task", task)
        db.insert("todo", null, valuesN)
        sp.edit().putInt("cList",countList).apply()
    }


    fun completeTask(isComplete: Boolean,id: Int){
        val db = dbhelper.writableDatabase
        db.execSQL("UPDATE todo SET complete = $isComplete WHERE id = $id")
    }



    fun getList(): Cursor {
        val db: SQLiteDatabase = dbhelper.readableDatabase
        var cursor: Cursor = db.rawQuery("SELECT * FROM todo",null)
        return cursor
    }

    fun deleteList(id: Int){
        val db = dbhelper.writableDatabase
        db.execSQL("DELETE FROM List WHERE id = $id")
        for (i in id..countList)
            db.execSQL("UPDATE todo SET id = $id-1 WHERE id = $i")
        countList--
        sp.edit().putInt("cList",countList).apply()
    }


    fun updateList(task: String,id:Int) {
        val db = dbhelper.writableDatabase
        db.execSQL("UPDATE todo SET task = ? WHERE id = $id",arrayOf(task));

    }
/*
    fun searchNotes(id: Int = 0,keyWord: String = "-1"): Cursor{
        val db: SQLiteDatabase = dbhelper.readableDatabase
        var cursor: Cursor =  db.rawQuery("SELECT * FROM notes",null)

        if (id == 0 && keyWord!= "-1") {
            cursor = db.rawQuery("SELECT * FROM notes WHERE title LIKE ?",arrayOf("%${keyWord}%"))
            cursor = db.rawQuery("SELECT * FROM notes WHERE content Like ?",arrayOf("%${keyWord}%"))
        }
        else if (id != 0 && Integer.parseInt(keyWord) != -1){
            cursor = db.rawQuery("SELECT * FROM notes WHERE id = $id",null)
        }

        return cursor

    }
*/


}
