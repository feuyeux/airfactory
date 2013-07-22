package org.feuyeux.air.ftp.client;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

public class AirFtpClient {
	Logger	log								= Logger.getLogger(getClass());
	long	keepAliveTimeout				= -1;
	int		controlKeepAliveReplyTimeout	= -1;
	String	username						= null;
	String	password						= null;
	String	server;
	int		port							= 0;
	boolean	storeFile						= false, binaryTransfer = false;
	boolean	localActive						= false, useEpsvWithIPv4 = false;

	public AirFtpClient() {
	}

	public AirFtpClient(String server, int port, String username, String password) {
		this.server = server;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	private FTPClient initializeFtpClient(String server, int port) throws SocketException, IOException {
		FTPClient ftp = new FTPClient();
				
		if (keepAliveTimeout >= 0) {
			ftp.setControlKeepAliveTimeout(keepAliveTimeout);
		}
		if (controlKeepAliveReplyTimeout >= 0) {
			ftp.setControlKeepAliveReplyTimeout(controlKeepAliveReplyTimeout);
		}
		
		int TIMEOUTVALUE = 30000;
		ftp.setSoTimeout(TIMEOUTVALUE);
		ftp.setDataTimeout(TIMEOUTVALUE);
		
		// Use passive mode as default because most of us are behind firewalls these days.
		if (localActive) {
			ftp.enterLocalActiveMode();
		} else {
			ftp.enterLocalPassiveMode();
		}

		ftp.setUseEPSVwithIPv4(useEpsvWithIPv4);
		ftp.connect(server, port);
		return ftp;
	}

	public boolean isConnect(String server, int port) throws IOException {
		FTPClient ftp = null;
		try {
			ftp = initializeFtpClient(server, port);
			int reply = ftp.getReplyCode();
			return FTPReply.isPositiveCompletion(reply);
		} catch (SocketException e) {
			log.error(e);
			return false;
		} catch (IOException e) {
			log.error(e);
			return false;
		} finally {
			ftp.disconnect();
		}
	}

	public boolean isAuth(String server, int port, String username, String password) throws IOException {
		FTPClient ftp = null;
		try {
			ftp = initializeFtpClient(server, port);
			return ftp.login(username, password);
		} catch (SocketException e) {
			log.error(e);
			return false;
		} catch (IOException e) {
			log.error(e);
			return false;
		} finally {
			ftp.logout();
			ftp.disconnect();
		}
	}

	public void storeFile(String targetFilePath, String sourceFilePath) throws IOException {
		InputStream input = null;
		FTPClient ftp = null;
		try {
			ftp = initializeFtpClient(server, port);
			ftp.login(username, password);
			input = new FileInputStream(sourceFilePath);
			ftp.storeFile(targetFilePath, input);
		} catch (SocketException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} finally {
			input.close();
			ftp.logout();
			ftp.disconnect();
		}
	}
	
	public void retrieveFile(String sourceFilePath, String targetFilePath) throws IOException {
		OutputStream output = null;
		FTPClient ftp = null;
		try {
			ftp = initializeFtpClient(server, port);
			ftp.login(username, password);
			output = new FileOutputStream(targetFilePath);
			ftp.retrieveFile(sourceFilePath, output);
		} catch (SocketException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} finally {
			output.close();
			ftp.logout();
			ftp.disconnect();
		}
	}
}
