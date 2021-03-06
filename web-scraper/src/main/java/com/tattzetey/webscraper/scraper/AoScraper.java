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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is scraper class for Ao website
 * */
@Component
public class AoScraper extends BaseScraper {

    @Autowired
    AoScraper(BrandDAO brandDAO, LaptopDAO laptopDAO, SpecificationDAO specificationDAO, LaptopSpecificationDAO laptopSpecificationDAO, SourceDAO sourceDAO) {
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
            driver.navigate().to(UrlConst.AO_BASE_URL + "/l/laptops-gaming/1-150/250-251/");

            // Accept cookies
            WebElement cookieBtn = driverWait(driver, Duration.ofSeconds(5)).until(d -> d.findElement(By.id("acceptMessage")));
            driverClick(driver, cookieBtn);

            // Get the products number
            int itemNumbers = Integer.parseInt(driver.findElement(By.cssSelector("span[itemprop='numberOfItems']")).getAttribute("innerText"));

            // Loop through all the pages in the website
            for (int page = 0; page <= Math.ceil(itemNumbers / 12); page++) {

                // Navigate page by page
                driver.navigate().to(UrlConst.AO_BASE_URL + "/l/laptops-gaming/1-150/250-251/?page=" + page * 12);

                // Scroll to the bottom to load every details
                driverScroll(driver, 12, 350);

                // Get all the product links
                List<String> hrefList = Util.getElementsValueByAttr(driver.findElements(By.cssSelector("a[data-testid='product-card-link']")), "href");

                // Loop through all the product links
                for (String href : hrefList) {

                    // Navigate to product details page
                    driver.navigate().to(href);

                    // Get the product details and
                    // save it to database
                    try {
                        WebElement mainElement = driverWait(driver, Duration.ofSeconds(5)).until(d -> d.findElement(By.cssSelector("main[data-main-container]")));
                        Map<String, String> productDetails = getProductDetails(driver, mainElement);
                        saveProductDetails(productDetails);
                    } catch (Exception ex) {
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
        String title = element.findElement(By.id("pageTitle")).getAttribute("innerText");
        String brand = title;
        String price = element.findElement(By.cssSelector("span[itemprop='price']")).getAttribute("innerText");
        String imageUrl = driverWait(driver, Duration.ofSeconds(5)).until(d -> d.findElement(By.cssSelector("img[data-testid='carousel-centre-image'].product-gallery-image"))).getAttribute("src");
        String url = driver.getCurrentUrl();

        List<WebElement> elementArr = element.findElements(By.cssSelector("div[data-tag-section='key features'] li"));
        List<String> specArr = new ArrayList<String>();

        for (int i = 0; i < elementArr.size(); i++) {
            if (i == 0) {
                if (!elementArr.get(i).findElements(By.cssSelector("a")).isEmpty()) {
                    specArr.add(elementArr.get(i).findElement(By.cssSelector("a")).getAttribute("innerText"));
                } else {
                    specArr.add(elementArr.get(i).getAttribute("innerText"));
                }
                continue;
            }
            specArr.add(elementArr.get(i).getAttribute("innerText"));
        }
        String specString = specArr.toString();

        // Get product details by regex
        String processor = Util.getStringByRegex(specString, RegexConst.PROCESSOR_PATTERN);
        String graphicsCard = Util.getStringByRegex(specString, RegexConst.GRAPHICS_CARD_PATTERN);
        String ram = Util.getStringByRegex(element.findElement(By.cssSelector("span[data-tag-name='ram']")).getAttribute("innerText"), RegexConst.RAM_PATTERN);
        String ssd;
        String hdd;
        // Split hdd and ssd if the computer has two types of storage
        Boolean hasHdd = specString.contains("hard drive");
        if (hasHdd) {
            String[] storageArr = specString.split("hard drive");
            ssd = Util.getStringByRegex(storageArr[1], RegexConst.SSD_PATTERN);
            hdd = Util.getStringByRegex(storageArr[0], RegexConst.HDD_PATTERN);
        } else {
            ssd = Util.getStringByRegex(specString, RegexConst.SSD_PATTERN);
            hdd = "";
        }
        String screenSize = Util.getStringByRegex(title, RegexConst.SCREEN_SIZE);

        System.out.println(processor);
        System.out.println(graphicsCard);
        System.out.println(ram);
        System.out.println(ssd);
        System.out.println(screenSize);

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
        result.put(ScraperConst.WEBSITE, "Ao");
        result.put(ScraperConst.URL, url);
        result.put(ScraperConst.PRICE, price);

        return result;
    }
}
