data class UserData(
    var step: UserStep = UserStep.CHOOSE_MAHALLA,
    var mahalla: String? = null,
    var fullName: String? = null,
    var phone: String? = null,
    var request: String? = null
)
