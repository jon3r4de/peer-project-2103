/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package util.exception;

/**
 *
 * @author jonang
 */
public class FareExistException extends Exception {

    /**
     * Creates a new instance of <code>FareExistException</code> without detail
     * message.
     */
    public FareExistException() {
    }

    /**
     * Constructs an instance of <code>FareExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public FareExistException(String msg) {
        super(msg);
    }
}
