package util;

import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import com.sun.mail.util.MailSSLSocketFactory;

import service.NewsServiceImp;

public class MailUtil {

	public static void sendMail(String email, String emailMsg)
			throws AddressException, MessagingException {
		// 1.创建一个程序与邮件服务器会话对象 Session

		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "SMTP");//发送邮件的协议
		props.setProperty("mail.host", "smtp.qq.com");//qq邮箱服务器地址
		props.setProperty("mail.smtp.auth", "true");// 指定验证为true

		// 创建验证器
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("523262937@qq.com", "jwxmejomyutfcadf");//客户端授权密码
			}
		};

		Session session = Session.getInstance(props, auth);
		
		//qq邮箱开启ssl认证
		MailSSLSocketFactory sf=null;
		try {
			sf = new MailSSLSocketFactory();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sf.setTrustAllHosts(true);
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.socketFactory", sf); 
		
		// 2.创建一个Message，它相当于是邮件内容
		Message message = new MimeMessage(session);

		message.setFrom(new InternetAddress("523262937@qq.com")); // 设置发送者

		message.setRecipient(RecipientType.TO, new InternetAddress("523262937@qq.com")); // 设置发送方式与接收者

		message.setSubject("测试邮件");
//		 message.setText("这是一封激活邮件，请<a href='/WEB-INF/toLife.jsp?email="+email+">点击</a>");

		message.setContent(emailMsg, "text/html;charset=utf-8");
		
		message.setSentDate(new Date());

		// 3.创建 Transport用于将邮件发送

		Transport.send(message);
	}
}
