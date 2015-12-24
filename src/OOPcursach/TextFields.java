package OOPcursach;

public class TextFields {
	public static final String RUS_LANGUAGE = "rus";
	public static String ENG_LANGUAGE = "eng";
	public static final String UA_LANGUAGE = "ua";

	private int statusLanguage;

	// for work with HelloWindow
	private String[] forHelloWindowUserHi = { "            Введите свой ник             ",
			"            Write you nick             ", "            Введіть своє ім'я             " };
	private String[] forHelloWindowLoseWrite = { "Введите ник, не используя пробелы", "Write you nick, no use gaps",
			"Введіть своє ім'я, не використовуючи пропуски" };
	private String[] forHelloWindowNoWrite = { "Введите ник", "Write you nick", "Введіть своє ім'я" };

	// for work with SecondStageWindow;
	private String[] forSecondStageWindowHiMessage1 = { "        Приветствую вас, ", "        Greetings, ",
			"        Вітаю вас, " };
	private String[] forSecondStageWindowHiMessage2 = { "Выберете режим программы, нажав нужную кнопку",
			"Select the Program mode by pressing the appropriate button",
			"Виберіть режим програми, натиснувши потрібну кнопку" };
	private String[] forSecondStageWindowGameModeButton = { "режим Игры", "Game mode", "режим Ігри" };
	private String[] forSecondStageWindowChatAppModeButton = { "режим ChatApp", "mode ChatApp", "режим ChatApp" };
	private String[] forSecondStageWindowGameChoose = { "Выбран режим ИГРЫ", "select GAME mode", "oбрано режим ІГРИ" };
	private String[] forSecondStageWindowChatAppChoose = { "Выбран режим ChatApp", "mode is selected ChatApp",
			"Обрано режим Chat App" };

	// for work with GameModeWindow
	private String[] forGameModeWindowHiMessage = { "Выберете режим игры, ", "Select game mode, ",
			"Виберіть режим гри, " };
	private String[] forGameModeWindowOnlineStr = { "Онлайн игры", "Online Games", "Онлайн ігри" };
	private String[] forGameModeWindowOflineStr = { "Офлайн игры", "Offline games", "Офлайн ігри" };
	private String[] forGameModeWindowGameOflineSnakePlay = { "Играть в Змейку", "Play Snake", "Грати в Змійку" };
	private String[] forGameModeWindowGameOnlineTicTacToePlay = { "Играть в Крестики-Нолики", "Play Tic Tac Toe",
			"Грати в хрестики-нулики" };
	private String[] forGameModeWindowBackButtonText = { "Вернуться назад", "Come back", "Повернутися назад" };

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
