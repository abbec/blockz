/**
 * 
 */
package com.blockz.graphics;
import java.util.Map;

/**
 * The spritemanager associates names of sprites with the corresponding sprite object.
 * @author Albert, Calle, Annsofi
 * 
 */
public class SpriteManager
{
	private Map<String,Sprite> _spriteTable;
	
	public SpriteManager()
	{
		_spriteTable = new Map<String,Sprite>();
	}
	
	/**
	 * addSprite() takes a sprite name as argument and add the corresponding sprite object to the MAP.
	 */	
	public void addSprite(String spriteName)
	{
		if(_spriteTable.get(spriteName) != null)
			_spriteTable.put(spriteName,new Sprite(spriteName + ".png"));
	}
	/**
	 * getSprite() takes a sprite name as argument and returns the corresponding sprite object.
	 */	
	public Sprite getSprite(String spriteName)
	{
		return _spriteTable.get(spriteName);
	}
}
