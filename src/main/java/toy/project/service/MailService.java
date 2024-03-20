package toy.project.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private static final String senderEmail = "leekyungbin55@gmail.com";
    private static int number;

    // createNumber 메소드는 Math.random 라이브러리를 이용하여 랜덤으로 난수를 생성하게 해줌.
    public static void createNumber() {
        // (int) Math.random() * (최댓값-최소값+1) + 최소값
        number = (int) (Math.random() * (90000)) + 100000;
    }

    /* MailSender 인터페이스를 상속받은 JavaMailSender 는 Java Mail API의 MimeMessage 를 이용해서 메일을 발송하는 추가적인 기능을 수행하게 해준다. */
    public MimeMessage createMail(String mail) {
        createNumber();
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, mail);
            message.setSubject("[Mode 이메일 인증 번호입니다.]");
            String body = "";
            body += "<h1>" + "Mode 메일 인증" + "</h1>";
            body += "<h3>" + "Mode에 오신 것을 환영합니다!" + "</h3>";
            body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
            body += "<h1>" + number + "</h1>";
            body += "<h3>" + "상단의 인증 번호를 입력창에 입력해주세요. 감사합니다." + "</h3>";
            message.setText(body,"UTF-8", "html");
        } catch (MessagingException e){
            e.printStackTrace();
        }

        return message;
    }

    public int sendMail(String mail) {
        MimeMessage message = createMail(mail);
        javaMailSender.send(message);

        return number;
    }
}
