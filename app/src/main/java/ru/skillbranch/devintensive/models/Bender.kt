package ru.skillbranch.devintensive.models

import android.util.Log

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME)
{
    fun askQuestion(): String = when (question)
    {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }


    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>>
    {
        val (result, message) = question.validate(answer)

        return if (result) {
            if (question == Question.IDLE)
                "Отлично - ты справился\n" +
                        "На этом все, вопросов больше нет" to status.color
            else if (question.answers.contains(answer))
            {
                question = question.nextQuestion()
                "Отлично - ты справился\n" +
                        "${question.question}" to status.color
            }
            else
            {
                if (status == Status.CRITICAL)
                {
                    status = Status.NORMAL
                    question = Question.NAME
                    "Это неправильный ответ. Давай все по новой\n" +
                            "${question.question}" to status.color
                }
                else
                {
                    status = status.nextStatus()
                    "Это неправильный ответ\n" +
                            "${question.question}" to status.color
                }
            }
        }
        else
            "$message\n${question.question}" to status.color
    }

    enum class Status(val color: Triple<Int, Int, Int>)
    {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status = if (this.ordinal < values().lastIndex)
                values()[this.ordinal + 1]
            else
                values()[0]
    }

    enum class Question(val question: String, val answers: List<String>)
    {
        NAME("Как меня зовут?", listOf("Бендер", "Bender"))
        {
            override fun nextQuestion(): Question = PROFESSION
            override fun validate(answer: String): Pair<Boolean, String?>
            {
                return if (answer.length == 0 || answer[0].isLowerCase())
                    false to "Имя должно начинаться с заглавной буквы"
                else
                    true to null
            }

        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender"))
        {
            override fun nextQuestion(): Question = MATERIAL
            override fun validate(answer: String): Pair<Boolean, String?>
            {
                return if (answer.length == 0 || answer[0].isUpperCase())
                    false to "Профессия должна начинаться со строчной буквы"
                else
                    true to null
            }
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood"))
        {
            override fun nextQuestion(): Question = BDAY
            override fun validate(answer: String): Pair<Boolean, String?>
            {
                return if (answer.contains("""[0-9]""".toRegex()))
                    false to "Материал не должен содержать цифр"
                else
                    true to null
            }
        },
        BDAY("Когда меня создали?", listOf("2993"))
        {
            override fun nextQuestion(): Question = SERIAL
            override fun validate(answer: String): Pair<Boolean, String?>
            {
                return if (answer.contains("""[^0-9]""".toRegex()))
                    false to "Год моего рождения должен содержать только цифры"
                else
                    true to null
            }
        },
        SERIAL("Мой серийный номер?", listOf("2716057"))
        {
            override fun nextQuestion(): Question = IDLE
            override fun validate(answer: String): Pair<Boolean, String?>
            {
                return if (answer.matches("""[0-9]{7}""".toRegex()))
                    true to null
                else
                    false to "Серийный номер содержит только цифры, и их 7"
            }
        },
        IDLE("На этом все, вопросов больше нет", listOf())
        {
            override fun nextQuestion(): Question = IDLE
            override fun validate(answer: String): Pair<Boolean, String?> = true to null
        };

        abstract fun nextQuestion(): Question
        abstract fun validate(answer: String): Pair<Boolean, String?>
    }

}