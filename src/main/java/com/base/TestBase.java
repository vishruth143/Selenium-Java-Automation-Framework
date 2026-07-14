package com.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.decorators.WebDriverDecorator;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.util.TestUtil;
import com.util.WebDriverEventLogger;

public class TestBase {	
	
	public static WebDriver driver;
	public static Properties prop;
	public static Logger log;
	public static ExtentSparkReporter sparkReporter;
	public static ExtentReports extent;
	public static ExtentTest test;
	public static Environment activeEnvironment;

	private static volatile boolean configLogged = false;
	private static volatile boolean screenshotsCleared = false;

	public TestBase() {
		log = Logger.getLogger(TestBase.class);
		try {
			// Resolve active application from -Dapp system property (defaults to pta)
			String appName = System.getProperty("app", "pta");

			// Resolve active environment from -Denv system property (defaults to QA)
			String envValue = System.getProperty("env");
			activeEnvironment = Environment.fromString(envValue);

			String configFileName = "config." + activeEnvironment.getKey() + ".properties";
			String configFilePath = System.getProperty("user.dir")
					+ "/src/main/java/com/" + appName + "/config/" + configFileName;

			// Log config details only once across all test class instantiations
			if (!configLogged) {
				configLogged = true;
				log.info("=".repeat(100));
				log.info("  Active application  : " + appName);
				log.info("  Active environment  : " + activeEnvironment.name());
				log.info("  Loading config file : " + configFilePath);
				log.info("=".repeat(100));
			}

			prop = new Properties();
			FileInputStream ip = new FileInputStream(configFilePath);
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@BeforeClass
	public void logTestClassStart() {
		Logger classLog = Logger.getLogger(this.getClass());
		classLog.info("");
		classLog.info(">>>>>>>>>> [TEST CLASS START] " + this.getClass().getSimpleName() + " <<<<<<<<<<");
	}

	@AfterClass
	public void logTestClassEnd() {
		Logger classLog = Logger.getLogger(this.getClass());
		classLog.info("<<<<<<<<<< [TEST CLASS END]   " + this.getClass().getSimpleName() + " >>>>>>>>>>");
		classLog.info("");
	}
	
	public static void initialization() throws MalformedURLException {
		//String browser = prop.getProperty("browser");
		String browser = System.getProperty("browser", "chrome");
		WebDriver baseDriver = null;
		DesiredCapabilities cap = new DesiredCapabilities();
		
		if(browser.equals("chrome")) {			
			cap.setBrowserName("chrome");
			cap.setPlatform(Platform.LINUX);
			log.info("--- [SETUP] Launching Chrome (remote: localhost:4441) ---");
			baseDriver = new RemoteWebDriver(URI.create("http://localhost:4441/wd/hub").toURL(), cap);
		} else if(browser.equals("firefox")) {
			cap.setBrowserName("firefox");
			cap.setPlatform(Platform.LINUX);
			log.info("--- [SETUP] Launching Firefox (remote: localhost:4442) ---");
			baseDriver = new RemoteWebDriver(URI.create("http://localhost:4442/wd/hub").toURL(), cap);
		} else if(browser.equals("edge")) {
			cap.setBrowserName("MicrosoftEdge");
			cap.setPlatform(Platform.LINUX);
			log.info("--- [SETUP] Launching Edge (remote: localhost:4443) ---");
			baseDriver = new RemoteWebDriver(URI.create("http://localhost:4443/wd/hub").toURL(), cap);
		}
		
		// Add the WebDriver listener
		WebDriverDecorator<WebDriver> decorator = new EventFiringDecorator<>(new WebDriverEventLogger());
		driver = decorator.decorate(baseDriver);
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtil.IMPLICIT_WAIT));
		
		driver.get(prop.getProperty("url"));
	}
	
	public static void initializeReport() {
		// ── Clear screenshot folders once per JVM run ─────────────────────────────
		if (!screenshotsCleared) {
			screenshotsCleared = true;
			String userDir = System.getProperty("user.dir");
			String fileSeparator = System.getProperty("file.separator");

			File[] screenshotDirs = {
				new File(userDir + fileSeparator + "screenshots"),
				new File(userDir + fileSeparator + "Reports" + fileSeparator + "screenshots")
			};
			for (File dir : screenshotDirs) {
				try {
					if (dir.exists()) {
						FileUtils.cleanDirectory(dir);
						log.info("--- Cleared screenshots folder: " + dir.getPath() + " ---");
					} else {
						dir.mkdirs();
						log.info("--- Created screenshots folder: " + dir.getPath() + " ---");
					}
				} catch (IOException e) {
					log.warn("Could not clear screenshots folder: " + dir.getPath() + " – " + e.getMessage());
				}
			}
		}

		// ── Initialise Extent report ──────────────────────────────────────────────
		sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir")+"/reports/extentSparkReport.html");
		sparkReporter.config().setDocumentTitle("PTA Automation Report");
		sparkReporter.config().setReportName(
				"PTA Automation Test Execution Report [ENV: " + activeEnvironment.name() + "]");
		sparkReporter.config().setTheme(Theme.STANDARD);
		sparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
	}
	
	public static String captureScrenshot(ITestResult result) throws IOException {
		String fileSeparator = System.getProperty("file.separator");
		String extentReportPath = "." + fileSeparator + "Reports";
		String screenshotPath = extentReportPath + fileSeparator + "screenshots";

		// Ensure directory exists
		File screenshotDir = new File(screenshotPath);
		if (!screenshotDir.exists()) {
			screenshotDir.mkdirs();
		}

		// Get class name and method name
		String className = result.getTestClass().getRealClass().getSimpleName();
		String methodName = result.getMethod().getMethodName();
		String screenshotName = className + "_" + methodName + ".png";

		// Take screenshot
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String fullPath = screenshotPath + fileSeparator + screenshotName;
		FileUtils.copyFile(srcFile, new File(fullPath));

		// Return relative path for ExtentReports
		return "." + fileSeparator + "screenshots" + fileSeparator + screenshotName;
	}
}


