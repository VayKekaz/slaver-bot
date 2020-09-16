package com.vk.oed.slaver.util

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.time.Duration

class DurationDeserializer : JsonDeserializer<Duration> {

  override fun deserialize(
      json: JsonElement?,
      typeOfT: Type?,
      context: JsonDeserializationContext?,
  ): Duration {

    if (json == null)
      throw IllegalArgumentException("Please provide duration parameter")
    val minutes = json.asLong
    return Duration.ofMinutes(minutes)
  }
}