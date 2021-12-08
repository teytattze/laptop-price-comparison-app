package com.tattzetey.webscraper.lib;

import com.tattzetey.webscraper.constant.RegexConst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class has included all the formatting
 * functions that are used to format
 * the data before saving into database
 * */
public class Format {

    /**
     * This function is used to format
     * the product's title string
     * @param str laptop's title
     * @return formatted laptop's title
     * */
    public static String title(String str) {
        str = Util.removeSpecialChar(str);
        str = str.replaceAll(",", " ");
        str = str.replaceAll(" ", " ");
        String result = "";
        String brand = Util.getStringByRegex(str, RegexConst.GAMING_LAPTOP_BRAND_PATTERN);
        List<String> arr = new ArrayList<String>(Arrays.asList(str.split(" ")));

        if (!brand.isEmpty()) {
            int endIndex = arr.indexOf(brand);
            arr = arr.subList(endIndex+1, arr.size());
        }

        for (String item : arr) {
            String curr = item.toLowerCase();
            if (curr.contains("(") || curr.contains("gaming") || curr.contains("intel") || curr.contains("amd") || curr.contains("nvidia") || curr.contains("\"") || curr.contains("core") || curr.contains("ryzen") || curr.contains("gamng") || curr.contains("rtx") || curr.contains("in")) {
                break;
            }
            result = result.concat(item).concat(" ");
        }
        return result.trim();
    }

    /**
     * This function is used to format
     * the product's brand string
     * @param str brand's name
     * @return formatted brand's name
     * */
    public static String brand(String str) {
        str = Util.removeSpecialChar(str);
        String brand = Util.getStringByRegex(str.toUpperCase(), RegexConst.GAMING_LAPTOP_BRAND_PATTERN);
        if (brand.equals("AORUS")) {
            brand = "GIGABYTE";
        }
        return brand;
    }

    /**
     * This function is used to format
     * the product's processor string
     * @param str processor's name
     * @return formatted processor's name
     * */
    public static String processor(String str) {
        str = str.replaceAll("(?i)Processor", "");
        str = Util.removeSpecialChar(str);
        String brand = Util.getStringByRegex(str, RegexConst.PROCESSOR_BRAND_PATTERN);
        String model = Util.getStringByRegex(str, RegexConst.PROCESSOR_MODEL_PATTERN);
        if (brand.contains("Intel") && model.contains(" -")) {
            model = model.replace(" ", "");
        }
        if (brand.contains("Intel")) {
            model = model.replace(" ", "-");
        }
        return brand.concat(" ").concat(model).trim();
    }

    /**
     * This function is used to format
     * the product's graphics card string
     * @param str graphicsCard's name
     * @return formatted graphicsCard's name
     * */
    public static String graphicsCard(String str) {
        String brand = "";
        String model = Util.removeSpecialChar(str);

        if (model.isEmpty()) {
            return "";
        }

        if (model.contains("RX")) {
            brand = "AMD Radeon";
        } else {
            brand = "NVIDIA GeForce";
        }

        return brand.concat(" ").concat(model);
    }

    /**
     * This function is used to format
     * the product's storage (SSD & HDD) string
     * @param str storage's size string
     * @return formatted storage's size string
     * */
    public static String hddOrSdd(String str) {
        String amount = Util.getStringByRegex(str, RegexConst.STORAGE_SIZE_AMOUNT_PATTERN);
        String unit = Util.getStringByRegex(str, RegexConst.STORAGE_SIZE_UNIT_PATTERN).toUpperCase();
        return amount.concat(" ").concat(unit).trim();
    }

    /**
     * This function is used to format
     * the product's ram string
     * @param str ram's size string
     * @return formatted ram's size string
     * */
    public static String ram(String str) {
        String amount = Util.getStringByRegex(str, RegexConst.STORAGE_SIZE_AMOUNT_PATTERN);
        String unit = Util.getStringByRegex(str, RegexConst.STORAGE_SIZE_UNIT_PATTERN).toUpperCase();
        return amount.concat(" ").concat(unit).trim();
    }

    /**
     * This function is used to format
     * the product's screen size string
     * @param str screen's size string
     * @return formatted screen's size string
     * */
    public static String screenSize(String str) {
        String size = Util.getStringByRegex(str, RegexConst.SCREEN_SIZE_AMOUNT_PATTERN);
        if (size.isEmpty()) {
            return "";
        }
        return size.concat("\"").trim();
    }

    /**
     * This function is used to format
     * the product's price string
     * @param str price's string
     * @return formatted price's string
     * */
    public static String price(String str) {
        str = Util.removeSpecialChar(str);
        str = str.replaceAll(",", "");
        str = str.replaceAll(" ", "");
        return str;
    }

}
