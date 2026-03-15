import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

const val ADMIN_ID = 123456789 // ⚠️ ADMIN TELEGRAM ID ni yoz

fun TelegramLongPollingBot.sendText(chatId: Long, text: String) {
    val msg = SendMessage(chatId.toString(), text)
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

    val msg = SendMessage(ADMIN_ID.toString(), text)
    execute(msg)
}
