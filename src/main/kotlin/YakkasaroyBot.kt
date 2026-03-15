import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

class YakkasaroyBot : TelegramLongPollingBot() {

    override fun getBotUsername(): String {
        return "yakkasaroy_mahallalari_bot"
    }

    override fun getBotToken(): String = System.getenv("BOT_TOKEN")


    override fun onUpdateReceived(update: Update) {

        if (update.hasMessage() && update.message.hasText()) {
            val text = update.message.text
            val chatId = update.message.chatId

            val user = UserStorage.users.getOrPut(chatId) { UserData() }

            if (text == "➕ Yana murojaat yuborish") {
                UserStorage.users[chatId] = UserData(step = UserStep.CHOOSE_MAHALLA)
                sendStart(chatId)
                return
            }

            when {
                text == "/start" -> {
                    user.step = UserStep.CHOOSE_MAHALLA
                    sendStart(chatId)
                }

                user.step == UserStep.ASK_FULL_NAME -> {
                    user.fullName = text
                    user.step = UserStep.ASK_PHONE
                    sendContactRequest(chatId)
                }

                user.step == UserStep.ASK_REQUEST -> {
                    user.request = text
                    sendToAdmin(user)
                    user.step = UserStep.DONE

                    val msg = SendMessage(
                        chatId.toString(),
                        "✅ Murojaatingiz qabul qilindi. Rahmat!\n\nYana murojaat yubormoqchimisiz?"
                    )
                    msg.replyMarkup = againKeyboard()
                    execute(msg)
                }
            }
        }

        // CONTACT
        if (update.hasMessage() && update.message.hasContact()) {
            val chatId = update.message.chatId
            val contact = update.message.contact

            val user = UserStorage.users.getOrPut(chatId) { UserData() }

            user.phone = contact.phoneNumber
            user.step = UserStep.ASK_REQUEST

            sendText(chatId, "Talab yoki taklifingizni yozing ✍️")
        }

        // CALLBACK
        if (update.hasCallbackQuery()) {
            val chatId = update.callbackQuery.message.chatId
            val data = update.callbackQuery.data

            if (data.startsWith("MAHALLA_")) {
                val mahalla = data.removePrefix("MAHALLA_")
                val user = UserStorage.users.getOrPut(chatId) { UserData() }

                user.mahalla = mahalla
                user.step = UserStep.ASK_FULL_NAME

                sendText(chatId, "Mahalla tanlandi: $mahalla\n\nIsm-sharifingizni kiriting ✍️")
            }
        }

    }
}
