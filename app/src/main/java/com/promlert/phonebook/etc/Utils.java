package com.promlert.phonebook.etc;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.text.DecimalFormat;

public class Utils {

    public static void showOkDialog(Context context, String title, String msg,
                                    DialogInterface.OnClickListener listener) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("OK", listener)
                .create();
        alertDialog.show();

        TextView titleTextView = new TextView(context);
        Typeface typeface = Typeface.createFromAsset(
                context.getAssets(),
                "fonts/arabica.ttf"
        );
        titleTextView.setLayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        );
        titleTextView.setTextColor(Color.RED);
        titleTextView.setGravity(Gravity.START);
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
        titleTextView.setTypeface(typeface);
        //titleTextView.setBackgroundColor(Color.YELLOW);
        //titleTextView.setPadding(15, 25, 15, 15);
        titleTextView.setText(title);
        titleTextView.setTextColor(Color.BLACK);
        alertDialog.setCustomTitle(titleTextView);

        /*Window alertDialogWindow = alertDialog.getWindow();
        if (alertDialogWindow != null) {
            TextView textView = alertDialogWindow.findViewById(android.R.id.message);
            TextView alertTitle = alertDialogWindow.findViewById(R.id.alertTitle);
            Button button1 = alertDialogWindow.findViewById(android.R.id.button1);
            Button button2 = alertDialogWindow.findViewById(android.R.id.button2);

            textView.setTypeface(FontHelper.getFont(Fonts.MULI_REGULAR));
            alertTitle.setTypeface(FontHelper.getFont(Fonts.MULI_REGULAR));
            button1.setTypeface(FontHelper.getFont(Fonts.MULI_BOLD));
            button2.setTypeface(FontHelper.getFont(Fonts.MULI_BOLD));
        }*/
    }

    public static void showShortToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static void hideKeyboard(Activity activity) {
        // Check if no view has focus:
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public static String formatThaiDate(String date) {
        String[] dateParts = date.split("-");
        return dateParts[2] + "." + dateParts[1] + "." + String.valueOf(Integer.parseInt(dateParts[0]) + 543);
    }

    public static String formatWholeNumberWithComma(double number) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(number);
    }

    public static String formatNumber2DecimalDigitsWithComma(double number) {
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        return formatter.format(number);
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context) {
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context) {
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
