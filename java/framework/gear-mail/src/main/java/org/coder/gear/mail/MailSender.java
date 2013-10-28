package org.coder.gear.mail;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * MailSender.
 * 
 * @author yoshida-n
 *
 */
public class MailSender {

    private List<String> cc = null;

    private List<String> to = null;

    private List<String> bcc = null;

    private String fromName = null;

    private String fromAddress = null;

    private String charset = "UTF-8";

    private String templateName;

    private final Map<String, Object> parameter = new HashMap<String, Object>();

    private Transporter transporter = new ManagedTransporter();
    
    private static final Log LOG = LogFactory.getLog(MailSender.class);

    /**
     * <pre>
     *    コンストラクタ .
     * </pre>
     * 
     * @param transporter
     *            to set
     */
    public MailSender changeTransporter(Transporter transporter) {
        this.transporter = transporter;
        return this;
    }

    /**
     * <pre>
     *  テンプレート名称を設定する .
     * </pre>
     * 
     * @param templateName
     *            テンプレート名称
     * @return MailSender
     */
    public MailSender withTemplateName(String templateName) {
        this.templateName = templateName;
        return this;
    }

    /**
     * <pre>
     *    CC設定 .
     * </pre>
     * 
     * @param cc
     *            CC
     * @return MailSender
     */
    public MailSender withCc(List<String> cc) {
        this.cc = cc;
        return this;
    }

    /**
     * <pre>
     *    TO設定 .
     * </pre>
     * 
     * @param to
     *            TO
     * @return MailSender
     */
    public MailSender withTo(List<String> to) {
        this.to = to;
        return this;
    }

    /**
     * <pre>
     *    BCC 設定 .
     * </pre>
     * 
     * @param bcc
     *            BCC
     * @return MailSender
     */
    public MailSender withBcc(List<String> bcc) {
        this.bcc = bcc;
        return this;
    }

    /**
     * <pre>
     *    置換文字列のキーと値を追加 .
     * </pre>
     * 
     * @param key
     *            置換文字列のキー
     * @param value
     *            置換文字列
     * @return MailSender
     */
    public MailSender addParam(String key, Object value) {
        this.parameter.put(key, value);
        return this;
    }

    /**
     * <pre>
     *    FROMに表示する名称を設定 .
     * </pre>
     * 
     * @param fromName
     *            FROMに表示する名称
     * @return MailSender
     */
    public MailSender withFromName(String fromName) {
        this.fromName = fromName;
        return this;
    }

