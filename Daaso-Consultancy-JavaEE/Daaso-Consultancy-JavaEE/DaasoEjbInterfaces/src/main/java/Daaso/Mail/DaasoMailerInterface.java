/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daaso.Mail;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import Daaso.Mail.SenderType;

/**
 *
 * @author Omar
 */
public interface DaasoMailerInterface {    
    public void sendMail(SimpleEmail email, SenderType sender) throws EmailException;
}
