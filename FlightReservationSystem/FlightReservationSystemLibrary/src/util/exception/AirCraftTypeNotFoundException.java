/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package util.exception;

/**
 *
 * @author jonang
 */
public class AirCraftTypeNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>AirCraftTypeNotFoundException</code>
     * without detail message.
     */
    public AirCraftTypeNotFoundException() {
    }

    /**
     * Constructs an instance of <code>AirCraftTypeNotFoundException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public AirCraftTypeNotFoundException(String msg) {
        super(msg);
    }
}
