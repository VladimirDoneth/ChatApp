package ChatApp2;

import java.io.DataInput;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

public class WorkWithFile {
	private Contacts contacts;
	private RandomAccessFile file;
	private boolean isHaveComponent;
    
	public static final int LENGTH_NICK = 200, LENGTH_IP = 15;
	private static final int SIZE_CONT = 32+LENGTH_NICK*16 + 32 + LENGTH_IP*16 + 8;

	public WorkWithFile() throws FileNotFoundException {
		file = new RandomAccessFile("ContactsFile.txt", "rw");
		contacts = new Contacts();
	}

	private String getStringFromFile(int size, int LENGTH) throws IOException {
		StringBuffer sb = new StringBuffer();
		int i = 0;
		for (; i < size; i++){
			char c = file.readChar();
			System.out.println(c);
			sb.append(c);
		}
		for (; i < LENGTH; i++)
			file.readChar();

		return sb.toString();
	}

	public void readContacts() throws IOException {
		file.seek(0);
		int count;
		try {
			count = file.readInt();
		} catch (IOException e) {
			count = 0;
			file.seek(0);
			file.writeInt(0);
		}
		if (count != 0) {
			for (int i = 0; i < count; i++) {
				String IP, nick;
				count = file.readInt();
				nick = getStringFromFile(count, LENGTH_NICK);
				count = file.readInt();
				IP = getStringFromFile(count, LENGTH_IP);
				boolean isDeleted = file.readBoolean();
				if (!isDeleted)
					contacts.addContact(nick, IP);
			}
		}
	}

	private void writeInFile(String str, int LENGTH) throws IOException {
		int i = 0;
		file.writeInt(str.length());
		for(;i<str.length();i++) 
			file.writeChar(str.charAt(i));
		for(;i<LENGTH;i++)
			file.writeChar(' ');
	}

	public void addContactToFile(String nick, String IP) throws IOException {
		file.seek(0);
		int count;
		try {
			count = file.readInt();
		} catch (IOException e) {
			count = 0;
			file.seek(0);
			file.writeInt(0);
		}
		file.seek(0);
		file.writeInt(count+1);
		file.seek(32+count*SIZE_CONT);
		writeInFile(nick,LENGTH_NICK);
		writeInFile(IP,LENGTH_IP);
		file.writeBoolean(false);
	}
	
	
	//it method not created
	public boolean replacementIP(String nickName) throws IOException{
		boolean isMaked = false;
		file.seek(0);
		int count;
		try {
			count = file.readInt();
		} catch (IOException e) {
			count = 0;
			file.seek(0);
			file.writeInt(0);
		}
		if (count != 0) {
			for (int i = 0; i < count; i++) {
				String IP, nick;
				count = file.readInt();
				nick = getStringFromFile(count, LENGTH_NICK);
				count = file.readInt();
				IP = getStringFromFile(count, LENGTH_IP);
				boolean isDeleted = file.readBoolean();
				//if (!isDeleted)
					
			}
		}
		return isMaked;
	}

	public Contacts getContacts() {
		return contacts;
	}

}
