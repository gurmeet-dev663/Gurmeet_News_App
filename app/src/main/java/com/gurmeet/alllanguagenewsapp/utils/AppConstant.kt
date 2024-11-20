package com.gurmeet.alllanguagenewsapp.utils

import com.gurmeet.alllanguagenewsapp.data.model.country.Country

object AppConstant {
const val API_KEY="ec89061f0a4f42c6bf958186f898718f"
    const val API_KEY_ALL_LANGUAGE="C2Bqk3pVqDhqY1y4PR0i3_Lh4KUApQ30-R8p48oO2DPVx453"
const val COUNTRY="us"
const val TESLA="tesla"
const val PUBLISHEDAT="publishedAt"
    const val DEBOUNCE_TIMEOUT = 200L
    const val MIN_SEARCH_CHAR = 2

    val COUNTRIES = listOf(
        Country("ar", "Argentina"),
        Country("ca", "Canada"),
        Country("de", "Germany"),
        Country("fr", "France"),
        Country("hu", "Hungary"),
        Country("in", "India"),
        Country("it", "Italy"),
        Country("mx", "Mexico"),
        Country("my", "Malaysia"),

        Country("nl", "Netherlands"),
        Country("no", "Norway"),
        Country("nz", "New Zealand"),
        Country("ph", "Philippines"),
        Country("pl", "Poland"),
        Country("pt", "Portugal"),
        Country("ro", "Romania"),

        Country("ru", "Russia"),
        Country("sa", "Saudi Arabia"),
        Country("se", "Sweden"),
        Country("sg", "Singapore"),

        Country("th", "Thailand"),
        Country("tr", "Turkey"),
        Country("tw", "Taiwan"),
        Country("ua", "Ukraine"),
        Country("us", "United States"),

        Country("za", "South Africa")
    )
    val LANGUAGES = listOf(
        Country("ar", "Arabic"),
        Country("zh", "Chinese"),
        Country("nl", "Dutch"),
        Country("en", "English"),
        Country("fr", "French"),
        Country("de", "German"),
        Country("hi", "Hindi"),
        Country("de", "Germany"),
        Country("it", "Italian"),
        Country("ja", "Japanese"),
        Country("es", "Spanish"),
        Country("ko", "Korean"),
        Country("da", "Danish"),
        Country("sv", "Swedish"),
        Country("sv", "Czech"),

    )


}