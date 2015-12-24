package OOPcursach;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class WorkWithNick {

	private RandomAccessFile randomAccessFile;
	private static final String errorMessage = "itMakeError";

	public WorkWithNick() throws FileNotFoundException {
		randomAccessFile = new RandomAccessFile("ThisFileSaveUserNickName.txt", "rw");
	}

	public String getNickName() throws IOException {
		StringBuffer stringBuffer = new StringBuffer();
		randomAccessFile.seek(0);
		DataInput dataInput = randomAccessFile;
		int countChar = dataInput.readInt();
		for (int i = 0; i < countChar; i++)
			stringBuffer.append(dataInput.readChar());
		return stringBuffer.toString();
	}

	public void writeInFileNickName(String nickName) throws IOException {
		randomAccessFile.seek(0);
		DataOutput dataOutput = randomAccessFile;
		dataOutput.writeInt(nickName.length());
		dataOutput.writeChars(nickName);
	}
	
	public void deleteNickNameFromFile()throws IOException{
		randomAccessFile.seek(0);
		DataOutput dataOutput = randomAccessFile;
		dataOutput.writeChars(errorMessage);
	}
}
