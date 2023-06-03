package com.nkoyo.componentidentifier.domain.extensions

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun LocalDateTime.asString() =
    DateTimeFormatter.ofPattern("dd MMM, yyyy H:mma", Locale.getDefault())
        .format(this)
