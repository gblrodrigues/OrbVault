package com.gblrod.orbvault.data.local.db.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            ALTER TABLE favorite_countries 
            ADD COLUMN official TEXT NOT NULL DEFAULT ''
        """.trimIndent())
    }
}