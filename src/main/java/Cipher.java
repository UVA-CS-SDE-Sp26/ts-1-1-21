public class Cipher {


    private final String actual; // I'm storing real alphabet line here
    private final String cipher; // I'm storing cipher alphabet line here


    public Cipher(String actualline, String cipherline){
        if (actualline == null || cipherline == null){ //if the key is null
            throw new NullPointerException("Cannot create cipher with null key");
        }
        this.actual = actualline;
        this.cipher = cipherline;
        validateKey();
    }


    //checking if the key is valid
    private void validateKey(){

        if (actual.isEmpty() || cipher.isEmpty()){ // if the either key line is empty
            throw new IllegalArgumentException("Key cannot be empty");
        }

        if (actual.length() != cipher.length()){
            throw new IllegalArgumentException("Key length must match actual line length");
        }

        //I account for whitespace characters in the key lines
        for (int i = 0; i < actual.length(); i++){
            if (Character.isWhitespace(actual.charAt(i))){
                throw new IllegalArgumentException("Key cannot contain whitespace characters");
            }
        }

        for (int i = 0; i < cipher.length(); i++){
            if (Character.isWhitespace(cipher.charAt(i))){
                throw new IllegalArgumentException("Key cannot contain whitespace characters");
            }
        }

        //dups for actual
        for (int i = 0; i < actual.length(); i++){
            char character = actual.charAt(i);
            if (actual.indexOf(character) != actual.lastIndexOf(character)){
                throw new IllegalArgumentException("Key cannot contain duplicate characters");
            }
        }

        //dups for cipher
        for (int i = 0; i < cipher.length(); i++){
            char character = cipher.charAt(i);
            if (cipher.indexOf(character) != cipher.lastIndexOf(character)){
                throw new IllegalArgumentException("Key cannot contain duplicate characters");
            }
        }

        //making sure both lines contain the same characters
        for (int i = 0; i < actual.length(); i++){
            if (cipher.indexOf(actual.charAt(i)) < 0){
                throw new IllegalArgumentException("Key must contain all characters from actual line");
            }
        }

        for (int i = 0; i < cipher.length(); i++){
            if (actual.indexOf(cipher.charAt(i)) < 0){
                throw new IllegalArgumentException("Key must contain all characters from cipher line");
            }
        }


    }



    // the decipher method
    public String decipher(String input){
        if (input == null){
            throw new IllegalArgumentException("Cannot decipher null string");
        }
        String result = "";
        for (int i =0; i < input.length(); i++){
            char character = input.charAt(i);
            int index = cipher.indexOf(character);

            if (index >= 0){
                result += actual.charAt(index);
            } else{
                result += character;
            }

        }
        return result;




    }


}
