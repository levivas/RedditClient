package com.levivas.reddit.utils

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.TimeZone

class MoshiDateAdapter : JsonAdapter<LocalDateTime>() {

    private val dateTimeFormatter = DateTimeFormatter.ofPattern(POST_TEMPLATE_TIME_FORMAT)

    @FromJson
    override fun fromJson(reader: JsonReader): LocalDateTime? {
        return try {
            val dateAsString = reader.nextString().replace(".0", "")
            synchronized(dateTimeFormatter) {
                LocalDateTime.ofInstant(Instant.ofEpochMilli(dateAsString.toLong() * 1000), TimeZone.getDefault().toZoneId())
            }
        } catch (e: Exception) {
            null
        }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: LocalDateTime?) {
        if (value != null) {
            synchronized(dateTimeFormatter) {
                    writer.value(value.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()).toString()
            }
        }
    }
}
