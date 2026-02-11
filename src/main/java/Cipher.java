public class Cipher {


    private final String actual;
    private final String cipher;


    public Cipher(String actualline, String cipherline){
        this.actual = actualline;
        this.cipher = cipherline;
        validateKey();
    }


    //checking if the key is valid
    private void validateKey(){


        if(actual.length() != cipher.length()){
            throw new IllegalArgumentException("Key length must match actual line length");
        }


        if (actual == null || cipher == null){
            throw new IllegalArgumentException("Key cannot be null");
        }


        if (actual.length() == 0 || cipher.length() == 0 || cipher.length() == 0){
            throw new IllegalArgumentException("Key cannot be empty");
        }


        // checking for dups in actual
        for (int i = 0; i < actual.length(); i++){
            char c = actual.charAt(i);
            if (actual.indexOf(c) != actual.lastIndexOf(c)){
                throw new IllegalArgumentException("Key cannot contain duplicates");
            }
        }


        //checking for dups in cipher
        for (int i = 0; i < cipher.length(); i++){
            char c = cipher.charAt(i);
            if (cipher.indexOf(c) != cipher.lastIndexOf(c)){
                throw new IllegalArgumentException("Key cannot contain duplicates");
            }
        }


    }


    // the decipher method
    public String decipher(String input){
        String result = "";


        for (int i = 0; i < input.length(); i++){
            char c = input.charAt(i);


            int index = cipher.indexOf(c);
            if (index >= 0){
                result += actual.charAt(index);
            }
            else{
                result += c;
            }
        }
        return result;
    }




}
