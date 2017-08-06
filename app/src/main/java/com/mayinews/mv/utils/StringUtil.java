/**
 * 
 */
package com.mayinews.mv.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil {

	/**
	 * ���ִ�ǰ���a�հ�.
	 * 
	 * @param in
	 *            the input string
	 * @param length
	 *            the output string length
	 * @return the output string
	 */
	public static String fillBlankHead(String in, int length) {
		return fillString(in, length, true, ' ');
	}

	/**
	 * ���ִ�ǰ���a0.
	 * 
	 * @param in
	 *            the input
	 * @param length
	 *            �a�M���ִ��L��
	 * @return String
	 */
	public static String fillZeroHead(String in, int length) {
		return fillString(in, length, true, '0');
	}

	/**
	 * fill space character to tail of string to length.
	 * 
	 * @param in
	 *            the input string
	 * @param length
	 *            the output string length
	 * @return the output string
	 */
	public static String fillBlankTail(String in, int length) {
		return fillString(in, length, false, ' ');
	}

	/**
	 * ���ִ������a0
	 * 
	 * @param in
	 *            the input
	 * @param length
	 *            �a�M���ִ��L��
	 * @return String
	 */
	public static String fillZeroTail(String in, int length) {
		return fillString(in, length, false, '0');
	}

	/**
	 * fill specify character to string. ���ִ��a������Ԫ.
	 * 
	 * @param in
	 *            the input string. ݔ���ִ�.
	 * @param length
	 *            the output string length. �L��.
	 * @param rightAlign
	 *            true, the source string is right alignment, otherwise the
	 *            output string is left alignment. �Ƿ��ҿ�.
	 * @param ch
	 *            the append character. ��M����Ԫ.
	 * @return the output string. ݔ�����L���ִ�.
	 */
	public static String fillString(String in, int length, boolean rightAlign,
			char ch) {

		StringBuffer sb = new StringBuffer();
		if (in == null) {
			in = ""; //$NON-NLS-1$
		}
		int inLength = in.getBytes().length;
		if (inLength >= length) {
			return in;
		} else {
			int loopLength = ch < 256 ? length - inLength
					: (length - inLength) / 2;
			for (int i = 0; i < loopLength; i++) {
				sb.append(ch);
			}
			if (rightAlign) {
				sb.append(in);
			} else {
				sb.insert(0, in);
			}
		}
		return sb.toString();
	}
	
	/**
	 * �Д��ִ��Ƿ��հ�.
	 * 
	 * @param s
	 *            �ִ�
	 * @return boolean
	 */
	public static boolean isEmpty(String s) {
		return s == null || s.trim().length() == 0;
	}
	
	/**
	 * �ֻ�������֤
	 * @param phoneNbr
	 * @return
	 */
	public static boolean isMobileNo(String phoneNbr){
		Pattern p = Pattern.compile("^(1)\\d{10}$");
		Matcher m = p.matcher(phoneNbr);
		return m.matches();
	}
	
	
	/**
	 * ����������֤
	 * @param zipString
	 * @return
	 */
	public static boolean isZipNo(String zipString){
		String str = "^[1-9][0-9]{5}$";
		return Pattern.compile(str).matcher(zipString).matches();
	}
	
	/**
	 * Email ��֤
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email){
		if(!isEmail(email)){
			return false;
		}
		Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\ww+)*");
		Matcher m = p.matcher(email);
		return m.matches();
	}
	
	
	public static boolean isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][0-9]{10}$");
		m = p.matcher(str);
		b = m.matches();
		return b;
	}
	
	public static boolean isChineseName(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("([\u4e00-\u9fa5]{2,4})");
		m = p.matcher(str);
		b = m.matches();
		return b;
	}
	public static boolean isName(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("([\u4e00-\u9fa5a-zA-Z -]{2,20})");
		m = p.matcher(str);
		b = m.matches();
		return b;
	}
	
	
	public static boolean isIDCard(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65)[0-9]{4})" +  
                "(([1|2][0-9]{3}[0|1][0-9][0-3][0-9][0-9]{3}" +  
                "[Xx0-9])|([0-9]{2}[0|1][0-9][0-3][0-9][0-9]{3}))");
		m = p.matcher(str);
		b = m.matches();
		return b;
	}

	/**
	 * ��ȡappǩ����Ϣ
	 * @param context
	 * @return
	 *
	 */
	public static String getSignature(Context context) {
		try {
			/** ͨ�������������ָ����������ǩ���İ���Ϣ **/
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
			/******* ͨ�����صİ���Ϣ���ǩ������ *******/
			Signature[] signatures = packageInfo.signatures;
			/******* ѭ������ǩ������ƴ��Ӧ��ǩ�� *******/
			return signatures[0].toCharsString();
			/************** �õ�Ӧ��ǩ�� **************/
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	public  static String getSign(Context context) {
		try {
			// ���漸�д���չʾ��������ȡContext������jni��Ҳ����ʹ�����ַ�ʽ
//            Class<?> activityThreadClz = Class.forName("android.app.ActivityThread");
//            Method currentApplication = activityThreadClz.getMethod("currentApplication");
//            Application application = (Application) currentApplication.invoke(null);
//            PackageManager pm = application.getPackageManager();
//            PackageInfo pi = pm.getPackageInfo(application.getPackageName(), PackageManager.GET_SIGNATURES);

			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
			Signature[] signatures = pi.signatures;
			Signature signature0 = signatures[0];
			return signature0.toCharsString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}


}
