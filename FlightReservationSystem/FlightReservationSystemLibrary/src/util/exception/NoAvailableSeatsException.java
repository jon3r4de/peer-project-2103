/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package util.exception;

/**
 *
 * @author jonang
 */
public class NoAvailableSeatsException extends Exception {

    /**
     * Creates a new instance of <code>NoAvailableSeatsException</code> without
     * detail message.
     */
    public NoAvailableSeatsException() {
    }

    /**
     * Constructs an instance of <code>NoAvailableSeatsException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NoAvailableSeatsException(String msg) {
        super(msg);
    }
}
