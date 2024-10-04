import kotlin.system.exitProcess

fun main() {
    val charToNumber = mapOf(
        'а' to 21,
        'б' to 13,
        'в' to 4,
        'г' to 20,
        'д' to 22,
        'е' to 1,
        'ё' to 25,
        'ж' to 12,
        'з' to 24,
        'и' to 14,
        'й' to 2,
        'к' to 28,
        'л' to 9,
        'м' to 23,
        'н' to 3,
        'о' to 29,
        'п' to 6,
        'р' to 16,
        'с' to 15,
        'т' to 11,
        'у' to 26,
        'ф' to 5,
        'х' to 30,
        'ц' to 27,
        'ч' to 8,
        'ш' to 18,
        'щ' to 10,
        'ь' to 33,
        'ы' to 31,
        'ъ' to 32,
        'э' to 19,
        'ю' to 7,
        'я' to 17
    )

    val numberToChar = MutableList<Char>(34) { ' ' }
    for ((char, number) in charToNumber) {
        numberToChar[number] = char
    }

    fun readInput(prompt: String): String {
        print(prompt)
        return readLine()?.trim() ?: ""
    }

    fun getActionChoice(): String {
        while (true) {
            val choice = readInput("Выберите действие: зашифровать (1) или расшифровать (2) текст: ")
            when (choice) {
                "1", "2" -> return choice
                else -> println("Ошибка: введите 1 для шифрования или 2 для дешифрования.")
            }
        }
    }

    fun isRussianLetter(ch: Char): Boolean {
        val lowerCh = ch.lowercaseChar()
        return (lowerCh in 'а'..'я') || lowerCh == 'ё'
    }

    fun getValidText(prompt: String): String {
        while (true) {
            val text = readInput(prompt)
            if (text.isEmpty()) {
                println("Ошибка: текст не должен быть пустым.")
                continue
            }
            if (text.all { isRussianLetter(it) }) {
                return text.lowercase()
            } else {
                println("Ошибка: введите корректный текст на русском языке (только буквы).")
            }
        }
    }

    fun getValidKey(prompt: String): String {
        while (true) {
            val key = readInput(prompt)
            if (key.isEmpty()) {
                println("Ошибка: ключевое слово не должно быть пустым.")
                continue
            }
            if (key.all { isRussianLetter(it) }) {
                return key.lowercase()
            } else {
                println("Ошибка: введите корректное ключевое слово на русском языке (только буквы).")
            }
        }
    }

    fun mod(a: Int, b: Int): Int {
        return ((a % b) + b) % b
    }

    fun processText(text: String, key: String, action: String): String {
        val result = StringBuilder()
        val keyLength = key.length

        for (i in text.indices) {
            val textChar = text[i]
            val keyChar = key[i % keyLength]

            val textPos = charToNumber[textChar]
            val keyPos = charToNumber[keyChar]

            if (textPos == null || keyPos == null) {
                result.append(textChar)
                continue
            }

            val newPos = if (action == "1") {
                mod(textPos + keyPos, 33).let { if (it == 0) 33 else it }
            } else {
                mod(textPos - keyPos, 33).let { if (it == 0) 33 else it }
            }

            val newChar = numberToChar[newPos]
            result.append(newChar)
        }

        return result.toString().uppercase()
    }

    val action = getActionChoice()
    val textPrompt = if (action == "1") "Введите текст для шифрования: " else "Введите текст для дешифрования: "
    val text = getValidText(textPrompt)
    val key = getValidKey("Введите ключевое слово: ")

    val processedText = processText(text, key, action)

    if (action == "1") {
        println("Результат шифрования: $processedText")
    } else {
        println("Результат дешифрования: $processedText")
    }
}