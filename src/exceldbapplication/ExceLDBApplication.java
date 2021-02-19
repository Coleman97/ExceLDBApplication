/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceldbapplication;

/**
 *
 * @author Nrg_ColdColean
 */
public class ExceLDBApplication {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ExcelDataImport ed = new ExcelDataImport();
        //ed.getDataFromExcel();
        ed.retrieveInJSON();
    }
    
}
