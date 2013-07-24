package org.feuyeux.air.ftp.client;

import org.apache.log4j.Logger;
import org.junit.Test;

public class TestFtpUtils {
	Logger log = Logger.getLogger(getClass());

	@Test
	public void getLogContent() throws Exception {
		final String server = "ftp-utf.rennes.eu.thmulti.com"; //"localhost";
		final int port = 21;
		final String user = "jenkins";//"feuyeux";
		final String pw = "PWD4j3nkins";//"eric";
		final FtpUtils ftpUtils = new FtpUtils(server, port, user, pw);
		//final String sourceFilePath = "ftp://feuyeux:eric@localhost/tgz/2013_04_28_14h53m41s_1367132021835283.tgz";
		final String sourceFilePath = "ftp://jenkins:PWD4j3nkins@ftp-utf.rennes.eu.thmulti.com/ForTest/donotDelete/2013_04_28_14h53m41s_1367132021835283.tgz";
		final String fileName = "TI_Adv_PPP_DataPPP_01.log";
		String filePathAndName = "/ForTest/donotDelete/2013_04_28_14h53m41s_1367132021835283.tgz";
		final String result = ftpUtils.getLogContent(sourceFilePath, fileName,filePathAndName);
		log.debug("tgz log content:\n" + result);
	}
}
