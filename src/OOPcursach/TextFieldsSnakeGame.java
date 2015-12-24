package OOPcursach;

public class TextFieldsSnakeGame {

	private int statusLanguage;

	// for work with SetComplexityWindow
	private String[] forSetComplexityWindowComplexity = { "Сложность на данном этапе: ",
			"The difficulty at this stage: ", "Складність на даному етапі: " };
	private String[] forSetComplexityWindowHiMessage = { "Настройте удобную для вас сложность, ",
			"Set up a comfortable for you difficulty, ", "Налаштуйте зручну для вас складність, " };
	private String[] forSetComplexityWindowPlusButton = { "Увеличить сложность", "Increase difficulty",
			"Збільшити складність" };
	private String[] forSetComplexityWindowMinusButton = { "Уменьшить сложность", "Reduce complexity",
			"Зменшити складність" };
	private String[] forSetComplexityWindowExit = { "Выход", "Exit", "Вихід" };
	private String[] forSetComplexityWindowPlusMessage = { "Невозможно увеличить сложность игры",
			"It is impossible to increase the difficulty of the game", "Неможливо збільшити складність гри" };
	private String[] forSetComplexityWindowMinusMessage = { "Невозможно уменьшить сложность игры",
			"It is impossible to reduce the complexity of the game", "Неможливо зменшити складність гри" };

	public TextFieldsSnakeGame(String language) {
		statusLanguage = -1;
		if (TextFields.RUS_LANGUAGE.equals(language))
			statusLanguage = 0;
		if (TextFields.ENG_LANGUAGE.equals(language))
			statusLanguage = 1;
		if (TextFields.UA_LANGUAGE.equals(language))
			statusLanguage = 2;
	}
	
	public String getForSetComplexityWindowComplexity(){
		return forSetComplexityWindowComplexity[statusLanguage];
	}
	
	public String getForSetComplexityWindowHiMessage(){
		return forSetComplexityWindowHiMessage[statusLanguage];
	}
	
	public String getForSetComplexityWindowPlusButton(){
		return forSetComplexityWindowPlusButton[statusLanguage];
	}
	
	public String getForSetComplexityWindowMinusButton(){
		return forSetComplexityWindowMinusButton[statusLanguage];
	}
	
	public String getForSetComplexityWindowExit(){
		return forSetComplexityWindowExit[statusLanguage];
	}
	
	public String getForSetComplexityWindowPlusMessage(){
		return forSetComplexityWindowPlusMessage[statusLanguage];
	}
	
	public String getForSetComplexityWindowMinusMessage(){
		return forSetComplexityWindowMinusMessage[statusLanguage];
	}
}
