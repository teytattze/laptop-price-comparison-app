package com.tattzetey.webscraper.lib;

import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class has contained the
 * static utility functions
 */
public class Util {

    /**
     * This function will get an array
     * of attribute from web element list
     * @param elements a list of web elements
     * @param attr the attribute of web elements
     * @return a list of value from the web element attribute
     * */
    public static List<String> getElementsValueByAttr(List<WebElement> elements, String attr) {
        List<String> result = new ArrayList<String>();
        for (WebElement element : elements) {
            String value = element.getAttribute(attr);
            result.add(value);
        }
        return result;
    }

    /**
     * This function will return a specific string
     * based on the given regex
     * @param str a long text
     * @param regex the string pattern
     * @return a string that matched regex pattern from str
     * */
    public static String getStringByRegex(String str, String regex) {
        if (str == null) {
            return "";
        }
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    /**
     * This function will remove three commons (®, ™, £)
     * special characters from the string
     * @param str the input string
     * @return the string without special characters
     * */
    public static String removeSpecialChar(String str) {
        str = str.replaceAll("\\u00ae", "");
        str = str.replaceAll("\\u2122", "");
        str = str.replaceAll("\\u00a3", "");
        return str;
    }

}
