package system.ant;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

public class Ant 
{
	private static String BUILD_FILE = "build.xml",
							  TARGET = "allTests";
	
	/**
	 * Runs targets from build XML file
	 * @param args
	 */
	public static void main(String args[])
	{
		File buildFile = new File(BUILD_FILE);
		Project project = new Project();
		project.setUserProperty("ant.file", buildFile.getAbsolutePath());		
		
		DefaultLogger consoleLogger = new DefaultLogger();
		consoleLogger.setErrorPrintStream(System.err);
		consoleLogger.setOutputPrintStream(System.out);
		consoleLogger.setMessageOutputLevel(Project.MSG_INFO);
		
		project.addBuildListener(consoleLogger);
		
		try 
		{
			project.fireBuildStarted();
			project.init();
			
			ProjectHelper helper = ProjectHelper.getProjectHelper();
			project.addReference("ant.projectHelper", helper);
			helper.parse(project, buildFile);
			
			project.executeTarget(TARGET);
			project.fireBuildFinished(null);
		} 
		catch (BuildException e) 
		{
			project.fireBuildFinished(e);
		}
	}
}
