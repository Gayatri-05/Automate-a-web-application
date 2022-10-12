package com.test;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Parameters;

import static org.testng.Assert.assertEquals;

import java.awt.Toolkit;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;

public class Website {
	WebDriver driver;
	
  @Test
  public void navigate() {
	  System.setProperty("webdriver.chrome.driver", "C:\\Users\\KOPPURAVURI\\Desktop\\Java EE\\Phase-5 workspace\\chromedriver.exe");
	  driver = new ChromeDriver();
	  driver.get("https://www.flipkart.com/");
	  driver.manage().window().maximize();
	  driver.manage().timeouts().implicitlyWait(Duration.ofMinutes(2));
  }
  
  @Test
  public void pageLoadTime() {
	  Long loadtime = (Long)((JavascriptExecutor)driver).executeScript(
  		    "return performance.timing.loadEventEnd - performance.timing.navigationStart;");
    System.out.println("Page load time in milliseconds is :"+loadtime);
  }
 
  @Test
  public void search() {
	  driver.findElement(By.className("_2doB4z")).click();
	  driver.findElement(By.name("q")).sendKeys("iPhone13" + Keys.ENTER);
	  Actions actions = new Actions(driver);
	  Action sendPageDown = actions.sendKeys(Keys.PAGE_DOWN).build();
	  sendPageDown.perform();
	  
	  try {
		  Thread.sleep(3000);
	  }catch(InterruptedException e) {
		  e.printStackTrace();
	  }
  }

  @Test(dependsOnMethods = "navigate")
  public void LoadingImages() {
	  driver.manage().window().maximize();
	  //Thread.sleep(3000);
		WebElement i = driver.findElement(By.xpath(
				"//*[@id=\"container\"]/div/div[3]/div[1]/div[2]/div[4]/div/div/div/a/div[1]/div[1]/div/div/img"));
		// Javascript executor to check image
		Boolean p = (Boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].complete "
				+ "&& typeof arguments[0].naturalWidth != \"undefined\" " + "&& arguments[0].naturalWidth > 0", i);

		// verify if status is true
		if (p) {
			System.out.println("Images loaded");
		} else {
			System.out.println("Images not loaded");
		}
  }
  
  @Test
  public void scroll() {
	  JavascriptExecutor js = (JavascriptExecutor) driver;
	  Long v = (Long) js.executeScript("return window.pageYOffset;");
      System.out.println("Scroll position after launch: " + v);
      js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
      JavascriptExecutor j = (JavascriptExecutor) driver;
      Double d = (Double) j.executeScript("return window.pageYOffset;");
      System.out.println("Scroll position after scrolling upto an element: "+d);
  }
  
  @Test
  public void refresh() {
	  driver.navigate().refresh();
	  Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
			  .withTimeout(Duration.ofSeconds(30))
			  .pollingEvery(Duration.ofSeconds(5))
			  .ignoring(NoSuchElementException.class);
	  driver.manage().timeouts().implicitlyWait(Duration.ofMinutes(2));
	  System.out.println(wait);
  }
  
  @Test(dependsOnMethods = "navigate")
  public void imageDownload() {
	  driver.manage().window().maximize();
	  WebElement i = driver.findElement(By.xpath(
				"//*[@id=\"container\"]/div/div[3]/div[1]/div[2]/div[4]/div/div/div/a/div[1]/div[1]/div/div/img"));
		// Javascript executor to check image
		Boolean p = (Boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].complete "
				+ "&& typeof arguments[0].naturalWidth != \"undefined\" " + "&& arguments[0].naturalWidth > 0", i);

		// verify if status is true
		if (p) {
			System.out.println("Images can be downloaded just before scrolling to its position");
		} else {
			System.out.println("Images not downloaded");
		}
  }
  
  @Test(dependsOnMethods = "navigate")
  public void NavigateToBottom() {
	  driver.manage().window().maximize();
	  JavascriptExecutor js = (JavascriptExecutor) driver;
      js.executeScript("window.scrollBy(0, document.body.scrollHeight)");
  }
  
  @Test
  public void CheckResolution() {
	  int Resolutionchrome = Toolkit.getDefaultToolkit().getScreenResolution();
      System.out.println("Screen resolution is "+Resolutionchrome);
      assertEquals(120, Resolutionchrome);
      
  }
  
  @Ignore
  @Test(groups="Firefox")
  public void LaunchFirefox() {
      System.setProperty("webdriver.gecko.driver", "C:\\Users\\KOPPURAVURI\\Desktop\\Java EE\\Phase-5 workspace\\geckodriver.exe");
      driver = new FirefoxDriver();
      driver.get("https://www.flipkart.com/");
      try {
          Thread.sleep(4000);
      } catch (Exception e) {
          e.printStackTrace();
      }
      int Resolutionfirefox = Toolkit.getDefaultToolkit().getScreenResolution();
      System.out.println("Screen resolution is "+Resolutionfirefox);
      assertEquals(120, Resolutionfirefox);
      
  }

}
