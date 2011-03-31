/**
 * 
 */
package com.blockz.graphics;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;

/**
 * @author
 *
 */
public class Scene extends SurfaceView
{
	private Map<String,Sprite> _spriteTable;
	private SurfaceHolder _surfHolder;
	
    public Scene(Context context)
    {
    	super(context);
    	_surfHolder = getHolder();
    	_spriteTable = new Map<String,Sprite>();
    }
    
    /**
	 * addSprite() takes a sprite name as argument and add the corresponding sprite object to the MAP.
	 */	
	public void addSprite(String spriteName)
	{		
		if(_spriteTable.get(spriteName) != null)
			_spriteTable.put(spriteName, new Sprite(spriteName + ".png"));
	}
    
	
    public void draw(List<Item> renderList)
    {
    	for(int i=0; i<renderList.size(); i++)
    	{
    		Item it = renderList.get(i);
    		Sprite s = _spriteTable.get(it.getSpriteName() + ".png");
    		s.draw(/*Position + canvas?*/);
    		
    	}
    }
    
}