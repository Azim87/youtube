package com.example.kotlin2.converter

import androidx.room.TypeConverter
import com.example.kotlin2.model.ItemsItem
import com.example.kotlin2.model.PageInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class PlaylistModelTypeConverter {

    val gson = Gson()

    @TypeConverter
    public fun toItemsItem(objects: List<ItemsItem>): String {
        return gson.toJson(objects)
    }

    @TypeConverter
    public fun fromItemsItem(data: String?): List<ItemsItem> {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType = object : TypeToken<List<ItemsItem>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun toPageInfo(objects: PageInfo): String = gson.toJson(objects)

    @TypeConverter
    fun fromPageInfo(value: String?): PageInfo = gson.fromJson(value, PageInfo::class.java)
}