package ChatApp2;


public class TestWorkingContactsIO {

	public static void main(String[] args) throws Exception {
		WorkWithContactsFile wwf = new WorkWithContactsFile();
		/*
		wwf.addContactToFile("vladimir", "123.12.15.66");
		wwf.addContactToFile("anna", "173.12.15.66");
		wwf.addContactToFile("sanya", "174.12.15.66");
		wwf.addContactToFile("masha", "175.12.15.66");
		wwf.addContactToFile("bob", "176.12.15.66");
		wwf.addContactToFile("vahaMaha", "177.12.15.66");
		*/
		//wwf.deleteContacts("bob");
		//wwf.deleteContacts("vahaMaha");
		//wwf.deleteContacts("masha");
		wwf.readContacts();
		Contacts con = wwf.getContacts();
		System.out.println(con.getSize()+" size");
		for (int i = 0; i<con.getSize(); i++){
			System.out.println(con.getNick(i)+" "+con.getIP(i));
		}
		
		/*
		Contacts con = new Contacts();
		con.addContact("lala", "11111111111122222");
		con.addContact("ldia", "12542");
		con.addContact("djsja", "10023");
		con.addContact("jdjfa", "847277473");
		for(int i=0; i<con.getSize(); i++)System.out.println(con.getNick(i)+" "+con.getIP(i));
		*/
	//	System.out.println();
	}

}
