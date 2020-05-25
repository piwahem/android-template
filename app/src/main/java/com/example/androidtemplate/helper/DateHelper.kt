package com.example.androidtemplate.helper

import android.app.DatePickerDialog
import android.content.Context
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.*
import java.util.Locale.getDefault

object DateHelper {

    const val MOBILE_DATE_FORMAT_DETAIL = "dd MMMM yyyy"
    const val MOBILE_DATE_FORMAT_LIST = "dd MMM yyyy"
    const val MOBILE_DATE_FORMAT_LIST_RIGHT = "dd/MM/yy"
    const val SERVICE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"

    const val SECOND_MILLIS = 1000
    const val MINUTE_MILLIS = 60 * SECOND_MILLIS
    const val HOUR_MILLIS = 60 * MINUTE_MILLIS
    const val DAY_MILLIS = 24 * HOUR_MILLIS

//    const val TIME_FORMAT_SECOND = "hh:mm:ss"
//    var TIME_FORMAT: String = ""
//        get() = if (is24hFormat()) "HH:mm" else "hh:mm aa"
//
//    var TIME_FORMAT_BLINK: String = ""
//        get() = if (is24hFormat()) "HH mm" else "hh mm aa"
//
//    var TIME_FORMAT_2: String = ""
//        get() = if (is24hFormat()) "HH:mm" else "KK:mm"
    const val WEEK_FULL_DATE_FORMAT = "EEE, dd MMM yyyy"
    const val SHORT_DATE_FORMAT = "dd MMM"
    const val SHORT_YEAR_DATE_FORMAT = "dd MMM yy"
    const val NORMAL_DATE_FORMAT = "dd MMM yyyy"
    const val WEEK_DATE_FORMAT = "EEE, dd MMM"
    const val VOUCHER_DATE_FORMAT = "yyyy-MM-dd"
//    var NORMAL_DATE_TIME_FORMAT: String = ""
//        get() = "$NORMAL_DATE_FORMAT $TIME_FORMAT"
//    var WEEK_DATE_TIME_FORMAT: String = ""
//        get() = "$WEEK_FULL_DATE_FORMAT $TIME_FORMAT"

    fun day(): Int {
        return getInstance().get(DAY_OF_MONTH)
    }

    fun hour(): Int {
        return getInstance().get(HOUR_OF_DAY)
    }

    fun minute(): Int {
        return getInstance().get(MINUTE)
    }

    fun dateNumber(): Int {
        return getInstance().get(DATE)
    }

    fun day(calendar: Calendar): Int {
        return calendar.get(DAY_OF_MONTH)
    }

    fun dayInWeek(): Int {
        return getInstance().get(DAY_OF_WEEK)
    }

    fun day(date: Date): Int {
        val c = getInstance()
        c.time = date
        return day(c)
    }

    fun month(): Int {
        return getInstance().get(MONTH)
    }

    fun month(calendar: Calendar): Int {
        return calendar.get(MONTH)
    }

    fun month(date: Date): String {
        val calendar = getInstance()
        calendar.time = date
        val m = month(calendar) + 1
        return m.toString()
    }

    fun year(date: Date): String {
        val calendar = getInstance()
        calendar.time = date
        val y = year(calendar)
        return y.toString()
    }

    fun year(): Int {
        return getInstance().get(YEAR)
    }

    fun year(calendar: Calendar): Int {
        return calendar.get(YEAR)
    }

    internal fun timestamp(): Long {
        return System.currentTimeMillis() / 1000
    }

    fun date(): Date {
        val cal = getInstance()
        cal.timeInMillis = timestamp()
        return cal.time
    }

    fun addDayAfter(date: Date, amount: Int): Date {
        val c = Calendar.getInstance()
        c.add(Calendar.DATE, amount)
        return c.time
    }

    /**
     * @see BaseApplication,
     * Locale by default is Locale("in")
     */
    fun dateFormat(format: String): SimpleDateFormat {
        return SimpleDateFormat(format, getDefault())
    }

