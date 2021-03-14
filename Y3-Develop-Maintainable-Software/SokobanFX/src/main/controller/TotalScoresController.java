package main.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.com.ae2dms.GameEngine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * The {@code TotalScoresController} class is used to show history scores rankings
 * in the game map set.
 */
public class TotalScoresController implements Initializable {
    /**
     * listview for ranking records
     */
    @FXML
    private ListView<String> listView;
    /**
     * the selected map set name
     */
    @FXML
    private Text mapName;

    /**
     * The default view is initiated when no map set is selected,
     * @param url url location
     * @param resourceBundle resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] users = { "       ---- No records ----", "       ---- No records ----","       ---- No records ----","       ---- No records ----" };
        listView.setItems(FXCollections.observableArrayList(users));
    }

    /**
     * show the ranking of the default map set.
     * @param event mouse click
     * @throws IOException if the record file fails loading
     */
    @FXML
    void defaultMapScores(ActionEvent event) throws IOException {
        mapName.setVisible(true);
        mapName.setText("Scores for Example Game! map set!");
        showScores("Example Game!");
    }

    /**
     * show the ranking of the new map set.
     * @param event mouse click
     * @throws IOException if the record file has IO error
     */
    @FXML
    void newMapScores(ActionEvent event) throws IOException {
        mapName.setVisible(true);
        mapName.setText("Scores for Adventure map set!");
        showScores("Adventure");


    }

    /**
     * extracts records of the given map set in the record file and sorted
     * them in descending orders.
     * @param mapSetName given map set name
     * @throws IOException if the record file has IO error
     */
    private void  showScores(String mapSetName) throws IOException {
        String dir= System.getProperty("user.dir") + "/src/main/resources/records/record.txt".replace("/",System.getProperty("file.separator"));
        Path recordFilePath = Paths.get(dir);
        if (Files.exists(recordFilePath)){
            boolean noRecords = hasRecords(mapSetName,dir);
            if (!noRecords){
                try(BufferedReader reader = new BufferedReader(new FileReader(dir))){
                    String defaultMapsetName = mapSetName;
                    Map<String,Integer> scoresMap = new TreeMap<String,Integer>();
                    String line = reader.readLine();
                    while (!line.contains(defaultMapsetName)){line = reader.readLine();} //go to the line with mapset name
                    line = reader.readLine();
                    while (line != null && !line.equals("\n")){
                        String[] buff = line.split(":");  //get username and move count
                        if (buff.length <2){
                            break;
                        }
                        scoresMap.put(buff[0],Integer.parseInt(buff[1].trim()));
                        line = reader.readLine();
                    }

                    java.util.List<Map.Entry<String,Integer>> scoresList = new ArrayList<Map.Entry<String,Integer>>(scoresMap.entrySet());
                    Collections.sort(scoresList, new Comparator<Map.Entry<String, Integer>>() {
                        @Override
                        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                            return o1.getValue().compareTo(o2.getValue());
                        }
                    });
                    String[] records = new String[scoresList.size()];
                    for (int i =0; i<scoresList.size();i++){ // print to listview
                        String formattedRecord = "Top"+ (i+1) + " " +scoresList.get(i).getKey() + "      ------   " + scoresList.get(i).getValue();
                        records[i] = formattedRecord;
                    }
                    listView.setItems(FXCollections.observableArrayList(records));
                }
            }

        }
    }

    /**
     * search the record file to see if there are history records of the selected map set
     * @param mapSetName the selected map set name
     * @param dir path of the record file
     * @return true if it has history records, false otherwise
     */
    private  boolean  hasRecords(String mapSetName,String dir){
        try(BufferedReader reader = new BufferedReader(new FileReader(dir))){
            String line = reader.readLine();
            while (line != null){
                if (line.contains(mapSetName)){
                    return false;
                }
                line = reader.readLine();
            }
        }catch (Exception e){
            GameEngine.logger.warning("No records found");
        }
        return  true;
    }
}
