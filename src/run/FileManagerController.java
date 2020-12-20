package run;

import equipment.Disk;
import filemanager.file.Document;
import filemanager.file.SparrowDirectory;
import filemanager.file.SparrowFile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Create By  @林俊杰
 * 2020/12/19 12:22
 *
 * @version 1.0
 */
public class FileManagerController implements Initializable {

    @FXML
    private AnchorPane fileManager_AnchorPane;

    @FXML
    private TreeView<String> fileTree_TreeView;

    @FXML
    private VBox fileTree_VBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showTree();
    }

    TreeItem<String> rootNode;

    public void showTree(){
        //Node rootIcon = new ImageView(new Image(getClass().getResourceAsStream("resource/icon/资源管理器.png")));
        rootNode = new TreeItem<>("根目录");
        //TODO替换此List
        List<Document> rootList = new ArrayList<>();


        for (int i = 0; i <10; i++) {
            TreeItem<String> leaf = new TreeItem<>(String.valueOf(i+1));
            rootNode.getChildren().add(leaf);
        }
        fileTree_TreeView.setRoot(rootNode);
    }

    private void loadTreeNode(TreeItem<String> subTree,Document doc){
        if(doc instanceof SparrowFile){
            TreeItem<String> leaf = new TreeItem<>(doc.getFileCatalog().getCatalogName());
            subTree.getChildren().add(leaf);
        }else if(doc instanceof SparrowDirectory){
            TreeItem<String> leaf = new TreeItem<>(doc.getFileCatalog().getCatalogName());
            subTree.getChildren().add(leaf);

            loadTreeNode(subTree,(SparrowDirectory)((SparrowDirectory) doc).getData());
        }else{
            System.out.println("Error:错误类型!既不是File也不是Directory");
        }
    }
}
