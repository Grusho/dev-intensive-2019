package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts: List<String?> = fullName?.split(" ") ?: listOf<String?>(null, null)
        var firstName: String? = parts.getOrNull(0)
        if (firstName is String && firstName.isEmpty())
            firstName = null
        var lastName: String? = parts.getOrNull(1)
        if (lastName is String && lastName.isEmpty())
            lastName = null
        return Pair(firstName, lastName)
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val firstName = firstName?.trim()
        val lastName = lastName?.trim()

        var firstLetterOfFirstName: Char? = null
        var firstLetterOfLastName: Char? = null

        if (firstName is String && ! firstName.isEmpty())
            firstLetterOfFirstName = firstName.first().toUpperCase()
        if (lastName is String && ! lastName.isEmpty())
            firstLetterOfLastName = lastName.first().toUpperCase()

        var initials = ""
        if (firstLetterOfFirstName != null)
            initials += firstLetterOfFirstName
        if (firstLetterOfLastName != null)
            initials += firstLetterOfLastName
        return if (initials.isEmpty()) null else initials
    }

    fun transliteration(payload: String?, divider: String = " "): String {
        if (payload == null)
            return ""
        var result = ""
        payload.forEach {
            val ch: String = when (it.toString().toLowerCase()) {
                " " -> divider
                "а" -> "a"
                "б" -> "b"
                "в" -> "v"
                "г" -> "g"
                "д" -> "d"
                "е" -> "e"
                "ё" -> "e"
                "ж" -> "zh"
                "з" -> "z"
                "и" -> "i"
                "й" -> "i"
                "к" -> "k"
                "л" -> "l"
                "м" -> "m"
                "н" -> "n"
                "о" -> "o"
                "п" -> "p"
                "р" -> "r"
                "с" -> "s"
                "т" -> "t"
                "у" -> "u"
                "ф" -> "f"
                "х" -> "h"
                "ц" -> "c"
                "ч" -> "ch"
                "ш" -> "sh"
                "щ" -> "sh'"
                "ъ" -> ""
                "ы" -> "i"
                "ь" -> ""
                "э" -> "e"
                "ю" -> "yu"
                "я" -> "ya"
                else -> it.toString()
            }
            result += if (it.isUpperCase()) ch.capitalize() else ch
        }
        return result
    }

    fun plural(count: Long, one: String, two: String, five: String): String {
        var c = count % 100
        if (c > 19)
            c %= 10

        return when (c) {
            1L -> one
            2L, 3L, 4L -> two
            else -> five
        }
    }
}