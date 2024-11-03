package clinic.controller;

import clinic.model.util.Location;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class LocationView {

    @FXML
    private TableView <Location> locationTable;
    @FXML
    private TableColumn <Location, String> county;
    @FXML
    private TableColumn <Location, String> city;
    @FXML
    private TableColumn <Location, String> zipCode;

    ObservableList<Location> locations = FXCollections.observableArrayList(Location.values());

    public void initialize() {
        county.setCellValueFactory(cellData -> new SimpleStringProperty((cellData.getValue()).getCounty()));
        city.setCellValueFactory(cellData -> new SimpleStringProperty((cellData.getValue()).getCity()));
        zipCode.setCellValueFactory(cellData -> new SimpleStringProperty((cellData.getValue()).getZip()));
        locationTable.setItems(locations);
    }
}