package cross;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CrossBrowser {
	WebDriver driver = null;

	@BeforeMethod @Parameters(value = { "browser" })
	public void beforeMethod(String browser) {

		if (browser.equalsIgnoreCase("Chrome")) {
			System.out.println("CHROME NAVIGATED");
			//System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("Firefox")) {
			System.out.println("FIRE NAVIGATED");
			//System.setProperty("webdriver.firefox.driver", "geckodriver.exe");
			driver = new FirefoxDriver();
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

	}

	@Test
	public void TestPopUp() {

		driver.get("http://www.popuptest.com/");
		WebElement click = driver.findElement(By.linkText("Multi-PopUp Test #2"));
		click.click();

		// This will return the parent window name as a String
		String parent = driver.getWindowHandle();

		// This will return the number of windows opened by WebDriver
		Set<String> s1 = driver.getWindowHandles();

		// Now we will iterate using Iterator
		Iterator<String> I1 = s1.iterator();

		while (I1.hasNext()) {

			String child_window = I1.next();

			if (!parent.equals(child_window)) {
				driver.switchTo().window(child_window);
				System.out.println(driver.switchTo().window(child_window).getTitle());
			}
		}
		// once all pop up closed now switch to parent window
		driver.switchTo().window(parent);
	}

	@AfterMethod
	public void afterMethod() throws InterruptedException {
		driver.quit();
	}

}
