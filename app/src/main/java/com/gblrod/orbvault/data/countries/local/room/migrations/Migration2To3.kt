package com.gblrod.orbvault.data.countries.local.room.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            ALTER TABLE favorite_countries 
            ADD COLUMN position INTEGER NOT NULL DEFAULT 0
        """.trimIndent())
    }
}