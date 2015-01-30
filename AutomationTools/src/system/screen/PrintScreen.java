package system.screen;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PrintScreen
{
	Rectangle screenRectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());  
	Robot myRobot;  
	BufferedImage screenImage; 
	 
	/**
	 * Gets print from current screen and saves it in .jpg file
	 * @param fileName
	 * @throws AWTException
	 * @throws IOException
	 */
	public void getPrintScreen(String fileName) 
	{  
		Toolkit tool = Toolkit.getDefaultToolkit();
		Robot robot;
		try 
		{
			robot = new Robot();
			File f = new File(fileName + ".jpg");
			BufferedImage img = robot.createScreenCapture(screenRectangle);
			ImageIO.write(img, "jpeg", f);
			tool.beep();
		} 
		catch (AWTException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		 
	} 
	
	/**
	 * Saves a lot of screens in folder with some delay
	 * @param filesPath
	 * @param periods
	 * @param delay
	 * @throws AWTException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void getPrintScreensPeriodically(String filesPath, int periods, int delay)
	{
		for (int i = 0; i < periods; i++)
		{
			getPrintScreen(filesPath);
			try 
			{
				Thread.sleep(delay);
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
}
