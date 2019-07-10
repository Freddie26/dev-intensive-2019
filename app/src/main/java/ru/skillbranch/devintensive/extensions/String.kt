package ru.skillbranch.devintensive.extensions

fun String.truncate(len: Int = 16): String
{
    return if (this.trim().length > len)
        this.trim().dropLast(this.length - len).trim() + "..."
    else
        this.trim()
}

fun String.stripHtml(): String = this
    .replace("<[^>]*>".toRegex(), "")
    .replace("[ ]+".toRegex(), " ")

//fun String.stripHtml(): String
//{
//    val matchResult = """<.+?>(.+)</.+?>""".toRegex().matchEntire(this)
//    return if (matchResult != null)
//    {
//        matchResult.destructured.component1()
//            .replace("""&|<|>|'|\"""".toRegex(), "")
//            .replace("""[\s]{1,}""".toRegex(), " ")
//    }
//    else
//        this
//}