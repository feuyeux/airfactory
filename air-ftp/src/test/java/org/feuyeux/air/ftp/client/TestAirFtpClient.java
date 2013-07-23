package org.feuyeux.air.ftp.client;

import java.io.IOException;
import java.net.SocketException;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class TestAirFtpClient {
	Logger log = Logger.getLogger(getClass());
	private final String server = "ftp-utf.rennes.eu.thmulti.com"; //"localhost";
	private final int port = 21;
	private final String user = "jenkins";//"feuyeux";
	private final String pw = "PWD4j3nkins";//"eric";

	@Test
	public void testConnection() throws SocketException, IOException {
		final AirFtpClient client = new AirFtpClient();
		Assert.assertTrue(client.isConnect(server, port));
	}

	@Test
	public void testAuth() throws SocketException, IOException {
		final AirFtpClient client = new AirFtpClient();
		Assert.assertTrue(client.isAuth(server, port, user, pw));
	}

	@Test
	public void testStoreFile() throws IOException {
		final String sourceFilePath = "01.txt";
		final String targetFilePath = "1.txt";
		final AirFtpClient client = new AirFtpClient(server, port, user, pw);
		client.storeFile(targetFilePath, sourceFilePath);
	}

	@Test
	public void testRetrieveFile() throws IOException {
		final String sourceFilePath = "1.txt";
		final String targetFilePath = "11.txt";
		final AirFtpClient client = new AirFtpClient(server, port, user, pw);
		client.retrieveFile(sourceFilePath, targetFilePath);
	}

	@Test
	public void testTgzFileContent() throws Exception {
		final String sourceFilePath = "/ForTest/donotDelete/2013_04_28_14h53m41s_1367132021835283.tgz";
		final String fileName = "TI_Adv_PPP_DataPPP_01.log";
		final AirFtpClient client = new AirFtpClient(server, port, user, pw);
		final String result = client.tgzFileContent(sourceFilePath, fileName);
		log.debug("tgz log content:\n" + result);
	}
}