package com.crm.qa.TestCases;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SeleniumExecutionLogger {

    private WebDriver driver;
    private PDDocument document;
    private PDPage page;
    private PDPageContentStream contentStream;
    private int stepCounter;

    @BeforeClass
    public void setupClass() {
        // Initialize the WebDriver instance (e.g., ChromeDriver, FirefoxDriver)
    	WebDriverManager.chromedriver().setup();
    	driver =new ChromeDriver();
    	driver.manage().window().maximize();
    }

    @BeforeMethod
    public void setupMethod() {
        // Create a new PDF document
        document = new PDDocument();
        page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        try {
			contentStream = new PDPageContentStream(document, page);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        stepCounter = 1;
    }

    @AfterMethod
    public void tearDownMethod(ITestResult result) throws IOException {
        // Save the PDF document
        contentStream.close();
        String filePath = "logs/execution_log_" + result.getMethod().getMethodName() + ".pdf";
        document.save(new File(filePath));
        document.close();

        // Quit the WebDriver instance
        driver.quit();
    }

    @Test
    public void testExample() throws IOException {
        driver.get("https://www.google.com/");

        

        // Log the step with a highlighted area in the screenshot
        logStep("Step 1: Open google.com", null);

        // Perform actions on the page
//        driver.findElement(By.id("APjFqb")).sendKeys("HEllo");
        WebElement element = driver.findElement(By.name("q"));
//        element.click();
        element.sendKeys("Sai");


        // Log the step with a highlighted area in the screenshot
        logStep("Step 2: Click on element", element);
    }




    private void logStep(String stepDescription, WebElement element) throws IOException {
        PDPage newPage = new PDPage(PDRectangle.A4);
        document.addPage(newPage);

        PDPageContentStream contentStream = new PDPageContentStream(document, newPage);

        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.setLeading(14.5f);

        // Capture the entire browser window screenshot
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        BufferedImage screenshot = ImageIO.read(screenshotFile);

        contentStream.beginText();
        contentStream.newLineAtOffset(40, 750);
        contentStream.showText("Step " + stepCounter + ": " + stepDescription);
        contentStream.newLine();
        contentStream.endText();

        if (element != null) {
            try {
                org.openqa.selenium.Point elementLocation = element.getLocation();
                int elementWidth = element.getSize().getWidth();
                int elementHeight = element.getSize().getHeight();

                // Highlight the element by drawing a red rectangle
                BufferedImage highlightedImage = highlightElement(screenshot, elementLocation.x, elementLocation.y, elementWidth, elementHeight);
                PDImageXObject highlightedXObject = LosslessFactory.createFromImage(document, highlightedImage);

                // Calculate the position to display the image below the step description
                float imageX = 40;
                float imageY = 750 - (2 * 14.5f)-300; // Adjust the value for reduced spacing

                contentStream.drawImage(highlightedXObject, imageX, imageY, 500, 300);
            } catch (WebDriverException e) {
                // Failed to capture element screenshot
                e.printStackTrace();
            }
        } else {
            PDImageXObject image = LosslessFactory.createFromImage(document, screenshot);
            contentStream.drawImage(image, 40, 700, 500, 300);
        }

        contentStream.close();
        stepCounter++;
    }



    private BufferedImage highlightElement(BufferedImage screenshot, int x, int y, int width, int height) {
        BufferedImage highlightedImage = new BufferedImage(screenshot.getWidth(), screenshot.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = highlightedImage.createGraphics();
        graphics.drawImage(screenshot, 0, 0, null);

        // Highlight the element by drawing a red rectangle
        graphics.setColor(Color.RED);
        graphics.setStroke(new BasicStroke(3));
        graphics.drawRect(x, y, width, height);

        graphics.dispose();
        return highlightedImage;
    }




}
