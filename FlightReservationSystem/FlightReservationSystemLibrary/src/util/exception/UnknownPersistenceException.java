/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package util.exception;

/**
 *
 * @author jonang
 */
public class UnknownPersistenceException extends Exception {

    /**
     * Creates a new instance of <code>UnknownPersistenceException</code>
     * without detail message.
     */
    public UnknownPersistenceException() {
    }

    /**
     * Constructs an instance of <code>UnknownPersistenceException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public UnknownPersistenceException(String msg) {
        super(msg);
    }
}
