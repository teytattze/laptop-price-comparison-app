package com.tattzetey.webscraper.constant;

/**
 * This class has contained different
 * types of regex constant to abstract
 * specific info from the result string
 * from the scraper
 * */
public class RegexConst {
    public static final String PROCESSOR_PATTERN = "(Intel\\u00ae? Core\\u2122?|AMD Ryzen\\u2122?) \\w*( ?-? ?\\w*)?";
    public static final String GRAPHICS_CARD_PATTERN = "((GTX|RTX|MX) ?([0-9]{3,4}))|(RX ?[0-9]{4}( ?M)?)";
    public static final String SSD_PATTERN = "([0-9]{3,4} ?GB|[0-9]{1} ?TB)( SSD)?";
    public static final String HDD_PATTERN = "(([0-9]{3,4} ?GB|[0-9]{1} ?TB)( (HDD)|(Hard Drive))?)";
    public static final String RAM_PATTERN = "[0-9]{1,2} ?GB((.*)? RAM)?";
    public static final String SCREEN_SIZE = "[1-9]{2}(\\.[0-9])?(\"|( ?Inches| ?Inch| ?In))";

    public static final String GAMING_LAPTOP_BRAND_PATTERN = "(Asus|Acer|Alienware|Dell|Gigabyte|HP|Lenovo|Medion|MSI|Razer|Pcspecialist|AORUS)";
    public static final String PROCESSOR_BRAND_PATTERN = "(Intel Core)|(AMD Ryzen)";
    public static final String PROCESSOR_MODEL_PATTERN = "(i[3579] ?( |-)\\w*)|([579] \\w*)";
    public static final String STORAGE_SIZE_AMOUNT_PATTERN = "[0-9]{1,4}";
    public static final String STORAGE_SIZE_UNIT_PATTERN = "MB|GB|TB";
    public static final String SCREEN_SIZE_AMOUNT_PATTERN = "[0-9]{2}(\\.[0-9])?";
}
