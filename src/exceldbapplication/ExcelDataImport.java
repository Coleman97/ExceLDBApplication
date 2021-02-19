/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceldbapplication;

import java.io.*;
import java.sql.*;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.json.simple.*;
        

/**
 *
 * @author Nrg_ColdColean
 */
public class ExcelDataImport extends MyVariables{
    public void getDataFromExcel(){
        try{
            long begin = System.currentTimeMillis();
            FileInputStream inputStream = new FileInputStream(excelFileLocation);
            Workbook myWorkbook = new XSSFWorkbook(inputStream);
            Sheet myFirstSheet =myWorkbook.getSheetAt(0);
            Iterator<Row> myRowIterator = myFirstSheet.iterator();
            
            myConnection = DriverManager.getConnection(url,username,password);
            myConnection.setAutoCommit(false);
            
            String query = "insert into app.stateinformation(lga,state) values(?,?) ";
            PreparedStatement myPrepareStatement = myConnection.prepareStatement(query);
            
            int counter = 0;
            myRowIterator.next();
            while(myRowIterator.hasNext()){
                Row newRow = myRowIterator.next();
                Iterator<Cell> myCellIterator = newRow.cellIterator();
                
                while(myCellIterator.hasNext()){
                    Cell newCell = myCellIterator.next();
                    
                    int columnIndex = newCell.getColumnIndex();
                    
                    switch(columnIndex){
                        case 0:
                            String LGA = newCell.getStringCellValue();
                            myPrepareStatement.setString(1,LGA);
                            break;
                        case 1:
                            String State = newCell.getStringCellValue();
                            myPrepareStatement.setString(2,State);
                            break;      
                    }
                }
                myPrepareStatement.addBatch();
                
                if(counter % filesize == 0){
                    myPrepareStatement.executeBatch();
                }
            }
            myWorkbook.close();
            
            myPrepareStatement.executeBatch();
            myConnection.commit();
            myConnection.close();
            
            long end = System.currentTimeMillis();
            System.out.printf("Import completed in %d ms \n",  (end -begin));
        }catch(IOException e1){
            System.out.println("Cannot read file!!");
            e1.printStackTrace();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void retrieveInJSON(){
        try{
            String query = "Select state,lga from app.stateinformation ";
            myConnection = DriverManager.getConnection(url, username, password);
            myStatement = myConnection.createStatement();
            myResult = myStatement.executeQuery(query);
            myMetadata = myResult.getMetaData();
            
             List<String[]> fromDB = new ArrayList<>();
                 // add items to the mock of the db response
                    String[] ar1 = {"Abaji", "FCT"};
                    fromDB.add(ar1);
                    String[] ar2 = {"Abuja Municpal", "FCT"};
                    fromDB.add(ar2);
                    String[] ar3 = {"Alimosho", "Lagos"};
                    fromDB.add(ar3);
                    String[] ar4 = {"Agege", "Lagos"};
                    fromDB.add(ar4);

                    // declare the data map
                    Map<String, List<String>> data = new HashMap<>();

                    // populate the data map with the right information.
                    fromDB.forEach(item -> {
                      String state = item[1];
                      List<String> l;
                      if (data.containsKey(state)) {
                        // add to the List
                        l = data.get(state);
                        l.add(item[0]);
                      } else {
                        l = new ArrayList<>(){{
                          add(item[0]);
                        }};
                      }
                      data.put(state, l);
                    });
                  //  System.out.println(data); 
                    
           JSONObject jsonObject = new JSONObject();
           JSONObject jsonObject1 = new JSONObject();
           JSONObject jsonObject2 = new JSONObject();
          jsonObject.put("Status : Success," , jsonObject1);
          jsonObject1.put("data", data); 
          jsonObject2.putIfAbsent(jsonObject, jsonObject1);
          //System.out.println(jsonObject.toJSONString());
          
          try {
         FileWriter file = new FileWriter("C:/output.json");
         file.write(jsonObject.toJSONString());
         file.close();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
                           
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
