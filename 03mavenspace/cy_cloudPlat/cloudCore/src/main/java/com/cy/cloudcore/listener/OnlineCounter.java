package com.cy.cloudcore.listener;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OnlineCounter {
	private static List<String[]> list = new ArrayList();

	private static List<String[]> list1 = new ArrayList();

	public static String allcount = "0";

	public static String online_time = "";

	public static String wenzCount = "";

	public static String hyCount = "";

	public static String newUser = "";

	public static String getAllcount() {
		return allcount;
	}

	public static String getOnline_time() {
		return online_time;
	}

	public static String getWenzCount() {
		return wenzCount;
	}

	public static String getHyCount() {
		return hyCount;
	}

	public static String getNewUser() {
		return newUser;
	}

	public static void initCount(String allcount, String online_time,
			String wenzCount, String hyCount) {
		allcount = allcount;
		online_time = online_time;
		wenzCount = wenzCount;
		hyCount = hyCount;
	}

	public static void doUser(Object object, boolean bl) {
		String nowtime = getTime("mm:ss");

		String[] str = new String[2];
		str[0] = object.toString();
		str[1] = nowtime;

		String[] temp = new String[2];

		for (int i = 0; i < list.size(); i++) {
			temp = (String[]) list.get(i);

			if ((!bl) && (temp[0].equals(str[0]))) {
				list.set(i, str);
				return;
			}

			if ((bl) && (temp[0].equals(str[0]))) {
				list.remove(i);
			}
		}

		if (!bl) {
			list.add(str);
		}
		str = null;
		temp = null;
	}

	public static void allUser(Object object) {
		String nowtime = getTime("mm:ss");

		String[] str = new String[2];
		str[0] = object.toString();
		str[1] = nowtime;

		String[] temp = new String[2];

		for (int i = 0; i < list1.size(); i++) {
			temp = (String[]) list1.get(i);

			if (temp[0].equals(str[0])) {
				list1.set(i, str);
				return;
			}

			if (subTime(nowtime, temp[1]) > 600) {
				list1.remove(i);
			}
		}

		list1.add(str);

		str = null;
		temp = null;
	}

	public static int getOnlineAllCount() {
		return list1.size();
	}

	public static int getOnlineCount() {
		return list.size();
	}

	public static List<String[]> getOnline() {
		return list;
	}

	public static int subTime(String src, String des) {
		int n = 0;
		Calendar ca = Calendar.getInstance();
		long time1 = compareStringTime(src, des, "mm:ss");
		ca.setTimeInMillis(time1);
		n = ca.get(12) * 60;
		n += ca.get(13);
		return n;
	}

	public static String getTime(String parrten) {
		if ((parrten == null) || (parrten.equals(""))) {
			parrten = "yyyyMMddHHmmss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(parrten);
		Date cday = new Date();
		String timestr = sdf.format(cday);
		return timestr;
	}

	public static long compareStringTime(String t1, String t2, String parrten) {
		SimpleDateFormat formatter = new SimpleDateFormat(parrten);
		ParsePosition pos = new ParsePosition(0);
		ParsePosition pos1 = new ParsePosition(0);
		Date dt1 = formatter.parse(t1, pos);
		Date dt2 = formatter.parse(t2, pos1);
		long l = dt1.getTime() - dt2.getTime();
		return l;
	}

	public static List<String[]> getList() {
		return list;
	}

	public static List<String[]> getList1() {
		return list1;
	}
}
