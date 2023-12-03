package javafxjdbc;

import db.DBHelper;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CRUDController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<Mahasiswa, String> colAlamat;

    @FXML
    private TableColumn<Mahasiswa, String> colNama;

    @FXML
    private TableColumn<Mahasiswa, String> colNpm;

    @FXML
    private TextField tfAlamat;

    @FXML
    private TextField tfNama;

    @FXML
    private TextField tfNpm;

    @FXML
    private TableView<Mahasiswa> tvData;

    @FXML
    void handleButtonAction(ActionEvent event) {
        if(event.getSource() == btnInsert){
            insertRecord();
        }else if(event.getSource() == btnUpdate){
            updateRecord();
        }else if (event.getSource() == btnDelete){
            deleteRecord();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        showMahasiswa();
    }
    
    public ObservableList<Mahasiswa> getDataMahasiswa(){
        ObservableList<Mahasiswa> mhs = FXCollections.observableArrayList();
        Connection conn = DBHelper.getConnection();
        String query = "SELECT * FROM `mhs`";
        Statement st;
        ResultSet rs;
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Mahasiswa temp;
            while(rs.next()){
                temp = new Mahasiswa(rs.getString("NPM"), rs.getString("NAMA"), rs.getString("alamat"));
                mhs.add(temp);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return mhs;
    }
    public void showMahasiswa(){
        ObservableList<Mahasiswa> list = getDataMahasiswa();
        
        colNpm.setCellValueFactory(new PropertyValueFactory<>("npm"));
        colNama.setCellValueFactory(new PropertyValueFactory<>("Nama"));
        colAlamat.setCellValueFactory(new PropertyValueFactory<>("alamat"));
        tvData.setItems(list);
    }
    
    private void update(String query, String... parameters){
//        Connection conn = DBHelper.getConnection();
//        Statement st;
//        
//        try{
//            st = conn.createStatement();
//            st.executeUpdate(query);
//        }catch(SQLException ex){
//            ex.printStackTrace();
//        }
        try(Connection conn = DBHelper.getConnection(); PreparedStatement ps
                = conn.prepareStatement(query)){
            for (int i = 0 ; i < parameters.length; i++){
                ps.setString(i+ 1, parameters[i]);
            }
            
            ps.executeUpdate();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    private void insertRecord(){
//        String query = "INSERT INTO `mhs` VALUES ('"+tfNpm.getText() + "','"+tfNama.getText() + "','" + tfAlamat.getText()+ "')";
        String query = "INSERT INTO mhs VALUES (?,?,?)";
        update(query, tfNpm.getText(), tfNama.getText(), tfAlamat.getText());
        
        showMahasiswa();
        
        tfNama.clear();
        tfNpm.clear();
        tfAlamat.clear();

        
    }
    private void updateRecord(){
//        String query = "UPDATE `mhs` SET `NAMA` = '" + tfNama.getText() + "', alamat = '" +tfAlamat.getText() + "' WHERE NPM = '" + tfNpm.getText() + "'" ;
        String query = "UPDATE mhs SET NAMA = ?, alamat = ? WHERE NPM = ?";
        update(query, tfNama.getText(), tfAlamat.getText(), tfNpm.getText());
        
        showMahasiswa();
        
        tfNama.clear();
        tfNpm.clear();
        tfAlamat.clear();
    }
    
    private void deleteRecord(){
//        String query = "DELETE FROM `mhs` WHERE NPM = '" + tfNpm.getText()+ "'";
        String query = "DELETE FROM mhs WHERE NPM = ? ";
        update(query, tfNpm.getText());
        showMahasiswa();
        
        tfNpm.clear();
    }

}
