package com.qfwebsite.rsx.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtils {
    //邮件发送协议
    private final static String PROTOCOL = "smtp";
    //SMTP邮件服务器
    private final static String HOST = "smtp.163.com";
    //是否要求身份验证
    private final static String IS_AUTH = "true";
    // 是否启用调试模式（启用调试模式可打印客户 端与服务器交互过程时一问一答的响应消息）
    private final static String IS_ENABLED_DEBUG_MOD = "true";

    private final static String EMAIL_ACCOUNT = "zyh765487473@163.com";

    private final static String EMAIL_PASSWORD = "QLAZOKMNDTQEDOSJ";

    // 成功购买标题
    private final static String SUCCESS_BUY_TITLE = "Thank you for ordering Luminique branded products.";
    // 成功购买内容
    private static final String SUCCESS_BUY_MSG = "Your order will be shipped within 3 working days. Please pay attention to your email, and we will send you the logistics information via email.";
    // 成功购买主题
    private final static String SUCCESS_BUY_SUBJECT = "Purchase successful!";

    // 成功获取优惠卷标题
    private final static String CODE_GET_TITLE = "I hope our product can bring you surprises. Please copy the following coupon code into your order and you will receive the corresponding discount.";
    // 成功获取优惠卷内容
    private static final String CODE_GET_MSG = "This is your coupon code:";
    // 成功获取优惠卷主题
    private final static String CODE_GET_SUBJECT = "Please check your coupon";

    // 物流信息标题
    private final static String LOGISTICS_TITLE = "Thank you for choosing Luminique brand products. We have sent out your package and you will receive it soon. If you encounter any problems while using our products, you can contact us through this email and we will solve them for you as soon as possible.";
    // 物流信息内容(这里后期考虑区分不同的物流信息)
    private static final String LOGISTICS_MSG = "Logistics number:";
    // 物流信息主题
    private final static String LOGISTICS_SUBJECT = "Logistics information";

    /**
     * @param mailAddress 收件人地址
     * @throws MessagingException
     */
    public static void sendBuySuccessMail(String mailAddress) throws MessagingException {
        sendMail(mailAddress, SUCCESS_BUY_SUBJECT, SUCCESS_BUY_TITLE, SUCCESS_BUY_MSG);
    }

    /**
     * @param mailAddress 收件人地址
     * @param code 优惠卷代码
     * @throws MessagingException
     */
    public static void sendGetCodeSuccessMail(String mailAddress, String code) throws MessagingException {
        sendMail(mailAddress, CODE_GET_SUBJECT, CODE_GET_TITLE, CODE_GET_MSG + code);
    }

    /**
     * @param mailAddress 收件人地址
     * @param logisticsNum 物流信息
     * @throws MessagingException
     */
    public static void sendLogisticsMail(String mailAddress, String logisticsNum) throws MessagingException {
        sendMail(mailAddress, LOGISTICS_SUBJECT, LOGISTICS_TITLE, LOGISTICS_MSG + logisticsNum);
    }

    public static void sendMail(String mailAddress, String subject, String title, String m) throws MessagingException {
        // -- 1.创建一个Properties对象.里面 封装基本协议和数据
        Properties props = new Properties();
        // -- 设置邮件的发送协议
        props.setProperty("mail.transport.protocol", PROTOCOL);
        // -- 设置发送邮件的服务器地址
        props.setProperty("mail.smtp.host", HOST);
        // -- 设置发送邮件需要验证
        props.setProperty("mail.smtp.auth", IS_AUTH);
        props.setProperty("mail.debug", IS_ENABLED_DEBUG_MOD);
        //-- 提供验证器
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // TODO Auto-generated method stub
                return new PasswordAuthentication(EMAIL_ACCOUNT, EMAIL_PASSWORD);
            }
        };
        //-- 开启和服务器的会话
        Session session = Session.getDefaultInstance(props, auth);
        //-- 创建消息对象.一个Message对象就 是一封邮件的内容
        Message msg = new MimeMessage(session);
        //-- 设置邮件的发送者
        msg.setFrom(new InternetAddress(EMAIL_ACCOUNT));
        //-- 设置邮件的发送方式和接受者
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(mailAddress));
        //设置邮件的主题
        msg.setSubject(subject);
        //设置邮件的正文
        String content = "<h3>" + title + "</h3>" + m;
        msg.setContent(content, "text/html;charset=utf-8");
        //-- 创建Transport用于发送邮件
        Transport.send(msg);
    }

    public static void main(String[] args) throws MessagingException {
        MailUtils.sendLogisticsMail("mark991000@gmail.com ","695645842152178");
    }
}
