/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actors;

import java.awt.event.KeyEvent;

/**
 *
 * @author eric.stock
 */
public interface KeyboardControllable {
    public void triggerKeyPress(KeyEvent e);
    public void triggerKeyRelease(KeyEvent e);
}
