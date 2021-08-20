package com.demozov.listmovie.pojo

import androidx.room.TypeConverter

class MyCustomTypeConverter {

    companion object {
        @JvmStatic
        @TypeConverter
        fun fromList(data: String?): List<Int> {
            if (data.isNullOrEmpty()) {
                return listOf(0)
            }
            return data.split(" ").map { it.toInt() }
        }

        @JvmStatic
        @TypeConverter
        fun toList(data: List<Int>?): String {
            if (data.isNullOrEmpty()) {
                return "0"
            }
            return data.joinToString ( " " )
        }
    }

}