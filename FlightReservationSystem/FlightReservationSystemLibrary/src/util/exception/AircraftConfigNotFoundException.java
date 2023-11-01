/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package util.exception;

/**
 *
 * @author jonang
 */
public class AircraftConfigNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>AircraftConfigNotFoundException</code>
     * without detail message.
     */
    public AircraftConfigNotFoundException() {
    }

    /**
     * Constructs an instance of <code>AircraftConfigNotFoundException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public AircraftConfigNotFoundException(String msg) {
        super(msg);
    }
}
