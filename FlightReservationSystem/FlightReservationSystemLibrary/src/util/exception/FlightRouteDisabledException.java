/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package util.exception;

/**
 *
 * @author jonang
 */
public class FlightRouteDisabledException extends Exception {

    /**
     * Creates a new instance of <code>FlightRouteDisabledException</code>
     * without detail message.
     */
    public FlightRouteDisabledException() {
    }

    /**
     * Constructs an instance of <code>FlightRouteDisabledException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public FlightRouteDisabledException(String msg) {
        super(msg);
    }
}
