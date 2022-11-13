package com.pr.potd.database.utils

import androidx.room.TypeConverter

class DateTypeConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun LongToString(dateinMillis: Long): String {
            return dateinMillis.toString()
        }

        @TypeConverter
        @JvmStatic
        fun stringToLong(strDateInMillis: String): Long {
            return strDateInMillis.toLong()
        }

    }


}