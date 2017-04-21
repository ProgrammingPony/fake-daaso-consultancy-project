/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daaso.Common;

import javax.xml.bind.DatatypeConverter;

/**
 * Generic data type converters
 * @author Omar
 */
public final class DaasoConverter {
    private DaasoConverter() {}
    
    /**
     * Source: http://stackoverflow.com/questions/8890174/in-java-how-do-i-convert-a-hex-string-to-a-byte
     * @param array
     * @return 
     */
    public static String toHexString(byte[] array) {
        return DatatypeConverter.printHexBinary(array);
    }

    /**
     * Source: http://stackoverflow.com/questions/8890174/in-java-how-do-i-convert-a-hex-string-to-a-byte
     * @param s
     * @return 
     */
    public static byte[] toByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }
}