package features.recovery

import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class EmailService {

    private val senderEmail = "cveterinary@mail.ru"
    private val senderPassword = "a6Lw9udGkRcKU8Rx5NUy" // НЕ обычный пароль, а специальный app password

    fun sendRecoveryCode(recipientEmail: String, code: String) {
        val props = Properties()
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.starttls.enable"] = "true"
        props["mail.smtp.host"] = "smtp.gmail.com"
        props["mail.smtp.port"] = "587"

        val session = Session.getInstance(props, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(senderEmail, senderPassword)
            }
        })

        val message = MimeMessage(session)
        message.setFrom(InternetAddress(senderEmail))
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail))
        message.subject = "Код для восстановления пароля"
        message.setText("Ваш код восстановления: $code")

        Transport.send(message)
    }
}
