package com.blockz;

import java.io.*;
import java.util.*;
import android.os.Environment;
import android.util.Log;
import junit.framework.Assert;

/**
 * Class that handles the levels.
 */
public class LevelManager 
{
	/**
	 * Creates a save slot with the objects needed to create/load a save file.
	 */
	public static class SaveSlot
	{
		private int _id;
		private String _name;
		private int _currentScore;
		private int _lastClearedLevel;
		private LinkedList<Integer> _clearedLevels;
		
		/**
		 * Constructor for a save slot.
		 * @param id of the slot
		 * @param name of the slot
		 */
		public SaveSlot(int id, String name)
		{
			Assert.assertTrue("Invalid save slot!", id < 6 && id > 0);
			_id = id;
			_name = name;
			_currentScore = 0;
			_lastClearedLevel = 0;
			_clearedLevels = new LinkedList<Integer>();
		}

		/**
		 * @return The id.
		 */
		public int getId() 
		{
			return _id;
		}
		
		/**
		 * @return The name.
		 */
		public String getName()
		{
			return _name;
		}
		
		/**
		 * @return The score.
		 */
		public int getScore()
		{
			return _currentScore;
		}
		
		/**
		 * @return The last cleared level.
		 */
		public int getLastClearedLevel()
		{
			return _lastClearedLevel;
		}
		
		/**
		 * Sets the score.
		 * @param Score to be set.
		 */
		public void setScore(int score)
		{
			if (score > 0)
				_currentScore = score;
		}
		
		/**
		 * Sets the last cleared level.
		 * @param Level to be set.
		 */
		public void setLastClearedLevel(int level)
		{
			if (level > 0)
				_lastClearedLevel = level;
		}
		
		/**
		 * Adds the level to the cleared levels list.
		 * @param Level to add.
		 */
		public void addClearedLevel(int level)
		{
			_clearedLevels.add(level);
		}
	}
	
	private static final String FILENAME = "save.bz";
	private SaveSlot _saveSlot;
	private int _currentLevel;
	private static File _dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/blockz");
	
	/**
	 * Constructor for the LevelManager.
	 * @param saveSlot to be handled by the LevelManager.
	 */
	public LevelManager(SaveSlot saveSlot)
	{
		_saveSlot = saveSlot;
	}
	
	/**
	 * Saves progress to the SD card in the slot associated with the <code>LevelManager</code>.
	 */
	public void save()
	{	
		if (!_dir.exists())
			_dir.mkdirs();

		File saveFile = new File(_dir, FILENAME);
		String buffer = "";
		
		if (saveFile.exists())
		{
			int saveslot = 0;
			
			try
			{
				Scanner sc = new Scanner(saveFile);
				saveslot = sc.nextInt();
				buffer += saveslot;
				
				while(saveslot != _saveSlot.getId())
				{
					buffer += sc.nextLine(); // Newline and name
					buffer += sc.nextLine(); // Points
					buffer += sc.nextLine(); // Cleared levels
					buffer += sc.nextLine(); // Last cleared level
					
					buffer += saveslot = sc.nextInt(); // Read a new slot
				}
				
				// ID + Name on saveslot
				buffer += " " + _saveSlot.getName() + "\n";
				sc.nextLine();
				
				// Points
				buffer += _saveSlot.getScore() + "\n";
				sc.nextLine();
				
				// Cleared levels
				buffer += "dummydata" + "\n";
				sc.nextLine();
				
				// Last cleared level
				buffer += _saveSlot.getLastClearedLevel() + "\n";
				sc.nextLine();
				
				// Read rest of file
				while (sc.hasNext())
				{
					buffer += sc.nextLine() + "\n";
				}
				
				sc.close();
				
				// Write buffer to file
				Log.d("B_FILE", "File contents: \n\n" + buffer);
				
			}
			catch (FileNotFoundException fe)
			{
				Assert.assertTrue("File not found", false);
			}
			catch (InputMismatchException ime)
			{
				Log.e("B_INFO", "File corrupt. Writing clean file...");
				buffer = cleanFile();
			}
		}
		else
		{
			buffer = cleanFile();
			Log.d("B_FILE", "File contents: \n\n" + buffer);
		}
		
		try
		{
			FileWriter f = new FileWriter(saveFile);
			f.write(buffer);
			f.close();
		}
		catch (IOException ioe)
		{
			Assert.assertTrue("BAD LUCK... :)", false);
		}
			
	}
	
