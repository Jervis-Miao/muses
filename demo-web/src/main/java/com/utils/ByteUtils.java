/*
Copyright All rights reserved.
 */

package com.utils;

/**
 * @author Jervis
 * @date 2018/6/20.
 */
public class ByteUtils {

    /**
     * 2进制转16进制字符串
     * @param bytes
     * @return
     */
    public static String byteToHexString(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String strHex = Integer.toHexString(bytes[i]);
            if (strHex.length() > 3) {
                sb.append(strHex.substring(6));
            } else {
                if (strHex.length() < 2) {
                    sb.append("0" + strHex);
                } else {
                    sb.append(strHex);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 16进制字符串转2进制
     * @param s
     * @return
     */
    public static byte[] hexStringToByte(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * 字符转为byte
     * @param c
     * @return
     */
    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * int to byte
     * @param i
     * @return
     */
    public static byte[] intToByte(int i) {
        byte[] bt = new byte[4];
        bt[0] = (byte) (0xff & i);
        bt[1] = (byte) ((0xff00 & i) >> 8);
        bt[2] = (byte) ((0xff0000 & i) >> 16);
        bt[3] = (byte) ((0xff000000 & i) >> 24);
        return bt;
    }

    /**
     * byte to int
     * @param bytes
     * @return
     */
    public static int bytesToInt(byte[] bytes) {
        int num = bytes[0] & 0xFF;
        num |= ((bytes[1] << 8) & 0xFF00);
        num |= ((bytes[2] << 16) & 0xFF0000);
        num |= ((bytes[3] << 24) & 0xFF000000);
        return num;
    }

    /**
     * 合并两个byte数组
     * @param pByteA 合并在前
     * @param pByteB 合并在后
     * @return
     */
    public static byte[] getMergeBytes(byte[] pByteA, byte[] pByteB) {
        int aCount = pByteA.length;
        int bCount = pByteB.length;
        byte[] b = new byte[aCount + bCount];
        for (int i = 0; i < aCount; i++) {
            b[i] = pByteA[i];
        }
        for (int i = 0; i < bCount; i++) {
            b[aCount + i] = pByteB[i];
        }
        return b;
    }

    /**
     * 合并三个byte数组
     * @param pByteA 合并前
     * @param pByteB 合并中
     * @param pByteC 合并后
     * @return 字节数组
     */
    public static byte[] getMergeBytes(byte[] pByteA, byte[] pByteB, byte[] pByteC) {

        int aCount = pByteA.length;
        int bCount = pByteB.length;
        int cCount = pByteC.length;

        byte[] b = new byte[aCount + bCount + cCount];

        for (int i = 0; i < aCount; i++) {
            b[i] = pByteA[i];
        }

        for (int i = 0; i < bCount; i++) {
            b[aCount + i] = pByteB[i];
        }

        for (int i = 0; i < cCount; i++) {
            b[aCount + bCount + i] = pByteC[i];
        }

        return b;
    }

    /**
     * 5个byte合并
     * @param a
     * @param b
     * @param c
     * @param d
     * @param e
     * @return
     */
    public static byte[] getMergeBytesFive(byte[] a, byte[] b, byte[] c, byte[] d, byte[] e) {

        int ia = a.length;
        int ib = b.length;
        int ic = c.length;
        int id = d.length;
        int ie = e.length;

        byte[] threeArr = new byte[ia + ib + ic];
        threeArr = getMergeBytes(a, b, c);

        byte[] twoArr = new byte[id + ie];
        twoArr = getMergeBytes(d, e);

        byte[] bs = new byte[ia + ib + ic + id + ie];
        bs = getMergeBytes(threeArr, twoArr);

        return bs;
    }

    public static String bytesToString(byte[] bytes) {

        StringBuffer sb = new StringBuffer();

        for (int k = 0; k < bytes.length; k++) {
            sb.append((char) bytes[k]);
        }

        return sb.toString();
    }

    /**
     * char转化为byte
     *
     * @param c
     * @return byte[]数组
     */
    public static byte[] charToByteArr(char c) {
        byte[] b = new byte[2];
        b[0] = (byte) ((c & 0xFF00) >> 8);
        b[1] = (byte) (c & 0xFF);
        return b;
    }

    /**
     * byte转换为char
     *
     * @param b
     * @return
     */
    public static char byteToChar(byte[] b) {
        char c = (char) (((b[0] & 0xFF) << 8) | (b[1] & 0xFF));
        return c;
    }
}
