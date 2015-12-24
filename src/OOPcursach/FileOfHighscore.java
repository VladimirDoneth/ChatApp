package OOPcursach;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class FileOfHighscore {
	private RandomAccessFile randomAccessFile;
	private ArrayList<String> arrayList = new ArrayList<String>();

	public static void main (String args[]){
		try {
			FileOfHighscore f = new FileOfHighscore();
			f.writeHighscoreInEnd(10);
			f.writeHighscoreInEnd(89);
			f.writeHighscoreInEnd(45);
			f.loadInArrayList();
			//for(int i = 0; i < f.getArrayList().size(); i++)
				//System.out.println(f.getArrayList().get(i));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public FileOfHighscore() throws FileNotFoundException {
		randomAccessFile = new RandomAccessFile("ThisFileSaveUserHighscore.txt", "rw");
	}

	public void loadInArrayList() throws IOException {
		randomAccessFile.seek(0);
		DataInput dataInput = randomAccessFile;
		try {
			int countH = dataInput.readInt();
			for (int i = 0; i < countH; i++) {
				int kkk = dataInput.readInt();
				System.out.println(kkk+"");
				arrayList.add(kkk + "");
			}
		} catch (IOException e) {
			DataOutput dataOutput = randomAccessFile;
			dataOutput.writeInt(0);
		}
	}

	public void writeHighscoreInEnd(int highscore) throws IOException {
		randomAccessFile.seek(0);
		DataInput dataInput = randomAccessFile;
		DataOutput dataOutput = randomAccessFile;
		int countH;
		try {
			countH = dataInput.readInt();
			randomAccessFile.seek(0);
			dataOutput.writeInt(countH+1);
			randomAccessFile.seek(countH*4+4);
			dataOutput.writeInt(highscore);
		} catch (IOException e) {
			randomAccessFile.seek(0);
			dataOutput.writeInt(1);
			dataOutput.writeInt(highscore);
		}
	}

	public ArrayList<String> getArrayList() {
		return arrayList;
	}

}
