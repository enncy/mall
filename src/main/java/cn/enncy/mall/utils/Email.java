package cn.enncy.mall.utils;


import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * //TODO
 * <br/>Created in 15:01 2021/11/7
 *
 * @author enncy
 */
public class Email {

    private static final String EMAIL_SENDER = "enncyfunnyblog@163.com";
    private static final String EMAIL_PASSWORD = "RJMVNHFAKLLPHFEP";

    public static void register(String receiver,String url) throws Exception {

        Email.sendEmail(receiver, "Mall 电商系统注册", "" +
                "<h1>欢迎注册 Mall 电商系统</h1>" +
                "<p>请点击下面的邮箱来验证您的账户，3分钟内有效请尽快验证</p>" +
                "<a href="+url+">"+url+"</a>");

    }

    public static void sendEmail(String receiver,String title,String content) throws Exception{
        Properties prop=new Properties();
        //指定一个默认的邮件服务器的主机名
        prop.setProperty("mail.host", "smtp.163.com");
        //设置smtp服务器需要进行验证
        prop.setProperty("mail.smtp.auth", "true");
        //指定一个默认的消息访问协议,Session的getTransport()返回实现此协议的Transport对象
        prop.setProperty("mail.transport.protocol", "smtp");
        //1.获取连接
        Session session=Session.getInstance(prop);
        //2.通过Session得到Transport对象,
        Transport ts=session.getTransport();

        //3.登录邮件服务器,使用你的用户名和密码
        ts.connect("smtp.163.com", EMAIL_SENDER, EMAIL_PASSWORD);

        //4.创建邮件内容Message
        MimeMessage msg=new MimeMessage(session);
        //设置发件人邮箱
        msg.setFrom(new InternetAddress("Mall电商系统<"+EMAIL_SENDER+">"));
        //指明收件人邮箱
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
        //指定邮件的标题
        msg.setSubject(title);
        //邮件的文本内容
        msg.setContent(content, "text/html;charset=UTF-8");
        //发送
        ts.sendMessage(msg, msg.getAllRecipients());
        //关闭连接
        ts.close();
    }
}
