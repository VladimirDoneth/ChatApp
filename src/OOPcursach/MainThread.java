package OOPcursach;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MainThread implements Runnable {
	private Thread t;
	private String nickName;
	public static boolean isAllTimeWork;

	public MainThread(String name) {
		isAllTimeWork = true;
		t = new Thread(this, name);
		t.start();
	}

	@Override
	public void run() {
		HelloLanguageWindow helloLanguageWindow = new HelloLanguageWindow(this);
		while (HelloLanguageWindow.isMainThreadWait) {
			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException e666) {
				}
			}
		}
		String languageStatus = helloLanguageWindow.getLanguageStatus();
		System.out.println("is work after " + languageStatus);
		WorkWithNick workWithNick = null;
		try {
			workWithNick = new WorkWithNick();
		} catch (FileNotFoundException e) {
			System.out.println("it is unposible error");
		}

		if (workWithNick != null) {
			boolean isFileEmpty = false;
			nickName = "";
			try {
				nickName = workWithNick.getNickName();
			} catch (IOException e) {
				System.out.println("file is empty");
				isFileEmpty = true;

			}
			if (isFileEmpty) {
				// start HelloWindow
				MainThread mainThread = this;
				HelloWindow helloWindow = new HelloWindow(mainThread, languageStatus);
				synchronized (this) {
					while (HelloWindow.isMainThreadWait)
						try {
							this.wait();
						} catch (InterruptedException e1) {
						}
				}
				System.out.println("is work after gui menu");
				nickName = helloWindow.getNickName();
				try {
					workWithNick.writeInFileNickName(nickName);
				} catch (IOException e) {
				}
			}

			boolean isNoWork = true;
			boolean isNoWorkGameMode = true;
			boolean isNoWorkChatAppMode = true;
			GameModeWindow gameModeWindow = null;
			ChatFrame chatFrame = null;
			SecondStageWindow secondStageWindow = null;
			while (isAllTimeWork) {
				if (isNoWork) {
					isNoWork = false;
					secondStageWindow = new SecondStageWindow(nickName, this, languageStatus);
				} else
					secondStageWindow.restartThisGUI();
				synchronized (this) {
					while (SecondStageWindow.isMainThreadWait) {
						try {
							this.wait();
						} catch (InterruptedException e2) {
						}
					}
				}

				isAllTimeWork = false;
				if (SecondStageWindow.isUserChoose) {
					// game mode
					if (isNoWorkGameMode) {
						isNoWorkGameMode = false;
						gameModeWindow = new GameModeWindow(nickName, this, languageStatus);
					} else
						gameModeWindow.restartThisGUI();
					synchronized (this) {
						while (GameModeWindow.isMainThreadWait)
							try {
								this.wait();
							} catch (InterruptedException e3) {
							}
					}
				} else {
					// chatApp mode
					synchronized (this) {
						if (isNoWorkChatAppMode){
							isNoWorkChatAppMode = false;
							chatFrame = new ChatFrame(this,nickName, languageStatus);
							chatFrame.setVisible(true);
						} else chatFrame.restartThisGUI();
						while (ChatFrame.isMainThreadWait) {
							try {
								this.wait();
							} catch (InterruptedException e3) {

							}
						}
					}
				}
			}

		}
	}

	public void notifityThisThread() {
		synchronized (this) {
			this.notify();
		}
	}

	public String getNickName() {
		return nickName;
	}

}
