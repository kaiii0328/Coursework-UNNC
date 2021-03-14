package main.controller;

import java.io.*;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.com.ae2dms.GameEngine;

/**
 * The {@code GameEndController} class  contains several useful class fields
 * and methods.
 * This controller class is specified to control gameEnd.fxml.
 * It will be instantiated during the fxml file loading process.
 * <p>
 *     Function {@link #saveRecord(MouseEvent)} and {@link #finishGame(MouseEvent)}
 *     are the main functions to save user records and restart the game.
 * </p>
 */
public class GameEndController implements Initializable {
    /**
     * Text for showing the total move count
     */
    @FXML
    private Text victoryText;

    /**
     * text field for user to enter name
     */
    @FXML
    private TextField userName;

    /**
     * listview to rank the records
     */
    @FXML
    private ListView<String> levelRecords;

    /**
     * completed map set name
     */
    private String mapSetName;
    /**
     * total move count
     */
    private int moveCount;

    /**
     * Close the prompted window asking users to enter user name and save records.
     * Switch the showing scene to the game-start scene.
     * @param event  mouse event passed when the button is clicked
     */
    @FXML
    void finishGame(MouseEvent event) {
        GameStartController.deleteTempFile();
        Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Stage parentStage = (Stage)currentStage.getOwner();
        currentStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("../resources/views/gameStart.fxml"));
                    parentStage.setScene(new Scene(root));  //switch back to the game-start scene
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        currentStage.fireEvent(new WindowEvent(currentStage, WindowEvent.WINDOW_CLOSE_REQUEST));// close the game-end scene for user input
    }

    /**
     * save the records in /resources/record.txt file. The methods will automatically create a one
     * if the file not exists or write to the existing file.
     *
     * It will first iterate through the record.txt file to check whether the completed map set has had
     * records before.
     * If not, the currently saving record will be the first record for this map set. Therefore, the methods
     * should write the map set name to the record.txt file.
     * If it already has some records, the currently saving records will be inserted the next line of the
     * map set name.
     * @param event mouse event passed when the button is clicked
     * @throws IOException if game file is not saved successfully
     */
    @FXML
    void saveRecord(MouseEvent event) throws IOException {
        boolean hasRecords = false;
        String recordContent="";
        String usrName = userName.getText();
        //set the path and replace the path separator to system file separator
        // for cross-platform application
        String dir= System.getProperty("user.dir") + "/src/main/resources/records/record.txt".replace("/",System.getProperty("file.separator"));
        Path recordFilePath = Paths.get(dir);
        try{
            Files.createFile(recordFilePath);   //create a file. throws an FileAlreadyExistsException if the file exists
        }catch (FileAlreadyExistsException e){}

        String record = usrName + ": " + moveCount;  //format the record message

        try(BufferedReader reader = new BufferedReader(new FileReader(dir))){
            String line = reader.readLine();
            while (line != null){ // this while loop is used to check whether the map set has records
                if (line.contains(mapSetName)){
                    hasRecords = true;   //if it has records, break the loop
                     break;
                }
                line=reader.readLine();
            }
        }catch (Exception e) {
            System.out.println("Error trying to load the game file: " + e);
        }

        try(BufferedReader reader = new BufferedReader(new FileReader(dir))){
            String line = reader.readLine();
            while (line != null){
                if (line.contains(mapSetName)){  //find where the map set is at in the file
                    line += "\n"+record;      //insert the current record to following line of the mapSetName
                }
                recordContent += line+"\n";
                line=reader.readLine();
            }
            if (!hasRecords){
                recordContent += mapSetName + "\n" + record; //if there are no records,insert with mapSetName
            }

        } catch (IOException e) {
             System.out.println("Error trying to load the game file: " + e);
        } catch (NullPointerException e) {
            System.out.println("Cannot open the requested file: " + e);
        }

        FileWriter filewriter = new FileWriter(String.valueOf(recordFilePath));
        PrintWriter printwriter = new PrintWriter(filewriter);
        printwriter.print(recordContent);  // write to the file
        printwriter.close();
        finishGame(event);                //close the window and switch back to game-start scene
    }

    /**
     * Received the message sent by {@link GameRunningController#startGameEndScene()}.
     * Set the message to be displayed. Use the {@code mapSetName} and {@code moveCount}
     * for saving game records.
     *
     * @param message  the sentence will displayed
     * @param mapSetName map set name of current game file
     * @param moveCount  total move count for the user
     */
    public  void initData(String message, String mapSetName, int moveCount){
        this.mapSetName = mapSetName;
        this.moveCount = moveCount;
        victoryText.setText(message);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String dir= System.getProperty("user.dir") + "/src/main/resources/records/temp.txt".replace("/",System.getProperty("file.separator"));
        Path recordFilePath = Paths.get(dir);
        if (Files.exists(recordFilePath)) {
            try (BufferedReader reader = new BufferedReader(new FileReader(dir))) {
                Map<String, Integer> scoresMap = new TreeMap<String, Integer>();
                String line = reader.readLine();
                while (line != null && !line.equals("\n")) {
                    String[] buff = line.split(":");
                    if (buff.length < 2) {
                        break;
                    }
                    scoresMap.put(buff[0], Integer.parseInt(buff[buff.length-1].trim()));
                    line = reader.readLine();
                }

                java.util.List<Map.Entry<String, Integer>> scoresList = new ArrayList<Map.Entry<String, Integer>>(scoresMap.entrySet());
                Collections.sort(scoresList, new Comparator<Map.Entry<String, Integer>>() {
                    @Override
                    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                        return o1.getValue().compareTo(o2.getValue());
                    }
                });
                String[] records = new String[scoresList.size()];
                for (int i = 0; i < scoresList.size(); i++) {
                    String formattedRecord = scoresList.get(i).getKey() + "      ------   " + scoresList.get(i).getValue();
                    records[i] = formattedRecord;
                }
                levelRecords.setItems(FXCollections.observableArrayList(records));
            } catch (FileNotFoundException e) {
                GameEngine.logger.warning("Temp files to store level records not found");
            } catch (IOException e) {
                GameEngine.logger.warning("IO exception in loading temp files");
            }
        }
    }
}
