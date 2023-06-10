package com.nkoyo.componentidentifier.domain.extensions

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun LocalDateTime.asString(): String =
    DateTimeFormatter.ofPattern("dd MMM, yyyy h:mma", Locale.getDefault())
        .format(this)
