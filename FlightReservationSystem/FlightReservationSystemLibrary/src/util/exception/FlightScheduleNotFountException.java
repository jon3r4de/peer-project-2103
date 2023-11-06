/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package util.exception;

/**
 *
 * @author jonang
 */
public class FlightScheduleNotFountException extends Exception {

    /**
     * Creates a new instance of <code>FlightScheduleNotFountException</code>
     * without detail message.
     */
    public FlightScheduleNotFountException() {
    }

    /**
     * Constructs an instance of <code>FlightScheduleNotFountException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public FlightScheduleNotFountException(String msg) {
        super(msg);
    }
}
