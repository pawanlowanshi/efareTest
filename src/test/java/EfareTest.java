import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import com.omniwyse.selenium.data.DataManager;

public class EfareTest {

	@Test
	public void efareTest(XmlTest config) throws Exception {
		DataManager.config = config;
		Properties properties = new Properties();
		properties.load(new FileInputStream(new File("config.properties")));
		String repPath = properties.getProperty("rootPath");
		DataManager.currentDate = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
		DataManager.timestamp = new SimpleDateFormat("HH_mm_ss").format(Calendar.getInstance().getTime());
		System.out.println(DataManager.currentDate);
		new DataManager().executeTestPlan(repPath.replace('\\', '/'), DataManager.currentDate);
		System.out.println("Input(TestPlan) file reading is done..");
	}
}
