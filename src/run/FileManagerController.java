package run;

import enums.FileTypeEnum;
import equipment.Disk;
import filemanager.FileCatalog;
import filemanager.file.Document;
import filemanager.file.SparrowDirectory;
import filemanager.file.SparrowFile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
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

    @FXML
    private FlowPane document_FlowPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showTree();

        /**
         * 测试数据,待会要删掉哈
         * */
        SparrowDirectory sparrowDirectory = new SparrowDirectory();
        List<Document> documentList = new ArrayList<>();
        for (int i = 0; i <10; i++) {
            SparrowDirectory directory1 = new SparrowDirectory();
            FileCatalog catalog = new FileCatalog();
            catalog.setCatalogName("子目录"+String.valueOf(i+1));
            directory1.setFileCatalog(catalog);
            directory1.setData(new ArrayList<>());
            documentList.add(directory1);
        }

        SparrowFile file = new SparrowFile();
        FileCatalog catalog = new FileCatalog();
        catalog.setCatalogName("a.exe");
        catalog.setExtensionName(1);
        file.setFileCatalog(catalog);
        documentList.add(file);

        SparrowFile file1 = new SparrowFile();
        FileCatalog catalog1 = new FileCatalog();
        catalog1.setCatalogName("ab.txt");
        catalog1.setExtensionName(2);
        file1.setFileCatalog(catalog1);
        documentList.add(file1);

        SparrowFile file2 = new SparrowFile();
        FileCatalog catalog2 = new FileCatalog();
        catalog2.setCatalogName("abc.word");
        catalog2.setExtensionName(3);
        file2.setFileCatalog(catalog2);
        documentList.add(file2);

        FileCatalog fileCatalog = new FileCatalog();
        fileCatalog.setCatalogName("/根目录");
        sparrowDirectory.setFileCatalog(fileCatalog);
        sparrowDirectory.setData(documentList);
        /**
         * 测试数据装填完毕
         * */


        showDocumentIcon(sparrowDirectory);
    }

    TreeItem<String> rootNode;

    public void showTree(){
        rootNode = new TreeItem<>("根目录");
        //TODO替换此List
        List<Document> rootList = new ArrayList<>();
        SparrowDirectory root = Disk.getDisk().getRoot();

        System.out.println(root.getFileCatalog());
        System.out.println("数据个数："+root.getData().size());

        rootNode.getChildren().add(loadTreeNode(root));
        //下面直接不放rootNode放loadTreeNode(root)即可
        fileTree_TreeView.setRoot(rootNode);
    }

    public void showDocumentIcon(SparrowDirectory sparrowDirectory){
        List<Document> directoryData = sparrowDirectory.getData();

        //设置外边距
        document_FlowPane.setPadding(new Insets(10));
        //水平间距
        document_FlowPane.setHgap(20);

        for (int i = 0; i < directoryData.size(); i++) {
            if(directoryData.get(i) instanceof SparrowDirectory){
                //如果是目录则显示目录图片
                ImageView dirsImage  = new ImageView("resource/icon/directory.png");
                dirsImage.setFitHeight(100);
                dirsImage.setFitWidth(100);
                document_FlowPane.getChildren().add(dirsImage);
            }else if (directoryData.get(i) instanceof SparrowFile){
                if(directoryData.get(i).getFileCatalog().getExtensionName() == FileTypeEnum.EXE_FILE.getCode()){
                    //如果是可执行文件则显示目录图片
                    ImageView dirsImage  = new ImageView("resource/icon/exe.png");
                    dirsImage.setFitHeight(125);
                    dirsImage.setFitWidth(100);
                    document_FlowPane.getChildren().add(dirsImage);
                }else if(directoryData.get(i).getFileCatalog().getExtensionName() == FileTypeEnum.TXT_FILE.getCode()){
                    //如果是文本文件则显示目录图片
                    ImageView dirsImage  = new ImageView("resource/icon/txt.png");
                    dirsImage.setFitHeight(100);
                    dirsImage.setFitWidth(100);
                    document_FlowPane.getChildren().add(dirsImage);
                }else{
                    //如果是未知文件则显示目录图片
                    ImageView dirsImage  = new ImageView("resource/icon/unknownDoc.png");
                    dirsImage.setFitHeight(100);
                    dirsImage.setFitWidth(100);
                    document_FlowPane.getChildren().add(dirsImage);
                }
            }
        }
    }

    private TreeItem<String> loadTreeNode(Document doc){
        TreeItem<String> leaf = new TreeItem<>();
        if(doc instanceof SparrowFile){
            String[] splits = doc.getFileCatalog().getCatalogName().split("/");
            leaf = new TreeItem<>(splits[splits.length-1]);
        }else if(doc instanceof SparrowDirectory){
            leaf = new TreeItem<>(doc.getFileCatalog().getCatalogName());
            List<Document> data =  ((SparrowDirectory) doc).getData();
            for (Document document:data
                 ) {
                leaf.getChildren().add(loadTreeNode(document));
            }
        }else{
            System.out.println("Error:错误类型!既不是File也不是Directory");
        }
        return leaf;
    }
}
