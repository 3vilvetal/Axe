package system.file;

import java.io.File;

public class DeleteFile 
{
	String fileName;
	File dir, child;
	
	public DeleteFile (String fileName)
	{
		this.fileName = fileName;
	}
	
	/**
	 * Deletes file
	 */
	public void delete()
	{
		File file = new File(fileName);
		file.delete();
	}
	
	/**
	 * Clears all files in the folder
	 */
	public void clearFolder()
	{
		dir = new File (fileName);
		if (dir.isDirectory()) 
		{
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) 
			{
				if (!children[i].equals("prefs.js"))
				{
					child = new File(fileName + "/" + children[i]);
					child.delete();
				}
			}
		}
	}
	
	/**
	 * Deletes folder with all files inside
	 * @param dir
	 * @return
	 */
	public static boolean deleteFolder(File dir) 
	{
		if (dir.isDirectory()) 
		{
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) 
			{
				boolean success = deleteFolder(new File(dir, children[i]));
				if (!success) 
				{
					return false;
				}
			}
		}
		// The directory is now empty so delete it
		return dir.delete();
	}
}
