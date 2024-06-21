package com.imnexerio.eyeris

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//class BlinkDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
//    companion object {
//        private const val DATABASE_NAME = "blink_data.db"
//        private const val DATABASE_VERSION = 1
//        private const val TABLE_NAME = "blink_data"
//        private const val COLUMN_TIMESTAMP = "timestamp"
//        private const val COLUMN_LEFT_BLINK_SCORE = "left_blink_score"
//        private const val COLUMN_RIGHT_BLINK_SCORE = "right_blink_score"
//
//        private const val TABLE_CREATE =
//            "CREATE TABLE $TABLE_NAME (" +
//                    "$COLUMN_TIMESTAMP TEXT, " +
//                    "$COLUMN_LEFT_BLINK_SCORE REAL, " +
//                    "$COLUMN_RIGHT_BLINK_SCORE REAL);"
//    }
//
//    override fun onCreate(db: SQLiteDatabase) {
//        db.execSQL(TABLE_CREATE)
//    }
//
//    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
//        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
//        onCreate(db)
//    }
//}


// Ensure the BlinkDatabaseHelper is included
//class BlinkDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
//    companion object {
//        private const val DATABASE_NAME = "blink_data.db"
//        private const val DATABASE_VERSION = 1
//    }
//
//    override fun onCreate(db: SQLiteDatabase?) {
//        val createTableStatement = """
//            CREATE TABLE blink_data (
//                id INTEGER PRIMARY KEY AUTOINCREMENT,
//                timestamp LONG,
//                left_blink_score FLOAT,
//                right_blink_score FLOAT
//            )
//        """.trimIndent()
//        db?.execSQL(createTableStatement)
//    }
//
//    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//        db?.execSQL("DROP TABLE IF EXISTS blink_data")
//        onCreate(db)
//    }
//}

class BlinkDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "blink_data.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableStatement = """
            CREATE TABLE blink_data (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                timestamp LONG,
                left_blink_score FLOAT,
                right_blink_score FLOAT
            )
        """.trimIndent()
        db?.execSQL(createTableStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS blink_data")
        onCreate(db)
    }
}
