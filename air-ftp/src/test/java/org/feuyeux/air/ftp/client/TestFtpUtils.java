package org.feuyeux.air.ftp.client;

import org.junit.Test;

public class TestFtpUtils {

	@Test
	public void getLogContent() throws Exception {
		FtpUtils ftpUtils = new FtpUtils();
		String logUrl = "2013_04_28_14h53m41s_1367132021835283.tgz";
		String fileName = "TI_Adv_PPP_DataPPP_01.log";
		ftpUtils.getLogContent(logUrl, fileName);
	}
}
