package com.omniwyse.selenium.utils;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;

/**
 * 
 * @author manohark
 * 
 */

public class GmailUtility {
	private static String LINK_START_SENTINEL = "<a style=\"padding-left:10px;\" shape=\"rect\" href=\"";
	private static String LINK_END_SENTINEL = "\"";

	/**
	 * Gets the INBOX folder
	 * 
	 * @param store
	 * @return
	 * @throws Exception
	 */
	public static Folder getInboxFolder(Store store) throws Exception {
		return getFolder("INBOX", store);
	}

	/**
	 * 
	 * @param folderName
	 * @param store
	 * @return
	 * @throws Exception
	 */
	public static Folder getFolder(String folderName, Store store) throws Exception {
		Folder folder = store.getFolder(folderName);
		folder.open(Folder.READ_WRITE);
		return folder;
	}

	/**
	 * Gets store
	 * 
	 * @param host
	 * @param emailAddress
	 * @param pass
	 * @return
	 * @throws Exception
	 */
	public static Store getEmailStore(String host, String emailAddress, String pass) throws Exception {

		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imaps");
		// Session session = Session.getDefaultInstance(props, props);
		Session session = Session.getInstance(props);
		Store store = session.getStore("imaps");
		store.connect(host, emailAddress, pass);
		return store;
	}

	/**
	 * Gets verification link from mail body
	 * 
	 * @param folder
	 * @return
	 * @throws Exception
	 */
	public static String getVerificationLink(Folder folder) throws Exception {
		Message[] messages = folder.getMessages();
		Message message = null;
		System.out.println("Total messages : " + messages.length);
		for (int i = messages.length, n = 0; i > n; i--) {
			message = messages[i - 1];
			Address address = message.getFrom()[0];
			String subject = message.getSubject();
			System.out.println("---------------------------------");
			System.out.println("Email Number " + (i));
			System.out.println("Subject: " + message.getSubject());
			System.out.println("From: " + message.getFrom()[0]);
			System.out.println("Text: " + message.getContent().toString());
			System.out.println("Mail received from : " + address.toString());
			System.out.println("subject : " + subject);
			if ((address.toString().contains("no-reply@cdta.org") && subject.equals("User Signup")) || i == 100) {
				break;
			}
		}

		String emailContent = (String) ((javax.mail.internet.MimeMultipart) message.getDataHandler().getContent()).getBodyPart(0).getContent();// buffer.toString();
		System.out.println("Email content : " + emailContent);
		int startIndex = emailContent.indexOf(LINK_START_SENTINEL) + LINK_START_SENTINEL.length();
		int endIndex = emailContent.indexOf(LINK_END_SENTINEL, startIndex);
		return emailContent.substring(startIndex, endIndex);
	}

	/**
	 * Will get
	 * 
	 * @param email
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static String getOrderHistoryEmailDetails(String email, String password) throws Exception {
		Folder folder = getInboxFolder(email, password);
		Message[] messages = folder.getMessages();
		Message message = null;
		String bodyContent = null;
		System.out.println("Total messages : " + messages.length);
		for (int i = messages.length, n = 0; i > n; i--) {
			message = messages[i - 1];
			String subject = message.getSubject();
			Address from = message.getFrom()[0];
			// no-reply@cdta.org
			if ((from.toString().contains("no-reply@cdta.org") && subject.contains("Order Receipt"))) {
				System.out.println("===================================================");
				System.out.println("Email Number " + (i));
				System.out.println("Subject: " + subject);
				System.out.println("From: " + message.getFrom()[0]);
				bodyContent = getContent(message);
				System.out.println("Mail body content : " + bodyContent);
				return bodyContent;
				// break;
			}
		}
		return null;
	}

	/**
	 * This method will get the mail body content
	 * 
	 * @param message
	 * @return
	 * @throws IOException
	 * @throws MessagingException
	 */
	public static String getContent(Message message) throws IOException, MessagingException {
		if (message.isMimeType("text/plain")) {
			return message.getContent().toString();
		} else if (message.isMimeType("multipart/*")) {
			String result = "";
			MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
			int count = mimeMultipart.getCount();
			for (int i = 0; i < count; i++) {
				BodyPart bodyPart = mimeMultipart.getBodyPart(i);
				if (bodyPart.isMimeType("text/plain")) {
					result = result + "\n" + bodyPart.getContent();
					break; // without break same text appears twice in my tests
				} else if (bodyPart.isMimeType("text/html")) {
					result = (String) ((javax.mail.internet.MimeMultipart) message.getDataHandler().getContent()).getBodyPart(0).getContent();
					break;
				} else if (bodyPart.isMimeType("multipart/alternative")) {
					Multipart mp = (Multipart) bodyPart.getContent();
					for (int j = 0; j < mp.getCount(); j++) {
						BodyPart bp = mp.getBodyPart(i);
						if (bp.isMimeType("text/plain")) {
							result = result + "\n" + bp.getContent();
							break;
						} else if (bp.isMimeType("text/html")) {
							result = result + "\n" + bp.getContent();
							break;
						} else
							result = result + "\n" + bp.getContent();
					}
				}
			}
			return result;
		}
		return "";
	}

	/**
	 * Move the messages from folder to trash folder
	 * 
	 * @param folder
	 * @param trashFolder
	 * @throws Exception
	 */
	public static void purgeMessages(Folder folder, Folder trashFolder) throws Exception {
		Message[] messages = folder.getMessages();
		if (messages == null) {
			return;
		}
		folder.copyMessages(messages, trashFolder);
	}

	/**
	 * Will get inbox folder
	 * 
	 * @param email
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static Folder getInboxFolder(String email, String password) throws Exception {

		String emailHost = "imap.gmail.com";
		System.out.println("Email:Password::" + email + ":" + password);
		Store emailStore = getEmailStore(emailHost, email, password);
		Folder testInbox = getInboxFolder(emailStore);
		return testInbox;
	}

	/**
	 * Will get the verification link from gmail inbox.
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getVerificationLinkFromGmail(String email, String password) throws Exception {
		Folder testInbox = getInboxFolder(email, password);
		String verificationLink = getVerificationLink(testInbox);
		System.out.println("Verification link from gmail : " + verificationLink);
		return verificationLink;
	}
}
