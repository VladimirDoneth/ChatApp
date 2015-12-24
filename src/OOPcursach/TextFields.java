package OOPcursach;

public class TextFields {
	public static final String RUS_LANGUAGE = "rus";
	public static String ENG_LANGUAGE = "eng";
	public static final String UA_LANGUAGE = "ua";

	private int statusLanguage;

	// for work with HelloWindow
	private String[] forHelloWindowUserHi = { "            ������� ���� ���             ",
			"            Write you nick             ", "            ������ ��� ��'�             " };
	private String[] forHelloWindowLoseWrite = { "������� ���, �� ��������� �������", "Write you nick, no use gaps",
			"������ ��� ��'�, �� �������������� ��������" };
	private String[] forHelloWindowNoWrite = { "������� ���", "Write you nick", "������ ��� ��'�" };

	// for work with SecondStageWindow;
	private String[] forSecondStageWindowHiMessage1 = { "        ����������� ���, ", "        Greetings, ",
			"        ³��� ���, " };
	private String[] forSecondStageWindowHiMessage2 = { "�������� ����� ���������, ����� ������ ������",
			"Select the Program mode by pressing the appropriate button",
			"������� ����� ��������, ���������� ������� ������" };
	private String[] forSecondStageWindowGameModeButton = { "����� ����", "Game mode", "����� ����" };
	private String[] forSecondStageWindowChatAppModeButton = { "����� ChatApp", "mode ChatApp", "����� ChatApp" };
	private String[] forSecondStageWindowGameChoose = { "������ ����� ����", "select GAME mode", "o����� ����� ����" };
	private String[] forSecondStageWindowChatAppChoose = { "������ ����� ChatApp", "mode is selected ChatApp",
			"������ ����� Chat App" };

	// for work with GameModeWindow
	private String[] forGameModeWindowHiMessage = { "�������� ����� ����, ", "Select game mode, ",
			"������� ����� ���, " };
	private String[] forGameModeWindowOnlineStr = { "������ ����", "Online Games", "������ ����" };
	private String[] forGameModeWindowOflineStr = { "������ ����", "Offline games", "������ ����" };
	private String[] forGameModeWindowGameOflineSnakePlay = { "������ � ������", "Play Snake", "����� � �����" };
	private String[] forGameModeWindowGameOnlineTicTacToePlay = { "������ � ��������-������", "Play Tic Tac Toe",
			"����� � ��������-������" };
	private String[] forGameModeWindowBackButtonText = { "��������� �����", "Come back", "����������� �����" };

	public TextFields(String language) {
		statusLanguage = -1;
		if (RUS_LANGUAGE.equals(language))
			statusLanguage = 0;
		if (ENG_LANGUAGE.equals(language))
			statusLanguage = 1;
		if (UA_LANGUAGE.equals(language))
			statusLanguage = 2;
	}

	public String getForHelloWindowUserHi() {
		return forHelloWindowUserHi[statusLanguage];
	}

	public String getForHelloWindowLoseWrite() {
		return forHelloWindowLoseWrite[statusLanguage];
	}

	public String getForHelloWindowNoWrite() {
		return forHelloWindowNoWrite[statusLanguage];
	}

	public String getForSecondStageWindowHiMessage1() {
		return forSecondStageWindowHiMessage1[statusLanguage];
	}

	public String getForSecondStageWindowHiMessage2() {
		return forSecondStageWindowHiMessage2[statusLanguage];
	}

	public String getForSecondStageWindowGameModeButton() {
		return forSecondStageWindowGameModeButton[statusLanguage];
	}

	public String getForSecondStageWindowChatAppModeButton() {
		return forSecondStageWindowChatAppModeButton[statusLanguage];
	}

	public String getForSecondStageWindowGameChoose() {
		return forSecondStageWindowGameChoose[statusLanguage];
	}

	public String getForSecondStageWindowChatAppChoose() {
		return forSecondStageWindowChatAppChoose[statusLanguage];
	}
	
	public String getForGameModeWindowHiMessage(){
		return forGameModeWindowHiMessage[statusLanguage];
	}
	
	public String getForGameModeWindowOnlineStr(){
		return forGameModeWindowOnlineStr[statusLanguage];
	}
	
	public String getForGameModeWindowOflineStr(){
		return forGameModeWindowOflineStr[statusLanguage];
	}
	
	public String getForGameModeWindowGameOflineSnakePlay(){
		return forGameModeWindowGameOflineSnakePlay[statusLanguage];
	}
	
	public String getForGameModeWindowGameOnlineTicTacToePlay(){
		return forGameModeWindowGameOnlineTicTacToePlay[statusLanguage];
	}
	
	public String getForGameModeWindowBackButtonText(){
		return forGameModeWindowBackButtonText[statusLanguage];
	}
	
}
