import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.ElementOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class ComparePrice extends CreateDriver {
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
        driver.findElementsByXPath("//android.widget.TextView[@text='ADD TO CART']").get(0).click();
        //Click on cart button
        driver.findElementById("com.androidsample.generalstore:id/appbar_btn_cart").click();
        Thread.sleep(5000);

        int itemscount = driver.findElementsById("com.androidsample.generalstore:id/productPrice").size();
        double sum = 0;
        for (int i = 0; i < itemscount; i++) {
            String ItemPrice = driver.findElementsById("com.androidsample.generalstore:id/productPrice").get(i).getText();
            sum = sum + getAmount(ItemPrice);
            System.out.println(sum);
        }

        String total = driver.findElementById("com.androidsample.generalstore:id/totalAmountLbl").getText();
        double totalAmount = getAmount(total);
        System.out.println("Total Amount " + totalAmount);
        Assert.assertEquals(sum, totalAmount);

        //Tap
        WebElement checkbox = driver.findElementByClassName("android.widget.CheckBox");
        TouchAction touchAction = new TouchAction(driver);
        touchAction.tap(TapOptions.tapOptions().withElement(ElementOption.element(checkbox))).perform();

        //LongPress
        WebElement termsAndConditions = driver.findElementByXPath("//*[@text=\"Please read our terms of conditions\"]");
        touchAction.longPress(LongPressOptions.longPressOptions().withElement(ElementOption.element(termsAndConditions)).withDuration(Duration.ofSeconds(2))).perform();

        WebElement closeButton = driver.findElementByXPath("//*[@text=\"CLOSE\"]");
        touchAction.tap(TapOptions.tapOptions().withElement(ElementOption.element(closeButton))).perform();

        WebElement webviewButton = driver.findElementByXPath("//android.widget.Button[@text='Visit to the website to complete purchase']");
        webviewButton.click();
    }

    public static Double getAmount(String value) {
        value = value.substring(1);
        Double amount = Double.parseDouble(value);
        return amount;
    }


}