    fun dateStr(from: Date): String {
        val formatter = dateFormat(MOBILE_DATE_FORMAT_DETAIL)
        val result = formatter.format(from)
        return result
    }

    fun toDay(from: Date, customFormat: String): String {
        val formatter = dateFormat(customFormat)
        val result = formatter.format(from)
        return result
    }

    fun dateStr(from: Date, customFormat: String): String {
        val formatter = dateFormat(customFormat)
        val result = formatter.format(from)
        return result
    }

    fun dateObj(from: String): Date {
        val formatter = dateFormat(SERVICE_DATE_FORMAT)
        val result = formatter.parse(from)
        return result
    }

    fun dateObjWithTimeZone(from: String, timeZoneId: String): Date {
        val formatter = dateFormat(SERVICE_DATE_FORMAT)
        formatter.timeZone = TimeZone.getTimeZone(timeZoneId)
        return formatter.parse(from)
    }

    fun dateCalendar(from: String): Date {
        val formatter = dateFormat("yyyy-MM-dd")
        val result = formatter.parse(from)
        return result
    }

    fun dateObj(from: String, customFormat: String): Date {
        val formatter = dateFormat(customFormat)
        val result = formatter.parse(from)
        return result
    }

    fun changeFormat(stringDate: String, prevFormat: String, toFormat: String): String {
        val df_prevFormat = dateFormat(prevFormat)
        val df_toFormat = dateFormat(toFormat)
        val date: Date
        try {
            date = df_prevFormat.parse(stringDate)
        } catch (e: ParseException) {
            return ""
        }

        return df_toFormat.format(date)
    }

    fun getFirstDateOfTheMonth(date: Date): String {
        val c = Calendar.getInstance()
        c.set(year(date).toInt(), month(date).toInt() - 1, day(date))
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH))

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(c.time)
    }

    fun getLastDateOfTheMonth(date: Date): String {
        val c = Calendar.getInstance()
        c.set(year(date).toInt(), month(date).toInt() - 1, day(date))
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH))

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(c.time)
    }

    fun datePicker(
        context: Context,
        listener: DatePickerDialogListener,
        defaultDate: Date,
        requestCode: Int,
        requestedFormat: String
    ) {
        val cal_defaultDate = getInstance()
        cal_defaultDate.time = defaultDate
        DatePickerDialog(context, DatePickerDialog.OnDateSetListener { datePicker, i, i1, i2 ->
            listener.onDateSet(
                requestCode, dateFormat(requestedFormat)
                    .format(
                        GregorianCalendar(
                            datePicker.year,
                            datePicker.month,
                            datePicker.dayOfMonth
                        ).time
                    )
            )
        }
            , year(cal_defaultDate), month(cal_defaultDate), day(cal_defaultDate)).show()
    }

    fun today(): Date {
        return Calendar.getInstance().time
    }

    fun todayStr(format: String): String {
        var dateStr = dateStr(today(), format)
        return dateStr
    }

    interface DatePickerDialogListener {
        fun onDateSet(requestCode: Int, date: String)
    }

    fun getTotalDayBetween(date1: Date, date2: Date): Int {
        val c1 = getInstance()
        c1.time = date2

        val c2 = getInstance()
        c2.time = date1

        return c1.get(Calendar.DAY_OF_YEAR) - c2.get(Calendar.DAY_OF_YEAR)
    }

    fun dateObj(from: Long, customFormat: String): String {
        val formatter = dateFormat(customFormat)
        val result = formatter.format(from)
        return result
    }

