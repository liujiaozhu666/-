package com.chillax.common;

import java.util.Random;

/**
 * ����ַ�������
 * @author zuoliangzhu
 *
 */
public class RandomStringGenerator {
	/**
     * ��ȡһ�����ȵ�����ַ���
     * @param length ָ���ַ�������
     * @return һ�����ȵ��ַ���
     */
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
