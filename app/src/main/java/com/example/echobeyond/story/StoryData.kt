package com.example.echobeyond.story

data class StoryMessage(
    val character: String,
    val text: String
)

val firstSceneStart = listOf(

    StoryMessage(
        character = "Каденс",
        text = "Есть кто-нибудь? Кто вы и где находитесь?"
    ),

    StoryMessage(
        character = "Гармония",
        text = "Каденс, не торопи. Мы не знаем, кто по ту сторону. " +
                "Это может быть человек. А, может быть, совсем не человек. " +
                "Мы даже не знаем, как возникла эта связь."
    ),

    StoryMessage(
        character = "Рид",
        text = "Прекрасно. Значит, официальная версия такая: " +
                "мы отправили сообщение неизвестно куда, неизвестно кому " +
                "и теперь очень надеемся, что это не окажется смертельной ошибкой."
    ),

    StoryMessage(
        character = "Лира",
        text = "Кто бы вы ни были, ответьте на один вопрос. " +
                "Вы понимаете, что мы вам пишем?"
    )
)


val firstSceneYes = listOf(

    StoryMessage(
        character = "Вы",
        text = "Да. Кто вы?"
    ),

    StoryMessage(
        character = "Каденс",
        text = "Нас четверо. Мы студенты музыкальной академии, " +
                "или, по крайней мере, ещё недавно ими были. " +
                "Я Каденс. Со мной Гармония, Рид и Лира."
    ),

    StoryMessage(
        character = "Гармония",
        text = "Мы были в библиотеке академии, работали с архивным текстом. " +
                "Там описывалась старая музыкальная последовательность — " +
                "что-то среднее между композицией, упражнением и ритуалом."
    ),

    StoryMessage(
        character = "Каденс",
        text = "Получилось лучше, чем планировалось."
    ),

    StoryMessage(
        character = "Рид",
        text = "Или хуже."
    ),

    StoryMessage(
        character = "Гармония",
        text = "Библиотека изменилась."
    ),

    StoryMessage(
        character = "Лира",
        text = "Или изменились мы."
    )
)


val firstSceneSilent = listOf(

    StoryMessage(
        character = "Рид",
        text = "Хорошо. Я уважаю это молчание. " +
                "Возможно, именно так и выживают самые мудрые из нас."
    ),

    StoryMessage(
        character = "Гармония",
        text = "Или он просто не хочет с нами разговаривать."
    ),

    StoryMessage(
        character = "Каденс",
        text = "Если вы нас читаете, вам стоит кое-что знать. " +
                "Нас четверо: я Каденс, со мной Гармония, Рид и Лира. " +
                "Мы студенты музыкальной академии."
    ),

    StoryMessage(
        character = "Лира",
        text = "Ритуал. Или заклинание?"
    ),

    StoryMessage(
        character = "Гармония",
        text = "Теперь мы заперты. Возможно, это было ошибкой."
    ),

    StoryMessage(
        character = "Рид",
        text = "Наконец-то голос здравого смысла."
    )
)