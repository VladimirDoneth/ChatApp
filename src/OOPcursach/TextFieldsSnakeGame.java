package OOPcursach;

public class TextFieldsSnakeGame {

	private int statusLanguage;

	// for work with SetComplexityWindow
	private String[] forSetComplexityWindowComplexity = { "��������� �� ������ �����: ",
			"The difficulty at this stage: ", "��������� �� ������ ����: " };
	private String[] forSetComplexityWindowHiMessage = { "��������� ������� ��� ��� ���������, ",
			"Set up a comfortable for you difficulty, ", "���������� ������ ��� ��� ���������, " };
	private String[] forSetComplexityWindowPlusButton = { "��������� ���������", "Increase difficulty",
			"�������� ���������" };
	private String[] forSetComplexityWindowMinusButton = { "��������� ���������", "Reduce complexity",
			"�������� ���������" };
	private String[] forSetComplexityWindowExit = { "�����", "Exit", "�����" };
	private String[] forSetComplexityWindowPlusMessage = { "���������� ��������� ��������� ����",
			"It is impossible to increase the difficulty of the game", "��������� �������� ��������� ���" };
	private String[] forSetComplexityWindowMinusMessage = { "���������� ��������� ��������� ����",
			"It is impossible to reduce the complexity of the game", "��������� �������� ��������� ���" };

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
