package run;

import enums.FileTypeEnum;
import enums.SizeEnum;
import equipment.Disk;
import filemanager.CurrentDirCatalog;
import filemanager.FileCatalog;
import filemanager.file.Document;
import filemanager.file.SparrowDirectory;
import filemanager.file.SparrowFile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

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

    @FXML
    private Label return_Label;

    @FXML
    private Label go_Label;

    @FXML
    private Label refresh_Label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showTree();
        initContextMenu();
        initLabel();
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
            ArrayList<Document> list = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                SparrowDirectory directory3 = new SparrowDirectory();
                FileCatalog catalog3 = new FileCatalog();
                catalog3.setCatalogName("子目录"+String.valueOf(i+1));
                directory3.setFileCatalog(catalog);
                directory3.setData(new ArrayList<>());
                list.add(directory3);
            }
            directory1.setData(list);
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

    private TreeItem<String> rootNode;
    private ContextMenu rightClickContextMenu;
    private ContextMenu fileContextMenu;

    private int newNum = 0; //一个全局变量,添加在文件名后面,用于防止新建文件夹或文件重名

    /**
     * 初始化右键菜单
     * */
    private void initContextMenu(){
        rightClickContextMenu = new ContextMenu();
        fileContextMenu = new ContextMenu();

        MenuItem pasteMenuItem =  new MenuItem("粘贴");
        MenuItem newFileMenuItem =  new MenuItem("新建文本文件");
        MenuItem newDirsMenuItem =  new MenuItem("新建文件夹");
        newDirsMenuItem.setOnAction(event -> {
            //TODO 创建文件夹应该返回一个在物理层面创建成功的SparrowDirectory
            SparrowDirectory directory = new SparrowDirectory();
            FileCatalog fileCatalog = new FileCatalog();
            fileCatalog.setExtensionName(FileTypeEnum.DIR_LABEL.getCode());
            String name = "新建文件夹";
            if (newNum>0){
                name+=String.valueOf(newNum);
                newNum++;
            }else{
                newNum++;
            }
            fileCatalog.setCatalogName(name);
            fileCatalog.setFileLength(SizeEnum.BLANK_FILE_SIZE.getCode());
            directory.setFileCatalog(fileCatalog);
            directory.setData(new ArrayList<>());
            directory.setSize(fileCatalog.getFileLength());
            Label label = loadDirsIconLabel(directory);
            document_FlowPane.getChildren().add(label);
        });

        newFileMenuItem.setOnAction(event -> {
            //TODO 创建文本文件应该返回一个在物理层面创建成功的SparrowFile
            SparrowFile file = new SparrowFile();
            FileCatalog fileCatalog = new FileCatalog();
            fileCatalog.setExtensionName(FileTypeEnum.TXT_FILE.getCode());
            String name = "新建文本文件";
            if (newNum>0){
                name+=String.valueOf(newNum);
                newNum++;
            }else{
                newNum++;
            }
            fileCatalog.setCatalogName(name);
            fileCatalog.setFileLength(SizeEnum.BLANK_FILE_SIZE.getCode());
            file.setFileCatalog(fileCatalog);
            file.setData("");
            file.setSize(fileCatalog.getFileLength());

            Label label = loadDocIconLabel("resource/icon/txt.png",file.getFileCatalog().getCatalogName());
            document_FlowPane.getChildren().add(label);
        });
        Menu newMenu = new Menu("新建");
        newMenu.getItems().addAll(newDirsMenuItem,newFileMenuItem);
        //先清理一下,免得越加越多
        rightClickContextMenu.getItems().clear();
        rightClickContextMenu.getItems().addAll(pasteMenuItem,newMenu);

        MenuItem openMenuItem =  new MenuItem("打开");
        MenuItem copyMenuItem =  new MenuItem("复制");
        MenuItem pasteIntoFileMenuItem =  new MenuItem("粘贴");
        MenuItem deleteMenuItem =  new MenuItem("删除");
        MenuItem detailMenuItem =  new MenuItem("属性");
        //先清理一下,免得越加越多
        fileContextMenu.getItems().clear();
        fileContextMenu.getItems().addAll(openMenuItem,copyMenuItem,pasteIntoFileMenuItem,deleteMenuItem,detailMenuItem);

    }

    /**
     * 初始化按钮标签
     * */
    private void initLabel(){
        ImageView dirsImage  = new ImageView("resource/icon/return.png");
        dirsImage.setFitWidth(40);
        dirsImage.setFitHeight(40);
        return_Label.setGraphic(dirsImage);
        return_Label = setLabelOnMouseFocus(return_Label);

        ImageView dirsImage2  = new ImageView("resource/icon/return.png");
        dirsImage2.setFitWidth(40);
        dirsImage2.setFitHeight(40);
        dirsImage2.setRotate(180);//旋转180°
        go_Label.setGraphic(dirsImage2);
        go_Label = setLabelOnMouseFocus(go_Label);

        ImageView dirsImage3  = new ImageView("resource/icon/refresh.png");
        dirsImage3.setFitWidth(40);
        dirsImage3.setFitHeight(40);
        refresh_Label.setGraphic(dirsImage3);
        refresh_Label = setLabelOnMouseFocus(refresh_Label);
    }

    /**
     * 选中动画效果
     * */
    private Label setLabelOnMouseFocus(Label pLabel){
        pLabel.setOnMouseEntered(event -> {
            pLabel.setStyle("-fx-background-color: #808080");
        });
        pLabel.setOnMouseExited(event -> {
            pLabel.setStyle("-fx-background-color: transparent");
        });
        return pLabel;
    }

    /**
     * 展示目录树
     * */
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

    /**
     * 展示文件夹和文件图标
     * */
    public void showDocumentIcon(SparrowDirectory sparrowDirectory){
        List<Document> directoryData = sparrowDirectory.getData();

        //设置外边距
        document_FlowPane.setPadding(new Insets(10));
        //水平间距
        document_FlowPane.setHgap(20);
        document_FlowPane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //右键菜单
            if(event.getButton() == MouseButton.SECONDARY && !fileContextMenu.isShowing()){

                rightClickContextMenu.show(document_FlowPane,event.getScreenX(),event.getScreenY());
                System.out.println(rightClickContextMenu.isShowing());
            }else{
                rightClickContextMenu.hide();
                System.out.println(rightClickContextMenu.isShowing());
            }
        });

        for (int i = 0; i < directoryData.size(); i++) {
            if(directoryData.get(i) instanceof SparrowDirectory){
                //如果是目录则显示目录标签
                Label dirsLabel = loadDirsIconLabel(directoryData.get(i));
                document_FlowPane.getChildren().add(dirsLabel);
            }else if (directoryData.get(i) instanceof SparrowFile){
                //如果不是目录,则
                if(directoryData.get(i).getFileCatalog().getExtensionName() == FileTypeEnum.EXE_FILE.getCode()){
                    //如果是可执行文件则显示exe标签
                    Label dirsLabel = loadDocIconLabel("resource/icon/exe.png", directoryData.get(i).getFileCatalog().getCatalogName());
                    document_FlowPane.getChildren().add(dirsLabel);
                }else if(directoryData.get(i).getFileCatalog().getExtensionName() == FileTypeEnum.TXT_FILE.getCode()){
                    //如果是文本文件则显示文本标签
                    Label dirsLabel = loadDocIconLabel("resource/icon/txt.png", directoryData.get(i).getFileCatalog().getCatalogName());
                    document_FlowPane.getChildren().add(dirsLabel);
                }else{
                    //如果是未知文件则显示未知标签
                    Label dirsLabel = loadDocIconLabel("resource/icon/unknownDoc.png", directoryData.get(i).getFileCatalog().getCatalogName());
                    document_FlowPane.getChildren().add(dirsLabel);
                }
            }
        }
    }

    /**
     * 装载文件图标信息
     * */
    private Label loadDocIconLabel(String imageUrl,String fileName){
        ImageView dirsImage  = new ImageView(imageUrl);
        dirsImage.setFitHeight(100);
        dirsImage.setFitWidth(100);
        Label dirsLabel = new Label(fileName,dirsImage);
        dirsLabel.setOnMouseClicked(event -> {
            if (event.getButton()==MouseButton.SECONDARY ){//右键单击
                fileContextMenu.show(dirsLabel,event.getScreenX(),event.getScreenY());
            }
        });
        dirsLabel.setContentDisplay(ContentDisplay.TOP);//让它上下布局显示
        dirsLabel.setOnMouseEntered(event -> {
            dirsLabel.setStyle("-fx-background-color: #808080");
        });
        dirsLabel.setOnMouseExited(event -> {
            dirsLabel.setStyle("-fx-background-color: transparent");
        });
        return dirsLabel;
    }

    /**
     * 装载文件夹图标信息
     * */
    private Label loadDirsIconLabel(Document directory){
        ImageView dirsImage  = new ImageView("resource/icon/directory.png");
        dirsImage.setFitHeight(100);
        dirsImage.setFitWidth(100);
        Label dirsLabel = new Label(directory.getFileCatalog().getCatalogName(),dirsImage);
        dirsLabel.setContentDisplay(ContentDisplay.TOP);//让它上下布局显示
        final Document document_i = directory;
        dirsLabel.setOnMouseClicked(event-> {
            //左键单击
            if (event.getButton() == MouseButton.PRIMARY){
                CurrentDirCatalog.setCurrentDir((SparrowDirectory)document_i);
                document_FlowPane.getChildren().clear();
                showDocumentIcon((SparrowDirectory)document_i);
            }else if (event.getButton()==MouseButton.SECONDARY ){
                //右键单击
                fileContextMenu.show(dirsLabel,event.getScreenX(),event.getScreenY());
            }else{
                fileContextMenu.hide();
            }
        });
        dirsLabel.setOnMouseEntered(event -> {
            dirsLabel.setStyle("-fx-background-color: #808080");
        });
        dirsLabel.setOnMouseExited(event -> {
            dirsLabel.setStyle("-fx-background-color: transparent");
        });
        return dirsLabel;
    }

    /**
     * 装载目录树结点
     * */
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

    public void showDocumentDetail(){
        Disk disk = Disk.getDisk();
        SparrowDirectory curDirCatalog = CurrentDirCatalog.getCurrentDir();
        showDocumentIcon(curDirCatalog);

    }

}
