package org.feuyeux.air.ftp.client;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.tools.tar.TarEntry;
import org.apache.tools.tar.TarInputStream;

public class FtpUtils {
	static final int TIMEOUTVALUE = 30000;
	static final String FtpLogFileMaxUnzippedFileSize = "Ftp.LogFile.MaxUnzippedFileSize";
	static final String FtpLogFileMaxLogFile = "Ftp.LogFile.MaxLogFile";
	static final String FtpLogFileDefaultFileName = "Ftp.LogFile.DefaultFileName";
	private String server = "";
	private String port = "";
	private String username = "";
	private String password = "";

	private String filePathAndName = "";
	private FTPClient ftpClient = null;
	private InputStream is = null;

	private Integer maxUnzippedFile = 20;
	private Integer maxLogFile = 20;
	public String defaultFileName = null;

	public FtpUtils() {

	}

	/**
	 * Validate the FTP URL.
	 * 
	 * @param iBuildUrl
	 *            : URL of the build to test
	 * @return Return true if the URL is reachable, false otherwise.
	 * @throws InvalidNameException
	 *             if the name is not well-formed.
	 * @throws IOException
	 *             if the connection has some problems.
	 */
	public Boolean checkFTPAddress(String iBuildUrl) throws Exception {
		if ((iBuildUrl != null) && (!iBuildUrl.isEmpty())) {
			parseUrlString(iBuildUrl);
			connectFtpServer();
			InputStream is = null;
			try {
				is = getFtpInputStream();
				if (is == null) {
					return false;
				} else {
					return true;
				}
			} catch (final IOException ex) {

			} finally {
				try {
					if (is != null) {
						is.close();
					}
					cleanEnv();
				} catch (final IOException e) {

				}

			}
		} else {
			throw new Exception("Invalid FTP URL");
		}

		return false;
	}

	/**
	 * Validate the FTP URL.
	 * 
	 * @param iBuildUrl
	 *            : URL of the build to test
	 * @return Return true is the URL is valid, false it is correct syntax but
	 *         invalid.
	 * @throws InvalidNameException
	 *             if the name is not well-formed.
	 * @throws IOException
	 *             if the connection has some problems.
	 */

	public String getLogContent(String logUrl) throws Exception {
		return getLogContent(logUrl, defaultFileName);
	}

	/**
	 * Get the content of all.log.
	 * 
	 * @param logUrl
	 *            : URL of the all.log package
	 * @return the content of all.log
	 */
	public String getLogContent(String logUrl, String fileName) throws Exception {
		if ((logUrl != null) && (!logUrl.isEmpty()) && (fileName != null) && (!fileName.isEmpty())) {
			InputStream is = null;
			TarInputStream tin = null;
			OutputStream fout = null;
			String content = null;
			try {

				// sample file
				// ftp://10.11.59.14/testresults/coremw/stcltest/00test.tgz
				// ftp://jenkins:PWD4j3nkins@ftp-utf.rennes.eu.thmulti.com/ForTest/Don'tDelete/test2.tgz
				parseUrlString(logUrl);

				connectFtpServer();
				final long tgzFileSize = getZipFileSize();
				// only consider the tgz file whose size is less than 20MB.
				if (tgzFileSize > (maxUnzippedFile * 1024 * 1024)) {
					throw new Exception("The tgz file is " + tgzFileSize + "B. It is more than " + maxUnzippedFile + "MB. Please download it from " + logUrl);
				}

				String ftpFile = "/tmp/" + filePathAndName;
				OutputStream output = new FileOutputStream(
						"ftp://jenkins:PWD4j3nkins@ftp-utf.rennes.eu.thmulti.com/ForTest/DonotDelete/2013_04_28_14h53m41s_1367132021835283.tgz");
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				ftpClient.enterLocalPassiveMode();
				ftpClient.retrieveFile(ftpFile, output);
				output.close();

				GZIPInputStream gzipInputStream = new GZIPInputStream(new FileInputStream(ftpFile));
				tin = new TarInputStream(gzipInputStream);
				TarEntry tarEntry = tin.getNextEntry();

				while (tarEntry != null) {
					if (!tarEntry.isDirectory()) {
						if (tarEntry.getName().equals(fileName) || tarEntry.getName().equals("./" + fileName)) {
							final long logFileSize = tarEntry.getSize();
							// only consider the log file whose size is less
							// than 5MB.
							if (logFileSize <= (maxLogFile * 1024 * 1024)) {
								fout = new ByteArrayOutputStream();
								tin.copyEntryContents(fout);
								content = fout.toString();
								return content;
							} else {
								throw new Exception(fileName + " is " + logFileSize + "B. It is more than " + maxLogFile + "MB. Please download it from "
										+ logUrl);
							}
						}
					}
					tarEntry = tin.getNextEntry();
				}

			} catch (final Exception e) {
				throw e;
			} finally {
				try {
					if (fout != null) {
						tin.close();
					}
					if (tin != null) {
						tin.close();
					}
					if (is != null) {
						is.close();
					}
					cleanEnv();
				} catch (final IOException e) {

				}
			}
			return content;
		} else {
			throw new Exception("The address or file name is empty.");
		}

	}

