/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package util.exception;

/**
 *
 * @author jonang
 */
public class AircraftConfigExistExcetpion extends Exception {

    /**
     * Creates a new instance of <code>AircraftConfigExistExcetpion</code>
     * without detail message.
     */
    public AircraftConfigExistExcetpion() {
    }

    /**
     * Constructs an instance of <code>AircraftConfigExistExcetpion</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public AircraftConfigExistExcetpion(String msg) {
        super(msg);
    }
}
