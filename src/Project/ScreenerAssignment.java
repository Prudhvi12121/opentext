package Project;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ScreenerAssignment {
	WebDriver driver;

	@BeforeMethod
	public void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://www.screener.in/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);

	}

	@Test
	public void verifyRoceValue() {
		// TODO Auto-generated method stub
		String input = "HCL Technologies";
		driver.findElement(By.xpath("(//div[@id='mobile-search']//following::input)[3]")).sendKeys(input);
		driver.findElement(By.xpath("//li[contains(text(),'" + input + "')]")).click();
		String roceValue = driver
				.findElement(By.xpath("//span[contains(text(),'ROCE')]//parent::li//span[@class='number']")).getText();
		double roceNumber = Double.parseDouble(roceValue);
		if (roceNumber > 20)
			Assert.assertTrue(true);
		else
			Assert.fail();
	}

	@Test
	public void verifyDividends() {
		WebElement screens = driver.findElement(By.xpath("//a[text()='Screens']"));
		screens.click();
		String userOption = "Banks";
		driver.findElement(By.xpath("//a[text()='" + userOption + "']")).click();

		boolean bool = true;
		System.out.println("Below are the banks which has dividend value greater than 1:");
		while (bool) {
			List<WebElement> dividends = driver
					.findElements(By.xpath("//div[@class='responsive-holder fill-card-width']//tr"));
			for (int i = 2; i < dividends.size() - 1; i++) {
				if (i == 17)
					continue;
				String divValue = "(//div[@class='responsive-holder fill-card-width']//tr[" + i + "])//td[6]";

				double divNum = Double.parseDouble(driver.findElement(By.xpath(divValue)).getText().trim());
				if (divNum > 1.00) {
					String bank = "(//div[@class='responsive-holder fill-card-width']//tr[" + i + "])//td[2]";
					String bankName = driver.findElement(By.xpath(bank)).getText().trim();
					Reporter.log(bankName);
				}
			}
			if (driver.findElements(By.xpath("//*[@class='icon-right']//parent::a")).size() > 0)
				driver.findElement(By.xpath("//*[@class='icon-right']//parent::a")).click();
			else
				break;
		}
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();

	}

}
