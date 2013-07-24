package org.feuyeux.air.ftp.client;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.GZIPInputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.apache.tools.tar.TarEntry;
import org.apache.tools.tar.TarInputStream;

public class FtpUtils {
	Logger log = Logger.getLogger(getClass());
	private String server;
	private int port;
	private String user;
	private String pw;
	private Integer MAX_UNZIPPED_FILE = 20;
	private Integer maxLogFile = 20;

	public FtpUtils() {
	}

	public FtpUtils(String server, int port, String user, String pw) {
		this.server = server;
		this.port = port;
		this.user = user;
		this.pw = pw;
	}

	public String getLogContent(String tgzPath, String logFile, String filePathAndName) throws Exception {
		if ((tgzPath != null) && (!tgzPath.isEmpty()) && (logFile != null) && (!logFile.isEmpty())) {

			final String targetFilePath = System.nanoTime() + ".tgz";
			FTPClient ftp = new FTPClient();

			ftp.connect(server, port);

			int reply = ftp.getReplyCode();
			log.debug("FTP Positive= " + FTPReply.isPositiveCompletion(reply));
			log.debug("FTP login= " + ftp.login(user, pw));

			int beginIndex = 0;
			int endIndex = filePathAndName.lastIndexOf("/");
			String folderName = filePathAndName.substring(beginIndex, endIndex);
			beginIndex = folderName.length() + 1;
			endIndex = filePathAndName.length();
			String fileName = filePathAndName.substring(beginIndex, endIndex);

			ftp.changeWorkingDirectory(folderName);
			log.debug("FTP set folder=" + folderName);

			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			FTPFile[] files = ftp.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (fileName.equals(files[i].getName())) {
					FileOutputStream output = new FileOutputStream(targetFilePath);
					ftp.retrieveFile(files[i].getName(), output);
					output.close();
					final GZIPInputStream gzipInputStream = new GZIPInputStream(new FileInputStream(targetFilePath));
					TarInputStream tin = new TarInputStream(gzipInputStream);
					TarEntry tarEntry = tin.getNextEntry();
					while (tarEntry != null) {
						if (!tarEntry.isDirectory()) {
							String remoteFileName = tarEntry.getName();
							if (remoteFileName.equals(logFile) || remoteFileName.equals("./" + logFile)) {
								final long logFileSize = tarEntry.getSize();
								if (logFileSize <= (maxLogFile * 1024 * 1024)) {
									ByteArrayOutputStream fout = new ByteArrayOutputStream();
									tin.copyEntryContents(fout);
									log.debug(fout.toString());
									return fout.toString();
								} else {
									throw new Exception(fileName + " is " + logFileSize + "B. It is more than " + maxLogFile + "MB. Please download it from "
											+ tgzPath);
								}
							}

						}
						tarEntry = tin.getNextEntry();
					}
				}
			}
			log.debug("FTP logout= " + ftp.logout());
			ftp.disconnect();
		} else {
			throw new Exception("The address or file name is empty.");
		}
		return "";
	}
}
