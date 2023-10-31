/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package util.exception;

/**
 *
 * @author jonang
 */
public class EmployeeNotFoundException extends Exception
{
    public EmployeeNotFoundException()
    {
    }
    
    
    
    public EmployeeNotFoundException(String msg)
    {
        super(msg);
    }
}
