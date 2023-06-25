package com.cdtde.chongdetang.utils

import com.google.gson.*
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/12 21:46
 * @Version 1
 */

val DateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
val DateDetailFormatter = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA)


class DateFormatSerializer : JsonSerializer<Date?>, JsonDeserializer<Date?> {
    override fun serialize(
        src: Date?,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement = context.serialize(src?.let { DateFormatter.format(it) })

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Date? =
        try {
            DateFormatter.parse(json.asString)
        } catch (e: ParseException) {
            null
        }
}