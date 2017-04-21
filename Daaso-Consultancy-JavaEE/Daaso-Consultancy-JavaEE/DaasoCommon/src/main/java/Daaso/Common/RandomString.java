/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daaso.Common;

import java.util.Random;
import java.security.SecureRandom;

/**
 * Generates an alphanumeric secure random string
 * Copied from: http://stackoverflow.com/questions/7111651/how-to-generate-a-secure-random-alphanumeric-string-in-java-efficiently
 * @author Omar
 */
public final class RandomString {
    /* Assign a string that contains the set of characters you allow. */
    private static final String symbols = "ABCDEFGJKLMNPRSTUVWXYZ0123456789"; 

    private final Random random = new SecureRandom();

    private final char[] buf;

    public RandomString(int length)
    {
      if (length < 1)
        throw new IllegalArgumentException("length < 1: " + length);
      buf = new char[length];
    }

    public String nextString()
    {
      for (int idx = 0; idx < buf.length; ++idx) 
        buf[idx] = symbols.charAt(random.nextInt(symbols.length()));
      return new String(buf);
    }

}
