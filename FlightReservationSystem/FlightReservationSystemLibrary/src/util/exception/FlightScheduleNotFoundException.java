/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package util.exception;

/**
 *
 * @author jonang
 */
public class FlightScheduleNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>FlightScheduleNotFountException</code>
     * without detail message.
     */
    public FlightScheduleNotFoundException() {
    }

    /**
     * Constructs an instance of <code>FlightScheduleNotFountException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public FlightScheduleNotFoundException(String msg) {
        super(msg);
    }
}
