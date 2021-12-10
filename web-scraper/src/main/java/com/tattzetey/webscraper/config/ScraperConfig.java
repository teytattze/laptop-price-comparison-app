package com.tattzetey.webscraper.config;

import com.tattzetey.webscraper.scraper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * This class is responsible for
 * initialize or stop all the scrapers
 * */
@Component
public class ScraperConfig {
    private final ArrayList<BaseScraper> scrapersList = new ArrayList<BaseScraper>();

    @Autowired
    ScraperConfig(ArgosScraper argosScraper, AoScraper aoScraper, BoxScraper boxScraper, CurrysScraper currysScraper, EbuyerScraper ebuyerScaper) {
        scrapersList.add(argosScraper);
        scrapersList.add(aoScraper);
        scrapersList.add(boxScraper); // This scraper get blocked often, change the user-agent to prevent block
        scrapersList.add(currysScraper);
        scrapersList.add(ebuyerScaper);
    }

    /**
     * Loop through the scraper array
     * then initialize each of them
     * */
    public void startAllScrapers() {
        for (BaseScraper scraper : scrapersList) {
            scraper.startRunning();
        }
    }

    /**
     * Loop through the scraper array
     * then stop each of the scraper
     * after the current task has been done
     * */
    public void stopAllScrapers() {
        for (BaseScraper scraper : scrapersList) {
            scraper.stopRunning();
        }
    }
}
