import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.net.MalformedURLException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class HandlingWebView extends CreateDriver {
    public static void main(String[] args) throws MalformedURLException, InterruptedException {

        AndroidDriver<AndroidElement> driver = capabilities();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.findElementById("com.androidsample.generalstore:id/spinnerCountry").click();
        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Argentina\"));");
        //   driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textMatches(\"" + containedText + "\").instance(0))"));
        driver.findElement(By.xpath("//*[@text='Argentina']")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Hello");
        driver.hideKeyboard();
        driver.findElement(By.xpath("//*[@text='Female']")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();

        //select first two item in the screen
        driver.findElementsByXPath("//android.widget.TextView[@text='ADD TO CART']").get(0).click();

        //Click on cart button
        driver.findElementById("com.androidsample.generalstore:id/appbar_btn_cart").click();
        Thread.sleep(5000);

        WebElement webviewButton = driver.findElementByXPath("//android.widget.Button[@text='Visit to the website to complete purchase']");
        webviewButton.click();
        Thread.sleep(7000);

        //get the context of the app
        Set<String> contextlist = driver.getContextHandles();
        for (String contexName: contextlist){
            System.out.println(contexName);
        }

        //switching to web view
        driver.context("WEBVIEW_com.androidsample.generalstore");
        //afte switching to webview, it only worked with xpath on using other it dint worked
        driver.findElement(By.xpath("//*[@name='q']")).sendKeys("Hello");

        //send key to hit enter
        driver.findElementByXPath("//*[@name='q']").sendKeys(Keys.ENTER);

        //send kye to hit back button
        driver.pressKey(new KeyEvent(AndroidKey.BACK));

        //switch back to native app
        driver.context("NATIVE_APP");

    }
}
