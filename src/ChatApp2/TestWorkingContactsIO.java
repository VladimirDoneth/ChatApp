package ChatApp2;


public class TestWorkingContactsIO {

	public static void main(String[] args) throws Exception {
		WorkWithContactsFile wwf = new WorkWithContactsFile();
		try{
		wwf.addContactToFile("WhatAreFuck", "127.0.4.14");
		   try{
			   wwf.readContacts();
		   }catch(Exception ef){
			   System.out.println("fuck 1");
			   ef.printStackTrace();
		   }
		} catch(Exception e){
			e.printStackTrace();
		}
		
		
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
