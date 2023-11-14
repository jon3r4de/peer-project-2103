/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package util.exception;

/**
 *
 * @author jonang
 */
public class ReservationNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>ReservationNotFoundException</code>
     * without detail message.
     */
    public ReservationNotFoundException() {
    }

    /**
     * Constructs an instance of <code>ReservationNotFoundException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public ReservationNotFoundException(String msg) {
        super(msg);
    }
}
