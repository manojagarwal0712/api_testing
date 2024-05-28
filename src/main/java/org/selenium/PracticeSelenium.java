package org.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class PracticeSelenium {
    @Test
    public void testWebElements(){



        //System.setProperty("webdriver.gecko.driver", "/Users/mankumm/Downloads/geckodriver");
        //System.setProperty("webdriver.gecko.driver", "/Users/mankumm/Downloads/geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("")));

        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(20))
                        .pollingEvery(Duration.ofSeconds(5))
                                .ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("")));


        driver.get("https://www.google.com");
        System.out.println("Title: " + driver.getTitle());
        driver.navigate().to("https://www.google.com");
        driver.manage().window().maximize();
        driver.close();;
        driver.quit();

       WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10)).
                until(ExpectedConditions.presenceOfElementLocated(By.xpath("")));

    }

    public void findALlBrokenLink(){
        WebDriver driver = new FirefoxDriver();
        driver.get("https://the-internet.herokuapp.com/checkboxes");
        List<WebElement> list = driver.findElements(By.tagName("a"));
        for (WebElement element:
             list) {
            String url = element.getAttribute("href");
            if (verifyLink(url)){
                System.out.println(url);
            }
        }
    }
    public boolean verifyLink(String link)  {
        URL url = null;
        try {
            url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.connect();
            if (connection.getResponseCode()>=400){
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }


    /**
     * Launch a new Chrome browser.
     * Open Shop.DemoQA.com
     * Get Page Title name and Title length
     * Print Page Title and Title length on the Eclipse Console.
     * Get Page URL and verify if it is a correct page opened
     * Get Page Source (HTML Source code) and Page Source length
     * Print Page Length on Eclipse Console.
     * Close the Browser.
     */

    @Test
    public void testPracticeExercise(){
        WebDriver driver = new FirefoxDriver();
        driver.get("https://the-internet.herokuapp.com/checkboxes");
        List<WebElement> checkBoxList = driver.findElements(By.xpath("(//form/input)"));
        for (WebElement element:
                checkBoxList) {
            if (!element.isSelected()){
                element.click();
            }

        }
        //Implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        //explicit wait - Webdriverwait
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10)).
                until(ExpectedConditions.presenceOfElementLocated(By.xpath("")));

        //fluent wait
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);


        //
        Set<String> windows = driver.getWindowHandles();
        String windowHandle = driver.getWindowHandle();
        driver.switchTo().window(windowHandle);

        Actions actions = new Actions(driver);
        WebElement element1 = driver.findElement(By.xpath("xpathddfdf"));
        actions.moveToElement(element1).click().build().perform();


    }

}
