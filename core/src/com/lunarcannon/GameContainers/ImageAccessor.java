package com.lunarcannon.GameContainers;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ImageAccessor implements TweenAccessor<Image> {
	 public static final int ALPHA = 1;

	    @Override
	    public int getValues(Image target, int tweenType, float[] returnValues) {
	        switch (tweenType) {
	        case ALPHA:
//	        	returnValues[0] = 1;
	            returnValues[0] = target.getColor().a;
	    
	            return 1;
	        default:
	            return 0;
	        }
	    }

	    @Override
	    public void setValues(Image target, int tweenType, float[] newValues) {
	        switch (tweenType) {
	        case ALPHA:
	            target.setColor(1, 1, 1, newValues[0]);
	            break;
	        }
	    }
}
