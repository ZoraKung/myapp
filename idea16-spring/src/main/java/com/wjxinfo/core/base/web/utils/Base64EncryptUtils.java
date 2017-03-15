package com.wjxinfo.core.base.web.utils;

public class Base64EncryptUtils {
    public static int findNumberOfDigitsRequired(String s) {
        int numberOfBitsRequired = 24 - ((8 * s.length()) % 24);
        int numberOfDigitsRequired = (((8 * s.length()) + numberOfBitsRequired) / 8) - s.length();
        return numberOfDigitsRequired;
    }

    public static String padNZero(int n, String s) {
        String result = "";
        for (int i = 0; i < n; i++) {
            result += "0";
        }
        return result + s;
    }

    public static byte[] base64Decode(String input) {
        final String codes = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
        if (input.length() % 4 != 0) {
            throw new IllegalArgumentException("Invalid base64 input");
        }
        byte decoded[] = new byte[((input.length() * 3) / 4) - (input.indexOf('=') > 0 ? (input.length() - input.indexOf('=')) : 0)];
        char[] inChars = input.toCharArray();
        int j = 0;
        int b[] = new int[4];
        for (int i = 0; i < inChars.length; i += 4) {
            // This could be made faster (but more complicated) by precomputing these index locations
            b[0] = codes.indexOf(inChars[i]);
            b[1] = codes.indexOf(inChars[i + 1]);
            b[2] = codes.indexOf(inChars[i + 2]);
            b[3] = codes.indexOf(inChars[i + 3]);
            decoded[j++] = (byte) ((b[0] << 2) | (b[1] >> 4));
            if (b[2] < 64) {
                decoded[j++] = (byte) ((b[1] << 4) | (b[2] >> 2));
                if (b[3] < 64) {
                    decoded[j++] = (byte) ((b[2] << 6) | b[3]);
                }
            }
        }

        return decoded;
    }

    public static String base64Encode(byte[] in) {
        final String codes = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
        StringBuilder out = new StringBuilder((in.length * 4) / 3);
        int b;
        for (int i = 0; i < in.length; i += 3) {
            b = (in[i] & 0xFC) >> 2;
            out.append(codes.charAt(b));
            b = (in[i] & 0x03) << 4;
            if (i + 1 < in.length) {
                b |= (in[i + 1] & 0xF0) >> 4;
                out.append(codes.charAt(b));
                b = (in[i + 1] & 0x0F) << 2;
                if (i + 2 < in.length) {
                    b |= (in[i + 2] & 0xC0) >> 6;
                    out.append(codes.charAt(b));
                    b = in[i + 2] & 0x3F;
                    out.append(codes.charAt(b));
                } else {
                    out.append(codes.charAt(b));
                    out.append('=');
                }
            } else {
                out.append(codes.charAt(b));
                out.append("==");
            }
        }

        return out.toString();
    }

    public static String caesarCipher(String input) {
        final String codes = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
        String output = "";
        for (int i = 0; i < input.length(); i++) {
            int newIndex = (codes.indexOf(input.charAt(i)) + 5) % 65;
            output += codes.charAt(newIndex);

        }
        return replaceSpecialCharacters(output);
    }

    private static String replaceSpecialCharacters(String input) {
        return input.replace("+", "!").replace("/", "@").replace("=", "#");
    }

    private static String restoreSpecialCharacters(String input) {
        return input.replace("!", "+").replace("@", "/").replace("#", "=");
    }

    public static String caesarCipherDecode(String input) {
        final String codes = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
        String actualInput = restoreSpecialCharacters(input);
        String output = "";
        for (int i = 0; i < actualInput.length(); i++) {
//			int newIndex = (codes.indexOf(input.charAt(i)) + 5) % 65;

            int newIndex = codes.indexOf(actualInput.charAt(i)) - 5;
            if (newIndex < 0) {
                newIndex = newIndex + 65;
            }
            output += codes.charAt(newIndex);
        }
        return output;
    }
}