	/**
	 * Loads progress from the SD card from the save slot associated with the <code>LevelManager</code>.
	 */
	public void load()
	{
		File saveFile = new File(_dir, FILENAME);
		int saveslot = 0;
		
		try
		{
			Scanner sc = new Scanner(saveFile);
			saveslot = sc.nextInt();
			
			while(saveslot != _saveSlot.getId())
			{
				sc.nextLine(); // Newline and name
				sc.nextLine(); // Points
				sc.nextLine(); // Cleared levels
				sc.nextLine(); // Last cleared level
				
				saveslot = sc.nextInt(); // Read a new slot
			}
			
			sc.nextLine(); // Skip the name
			
			_saveSlot.setScore(sc.nextInt());
			sc.nextLine();
			
			// TODO: Fix level tree
			sc.nextLine();
			
			_saveSlot.setLastClearedLevel(sc.nextInt());
			
			sc.close();
		}
		catch (FileNotFoundException fnfe)
		{
			Log.d("B_INFO", "No file found... a new one will be created on save().");
		}
		catch (InputMismatchException ime)
		{
			Log.e("B_INFO", "File corrupt...");
		}
	}
	
	/**
	 * Get a list of save slots specified in the save file.
	 * 
	 * @return An array with five save slots. A save slot has the name "Empty" if it is empty.
	 */
	public static SaveSlot[] getSaveSlots()
	{
		SaveSlot[] slots = new SaveSlot[5];
		
		File saveFile = new File(_dir, FILENAME);
		
		try
		{
			Scanner sc = new Scanner(saveFile);
			int i = 0;
			
			while (sc.hasNext())
			{
				slots[i] = new SaveSlot(sc.nextInt(), sc.nextLine().substring(1)); // Remove whitespace
				
				slots[i].setScore(sc.nextInt());
				
				sc.nextLine();
				sc.nextLine(); // FIXME: Row with cleared levels
				
				slots[i].setLastClearedLevel(sc.nextInt());
				
				i++;
			}
		}
		catch (FileNotFoundException fnfe)
		{
			Log.d("B_INFO", "No file found... a new one will be created on save().");
		}
		catch (InputMismatchException ime)
		{
			Log.e("B_INFO", "File corrupt...");
		}
		
		return slots;
	}
	
	/**
	 * Sets the level.
	 * @param level to be set.
	 */
	public void setLevel(int level)
	{
		_currentLevel = level;
	}
	
	/**
	 * Gets the level.
	 * @return the current level.
	 */
	public int getLevel()
	{
		return _currentLevel;
	}
	
	/**
	 * Set current score.
	 * @param score the integer score value.
	 */
	public void setScore(int score)
	{
		_saveSlot.setScore(score);
	}
	
	/**
	 * Cleans the text file.
	 * @return A fresh text file.
	 */
	private String cleanFile()
	{
		String buffer = "";
		
		for (int i = 0; i < 5; i++)
		{
			buffer += (i+1) + " ";
			
			if ((i+1) == _saveSlot.getId())
			{
				buffer += _saveSlot.getName() + "\n";
				buffer += _saveSlot.getScore() + "\n";
				buffer += "dummy data" + "\n";
				buffer += _saveSlot.getLastClearedLevel() + "\n";
			}
			else
				buffer += "Empty\n0\n0\n0\n";
		}
		
		return buffer;
	}
}
