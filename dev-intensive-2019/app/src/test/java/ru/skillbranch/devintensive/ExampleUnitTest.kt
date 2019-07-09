package ru.skillbranch.devintensive

import org.junit.Test

import ru.skillbranch.devintensive.utils.Utils
import java.util.*
import ru.skillbranch.devintensive.extensions.*
import ru.skillbranch.devintensive.models.*

class ExampleUnitTest {

    @Test
    fun test_Instance()
    {
    }

    @Test
    fun test_factory() {
        //TODO: fix me!
//        val user = User.makeUser("John Cena")
//        val user2 = User.makeUser("John Wick")
        val user3 = User.makeUser("John Silverhand")
        println(user3)
    }

    @Test
    fun test_fullName()
    {
        println(Utils.parseFullName(null))
        println(Utils.parseFullName(""))
        println(Utils.parseFullName(" "))
        println(Utils.parseFullName("John"))
    }

    @Test
    fun test_date_format()
    {
        println(Date().format())
        println(Date().format("HH:mm"))
    }

    @Test
    fun test_date_add()
    {
        println(Date().add(2, TimeUnits.SECOND)) //Thu Jun 27 14:00:02 GST 2019
        println(Date().add(-4, TimeUnits.DAY)) //Thu Jun 23 14:00:00 GST 2019
    }

    @Test
    fun test_toInitials()
    {
        println("1  \"${Utils.toInitials("john", "doe")}\"") //JD
        println("2.1  \"${Utils.toInitials("John", null)}\"") //J
        println("2.2  \"${Utils.toInitials(null, "doe")}\"") //D
        println("3  \"${Utils.toInitials(null, null)}\"") //null
        println("4  \"${Utils.toInitials(" ", "")}\"") //null
    }

    @Test
    fun test_transliteration()
    {
        println(Utils.transliteration("Женя Стереотипов"))//Zhenya Stereotipov
        println(Utils.transliteration("Amazing Петр","_")) //Amazing_Petr
    }

    @Test
    fun test_humanizeDiff()
    {
        println(Date().add(-2, TimeUnits.HOUR).humanizeDiff()) //2 часа назад
        println(Date().add(-5, TimeUnits.DAY).humanizeDiff()) //5 дней назад
        println(Date().add(2, TimeUnits.MINUTE).humanizeDiff()) //через 2 минуты
        println(Date().add(7, TimeUnits.DAY).humanizeDiff()) //через 7 дней
        println(Date().add(-400, TimeUnits.DAY).humanizeDiff()) //более года назад
        println(Date().add(400, TimeUnits.DAY).humanizeDiff()) //более чем через год
    }

    @Test
    fun test_BaseMessage()
    {
        val user = User.makeUser("Василий Орехов")
        val textMessage = BaseMessage.makeMessage(
            user,
            Chat("0"),
            Date(),
            payload ="any text message",
            type = "text"
        )
        val imageMessage = BaseMessage.makeMessage(
            user,
            Chat("0"),
            Date().add(-2, TimeUnits.HOUR),
            payload = "https://anyurl.com",
            type = "image",
            isIncoming = true
        )

        println(textMessage.formatMessage()) //Василий отправил сообщение "any text message" только что
        println(imageMessage.formatMessage()) //Василий получил изображение "https://anyurl.com" 2 часа назад
    }

    @Test
    fun test_String_truncate()
    {
        println("Bender Bending Rodriguez — дословно «Сгибальщик Сгибающий Родригес»".truncate()) //Bender Bending R...
        println("Bender Bending Rodriguez — дословно «Сгибальщик Сгибающий Родригес»".truncate(15)) //Bender Bending...
        println("A     ".truncate(3)) //A
    }

    @Test
    fun test_UserBuilder()
    {
        val user = User.Builder().id("15")
            .firstName("Игорь")
            .lastName("Анацкий")
            .avatar(null)
            .rating(100)
            .respect(100)
            .lastVisit(Date())
            .isOnline(true)
            .build()

        println(user)
    }

    @Test
    fun test_stripHTML()
    {
        println("<p class=\"title\">Образовательное IT-сообщество Skill Branch</p>".stripHtml()) //Образовательное IT-сообщество Skill Branch
        println("<p>Образовательное       IT-сообщество Skill Branch</p>".stripHtml()) //Образовательное IT-сообщество Skill Branch
    }
}
