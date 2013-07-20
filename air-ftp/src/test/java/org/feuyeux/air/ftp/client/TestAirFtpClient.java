package org.feuyeux.air.ftp.client;

import java.io.IOException;
import java.net.SocketException;

import org.junit.Assert;
import org.junit.Test;

public class TestAirFtpClient {
	private String	server	= "localhost";
	private int		port	= 21;
	private String	user	= "feuyeux";
	private String	pw		= "eric";

	@Test
	public void testConnection() throws SocketException, IOException {
		AirFtpClient client = new AirFtpClient();
		Assert.assertTrue(client.isConnect(server, port));
	}

	@Test
	public void testAuth() throws SocketException, IOException {
		AirFtpClient client = new AirFtpClient();
		Assert.assertTrue(client.isAuth(server, port, user, pw));
	}

	@Test
	public void testStoreFile() throws IOException {
		String sourceFilePath = "01.txt";
		String targetFilePath = "1.txt";
		AirFtpClient client = new AirFtpClient(server, port, user, pw);
		client.storeFile(targetFilePath, sourceFilePath);
	}

	@Test
	public void testRetrieveFile() throws IOException {
		String sourceFilePath = "1.txt";
		String targetFilePath = "11.txt";
		AirFtpClient client = new AirFtpClient(server, port, user, pw);
		client.retrieveFile(sourceFilePath, targetFilePath);
	}
}