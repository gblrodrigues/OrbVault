package com.gblrod.orbvault.data.countries.local.room.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS recent_countries (
                code TEXT NOT NULL,
                name TEXT NOT NULL,
                official TEXT NOT NULL,
                capital TEXT,
                region TEXT,
                flagUrl TEXT,
                visitedAt INTEGER NOT NULL,
                PRIMARY KEY(code)
            )
            """.trimIndent())
    }
}