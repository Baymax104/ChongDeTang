package com.cdtde.chongdetang.utils

import com.google.gson.*
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * 日期-字符串转换器，日期格式为yyyy-MM-dd
 */
val DateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)

/**
 * 日期-字符串转换器，日期格式为yyyyMMdd_HHmmss
 */
val DateDetailFormatter = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA)


/**
 * JSON序列化与反序列化的日期对象转换器
 */
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