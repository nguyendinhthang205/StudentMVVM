package com.ute.studentmvvm.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ute.studentmvvm.model.Student
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Student::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ute_student_room.db"
                )
                .addCallback(AppDatabaseCallback(scope))
                .build()
                INSTANCE = instance
                instance
            }
        }

        private class AppDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.studentDao())
                    }
                }
            }

            suspend fun populateDatabase(studentDao: StudentDao) {
                val sampleStudents = listOf(
                    Student(fullName = "Nguyễn Văn A", studentCode = "21110001", email = "nguyenvana@gmail.com", gpa = 3.5, major = "Công Nghệ Thông Tin"),
                    Student(fullName = "Trần Thị B", studentCode = "21110002", email = "tranthib@gmail.com", gpa = 3.8, major = "Công Nghệ Thông Tin"),
                    Student(fullName = "Lê Hoàng C", studentCode = "21110003", email = "lehoangc@gmail.com", gpa = 3.2, major = "Khoa Học Dữ Liệu"),
                    Student(fullName = "Phạm Minh D", studentCode = "21110004", email = "phamminhd@gmail.com", gpa = 3.9, major = "An Toàn Thông Tin")
                )
                studentDao.insertAll(sampleStudents)
            }
        }
    }
}
