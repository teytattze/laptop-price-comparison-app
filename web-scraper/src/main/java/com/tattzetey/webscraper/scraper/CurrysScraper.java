package com.tattzetey.webscraper.scraper;

import com.tattzetey.webscraper.constant.RegexConst;
import com.tattzetey.webscraper.constant.ScraperConst;
import com.tattzetey.webscraper.constant.UrlConst;
import com.tattzetey.webscraper.dao.*;
import com.tattzetey.webscraper.lib.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is scraper class for Currys website
 * */
@Component
public class CurrysScraper extends BaseScraper{

    @Autowired
    CurrysScraper(BrandDAO brandDAO, LaptopDAO laptopDAO, SpecificationDAO specificationDAO, LaptopSpecificationDAO laptopSpecificationDAO, SourceDAO sourceDAO) {
        this.brandDAO = brandDAO;
        this.laptopDAO = laptopDAO;
        this.specificationDAO = specificationDAO;
        this.laptopSpecificationDAO = laptopSpecificationDAO;
        this.sourceDAO = sourceDAO;
    }

    @Override
    public void run() {

        // Keep running if the isRunning
        // equal to true
        while (isRunning) {
            WebDriver driver = getDriver();

            // Navigate to the webpage
            driver.navigate().to(UrlConst.CURRYS_BASE_URL + "/gbuk/gaming/pc-gaming/gaming-laptops/654_4805_32603_xx_xx/xx-criteria.html");

            // Accept cookies
            driverWait(driver, Duration.ofSeconds(5)).until(d -> d.findElement(By.id("onetrust-accept-btn-handler"))).click();

            // Get the products number
            int itemNumbers = Integer.parseInt(driver.findElement(By.cssSelector("div[data-component='list-page-results-message'] span")).getAttribute("innerText").split(" ")[0]);

            // Loop through all the pages in the website
            for (int page = 1; page <= Math.ceil(itemNumbers / 20) + 1; page++) {

                // Navigate page by page
                driver.navigate().to(UrlConst.CURRYS_BASE_URL + "/gbuk/gaming/pc-gaming/gaming-laptops/654_4805_32603_xx_xx/" + page + "_20/relevance-desc/xx-criteria.html");

                // Get all the product containers
                List<WebElement> productElementList = driver.findElements(By.cssSelector("div[data-component='product-list-view'] article"));

                // Loop through all the product containers
                // Then get the data from each product container
                // Then save it to database
                for (WebElement element : productElementList) {
                    try {
                        Map<String, String> productDetails = getProductDetails(driver, element);
                        saveProductDetails(productDetails);
                    } catch(Exception ex) {
                        System.out.println(ex);
                    }
                }
            }

            driver.close();

            try {
                sleep(60000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Get product details from product container
     * */
    private Map<String, String> getProductDetails(WebDriver driver, WebElement element) {

        Map<String, String> result = new HashMap<String, String>();
        String title = element.findElement(By.cssSelector("span[data-product='name']")).getAttribute("innerText");
        String brand = element.findElement(By.cssSelector("span[data-product='brand']")).getAttribute("innerText");
        String price = element.findElement(By.cssSelector(".productPrices span:first-child")).getAttribute("innerText");
        String imageUrl = driverWait(driver, Duration.ofSeconds(5)).until(d -> d.findElement(By.cssSelector(".product-images img.image.product-image"))).getAttribute("src");
        String url = element.findElement(By.cssSelector("header.productTitle>a")).getAttribute("href");

        List<String> specArr = Util.getElementsValueByAttr(element.findElements(By.cssSelector(".productDescription li")), "innerText");
        String specString = specArr.toString();

        // Get product details by regex
        String processor = Util.getStringByRegex(specString, RegexConst.PROCESSOR_PATTERN);
        String graphicsCard = Util.getStringByRegex(specString, RegexConst.GRAPHICS_CARD_PATTERN);
        String ram = Util.getStringByRegex(specString, RegexConst.RAM_PATTERN);
        String ssd;
        String hdd;
        // Split hdd and ssd if the computer has two types of storage
        Boolean hasHdd = specString.contains("&");
        if (hasHdd) {
            String[] storageArr = specString.split("/")[1].split("&");
            ssd = Util.getStringByRegex(storageArr[1], RegexConst.SSD_PATTERN);
            hdd = Util.getStringByRegex(storageArr[0], RegexConst.HDD_PATTERN);
        } else {
            ssd = Util.getStringByRegex(specString, RegexConst.SSD_PATTERN);
            hdd = "";
        }
        String screenSize = Util.getStringByRegex(title, RegexConst.SCREEN_SIZE);

        // Save all the details into a map
        result.put(ScraperConst.BRAND, brand);
        result.put(ScraperConst.TITLE, title);
        result.put(ScraperConst.IMAGE_URL, imageUrl);
        result.put(ScraperConst.PROCESSOR, processor);
        result.put(ScraperConst.GRAPHICS_CARD, graphicsCard);
        result.put(ScraperConst.RAM, ram);
        result.put(ScraperConst.HDD, hdd);
        result.put(ScraperConst.SSD, ssd);
        result.put(ScraperConst.SCREEN_SIZE, screenSize);
        result.put(ScraperConst.WEBSITE, "Currys");
        result.put(ScraperConst.URL, url);
        result.put(ScraperConst.PRICE, price);

        return result;
    }
}