//    fun getTimeAgo(context: Context, currentTime: Long): String? {
//        var time = currentTime
//        if (time < 1000000000000L) {
//            // if timestamp given in seconds, convert to millis
//            time *= 1000
//        }
//
//        val now = System.currentTimeMillis()
//        if (time > now || time <= 0) {
//            return context.getString(R.string.time_relative_just_now)
//        }
//
//        val locale = LocaleHelper.getLanguage(context)
//        val diff = now - time
//        return if (diff < MINUTE_MILLIS) {
//            context.getString(R.string.time_relative_just_now)
//        } else if (diff < 2 * MINUTE_MILLIS) {
//            context.getString(R.string.time_relative_minute_ago, 1, "")
//        } else if (diff < 50 * MINUTE_MILLIS) {
//            context.getString(R.string.time_relative_minute_ago, diff / MINUTE_MILLIS, if (locale == LocaleHelper.LOCALE_ID) "" else "s")
//        } else if (diff < 90 * MINUTE_MILLIS) {
//            context.getString(R.string.time_relative_hour_ago, 1, "")
//        } else if (diff < 24 * HOUR_MILLIS) {
//            context.getString(R.string.time_relative_hour_ago, diff / HOUR_MILLIS, if (locale == LocaleHelper.LOCALE_ID) "" else "s")
//        } else if (diff < 48 * HOUR_MILLIS) {
//            context.getString(R.string.time_relative_yesterday)
//        } else {
//            if (dateObj(time, "yyyy").toInt() != year()) {
//                dateObj(time, SHORT_YEAR_DATE_FORMAT)
//            } else {
//                dateObj(time, SHORT_DATE_FORMAT)
//            }
//        }
//    }
//
//    fun getTimeFromFormat(mls: Long, format: String): String {
//        val sdf = SimpleDateFormat(format)
//        sdf.timeZone = TimeZone.getDefault()
//        return sdf.format(Date(mls))
//    }
//
//    fun getTimeFromMLS(mls: Long): String {
//        return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(mls),
//                TimeUnit.MILLISECONDS.toMinutes(mls) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(mls)),
//                TimeUnit.MILLISECONDS.toSeconds(mls) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mls)))
//    }
//    private fun isBrokenDevice(): Boolean {
//        return (Build.MANUFACTURER.equals("samsung", true))
//                && (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP || Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1)
//    }
//
//    fun getDatePickerDialog(context: Context, listener: DatePickerDialog.OnDateSetListener): DatePickerDialog {
//        val ct = if (isBrokenDevice()) {
//            object : ContextWrapper(context) {
//
//                private var wrappedResources: Resources? = null
//
//                override fun getResources(): Resources? {
//                    val r = super.getResources()
//                    if (wrappedResources == null) {
//                        wrappedResources = object : Resources(r.assets, r.displayMetrics, r.configuration) {
//                            @NonNull
//                            @Throws(NotFoundException::class)
//                            override fun getString(id: Int, vararg formatArgs: Any): String {
//                                return try {
//                                    super.getString(id, formatArgs)
//                                } catch (ifce: IllegalFormatConversionException) {
//                                    Log.e("DatePickerDialogFix", "IllegalFormatConversionException Fixed!", ifce)
//                                    var template = super.getString(id)
//                                    template = template.replace(("%" + ifce.conversion).toRegex(), "%s")
//                                    String.format(configuration.locale, template, formatArgs)
//                                }
//
//                            }
//                        }
//                    }
//                    return wrappedResources
//                }
//            }
//        } else {
//            ContextThemeWrapper(context, R.style.TalentaTheme_Dialog)
//        }
//        return DatePickerDialog(ct, listener, year(), month(), day())
//    }
//
//    fun getTimeByHourAndMinute(hourOfDay: Int, minute: Int): String {
//        if (is24hFormat()) {
//            val hourValue = String.format("%02d", hourOfDay)
//            val minuteValue = String.format("%02d", minute)
//            val value = "$hourValue:$minuteValue"
//            return value
//        } else {
//            val datetime = Calendar.getInstance()
//            datetime.set(Calendar.HOUR_OF_DAY, hourOfDay)
//            datetime.set(Calendar.MINUTE, minute)
//            val sdf = SimpleDateFormat(TIME_FORMAT)
//            sdf.timeZone = TimeZone.getDefault()
//            val value = sdf.format(Date(datetime.timeInMillis))
//            return value
//        }
//    }
//
//    fun is24hFormat(): Boolean {
//        if (MainApplication.appContext != null) {
//            val is24H = DateFormat.is24HourFormat(MainApplication.appContext)
//            LoggerHelper.error("is24hFormat = $is24H")
//            return is24H
//        } else {
//            return false
//        }
//    }
}