package com.mayinews.mv.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;

public class DialogUtils {
	public static void alert(Context context, Drawable icon, String title,
			String message, String positiveText,
			DialogInterface.OnClickListener positiveListener,
			String negativeText,
			DialogInterface.OnClickListener negativeListener,
			String neutralText, DialogInterface.OnClickListener neutralListener) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog = dialog.setIcon(icon);
		dialog = dialog.setTitle(title);
		dialog = dialog.setMessage(message);
		dialog = dialog.setPositiveButton(positiveText, positiveListener);
		dialog = dialog.setNegativeButton(negativeText, negativeListener);
		dialog = dialog.setNeutralButton(neutralText, neutralListener);
		Dialog noticeDialog=dialog.create();
		noticeDialog.setCanceledOnTouchOutside(false);
		noticeDialog.show();
	}

	public static void alert(Context context, int icon, String title,
			String message, String positiveText,
			DialogInterface.OnClickListener positiveListener,
			String negativeText,
			DialogInterface.OnClickListener negativeListener,
			String neutralText, DialogInterface.OnClickListener neutralListener) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog = dialog.setIcon(icon);
		dialog = dialog.setTitle(title);
		dialog = dialog.setMessage(message);
		dialog = dialog.setPositiveButton(positiveText, positiveListener);
		dialog = dialog.setNegativeButton(negativeText, negativeListener);
		dialog = dialog.setNeutralButton(neutralText, neutralListener);
		Dialog noticeDialog=dialog.create();
		noticeDialog.setCanceledOnTouchOutside(false);
		noticeDialog.show();
	}

	public static void alert(Context context, Drawable icon, int title,
			int message, int positiveText,
			DialogInterface.OnClickListener positiveListener, int negativeText,
			DialogInterface.OnClickListener negativeListener, int neutralText,
			DialogInterface.OnClickListener neutralListener) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog = dialog.setIcon(icon);
		dialog = dialog.setTitle(title);
		dialog = dialog.setMessage(message);
		dialog = dialog.setPositiveButton(positiveText, positiveListener);
		dialog = dialog.setNegativeButton(negativeText, negativeListener);
		dialog = dialog.setNeutralButton(neutralText, neutralListener);
		Dialog noticeDialog=dialog.create();
		noticeDialog.setCanceledOnTouchOutside(false);
		noticeDialog.show();
	}

	public static void alert(Context context, int icon, int title, int message,
			int positiveText, DialogInterface.OnClickListener positiveListener,
			int negativeText, DialogInterface.OnClickListener negativeListener,
			int neutralText, DialogInterface.OnClickListener neutralListener) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog = dialog.setIcon(icon);
		dialog = dialog.setTitle(title);
		dialog = dialog.setMessage(message);
		dialog = dialog.setPositiveButton(positiveText, positiveListener);
		dialog = dialog.setNegativeButton(negativeText, negativeListener);
		dialog = dialog.setNeutralButton(neutralText, neutralListener);
		Dialog noticeDialog=dialog.create();
		noticeDialog.setCanceledOnTouchOutside(false);
		noticeDialog.show();
	}

	public static void alert(Context context, Drawable icon, String title,
			String message, String positiveText,
			DialogInterface.OnClickListener positiveListener,
			String negativeText,
			DialogInterface.OnClickListener negativeListener) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog = dialog.setIcon(icon);
		dialog = dialog.setTitle(title);
		dialog = dialog.setMessage(message);
		dialog = dialog.setPositiveButton(positiveText, positiveListener);
		dialog = dialog.setNegativeButton(negativeText, negativeListener);
		Dialog noticeDialog=dialog.create();
		noticeDialog.setCanceledOnTouchOutside(false);
		noticeDialog.show();
	}

	public static void alert(Context context, int icon, String title,
			String message, String positiveText,
			DialogInterface.OnClickListener positiveListener,
			String negativeText,
			DialogInterface.OnClickListener negativeListener) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog = dialog.setIcon(icon);
		dialog = dialog.setTitle(title);
		dialog = dialog.setMessage(message);
		dialog = dialog.setPositiveButton(positiveText, positiveListener);
		dialog = dialog.setNegativeButton(negativeText, negativeListener);
		Dialog noticeDialog=dialog.create();
		noticeDialog.setCanceledOnTouchOutside(false);
		noticeDialog.show();
	}

	public static void alert(Context context, Drawable icon, int title,
			int message, int positiveText,
			DialogInterface.OnClickListener positiveListener, int negativeText,
			DialogInterface.OnClickListener negativeListener) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog = dialog.setIcon(icon);
		dialog = dialog.setTitle(title);
		dialog = dialog.setMessage(message);
		dialog = dialog.setPositiveButton(positiveText, positiveListener);
		dialog = dialog.setNegativeButton(negativeText, negativeListener);
		Dialog noticeDialog=dialog.create();
		noticeDialog.setCanceledOnTouchOutside(false);
		noticeDialog.show();
	}

	public static void alert(Context context, int icon, int title, int message,
			int positiveText, DialogInterface.OnClickListener positiveListener,
			int negativeText, DialogInterface.OnClickListener negativeListener) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog = dialog.setIcon(icon);
		dialog = dialog.setTitle(title);
		dialog = dialog.setMessage(message);
		dialog = dialog.setPositiveButton(positiveText, positiveListener);
		dialog = dialog.setNegativeButton(negativeText, negativeListener);
		Dialog noticeDialog=dialog.create();
		noticeDialog.setCanceledOnTouchOutside(false);
		noticeDialog.show();
	}

	public static void alert(Context context, Drawable icon, String title,
			String message, String positiveText,
			DialogInterface.OnClickListener positiveListener) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog = dialog.setIcon(icon);
		dialog = dialog.setTitle(title);
		dialog = dialog.setMessage(message);
		dialog = dialog.setPositiveButton(positiveText, positiveListener);
		Dialog noticeDialog=dialog.create();
		noticeDialog.setCanceledOnTouchOutside(false);
		noticeDialog.show();
	}

	public static void alert(Context context, int icon, String title,
			String message, String positiveText,
			DialogInterface.OnClickListener positiveListener) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context,AlertDialog.THEME_HOLO_LIGHT);
		dialog = dialog.setIcon(icon);
		dialog = dialog.setTitle(title);
		dialog = dialog.setMessage(message);
		dialog = dialog.setPositiveButton(positiveText, positiveListener);
		Dialog noticeDialog=dialog.create();
		noticeDialog.setCanceledOnTouchOutside(false);
		noticeDialog.show();
	}

	public static void alert(Context context, Drawable icon, int title,
			int message, int positiveText,
			DialogInterface.OnClickListener positiveListener) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog = dialog.setIcon(icon);
		dialog = dialog.setTitle(title);
		dialog = dialog.setMessage(message);
		dialog = dialog.setPositiveButton(positiveText, positiveListener);
		Dialog noticeDialog=dialog.create();
		noticeDialog.setCanceledOnTouchOutside(false);
		noticeDialog.show();
	}

	public static void alert(Context context, int icon, int title,
			String message, int positiveText,
			DialogInterface.OnClickListener positiveListener) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog = dialog.setIcon(icon);
		dialog = dialog.setTitle(title);
		dialog = dialog.setMessage(message);
		dialog = dialog.setPositiveButton(positiveText, positiveListener);
		Dialog noticeDialog=dialog.create();
		noticeDialog.setCanceledOnTouchOutside(false);
		noticeDialog.show();
	}

	public static void alert(Context context, int icon, int title, int message,
			int positiveText, DialogInterface.OnClickListener positiveListener) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog = dialog.setIcon(icon);
		dialog = dialog.setTitle(title);
		dialog = dialog.setMessage(message);
		dialog = dialog.setPositiveButton(positiveText, positiveListener);
		Dialog noticeDialog=dialog.create();
		noticeDialog.setCanceledOnTouchOutside(false);
		noticeDialog.show();
	}

	public static void alert(Context context, int icon, int title,
			String message, int positiveText,
			DialogInterface.OnClickListener positiveListener, int negativeText,
			DialogInterface.OnClickListener negativeListener) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog = dialog.setIcon(icon);
		dialog = dialog.setTitle(title);
		dialog = dialog.setMessage(message);
		dialog = dialog.setPositiveButton(positiveText, positiveListener);
		dialog = dialog.setNegativeButton(negativeText, negativeListener);
		Dialog noticeDialog=dialog.create();
		noticeDialog.setCanceledOnTouchOutside(false);
		noticeDialog.show();
	}
}
