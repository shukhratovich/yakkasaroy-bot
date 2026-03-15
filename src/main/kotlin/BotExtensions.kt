import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

const val ADMIN_ID = 339988173
const val ADMIN2_ID = 739293193

fun TelegramLongPollingBot.sendText(chatId: Long, text: String) {
    val msg = SendMessage(chatId.toString(), text)
    msg.replyMarkup = ReplyKeyboardRemove(true)
    execute(msg)
}

fun TelegramLongPollingBot.sendStart(chatId: Long) {
    val msg = SendMessage(chatId.toString(), "Mahallangizni tanlang 👇")
    msg.replyMarkup = mahallaKeyboard()
    execute(msg)
}

fun TelegramLongPollingBot.sendContactRequest(chatId: Long) {
    val button = KeyboardButton("📞 Telefon raqamni yuborish").apply {
        requestContact = true
    }

    val row = KeyboardRow().apply { add(button) }

    val keyboard = ReplyKeyboardMarkup().apply {
        keyboard = listOf(row)
        resizeKeyboard = true
        oneTimeKeyboard = true
    }

    val msg = SendMessage(chatId.toString(), "Telefon raqamingizni yuboring 👇")
    msg.replyMarkup = keyboard

    execute(msg)
}

fun TelegramLongPollingBot.sendToAdmin(user: UserData) {
    val text = """
📩 Yangi murojaat

📍 Mahalla: ${user.mahalla}
👤 Ism-sharif: ${user.fullName}
📞 Telefon: ${user.phone}

📝 Talab / Taklif:
${user.request}
""".trimIndent()

    val msgToAdmin1 = SendMessage(ADMIN_ID.toString(), text)
    execute(msgToAdmin1)
    val msgToAdmin2 = SendMessage(ADMIN2_ID.toString(), text)
    execute(msgToAdmin2)
}


fun againKeyboard(): ReplyKeyboardMarkup {
    val row = KeyboardRow().apply {
        add(KeyboardButton("➕ Yana murojaat yuborish"))
    }
    return ReplyKeyboardMarkup().apply {
        keyboard = listOf(row)
        resizeKeyboard = true
        oneTimeKeyboard = true
    }
}