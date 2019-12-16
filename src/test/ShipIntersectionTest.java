package test;

import SpaceInvaders.Model.Bullet;
import SpaceInvaders.Model.Ship;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ShipIntersectionTest {

    @Test
    public void playerOutOfBounds_ReturnFalse(){
        Ship ship = new Ship(800, 600);

        boolean actual = (ship.getPosX() < 0 || ship.getPosX() > 800);

        assertFalse(actual);
    }

    @Test
    public void playerInBounds_ReturnTrue(){
        Ship ship = new Ship(800, 600);

        boolean actual = (ship.getPosX() >= 0 && ship.getPosX() <= 800);

        assertTrue(actual);
    }

    @Test
    public void playerIsShot_ReturnTrue(){
        Ship ship = new Ship(800, 600);
        ship.setWidth(32);
        ship.setHeight(32);

        Bullet bullet = new Bullet(ship.getPosX(), ship.getPosY());
        bullet.setWidth(1);
        bullet.setHeight(4);

        boolean actual = ship.intersects(bullet);

        assertTrue(actual);
    }

    @Test
    public void playerIsAlive_ReturnTrue(){
        Ship ship = new Ship(800, 600);
        ship.setWidth(32);
        ship.setHeight(32);

        Bullet bullet = new Bullet(ship.getPosX() - 50, ship.getPosY() - 50);
        bullet.setWidth(1);
        bullet.setHeight(4);

        boolean actual = !ship.intersects(bullet);

        assertTrue(actual);
    }
}
