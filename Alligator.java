import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Alligator here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Alligator extends Enemy
{
    private int moveSpeed = 2;
    
     public Alligator() {
        super();
        strength = 5;
        defense = 2;
    }
    
    /**
     * Act - do whatever the Alligator wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        super.act();
        move();
        attack();
    }    
    public int getSpeed() {
        return 1;
    }
    
    public GreenfootImage getCurrentAnimationFrame() {
        return getImage();
    }
    
    private void move() {
        for (Hero hero : getObjectsInRange(1000,Hero.class)) {
            if (hero.getX() < getX()) {
                setLocation(getX() - moveSpeed, getY());
            }
            else if (hero.getX() > getX()) {
                setLocation(getX() + moveSpeed, getY());
            }
        }
    }
    
    public void attack() {
        super.attack();
        Hero hero = (Hero)getOneIntersectingObject(Hero.class);
        
        if (hero != null && attackFrame == attackDelay) {
            hero.damage(strength);
            attackFrame = 0;
        }
    }
}
