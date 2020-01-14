package com.example.kotlin2.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.kotlin2.converter.PlaylistModelTypeConverter
import com.google.gson.annotations.SerializedName

@Entity(tableName = "playlist_detail")
@TypeConverters(PlaylistModelTypeConverter::class)
data class DetailModel (
    @SerializedName("kind")
    var kind: String,
    @SerializedName("pageInfo")
    var pageInfo: PageInfo,
    @SerializedName("etag")
    @PrimaryKey
    var etag: String,
    @SerializedName("items")
    var items: List<ItemsItem>
)