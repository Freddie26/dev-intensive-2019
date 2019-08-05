package ru.skillbranch.devintensive.utils

import java.util.*

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?>
    {
        if (fullName.isNullOrBlank())
            return null to null

        val parts: List<String>? = fullName.split(" ")

        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)
        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String = " "): String
    {
        var result = ""
        for (c in payload)
        {
            result += when (c)
            {
                'а' -> "a"      'А' -> "A"
                'б' -> "b"      'Б' -> "B"
                'в' -> "v"      'В' -> "V"
                'г' -> "g"      'Г' -> "G"
                'д' -> "d"      'Д' -> "D"
                'е' -> "e"      'Е' -> "E"
                'ё' -> "e"      'Ё' -> "E"
                'ж' -> "zh"     'Ж' -> "Zh"
                'з' -> "z"      'З' -> "Z"
                'и' -> "i"      'И' -> "I"
                'й' -> "i"      'Й' -> "I"
                'к' -> "k"      'К' -> "K"
                'л' -> "l"      'Л' -> "L"
                'м' -> "m"      'М' -> "M"
                'н' -> "n"      'Н' -> "N"
                'о' -> "o"      'О' -> "O"
                'п' -> "p"      'П' -> "P"
                'р' -> "r"      'Р' -> "R"
                'с' -> "s"      'С' -> "S"
                'т' -> "t"      'Т' -> "T"
                'у' -> "u"      'У' -> "U"
                'ф' -> "f"      'Ф' -> "F"
                'х' -> "h"      'Х' -> "H"
                'ц' -> "c"      'Ц' -> "C"
                'ч' -> "ch"     'Ч' -> "Ch"
                'ш' -> "sh"     'Ш' -> "Sh"
                'щ' -> "sh'"    'Щ' -> "sh'"
                'ъ' -> ""       'Ъ' -> ""
                'ы' -> "i"      'Ы' -> "I"
                'ь' -> ""       'Ь' -> ""
                'э' -> "e"      'Э' -> "E"
                'ю' -> "yu"     'Ю' -> "Yu"
                'я' -> "ya"     'Я' -> "Ya"
                ' ' -> divider
                else -> c
            }
        }
        return result
    }

    fun toInitials(firstName: String?, lastName: String?): String?
    {
        if (firstName.isNullOrBlank() && lastName.isNullOrBlank())
            return null
        val firstInitial = firstName?.take(1) ?: ""
        val secondInitial = lastName?.take(1) ?: ""
        return ("$firstInitial$secondInitial").toUpperCase()
    }

    fun validateRepository(repository: String) : Boolean{
        if (repository.isBlank())
            return true
        var s = repository
        if (s.startsWith("https://"))
            s = s.replace("https://", "")
        if (s.startsWith("www."))
            s = s.replace("www.", "")
        if (!s.startsWith("github.com/"))
            return false
        s = s.replace("github.com/", "")
        if (s.contains('/'))
            return false

        setOf(
            "enterprise",
            "features",
            "topics",
            "collections",
            "trending",
            "events",
            "marketplace",
            "pricing",
            "nonprofit",
            "customer-stories",
            "security",
            "login",
            "join").forEach {
            if (s.endsWith(it))
                return false
        }

        return s.matches("^[A-Za-z0-9]+(-[A-Za-z0-9]+)*/?$".toRegex())
    }
}