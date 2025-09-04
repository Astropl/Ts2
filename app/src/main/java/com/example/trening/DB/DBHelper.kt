package com.example.trening

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "exercise1.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "workout"
        private const val COL_ID = "id"
        private const val COL_TYPE = "exercise_type"
        private const val COL_REPS = "reps"
        private const val COL_SERIES = "series"
        private const val COL_TIMESTAMP = "timestamp"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_TYPE TEXT,
                $COL_REPS INTEGER,
                $COL_SERIES INTEGER,
                $COL_TIMESTAMP DATETIME DEFAULT CURRENT_TIMESTAMP
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertWorkout(exerciseName: String,currentReps: Int, reps: Int): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_TYPE, exerciseName)
            put(COL_REPS, currentReps)     // liczba powtórzeń
            put(COL_SERIES, reps) // numer serii
        }
        val result = db.insert("COL_TYPE", null, values)
        db.close()
        return result != -1L  // jeśli insert się powiódł, result będzie >= 0
    }
}
