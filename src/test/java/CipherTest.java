/**
 * CipherTest.java
 * @author Ishmam Abtahi zqx9tm
 * Contains JUnit tests for the class Cipher
 */


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CipherTest {

    //Mock Constants for testing
    private static final String VALID_KEY = "abc123";
    private static final String VALID_CIPHER = "bca231";


    //Using the Mock actual and cipher values to test the constructor and Validate key

    @Test
    void constructor_validKey_doesNotThrow(){
        Cipher cipher = new Cipher(VALID_KEY,VALID_CIPHER);
    }

    @Test
    void constructor_nullActual_throwsNullPointerException(){
        assertThrows(NullPointerException.class, () -> new Cipher(null,VALID_CIPHER));
    }

    @Test
    void constructor_nullCipher_throwsNullPointerException(){
        assertThrows(NullPointerException.class, () -> new Cipher(VALID_KEY,null));
    }

    @Test
    void constructor_emptyActual_throwsIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> new Cipher("", VALID_CIPHER));
    }

    @Test
    void constructor_emptyCipher_throwsIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> new Cipher(VALID_KEY, ""));
    }

    @Test
    void constructor_lengthMismatch_throwsIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> new Cipher("abc", VALID_CIPHER));
    }

    @Test
    void constructor_whitespaceActual_throwsIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> new Cipher(" abc", "wxyz"));
    }

    @Test
    void constructor_whitespaceCipher_throwsIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> new Cipher("abcd", "w xz"));
    }

    @Test
    void constructor_duplicateActual_throwsIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> new Cipher("aabc", "wxyz"));
    }

    @Test
    void constructor_duplicateCipher_throwsIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> new Cipher("abcd", "wxxz"));
    }

    @Test
    void constructor_actualandCipherNotSameCharacterSet_throwsIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> new Cipher("abc", "abd"));
    }

    // Decipher() Tests

    @Test
    void decipher_validMapping_deciphersCorrectly(){
        Cipher cipher = new Cipher(VALID_KEY,VALID_CIPHER);
        assertEquals("cab312", cipher.decipher("abc123")); //uses the valid constants
    }

    @Test
    void decipher_leavesUnknownCharectersUnchanged(){
        Cipher cipher = new Cipher(VALID_KEY,VALID_CIPHER);
        assertEquals("c a-b!\n", cipher.decipher("a b-c!\n"));
    }

    @Test
    void decipher_nullInput_throwsNullPointerException(){
        Cipher cipher = new Cipher(VALID_KEY,VALID_CIPHER);
        assertThrows(NullPointerException.class, () -> cipher.decipher(null));
    }

    @Test
    void decipher_emptyInput_throwsIllegalArgumentException(){
        Cipher cipher = new Cipher(VALID_KEY,VALID_CIPHER);
        assertEquals("", cipher.decipher(""));
    }

}
