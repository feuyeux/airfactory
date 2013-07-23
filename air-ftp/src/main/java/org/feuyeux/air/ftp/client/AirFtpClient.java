package org.feuyeux.air.ftp.client;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.zip.GZIPInputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.apache.tools.tar.TarEntry;
import org.apache.tools.tar.TarInputStream;

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
	private Integer		MAX_UNZIPPED_FILE					= 20;
	private Integer		MAX_LOG_FILE						= 20;
	
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
	
	private long getZipFileSize(String filePathAndName, FTPClient ftpClient) throws Exception {
		long tgzFileSize = 0;
		final String s = "SIZE " + filePathAndName + " \r\n";
		if (ftpClient != null) {
			ftpClient.sendCommand(s);
			final String msg = ftpClient.getReplyString();
			if (msg.startsWith("213")) {
				tgzFileSize = Long.parseLong(msg.substring(3).trim());
			}
		}
		return tgzFileSize;
	}
	
	public String tgzFileContent(String sourceFilePath, String fileName) throws Exception {
		FTPClient ftp = null;OutputStream output = null;
		try {
			ftp = initializeFtpClient(server, port);
			ftp.login(username, password);
			final long tgzFileSize = getZipFileSize(sourceFilePath,ftp);
			if (tgzFileSize > (MAX_UNZIPPED_FILE * 1024 * 1024)) {
				throw new Exception("The tgz file is " + tgzFileSize + "B. It is more than " + MAX_UNZIPPED_FILE + "MB. Please download it from " + sourceFilePath);
			}
			
			String targetFilePath = "";
			output = new FileOutputStream(targetFilePath);
			//					ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			//					ftpClient.enterLocalPassiveMode();
			ftp.retrieveFile(sourceFilePath, output);
			
			GZIPInputStream gzipInputStream = new GZIPInputStream(new FileInputStream(targetFilePath));
			TarInputStream tin  = new TarInputStream(gzipInputStream);
			TarEntry tarEntry = tin.getNextEntry();
			
			while (tarEntry != null) {
				if (!tarEntry.isDirectory()) {
					if (tarEntry.getName().equals(fileName) || tarEntry.getName().equals("./" + fileName)) {
						final long logFileSize = tarEntry.getSize();
						// only consider the log file whose size is less
						// than 5MB.
						if (logFileSize <= (MAX_LOG_FILE * 1024 * 1024)) {
							OutputStream fout = new ByteArrayOutputStream();
							tin.copyEntryContents(fout);
							return fout.toString();
					 
						} else {
							throw new Exception(fileName + " is " + logFileSize + "B. It is more than " + MAX_LOG_FILE + "MB. Please download it from "
									+ sourceFilePath);
						}
					}
				}
				tarEntry = tin.getNextEntry();
			}
			
			throw new Exception("The address or file name is empty.");
		} catch (SocketException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} finally {
			output.close();
			ftp.logout();
			ftp.disconnect();
		}
		return "????"; 
	}
}
