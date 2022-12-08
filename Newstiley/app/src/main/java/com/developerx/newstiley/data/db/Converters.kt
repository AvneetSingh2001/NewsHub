package com.developerx.newstiley.data.db

import androidx.room.TypeConverter
import com.developerx.newstiley.data.model.Source

class Converters {


    @TypeConverter
    fun fromSource(source: Source): String? {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String?) : Source {
        return Source(name, name)
    }

}