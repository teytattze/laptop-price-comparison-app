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
 * This is scraper class for Argos website
 * */
@Component
public class ArgosScraper extends BaseScraper {

    @Autowired
    ArgosScraper(BrandDAO brandDAO, LaptopDAO laptopDAO, SpecificationDAO specificationDAO, LaptopSpecificationDAO laptopSpecificationDAO, SourceDAO sourceDAO) {
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
            driver.navigate().to(UrlConst.ARGOS_BASE_URL + "/browse/technology/laptops-and-pcs/gaming-laptops/c:30279/?clickOrigin=header:search:menu:gaming+laptops");

            // Accept cookies
            driverWait(driver, Duration.ofSeconds(5)).until(d -> d.findElement(By.id("consent_prompt_submit"))).click();

            // Slow down the process to prevent block
            try {
                sleep(2000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // Get all the product links
            List<String> hrefList = new ArrayList<String>(Util.getElementsValueByAttr(driver.findElements(By.cssSelector("a[data-test=component-product-card-title]")), "href"));

            // Loop through all the product links
            for (String href : hrefList) {

                // Navigate to product details page
                driver.navigate().to(href);

                // Get the product details and
                // save it to database
                try {
                    WebElement element = driver.findElement(By.cssSelector("main.pdp-main"));
                    Map<String, String> productDetails = getProductDetails(driver, element);
                    saveProductDetails(productDetails);
                } catch (Exception ex) {
                    System.out.println(ex);
                }

                try {
                    sleep(1000);
                } catch (Exception ex) {
                    ex.printStackTrace();
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
        String title = element.findElement(By.cssSelector("span[data-test='product-title']")).getAttribute("innerText");
        String brand = title;
        String price = element.findElement(By.cssSelector("li[data-test='product-price-primary'] h2")).getAttribute("innerText");
        String imageUrl = driverWait(driver, Duration.ofSeconds(5)).until(d -> d.findElement(By.cssSelector("div[data-test='component-media-gallery_activeSlide-0'] img"))).getAttribute("src");
        String url = driver.getCurrentUrl();

        List<String> specArr = Util.getElementsValueByAttr(element.findElements(By.cssSelector("div[itemprop='description'] li")), "innerText");
        String specString = specArr.toString();

        // Get product details by regex
        String processor = Util.getStringByRegex(specString, RegexConst.PROCESSOR_PATTERN);
        String graphicsCard = Util.getStringByRegex(specString, RegexConst.GRAPHICS_CARD_PATTERN);
        String ram = Util.getStringByRegex(specString, RegexConst.RAM_PATTERN);
        String ssd = Util.getStringByRegex(specString, RegexConst.SSD_PATTERN);
        String hdd = "";
        // Split hdd and ssd if the computer has two types of storage
        Boolean hasHdd = specString.contains("HDD");
        if (hasHdd) {
            hdd = Util.getStringByRegex(specString, RegexConst.HDD_PATTERN);
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
        result.put(ScraperConst.WEBSITE, "Argos");
        result.put(ScraperConst.URL, url);
        result.put(ScraperConst.PRICE, price);

        return result;
    }
}
