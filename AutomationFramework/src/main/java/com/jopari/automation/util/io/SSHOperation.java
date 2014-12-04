package com.jopari.automation.util.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jopari.automation.selenium.logging.InfoEvent;
import com.jopari.automation.selenium.logging.TestLogger;

public class SSHOperation
{
	/**
	 * This class uses JSCH for all the SSH operations. This class is used to
	 * upload file to NAS location and Event the file from that location.
	 * 
	 * @author rajnish.verma
	 */
	private static FileInputStream fileInputStream;
	private static final String FILE_PATH = ".\\src\\main\\resources\\";
	private static final String NAS_PATH = "/jopari/eb1hub/data/ACH/public/upload";
	private static final String HOST = "10.25.2.158";
	private static final String USER = "webtester";
	private static final String PASSWORD = "selenium";

	/**
	 * This method accept two parameters account number and routing number and
	 * create a new csv file, for uploading at NAS location.
	 * 
	 * @param accountNumber
	 * @param routingNumber
	 */
	public static void updateFile(String accountNumber, String routingNumber,
			String fileName)
	{
		int content;
		try
		{
			TestLogger.log(new InfoEvent(SSHOperation.class, "updating file for use"));
			File eventFile = new File(FILE_PATH + fileName);
			fileInputStream = new FileInputStream(eventFile);
			StringBuffer oldValue = new StringBuffer();
			StringBuffer newValue = new StringBuffer();
			while ((content = fileInputStream.read()) != -1)
			{
				oldValue.append((char) content);
			}
			String tokens[] = oldValue.toString().split(",");
			tokens[11] = routingNumber;
			tokens[12] = accountNumber;

			for (String token : tokens)
			{
				newValue.append(token);
				newValue.append(",");
			}
			int lastInx = newValue.toString().lastIndexOf(",");
			writeFile(eventFile, newValue, lastInx);

		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	private static void writeFile(File eventFile, StringBuffer newValue,
			int lastInx) throws IOException
	{
		TestLogger.log(new InfoEvent(SSHOperation.class, "writing file for use"));
		FileWriter fw = new FileWriter(eventFile.getPath());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(newValue.substring(0, lastInx));
		bw.close();

	}

	/**
	 * This method uses JSCH commands to upload file from local machine to a
	 * particular location of remote server.
	 */
	public static void uploadFile(String fileName)
	{
		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;
		try
		{
			TestLogger.log(new InfoEvent(SSHOperation.class, "logging in to copy file"));
			JSch jsch = new JSch();
			session = jsch.getSession(USER, HOST, 22);
			session.setPassword(PASSWORD);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.setConfig("PreferredAuthentications", 
	                  "publickey,keyboard-interactive,password");
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;
			channelSftp.cd(NAS_PATH);
			File file = new File(FILE_PATH + fileName);
			channelSftp.put(new FileInputStream(file), file.getName());

		} catch (Exception ex)
		{
			ex.printStackTrace();
		} finally
		{
			channelSftp.exit();
			channelSftp.disconnect();
			channel.disconnect();
			session.disconnect();
		}
	}

	/**
	 * This method use to event a file from remote server.
	 */
	public static void eventFile()
	{
		eventFile("PNC_ACH_SYNC_20140712000033.csv");
	}
	
	public static void eventFile(String filename)
	{
		String command1 = "sudo su - jopari";
		String command2 = "bineb1hub";
		String command3 = "./event.sh ACH " + filename + " ";
		try
		{
			TestLogger.log(new InfoEvent(SSHOperation.class, "logging in to event file"));
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			JSch jsch = new JSch();
			Session session = jsch.getSession(USER, HOST, 22);
			session.setPassword(PASSWORD);
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.setConfig("PreferredAuthentications", 
	                  "publickey,keyboard-interactive,password");
			session.connect();
			Channel channel = session.openChannel("shell");
			OutputStream ops = channel.getOutputStream();
			PrintStream ps = new PrintStream(ops, true);
			channel.connect();
			ps.println(command1);
			ps.println(command2);
			ps.println(command3);
			InputStream in = channel.getInputStream();
			channel.connect();
			byte[] tmp = new byte[1024];
			while (true)
			{
				while (in.available() > 0)
				{
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
				}
				if (channel.isClosed())
				{
					break;
				}
				try
				{
					Thread.sleep(5000);
					channel.disconnect();
					session.disconnect();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}
