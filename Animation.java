import java.util.*;
import greenfoot.*;

/**
 * Write a description of class Animation here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Animation  
{
    private List<GreenfootImage> currentFrames;
    private List<GreenfootImage> frames;
    private List<GreenfootImage> flippedFrames;

    private int numFrames;

    private int delay = 5;

    private int currentFrame = 0;
    private int counter = delay;
    public int offsetX = 0;

    private AnimationCompleteListener  completeListener = null;

    /** 
     * The formattedPath should include a format string "%0nd" where n is the number of total digits in
     * each file for the animation.
     * 
     * maxNum is the max number on the file name inclusive. Frames are assumed numbered 0 to n
     */
    public Animation(String formattedPath, int maxNum)
    {
        this.numFrames = maxNum + 1;

        frames = new ArrayList<GreenfootImage>();
        for (int i = 0; i <= maxNum; i++)
        {
            String fileName = String.format(formattedPath, i);
            frames.add(new GreenfootImage(fileName));
        }
        
        flippedFrames = new ArrayList<GreenfootImage>();
        for (GreenfootImage frame : frames)
        {
            flippedFrames.add(getMirroredImage(frame));
        }


        currentFrames = frames;
    }
    
    public Animation(String formattedPath, int maxNum, float scale)
    {
        this.numFrames = maxNum + 1;

        frames = new ArrayList<GreenfootImage>();
        for (int i = 0; i <= maxNum; i++)
        {
            String fileName = String.format(formattedPath, i);
            frames.add(new GreenfootImage(fileName));
        }
        
        scale(scale);

        flippedFrames = new ArrayList<GreenfootImage>();
        for (GreenfootImage frame : frames)
        {
            flippedFrames.add(getMirroredImage(frame));
        }

        
        
        currentFrames = frames;
    }

    public Animation setFlipped(boolean flip)
    {
        if (flip)
        {
            currentFrames = flippedFrames;
        }
        else 
        {
            currentFrames = frames;
        }

        return this;
    }
    
    public void scale(float s)
    {
        for(GreenfootImage frame : frames) {
            int height = frame.getHeight();
            
            int width = frame.getWidth();
            float ratio = (float)width / (float)height;
             
            int newHeight = (int)(height * s);
             frame.scale((int)(ratio * newHeight), newHeight);
   
        }    
    }

   public void setDelay(int d)
    {
        this.delay = d;
    }
    
    public void reset()
    {
        currentFrame = 0;
        counter = delay;
    }

    public void setAnimationCompleteListener(AnimationCompleteListener listener)
    {
        this.completeListener = listener;
    }

    /**
      * Advances the current frame depending on counter and speed
     */
    public GreenfootImage getNextFrame()
    {
        if (--counter < 1) 
        {
            counter = delay;
            if (++currentFrame >= numFrames)
            {
                currentFrame = 0;

                if (completeListener != null) 
                {
                    completeListener.animationCompleted(this);
                }
            }
        }

        return currentFrames.get(currentFrame);
    }

    public GreenfootImage getCurrentFrame()
    {
        return currentFrames.get(currentFrame);
    }

    public int getFrameNumber()
    {
        return currentFrame;
    }

    public int getLength()
    {
        return delay * numFrames;
    }
    
    public static GreenfootImage getMirroredImage(GreenfootImage original)
    {
        GreenfootImage mirrored = new GreenfootImage(original.getWidth(), original.getHeight());
        for (int y=0; y<original.getHeight(); y++) for (int x=0; x<original.getWidth(); x++)
        {
            Color color = original.getColorAt(x, y);
            int n = original.getWidth()-x-1;
            mirrored.setColorAt(n, y, color);
        }
        return mirrored;
    }

    public void tint(int r, int g, int b)
    {
        tint(frames, r, g, b);
        tint(flippedFrames, r, g, b);
    }

    private static void tint(List<GreenfootImage> images, int r, int g, int b)
    {
        for (GreenfootImage img : images)
        {
            for (int x = 0; x < img.getWidth(); x++)
            {
                for (int y = 0; y < img.getHeight(); y++)
                {
                    Color orig = img.getColorAt(x, y);
                    int a = orig.getAlpha();

                    img.setColorAt(x, y, 
                        new Color(addColors(orig.getRed(), r), 
                        addColors(orig.getGreen(), g), 
                        addColors(orig.getBlue(), b),
                        a));
                }
            }
        }
    }
    
    private static int addColors(int c, int a)
    {
        int sum = c + a;
        if (sum > 255) return 255;
        if (sum < 0) return 0;
        return sum;
    }

    public interface AnimationCompleteListener
    {
        public void animationCompleted(Animation animation);
    }
}
