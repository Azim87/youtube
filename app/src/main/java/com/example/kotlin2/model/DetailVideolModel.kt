package com.example.kotlin2.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.kotlin2.converter.PlaylistModelTypeConverter
import com.google.gson.annotations.SerializedName


@Entity(tableName = "detail_video")
@TypeConverters(PlaylistModelTypeConverter::class)
data class DetailVideolModel (
    @SerializedName("kind")
    var kind: String,
    @SerializedName("pageInfo")
    var pageInfo: PageInfo,
    @PrimaryKey
    @SerializedName("etag")
    var etag: String,
    @SerializedName("items")
    var items: List<ItemsItem>
)