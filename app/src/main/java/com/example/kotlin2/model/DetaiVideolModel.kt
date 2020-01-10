package com.example.kotlin2.model

import com.google.gson.annotations.SerializedName

data class DetaiVideolModel (
    @SerializedName("kind")
    var kind: String,
    @SerializedName("pageInfo")
    var pageInfo: PageInfo,
    @SerializedName("etag")
    var etag: String,
    @SerializedName("items")
    var items: List<ItemsItem>
)