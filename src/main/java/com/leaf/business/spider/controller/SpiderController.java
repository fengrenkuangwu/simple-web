package com.leaf.business.spider.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import net.sf.json.JSONObject;

@Controller
public class SpiderController {

private final Logger log = Logger.getLogger(this.getClass());
	
	@RequestMapping(value = "/spider")
	public void adminLogin(HttpServletRequest request,HttpServletResponse response){
//		JSONObject json = new JSONObject();
//		String crawlStorageFolder = "e://git";
//        int numberOfCrawlers = 7;
//
//        CrawlConfig config = new CrawlConfig();
//        config.setCrawlStorageFolder(crawlStorageFolder);
//
//        /*
//         * Instantiate the controller for this crawl.
//         */
//        PageFetcher pageFetcher = new PageFetcher(config);
//        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
//        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
//        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
//
//        /*
//         * For each crawl, you need to add some seed urls. These are the first
//         * URLs that are fetched and then the crawler starts following links
//         * which are found in these pages
//         */
//        controller.addSeed("http://www.ics.uci.edu/~lopes/");
//        controller.addSeed("http://www.ics.uci.edu/~welling/");
//    	controller.addSeed("http://www.ics.uci.edu/");
//
//        /*
//         * Start the crawl. This is a blocking operation, meaning that your code
//         * will reach the line after this only when crawling is finished.
//         */
//        controller.start(MyCrawler.class, numberOfCrawlers);
	}
}