	private void cleanEnv() throws Exception {
		if (is != null) {
			is.close();
		}
		if (ftpClient != null) {
			ftpClient.disconnect();
		}
	}

	public void connectFtpServer() throws Exception {
		ftpClient = new FTPClient();
		Pattern p = null;
		Matcher m = null;
		if (port.equals("")) {
			ftpClient.connect(server);
			ftpClient.setSoTimeout(TIMEOUTVALUE);
			ftpClient.setDataTimeout(TIMEOUTVALUE);
		} else {
			p = Pattern.compile("^[0-9]+$");
			m = p.matcher(port);
			if (m.find()) {
				ftpClient.connect(server, Integer.parseInt(port));
			} else {
				throw new Exception("Invalid FTP URL");
			}
		}

		if (!username.equals("") && !password.equals("")) {
			ftpClient.login(username, password);
		} else {
			ftpClient.login("anonymous", "anonymous");
		}
	}

	public String getFilePathAndName() {
		return filePathAndName;
	}

	public FTPClient getFtpClient() {
		return ftpClient;
	}

	private InputStream getFtpInputStream() throws Exception {
		if (ftpClient != null) {
			is = ftpClient.retrieveFileStream(filePathAndName);
		}
		return is;
	}

	public String getServer() {
		return server;
	}

	public long getZipFileSize() throws Exception {
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

	public void parseUrlString(String url) throws Exception {
		Pattern p = null;
		Matcher m = null;
		String serverAndPort = "";
		// the url string contains the username and password
		if (url.indexOf("@") != -1) {
			p = Pattern.compile("^ftp://(\\w+):(\\w+)@([^/]+)/(.+)");
			m = p.matcher(url);
			if (m.find()) {
				username = m.group(1);
				password = m.group(2);
				serverAndPort = m.group(3);
				filePathAndName = m.group(4);
			} else {
				throw new Exception("Invalid FTP URL");
			}
		} else {
			p = Pattern.compile("^ftp://([^/]+)/(.+)");
			m = p.matcher(url);
			if (m.find()) {
				serverAndPort = m.group(1);
				filePathAndName = m.group(2);
			} else {
				throw new Exception("Invalid FTP URL");
			}
		}

		if (serverAndPort.indexOf(":") != -1) {
			server = serverAndPort.substring(0, serverAndPort.indexOf(":"));
			port = serverAndPort.substring(serverAndPort.indexOf(":") + 1);
		} else {
			server = serverAndPort;
		}

		filePathAndName = "/" + filePathAndName;
		filePathAndName = URLDecoder.decode(filePathAndName, "UTF-8");
	}
}