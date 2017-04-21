/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daaso.Mail;

import javax.ejb.Stateless;
import javax.ejb.Remote;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.commons.mail.DefaultAuthenticator;

import java.util.Properties;


/**
 *
 * @author Omar
 */
@Stateless(name="DaasoMailer")
@Remote(DaasoMailerInterface.class)
public class DaasoMailer implements DaasoMailerInterface {

    private final int MAIL_PORT = 25;
    
    public DaasoMailer() { 
        System.out.println("Mailer Constr");
    }
    
    /**
     * Sets the necessary properties for the apache commons email and dispatches the email
     * @param email
     * @param sender
     * @throws EmailException
     */
    //@Override
    public void sendMail(SimpleEmail email, SenderType sender) throws EmailException {
        
        String authuser, authpwd;
        
        switch (sender) {
            case VERIFICATION:
                authuser = "verification@daaso-consultancy.com";
                authpwd = "oR4xJM93";
                break;
                
            case MARKETING:
                authuser = "marketing@daaso-consultancy.com";
                authpwd = "dFn4lHAp7";
                break;
                
            case ORDER:
                authuser = "order@daaso-consultancy.com";
                authpwd = "AsDX75MF";
                break;
                
            case CONFIRMATION:
                authuser = "confirmation@daaso-consultancy.com";
                authpwd = "dA8NF3Kv";
                break;
                
            case WEBMASTER:
                authuser = "webmaster@daaso-consultancy.com";
                authpwd = "Ym7V5GfZ";
                break;
                
            case SUPPORT:
                authuser = "support@daaso-consultancy.com";
                authpwd = "Ji0ZshL2SL";
                break;                
            
            case DEFAULT:
            case NO_REPLY:
            default:
                authuser = "no-reply@daaso-consultancy.com";
                authpwd = "AglTyO0Jl1";
                break;              
        }
        
        email.setSmtpPort(MAIL_PORT);
        email.setAuthenticator(new DefaultAuthenticator(authuser, authpwd));
        email.setDebug(true);
        email.setHostName("daaso-consultancy.com");
        
        Properties prop = email.getMailSession().getProperties();            
        prop.put("mail.smtps.auth", "true");
        prop.put("mail.debug", "true");
        prop.put("mail.smtps.port", Integer.toString(MAIL_PORT) );
        prop.put("mail.smtps.socketFactory.port", Integer.toString(MAIL_PORT) );
        prop.put("mail.smtps.socketFactory.class",   "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtps.socketFactory.fallback", "false");
        prop.put("mail.smtp.starttls.enable", "true");        

        email.send();
    }
}
