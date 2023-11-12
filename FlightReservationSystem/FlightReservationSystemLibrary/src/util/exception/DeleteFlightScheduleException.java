/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package util.exception;

/**
 *
 * @author jonang
 */
public class DeleteFlightScheduleException extends Exception {

    /**
     * Creates a new instance of <code>DeleteFlightScheduleException</code>
     * without detail message.
     */
    public DeleteFlightScheduleException() {
    }

    /**
     * Constructs an instance of <code>DeleteFlightScheduleException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public DeleteFlightScheduleException(String msg) {
        super(msg);
    }
}