    /**
     * <pre>
     *    FROMに表示するアドレスを設定 .
     * </pre>
     * 
     * @param fromAddress
     *            FROMに表示するアドレス
     * @return MailSender
     */
    public MailSender withFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
        return this;
    }

    /**
     * <pre>
     *    文字コードを設定 .
     * </pre>
     * 
     * @param charset
     *            文字コード
     * @return MailSender
     */
    public MailSender withCharset(String charset) {
        this.charset = charset;
        return this;
    }

    /**
     * @param session to use
     */
    public void send(Session session) {

        MimeMessage mimeMessage = new MimeMessage(session);

        try {
            // FROM 設定
            setFrom(mimeMessage);

            // 宛先設定
            setTarget(mimeMessage);

            // メッセージ設定
            setMessage(mimeMessage);

            // 送信
            doSend(mimeMessage);

        } catch (MessagingException | IOException e) {
            throw new IllegalStateException(e);
        } finally {
        	if(session.getDebug()){
        		LOG.debug(session.getDebugOut().toString());
        	}
        }
    }

    /**
     * <pre>
     *    FROMを設定する .
     * </pre>
     * 
     * @param mimeMessage
     *            メッセージ
     * @throws MessagingException
     *             例外
     * @throws UnsupportedEncodingException
     *             例外
     */
    protected void setFrom(MimeMessage mimeMessage) throws MessagingException,
            UnsupportedEncodingException {
        mimeMessage
                .setFrom(new InternetAddress(fromAddress, fromName, charset));
    }

    /**
     * <pre>
     *    TO、BCC、CCを設定する .
     * </pre>
     * 
     * @param mimeMessage
     *            メッセージ
     * @throws MessagingException
     *             例外
     */
    protected void setTarget(MimeMessage mimeMessage) throws MessagingException {

        // 送信先メールアドレスを指定
        if (to != null) {
            for (String v : to) {
                mimeMessage.addRecipient(Message.RecipientType.TO,
                        new InternetAddress(v));
            }
        }

        // ccメールアドレスを指定
        if (cc != null) {
            for (String v : cc) {
                mimeMessage.addRecipient(Message.RecipientType.CC,
                        new InternetAddress(v));
            }
        }

        // bccメールアドレスを指定
        if (bcc != null) {
            for (String v : bcc) {
                mimeMessage.addRecipient(Message.RecipientType.BCC,
                        new InternetAddress(v));
            }
        }
    }

    /**
     * <pre>
     *    本文とヘッダを設定する .
     * </pre>
     * 
     * @param mimeMessage
     *            メッセージ
     * @throws MessagingException
     *             例外
     * @throws IOException
     *             例外
     */
    protected void setMessage(MimeMessage mimeMessage)
            throws MessagingException, IOException {

        // ヘッダ設定
        mimeMessage.setHeader("Content-Transfer-Encoding",
                String.format("text/plain;charset=%s", charset));

        // 件名設定
        mimeMessage.setSubject(
                evaluate(String.format("%s.subject", templateName)), charset);

        // 本文設定
        MimeBodyPart mbp = new MimeBodyPart();
        mbp.setText(evaluate(String.format("%s.body", templateName)), charset);
        Multipart mp = new MimeMultipart();
        mp.addBodyPart(mbp);

        mimeMessage.setContent(mp);
        mimeMessage.setSentDate(new Date());
    }

    /**
     * <pre>
     *    Velocityを使用してテンプレートファイルの内容を読み取る .
     * </pre>
     * 
     * @param templatePath
     *            テンプレートパス
     * @return 置換後文字列
     * @throws IOException
     *             例外
     */
    protected String evaluate(String templatePath) throws IOException {
    	String path = templatePath;
    	String prefix = "classpath:";
    	if(templatePath.startsWith("classpath:")){
    		path = Thread.currentThread().getContextClassLoader().getResource(path.substring(prefix.length())).getFile();
    	}
        String value = FileUtils.readFileToString(new File(path), "UTF-8");
    	VelocityContext context = new VelocityContext(parameter);
		StringWriter writer = new StringWriter();
		try {
			Velocity.evaluate(context, writer, "", value);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		writer.flush();
		return writer.toString();
        
    }

    /**
     * @param mimeMessage
     * @throws MessagingException
     */
    protected void doSend(MimeMessage mimeMessage) throws MessagingException {
    	transporter.send(mimeMessage);
    }

    /**
     * <pre>
     *  ccを取得する .
     * </pre>
     * 
     * @return cc
     */
    protected List<String> getCc() {
        return cc;
    }

    /**
     * <pre>
     *  toを取得する .
     * </pre>
     * 
     * @return to
     */
    protected List<String> getTo() {
        return to;
    }

    /**
     * <pre>
     *  bccを取得する .
     * </pre>
     * 
     * @return bcc
     */
    protected List<String> getBcc() {
        return bcc;
    }

    /**
     * <pre>
     *  送信者名を取得する .
     * </pre>
     * 
     * @return 送信者名
     */
    protected String getFromName() {
        return fromName;
    }

    /**
     * <pre>
     *  送信者アドレスを取得する .
     * </pre>
     * 
     * @return fromAddress
     */
    protected String getFromAddress() {
        return fromAddress;
    }

    /**
     * <pre>
     *  charsetを取得する .
     * </pre>
     * 
     * @return charset
     */
    protected String getCharset() {
        return charset;
    }

    /**
     * <pre>
     *  templateNameを取得する .
     * </pre>
     * 
     * @return templateName
     */
    protected String getTemplateName() {
        return templateName;
    }

    /**
     * <pre>
     *  parameterを取得する .
     * </pre>
     * 
     * @return parameter
     */
    protected Map<String, Object> getParameter() {
        return parameter;
    }

    /**
     * <pre>
     *  transporterを取得する .
     * </pre>
     * 
     * @return transporter
     */
    protected Transporter getransporter() {
        return transporter;
    }

}
