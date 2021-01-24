package redmine.utils;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import redmine.Property;
import redmine.managers.Manager;

import java.util.concurrent.TimeUnit;

public class BrowserUtils {
    public static  boolean isElementPresent(WebElement element){
        try {
           return element.isDisplayed();
        }catch (NoSuchElementException exception){
            return false;
        }
    }

    public static  boolean isElementCurrentlyPresent(WebElement element){
        try {
            Manager.driver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            boolean isDisplayed=element.isDisplayed();
            Manager.driver().manage().timeouts().implicitlyWait(Property.getIntegerProperty("ui.implicitly.wait"),TimeUnit.SECONDS);
            return isDisplayed;
        }catch (NoSuchElementException exception){
            return false;
        }finally{
            Manager.driver().manage().timeouts().implicitlyWait(Property.getIntegerProperty("ui.implicitly.wait"),TimeUnit.SECONDS);
        }
    }
}
