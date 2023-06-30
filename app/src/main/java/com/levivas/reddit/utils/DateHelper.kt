package com.levivas.reddit.utils

import java.time.format.DateTimeFormatter

const val POST_TEMPLATE_TIME_FORMAT = "MM.dd.yy HH:mm"

val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(POST_TEMPLATE_TIME_FORMAT)
