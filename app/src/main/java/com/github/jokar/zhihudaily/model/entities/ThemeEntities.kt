package com.github.jokar.zhihudaily.model.entities

import com.google.gson.annotations.SerializedName

/**
 * Created by JokAr on 2017/6/15.
 */
data class ThemeEntities(@SerializedName("limit") var limit: Int,
                         @SerializedName("others") var themes: ArrayList<MainMenu>)