package ru.skillbranch.devintensive.extensions

fun String.truncate(count: Int = 16): String {
    val str = this.trimEnd(' ')
     return if (str.length <= count) {
        str
    } else {
        "${str.substring(0, count).trimEnd(' ')}..."
    }
}

fun String.stripHtml(): String {
    return this
        .replace("""<[^>]*>|&\d+;|&\w+;""".toRegex(), "")
        .replace("""\s+""".toRegex(), " ")
}