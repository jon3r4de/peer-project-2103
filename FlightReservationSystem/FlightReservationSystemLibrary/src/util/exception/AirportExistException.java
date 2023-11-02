/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package util.exception;

/**
 *
 * @author jonang
 */
public class AirportExistException extends Exception{

    /**
     * Creates a new instance of <code>AirportExistException</code> without
     * detail message.
     */
    public AirportExistException() {
    }

    /**
     * Constructs an instance of <code>AirportExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public AirportExistException(String msg) {
        super(msg);
    }
}
