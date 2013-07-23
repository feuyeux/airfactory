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
import org.apache.log4j.Logger;
import org.apache.tools.tar.TarEntry;
import org.apache.tools.tar.TarInputStream;

public class FtpUtils {
	Logger log = Logger.getLogger(getClass());

	static final String FtpLogFileMaxUnzippedFileSize = "Ftp.LogFile.MaxUnzippedFileSize";
	static final String FtpLogFileMaxLogFile = "Ftp.LogFile.MaxLogFile";
	static final String FtpLogFileDefaultFileName = "Ftp.LogFile.DefaultFileName";
	private String server = "";
	private String port = "";
	private String username = "";
	private String password = "";

	private String filePathAndName = "";
	private FTPClient ftpClient = null;
	private final InputStream is = null;

	private final Integer MAX_UNZIPPED_FILE = 20;
	private final Integer maxLogFile = 20;
	public String defaultFileName = null;

	public FtpUtils() {

	}

	public String getLogContent(String logUrl) throws Exception {
		return getLogContent(logUrl, defaultFileName);
	}

	private TarInputStream buildTarInputStream() throws Exception {
		OutputStream output = null;
		try {
			final String targetFilePath = System.nanoTime() + "_temp.tgz";
			output = new FileOutputStream(targetFilePath);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			ftpClient.retrieveFile(filePathAndName, output);
			final GZIPInputStream gzipInputStream = new GZIPInputStream(new FileInputStream(targetFilePath));

			return new TarInputStream(gzipInputStream);
		} catch (final Exception e) {
			log.error(e);
			return null;
		} finally {
			output.close();
		}
	}

	public String getLogContent(String logUrl, String fileName) throws Exception {
		if ((logUrl != null) && (!logUrl.isEmpty()) && (fileName != null) && (!fileName.isEmpty())) {
			final InputStream is = null;
			TarInputStream tin = null;
			OutputStream fout = null;
			try {
				parseUrlString(logUrl);
				connectFtpServer();
				final long tgzFileSize = getZipFileSize();
				// only consider the tgz file whose size is less than 20MB.
				if (tgzFileSize > (MAX_UNZIPPED_FILE * 1024 * 1024)) {
					throw new Exception("The tgz file is " + tgzFileSize + "B. It is more than " + MAX_UNZIPPED_FILE + "MB. Please download it from " + logUrl);
				}

				tin = buildTarInputStream();
				if (tin == null) {
					return null;
				}
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
								return fout.toString();
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
			return "";
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

	private void connectFtpServer() throws Exception {
		ftpClient = new FTPClient();
		Pattern p = null;
		Matcher m = null;
		if (port.equals("")) {
			ftpClient.connect(server);
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

	private long getZipFileSize() throws Exception {
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

	private void parseUrlString(String url) throws Exception {
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
