/**
 * 
 */
package com.blockz;

import java.io.*;
import java.util.*;

import com.blockz.graphics.Sprite;
import com.blockz.util.*;

import org.xmlpull.v1.*;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.os.Environment;
import android.util.Log;

import junit.framework.Assert;

/**
 * Class that handles the levels.
 */
public class LevelManager 
{
	private static final LevelManager INSTANCE = new LevelManager();
	
	public static LevelManager getInstance() {
	//	if(INSTANCE != null)
			return INSTANCE;
	//	else
	//		return new LevelManager();
	}

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

		public int getId() 
		{
			return _id;
		}
		
		public String getName()
		{
			return _name;
		}
		
		public int getScore()
		{
			return _currentScore;
		}
		
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
	private Context _context;
	private int _currentLevel;
	private Tree<LevelNode> _levelTree;
	private static File _dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/blockz");
	
	private LevelManager() {}
	
	/**
	 * Constructor for the LevelManager.
	 * @param saveSlot to be handled by the LevelManager.
	 */
//	public LevelManager(SaveSlot saveSlot)
//	{
//		
//		_saveSlot = saveSlot;
//		
//		File sdCard = Environment.getExternalStorageDirectory();
//		_dir = new File(sdCard.getAbsolutePath() + "/blockz");
//	}
//	
	public void setSaveSlot(Context c, SaveSlot saveSlot){
		_saveSlot = saveSlot;
		_context = c;
		
		File sdCard = Environment.getExternalStorageDirectory();
		_dir = new File(sdCard.getAbsolutePath() + "/blockz");
		
		_levelTree = new Tree<LevelNode>();
		
		// Read level XML
		readLevelXml();
	}
	


	/**
	 * Set the {@link Context} 
	 * @param c the {@link Context} to be set
	 */
	public void setContext(Context c)
	{
		_context = c;
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
		int tempInt = 0;
		String tempString = "";
		
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
	public int getCurrentLevel()
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
	
	
	private void readLevelXml()
	{
		XmlResourceParser parser = _context.getResources().getXml(R.xml.levels);
		Stack<Node<LevelNode> > parents = new Stack<Node<LevelNode>>();
		TreeMap<Integer, Node<LevelNode> > uniqueMap = new TreeMap<Integer, Node<LevelNode> >();
		
		try
		{
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.START_TAG) // Find the root tag
			{
				eventType = parser.next();
				Log.d("B_XML", "I loopen.");
			}
			
			// Set the root
			Node<LevelNode> node = new Node<LevelNode>(new LevelNode(parser.getAttributeIntValue(0, -1), false,
														parser.getAttributeIntValue("", "row", 0), parser.getAttributeIntValue("", "col", 0)));
			parents.add(node);
			
			// Add as tree root
			_levelTree.setRoot(node);
			
			eventType = parser.next();
			while (eventType != XmlPullParser.END_DOCUMENT)
			{
				switch (eventType)
				{
					case XmlPullParser.START_TAG:
					{	
						int levelId = parser.getAttributeIntValue(0, -1);
					/*	int r = parser.getAttributeIntValue("", "row", 0);
						int c = parser.getAttributeIntValue("", "col", 0);
					*/
						int r = parser.getAttributeIntValue(1, 0);
						int c = parser.getAttributeIntValue(2,0);
						
						Assert.assertTrue("Node at line " + parser.getLineNumber() + " has no level attribute! Please fix xml file!", levelId != -1);
						
						// Check if we already have this node
						node = uniqueMap.get(levelId);
						
						if (node == null)
						{
							node = new Node<LevelNode>(new LevelNode(levelId, false, r, c));
							uniqueMap.put(levelId, node);
						}
						
						parents.peek().addChild(node);
						parents.add(node);
						
						break;
					}
					case XmlPullParser.END_TAG:
					{
						parents.pop();
						break;
					}
					
					default:
						;
				}
				
				eventType = parser.next();
			}
			
			parser.close();
		}
		catch (IOException ioe)
		{
			Assert.assertTrue("IO Exception in reading the level xml. Nothing to do... sorry... :(", false);
		}
		catch (XmlPullParserException xe)
		{
			Log.e("B_INFO", "Xml parser exception: " + xe.getMessage());
		}
		
		Log.d("B_XML", "Tree in preorder: " + _levelTree.preOrderString());
	}

	/*
	 * getLevel returns levelId of level at row, col. if none is found, -1 is returned
	 * @param row, col are the row and col of the cell on levelSelect
	 * 
	 */
	public int getLevel(int row, int col)
	{
		List <Node<LevelNode> > levelList = _levelTree.postOrderList();
		int r, c;
		for (int i = 0; i < levelList.size(); i++)
		{
			c = levelList.get(i).getData().getCol();
			r = levelList.get(i).getData().getRow();
			
			if(r == row && c == col)
			{
				return levelList.get(i).getData().getLevel();
			}
		}
		return -1;
	}
	
	
	/* getRow returnerar vilken rad levelId har 
	 * @param int är levelId
	 * 
	 */
	
	public int getRow(int levelId)
	{
		List <Node<LevelNode> > levelList = _levelTree.postOrderList();
		int l;
		
		for (int i = 0; i < levelList.size(); i++)
		{
			l = levelList.get(i).getData().getLevel();
			
			if(l == levelId)
			{
				return levelList.get(i).getData().getRow();
			}
		}
		return -1;
	}
	

	/* getCol returnerar vilken kolumn levelId har 
	 * @param int är levelId
	 * 
	 */
	
	public int getCol(int levelId)
	{
		List <Node<LevelNode> > levelList = _levelTree.postOrderList();
		int l;
		
		for (int i = 0; i < levelList.size(); i++)
		{
			l = levelList.get(i).getData().getLevel();
			
			if(l == levelId)
			{
				return levelList.get(i).getData().getCol();
			}
		}
		return -1;
	}
	
}	