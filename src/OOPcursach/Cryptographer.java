package OOPcursach;
import javax.crypto.*;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import sun.misc.*;

/**
* Класс реализующий работу с алгоритмом шифрования DES
* @author Rumoku
*/
class Cryptographer {
Cipher ecipher;
Cipher dcipher;

/**
* Конструктор
* @param key секретный ключ алгоритма DES. Экземпляр класса SecretKey
* @throws NoSuchAlgorithmException
* @throws NoSuchPaddingException
* @throws InvalidKeyException
*/
public Cryptographer (SecretKey key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
ecipher = Cipher.getInstance("DES");
dcipher = Cipher.getInstance("DES");
ecipher.init(Cipher.ENCRYPT_MODE, key);
dcipher.init(Cipher.DECRYPT_MODE, key);
}

/**
* Функция шифровнаия
* @param str строка открытого текста
* @return зашифрованная строка в формате Base64
*/
public String encrypt(String str) throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
byte[] utf8 = str.getBytes("UTF8");
byte[] enc = ecipher.doFinal(utf8);
return sun.misc.BASE64Encoder().encode(enc);
}

/**
* Функция расшифрования
* @param str зашифрованная строка в формате Base64
* @return расшифрованная строка
*/
public String decrypt(String str) throws IOException, IllegalBlockSizeException, BadPaddingException {
byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
byte[] utf8 = dcipher.doFinal(dec);
return new String(utf8, "UTF8");
}
/**
* Функция для проверки правильности работі класса 
*/
public static void main(String[] s) throws IllegalBlockSizeException, BadPaddingException, IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException{
SecretKey key=null;
key = KeyGenerator.getInstance("DES").generateKey();
Cryptographer encrypter = new Cryptographer(key);
String OStr1="simple string";
String SStr = encrypter.encrypt(OStr1);
String OStr2 = encrypter.decrypt(SStr);
System.out.println("Open String:"+OStr1+
"\nAfter encripting: "+SStr+"\nAfter decripting: "+OStr2);
}
}
