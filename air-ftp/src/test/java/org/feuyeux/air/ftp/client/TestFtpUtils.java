package org.feuyeux.air.ftp.client;

import org.apache.log4j.Logger;
import org.junit.Test;

public class TestFtpUtils {
	Logger log = Logger.getLogger(getClass());

	@Test
	public void getLogContent() throws Exception {
		final FtpUtils ftpUtils = new FtpUtils();
		//final String sourceFilePath = "ftp://feuyeux:eric@localhost/tgz/2013_04_28_14h53m41s_1367132021835283.tgz";
		final String sourceFilePath = "ftp://jenkins:PWD4j3nkins@ftp-utf.rennes.eu.thmulti.com/ForTest/donotDelete/2013_04_28_14h53m41s_1367132021835283.tgz";
		final String fileName = "TI_Adv_PPP_DataPPP_01.log";
		final String result = ftpUtils.getLogContent(sourceFilePath, fileName);
		log.debug("tgz log content:\n" + result);
	}
}
