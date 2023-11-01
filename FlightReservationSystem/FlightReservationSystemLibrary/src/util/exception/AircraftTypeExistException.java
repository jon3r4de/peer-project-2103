/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package util.exception;

/**
 *
 * @author jonang
 */
public class AircraftTypeExistException extends Exception {

    /**
     * Creates a new instance of <code>AircraftTypeExistException</code> without
     * detail message.
     */
    public AircraftTypeExistException() {
    }

    /**
     * Constructs an instance of <code>AircraftTypeExistException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public AircraftTypeExistException(String msg) {
        super(msg);
    }
}
