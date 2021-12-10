package com.tattzetey.webscraper.scraper;

import com.tattzetey.webscraper.constant.ScraperConst;
import com.tattzetey.webscraper.dao.*;
import com.tattzetey.webscraper.lib.Format;
import com.tattzetey.webscraper.model.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

/**
 * This abstract class is the
 * based class for all the
 * scrapers. It has inherited
 * the thread class
 * */
public abstract class BaseScraper extends Thread {
    protected BrandDAO brandDAO;
    protected LaptopDAO laptopDAO;
    protected SpecificationDAO specificationDAO;
    protected LaptopSpecificationDAO laptopSpecificationDAO;
    protected SourceDAO sourceDAO;
    protected WebDriver driver;
    protected Boolean isRunning;

    protected WebDriver getDriver() {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");

        // driver options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        options.addArguments("window-size=1920,1080");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.93 Safari/537.36");
        driver = new ChromeDriver(options);

        return driver;
    }

    /**
     * This function is a wrapper
     * for selenium driver javascript
     * executor to perform click event
     * */
    protected void driverClick(WebDriver driver, WebElement clickableElement) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", clickableElement);
    }

    /**
     * This function is a wrapper
     * for selenium driver javascript
     * executor to perform scroll event
     * */
    protected void driverScroll(WebDriver driver, int count, int distance) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        for (int k = 1; k <= count; k++) {
            js.executeScript("window.scrollBy(0," + k * distance + ")");
            try {
                sleep(500);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * This function is a wrapper
     * for selenium driver to
     * perform wait event
     * */
    protected WebDriverWait driverWait(WebDriver driver, Duration duration) {
        return new WebDriverWait(driver, duration);
    }

    /**
     * This function will create the product
     * objects (brand, laptop, specification, laptop specification, source)
     * then save into the database
     * */
    protected void saveProductDetails(Map<String, String> product) {

        // Get data from product map
        // and format it, then assign it to
        // different variables
        String brand = Format.brand(product.get(ScraperConst.BRAND));
        String title = Format.title(product.get(ScraperConst.TITLE));
        String imageUrl = product.get(ScraperConst.IMAGE_URL);
        String processor = Format.processor(product.get(ScraperConst.PROCESSOR));
        String graphicsCard = Format.graphicsCard(product.get(ScraperConst.GRAPHICS_CARD));
        String ram = Format.ram(product.get(ScraperConst.RAM));
        String hdd = Format.hddOrSdd(product.get(ScraperConst.HDD));
        String ssd = Format.hddOrSdd(product.get(ScraperConst.SSD));
        String screenSize = Format.screenSize(product.get(ScraperConst.SCREEN_SIZE));
        String website = product.get(ScraperConst.WEBSITE);
        String url = product.get(ScraperConst.URL);
        String price = Format.price(product.get(ScraperConst.PRICE));

        // Setup objs and use setter to set
        // value to its fields
        Brand productBrand = new Brand();
        productBrand.setName(brand);

        Laptop laptop = new Laptop();
        laptop.setImageUrl(imageUrl);
        laptop.setName(title);
        laptop.setScreenSize(screenSize);

        Specification specification = new Specification();
        specification.setProcessor(processor);
        specification.setGraphicsCard(graphicsCard);
        specification.setRam(ram);
        specification.setSsd(ssd);
        specification.setHdd(hdd);

        Source source = new Source();
        source.setWebsite(website);
        source.setUrl(url);
        source.setPrice(Double.parseDouble(price));

        // Save all the objs into database
        saveData(productBrand, laptop, specification, source);
    }

    /**
     * This function is to save data into database
     * by passing objects as arguments
     * */
    private void saveData(Brand brand, Laptop laptop, Specification specification, Source source) {

        // Check if all the objects are valid
        // Stop the function if not valid
        if (!brand.validate() || !laptop.validate() || !specification.validate() || !source.validate()) {
            return;
        }

        try {
            Brand currBrand = brandDAO.saveBrand(brand);
            laptop.setBrand(currBrand);
            Laptop currLaptop = laptopDAO.saveLaptop(laptop);
            Specification currSpecification = specificationDAO.saveSpecification(specification);

            LaptopSpecification laptopSpecification = new LaptopSpecification();
            laptopSpecification.setLaptop(currLaptop);
            laptopSpecification.setSpecification(currSpecification);
            LaptopSpecification currLaptopSpecification = laptopSpecificationDAO.saveLaptopSpecification(laptopSpecification);

            source.setLaptopSpecification(currLaptopSpecification);
            sourceDAO.saveSource(source);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    /**
     * This function start the thread
     * */
    public void startRunning() {
        isRunning = true;
        this.start();
    }

    /**
     * This function stop the thread
     * after the thread has done the
     * current task
     * */
    public void stopRunning() {
        isRunning = false;
    }
}
