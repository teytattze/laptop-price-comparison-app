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
 * This is scraper class for Ebuyer website
 * */
@Component
public class EbuyerScraper extends BaseScraper {

    @Autowired
    EbuyerScraper(BrandDAO brandDAO, LaptopDAO laptopDAO, SpecificationDAO specificationDAO, LaptopSpecificationDAO laptopSpecificationDAO, SourceDAO sourceDAO) {
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
        while(isRunning) {
            WebDriver driver = getDriver();

            // Navigate to the webpage
            driver.navigate().to(UrlConst.EBUYER_BASE_URL + "/store/Computer/cat/Laptops/subcat/Gaming-Laptops");

            // Accept cookies
            driverWait(driver, Duration.ofSeconds(5)).until(d -> d.findElement(By.cssSelector("button.cookie-monster__cta.cookie-monster__cta--primary.js-cookie-monster-accept"))).click();

            // Get the products number
            int itemNumbers = Integer.parseInt(driver.findElement(By.cssSelector("strong.showing-count")).getAttribute("innerText").split(" ")[5]);

            // Loop through all the pages in the website
            for (int page = 1; page <= Math.ceil(itemNumbers / 24) + 1; page++) {

                // Navigate page by page
                driver.navigate().to(UrlConst.EBUYER_BASE_URL + "/store/Computer/cat/Laptops/subcat/Gaming-Laptops?page=" + page);

                // Scroll to the bottom to load every details
                driverScroll(driver, 5, 500);

                // Get all the product containers
                List<WebElement> productElementList = driver.findElement(By.id("grid-view")).findElements(By.cssSelector(".grid-item"));

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
        String title = element.findElement(By.cssSelector("a[data-event-label='Title']")).getAttribute("innerText");
        String brand = title;
        String price = element.findElement(By.cssSelector("p.price")).getAttribute("innerText").split(" ")[1];
        String imageUrl = driverWait(driver, Duration.ofSeconds(5)).until(d -> d.findElement(By.cssSelector("a[data-event-label='Image'] img"))).getAttribute("src");
        String url = element.findElement(By.cssSelector("a[data-event-label='Title']")).getAttribute("href");

        List<String> specArr = Util.getElementsValueByAttr(element.findElements(By.cssSelector("ul.grid-item__ksp li")), "innerText");
        String specString = specArr.toString();

        // Get product details by regex
        String processor = Util.getStringByRegex(specString, RegexConst.PROCESSOR_PATTERN);
        String graphicsCard = Util.getStringByRegex(specString, RegexConst.GRAPHICS_CARD_PATTERN);
        String ram = Util.getStringByRegex(specString, RegexConst.RAM_PATTERN);
        String ssd;
        String hdd;
        // Split hdd and ssd if the computer has two types of storage
        Boolean hasHdd = specString.contains("HDD");
        if (hasHdd) {
            String[] storageArr = specString.split("\\+");
            ssd = Util.getStringByRegex(storageArr[1], RegexConst.SSD_PATTERN);
            hdd = Util.getStringByRegex(storageArr[2], RegexConst.HDD_PATTERN);
        } else {
            ssd = Util.getStringByRegex(specString, RegexConst.SSD_PATTERN);
            hdd = "";
        }
        String screenSize = Util.getStringByRegex(specString, RegexConst.SCREEN_SIZE);

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
        result.put(ScraperConst.WEBSITE, "Ebuyer");
        result.put(ScraperConst.URL, url);
        result.put(ScraperConst.PRICE, price);

        return result;
    }
}
