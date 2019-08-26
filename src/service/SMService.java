/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;
import model.SMModel;
/**
 *
 * @author Nuwanga
 */
public interface SMService {
    public void customerTableLoad(SMModel sm);
    public boolean addtobill(SMModel sm);
}
