package com.example.myapplication.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myapplication.model.User
import com.example.myapplication.model.UserType

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "mentornet.db", null, 5) {

    override fun onCreate(db: SQLiteDatabase) {
        // Table mentor
        db.execSQL("""
            CREATE TABLE mentor (
                id INTEGER NOT NULL UNIQUE PRIMARY KEY,
                isim TEXT NOT NULL,
                yas INTEGER NOT NULL,
                soyisim TEXT NOT NULL,
                `e-posta` TEXT NOT NULL,
                uzmanlik TEXT NOT NULL,
                deneyim TEXT NOT NULL,
                mentorluk_sekli TEXT NOT NULL,
                musait_zaman INTEGER NOT NULL
            )
        """.trimIndent())

        // Table ogrenci
        db.execSQL("""
            CREATE TABLE ogrenci (
                id INTEGER NOT NULL UNIQUE PRIMARY KEY,
                isim TEXT NOT NULL,
                soyisim TEXT NOT NULL,
                yas INTEGER NOT NULL,
                `e-posta` TEXT NOT NULL,
                profilfotografi TEXT NOT NULL,
                egitim_bilgileri TEXT NOT NULL,
                universite TEXT,
                bolum TEXT,
                ilgi_alanlari TEXT NOT NULL,
                ogrenmek_istedikleri TEXT NOT NULL,
                hedefler TEXT NOT NULL,
                haftalik_zaman INTEGER NOT NULL,
                puan INTEGER NOT NULL
            )
        """.trimIndent())
        
        insertSampleData(db)
    }

    private fun insertSampleData(db: SQLiteDatabase) {
        // Mentors
        db.execSQL("INSERT INTO mentor (id, isim, soyisim, yas, `e-posta`, uzmanlik, deneyim, mentorluk_sekli, musait_zaman) VALUES (1, 'Ahmet', 'Yılmaz', 45, 'ahmet@esenler.bel.tr', 'Yazılım, Proje Yönetimi', '20 Yıl', 'Online', 1)")
        db.execSQL("INSERT INTO mentor (id, isim, soyisim, yas, `e-posta`, uzmanlik, deneyim, mentorluk_sekli, musait_zaman) VALUES (2, 'Ayşe', 'Demir', 38, 'ayse@esenler.bel.tr', 'Tasarım, UI/UX', '15 Yıl', 'Yüz Yüze', 2)")
        
        // Students
        db.execSQL("INSERT INTO ogrenci (id, isim, soyisim, yas, `e-posta`, profilfotografi, egitim_bilgileri, universite, bolum, ilgi_alanlari, ogrenmek_istedikleri, hedefler, haftalik_zaman, puan) VALUES (1, 'Emre', 'Can', 22, 'emre@email.com', '', 'Lisans', 'Yıldız Teknik Üniversitesi', 'Bilgisayar Mühendisliği', 'Kotlin, Java', 'Mobil Geliştirme', 'Android Developer Olmak', 10, 150)")
        db.execSQL("INSERT INTO ogrenci (id, isim, soyisim, yas, `e-posta`, profilfotografi, egitim_bilgileri, universite, bolum, ilgi_alanlari, ogrenmek_istedikleri, hedefler, haftalik_zaman, puan) VALUES (2, 'Selin', 'Öz', 20, 'selin@email.com', '', 'Lisans', 'İstanbul Teknik Üniversitesi', 'Endüstriyel Tasarım', 'Figma, Adobe XD', 'UI Design', 'Tasarımcı Olmak', 8, 200)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS mentor")
        db.execSQL("DROP TABLE IF EXISTS ogrenci")
        onCreate(db)
    }

    fun getAllMentors(): List<User> {
        val list = mutableListOf<User>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM mentor", null)
        if (cursor.moveToFirst()) {
            do {
                list.add(User(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")).toString(),
                    name = cursor.getString(cursor.getColumnIndexOrThrow("isim")),
                    surname = cursor.getString(cursor.getColumnIndexOrThrow("soyisim")),
                    email = cursor.getString(cursor.getColumnIndexOrThrow("e-posta")),
                    userType = UserType.MENTOR,
                    age = cursor.getInt(cursor.getColumnIndexOrThrow("yas")),
                    interests = cursor.getString(cursor.getColumnIndexOrThrow("uzmanlik")).split(",").map { it.trim() },
                    bio = "${cursor.getString(cursor.getColumnIndexOrThrow("deneyim"))} - ${cursor.getString(cursor.getColumnIndexOrThrow("uzmanlik"))}"
                ))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    fun getAllStudents(): List<User> {
        val list = mutableListOf<User>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ogrenci", null)
        if (cursor.moveToFirst()) {
            do {
                list.add(User(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")).toString(),
                    name = cursor.getString(cursor.getColumnIndexOrThrow("isim")),
                    surname = cursor.getString(cursor.getColumnIndexOrThrow("soyisim")),
                    email = cursor.getString(cursor.getColumnIndexOrThrow("e-posta")),
                    userType = UserType.YOUTH,
                    age = cursor.getInt(cursor.getColumnIndexOrThrow("yas")),
                    points = cursor.getInt(cursor.getColumnIndexOrThrow("puan")),
                    interests = cursor.getString(cursor.getColumnIndexOrThrow("ilgi_alanlari")).split(",").map { it.trim() },
                    education = cursor.getString(cursor.getColumnIndexOrThrow("egitim_bilgileri")),
                    university = cursor.getString(cursor.getColumnIndexOrThrow("universite")) ?: "",
                    department = cursor.getString(cursor.getColumnIndexOrThrow("bolum")) ?: "",
                    learningGoals = cursor.getString(cursor.getColumnIndexOrThrow("ogrenmek_istedikleri")),
                    careerGoals = cursor.getString(cursor.getColumnIndexOrThrow("hedefler")),
                    weeklyAvailability = cursor.getInt(cursor.getColumnIndexOrThrow("haftalik_zaman")),
                    bio = cursor.getString(cursor.getColumnIndexOrThrow("egitim_bilgileri")) + " - " + cursor.getString(cursor.getColumnIndexOrThrow("ilgi_alanlari"))
                ))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }
}
