package hometests.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MyFirstTest {
    private WebDriver driver;

    @BeforeMethod
    public void prepareFixture(){
        System.setProperty("webdriver.chrome.driver","src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://edu-at.dfu.i-teco.ru/login");
    }

    @Test
    public void myFirstLoginTest() {
        WebElement  loginElement=driver.findElement(By.xpath("//input[@id='username']"));
        WebElement  passwordElement=driver.findElement(By.xpath("//input[@id='password']"));
        WebElement  submitElement=driver.findElement(By.xpath("//input[@id='login-submit']"));

        loginElement.sendKeys("admin");
        passwordElement.sendKeys("admin123");
        submitElement.click();

        WebElement loggedAs=driver.findElement(By.xpath("//div[@id='loggedas']"));
        Assert.assertEquals(loggedAs.getText(),"Вошли как admin");
    }
    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
}