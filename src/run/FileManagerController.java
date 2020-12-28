package run;

import VO.DiskBlockVO;
import enums.FileTypeEnum;
import enums.SizeEnum;
import equipment.Disk;
import equipment.DiskBlock;
import filemanager.CurrentDirCatalog;
import filemanager.FileCatalog;
import filemanager.WriteFile;
import filemanager.file.Document;
import filemanager.file.SparrowDirectory;
import filemanager.file.SparrowFile;
import filemanager.fileserver.CopyFile;
import filemanager.fileserver.CreateFile;
import filemanager.fileserver.DeleteFile;
import filemanager.fileserver.MakeDir;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import sun.security.util.Cache;
import tools.FileTool;

import java.io.File;
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
    private Label path_Label;

    @FXML
    private Label absolutePath_Label;

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

    @FXML
    private TableView<DiskBlockVO> disk_TableView;

//    /**
//     * 测试数据,记得要删除
//     * */
//    private SparrowDirectory sparrowDirectory = new SparrowDirectory();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshPath();
        showTree();
        initContextMenu();
        initLabel();
        loadDiskTableView();
        /**
         * 测试数据,待会要删掉哈
         * */

//        List<Document> documentList = new ArrayList<>();
//        for (int i = 0; i <10; i++) {
//            SparrowDirectory directory1 = new SparrowDirectory();
//            FileCatalog catalog = new FileCatalog();
//            catalog.setCatalogName("\\子目录"+String.valueOf(i+1));
//            directory1.setFileCatalog(catalog);
//            ArrayList<Document> list = new ArrayList<>();
//            for (int j = 0; j < 10; j++) {
//                SparrowDirectory directory3 = new SparrowDirectory();
//                FileCatalog catalog3 = new FileCatalog();
//                catalog3.setCatalogName("\\子目录"+String.valueOf(i+1));
//                directory3.setFileCatalog(catalog);
//                directory3.setData(new ArrayList<>());
//                list.add(directory3);
//            }
//            directory1.setData(list);
//            documentList.add(directory1);
//        }
//
//        SparrowFile file = new SparrowFile();
//        FileCatalog catalog = new FileCatalog();
//        catalog.setCatalogName("\\a.exe");
//        catalog.setExtensionName(1);
//        file.setFileCatalog(catalog);
//        documentList.add(file);
//
//        SparrowFile file1 = new SparrowFile();
//        FileCatalog catalog1 = new FileCatalog();
//        catalog1.setCatalogName("\\ab.txt");
//        catalog1.setExtensionName(2);
//        file1.setFileCatalog(catalog1);
//        documentList.add(file1);
//
//        SparrowFile file2 = new SparrowFile();
//        FileCatalog catalog2 = new FileCatalog();
//        catalog2.setCatalogName("\\abc.word");
//        catalog2.setExtensionName(3);
//        file2.setFileCatalog(catalog2);
//        documentList.add(file2);
//
//        FileCatalog fileCatalog = new FileCatalog();
//        fileCatalog.setCatalogName("\\根目录");
//        sparrowDirectory.setFileCatalog(fileCatalog);
//        sparrowDirectory.setData(documentList);
        /**
         * 测试数据装填完毕
         * */

        Disk disk = Disk.getDisk();
        SparrowDirectory root = disk.getRoot();
        showDocumentIcon(root);
    }

    private TreeItem<String> rootNode;
    private ContextMenu rightClickContextMenu;
    private ContextMenu fileContextMenu;


    MenuItem copyMenuItem =  new MenuItem("复制");
    MenuItem pasteIntoFileMenuItem =  new MenuItem("粘贴");
    MenuItem deleteMenuItem =  new MenuItem("删除");
    MenuItem detailMenuItem =  new MenuItem("属性");
    MenuItem renameMenuItem =  new MenuItem("重命名");

    MenuItem pasteMenuItem =  new MenuItem("粘贴");

    /**
     * 文本文件中的菜单项
     * */
    MenuItem saveMenuItem = new MenuItem("保存");
    MenuItem deleteTxtMenuItem = new MenuItem("清空");

    /**
     * 文本文件中的控件
     * */
    TextArea textArea =  new TextArea();


    /**
     * 文件的提示框控件
     * */
    Tooltip file_Tooltip = new Tooltip();

    /**
     * 属性面板的控件
     * */
    TextField attribute_TextField = new TextField();
    ToggleGroup readOnlyGroup = new ToggleGroup();


    private int newNum = 0; //一个全局变量,添加在文件名后面,用于防止新建文件夹或文件重名

    private SparrowDirectory tempHistoryDir;

    private Document clipboard;//剪贴板

    /**
     * 初始化右键菜单
     * */
    private void initContextMenu(){
        rightClickContextMenu = new ContextMenu();
        fileContextMenu = new ContextMenu();


        MenuItem newFileMenuItem =  new MenuItem("新建文本文件");
        MenuItem newDirsMenuItem =  new MenuItem("新建文件夹");
        newDirsMenuItem.setOnAction(event -> {
            //TODO 创建文件夹应该返回一个在物理层面创建成功的SparrowDirectory √
            MakeDir makeDir = new MakeDir();
<<<<<<< Updated upstream
            newNum++;
            SparrowDirectory directory = makeDir.mkFile("新建文件夹"+String.valueOf(newNum));
=======
            SparrowDirectory directory = makeDir.mkFile(FileTool.getNewName("新建文件夹",true));
>>>>>>> Stashed changes
            Label label = loadDirsIconLabel(directory);
            document_FlowPane.getChildren().add(label);
            refreshFileTree();
            refreshDiskBlockTable();
            refreshFlowPaneDisplay();
        });

        newFileMenuItem.setOnAction(event -> {
            //TODO 创建文本文件应该返回一个在物理层面创建成功的SparrowFile √
            CreateFile createFile = new CreateFile();
            SparrowFile file = null;
            try {
<<<<<<< Updated upstream
                newNum++;
                file = createFile.createFile("新建文件"+String.valueOf(newNum));
=======
                file = createFile.createFile(FileTool.getNewName("新建文件",true));
>>>>>>> Stashed changes
            } catch (Exception e) {
                e.printStackTrace();
            }

            Label label = loadDocIconLabel(file,"resource/icon/txt.png");
            document_FlowPane.getChildren().add(label);
            refreshFileTree();
            refreshDiskBlockTable();
            refreshFlowPaneDisplay();
        });
        Menu newMenu = new Menu("新建");
        newMenu.getItems().addAll(newDirsMenuItem,newFileMenuItem);
        //先清理一下,免得越加越多
        rightClickContextMenu.getItems().clear();
        rightClickContextMenu.getItems().addAll(pasteMenuItem,newMenu);


        //先清理一下,免得越加越多
        fileContextMenu.getItems().clear();
        fileContextMenu.getItems().addAll(copyMenuItem,pasteIntoFileMenuItem,deleteMenuItem,renameMenuItem,detailMenuItem);

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
        return_Label.setOnMouseClicked(event -> {
            tempHistoryDir = CurrentDirCatalog.getCurrentDir();
            CurrentDirCatalog.setCurrentDir(CurrentDirCatalog.getFatherDir());
            System.out.println(CurrentDirCatalog.getCurrentDir().getFileCatalog().getCatalogName());
            System.out.println("个数 "+CurrentDirCatalog.getCurrentDir().getData().size());
            document_FlowPane.getChildren().clear();
            System.out.println(document_FlowPane.getChildren().size());
            showDocumentIcon(CurrentDirCatalog.getCurrentDir());
            refreshPath();
        });


        ImageView dirsImage2  = new ImageView("resource/icon/return.png");
        dirsImage2.setFitWidth(40);
        dirsImage2.setFitHeight(40);
        dirsImage2.setRotate(180);//旋转180°
        go_Label.setGraphic(dirsImage2);
        go_Label = setLabelOnMouseFocus(go_Label);
        go_Label.setOnMouseClicked(event -> {
            //如果都没点击去过就没有啦
            if(tempHistoryDir!=null){
                CurrentDirCatalog.setCurrentDir(tempHistoryDir);
                document_FlowPane.getChildren().clear();
                showDocumentIcon(CurrentDirCatalog.getCurrentDir());
                refreshPath();
            }
        });

        ImageView dirsImage3  = new ImageView("resource/icon/refresh.png");
        dirsImage3.setFitWidth(40);
        dirsImage3.setFitHeight(40);
        refresh_Label.setGraphic(dirsImage3);
        refresh_Label = setLabelOnMouseFocus(refresh_Label);
        refresh_Label.setOnMouseClicked(event -> {
            document_FlowPane.getChildren().clear();
            showDocumentIcon(CurrentDirCatalog.getCurrentDir());
            System.out.println("用户刷新了一下");
        });
    }

    /**
     * 刷新当前路径显示
     * */
    private void refreshPath(){
        String pathName = FileTool.getEndFileName(CurrentDirCatalog.getCurrentDir().getFileCatalog().getCatalogName());
        ImageView imageView = new ImageView("resource/icon/资源管理器.png");
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        path_Label.setGraphic(imageView);
        path_Label.setPadding(new Insets(10));
        path_Label.setText(pathName);
        absolutePath_Label.setPadding(new Insets(10));
        StringBuilder strBuilder = new StringBuilder();
        String[] split = CurrentDirCatalog.getCurrentDir().getFileCatalog().getCatalogName().split("/");
        if (split.length==0){
            strBuilder.append("根目录");
        }else {
            strBuilder.append("根目录");
            for (int i = 1; i < split.length; i++) {
                strBuilder.append("> ");
                strBuilder.append(split[i]);
            }
        }
        absolutePath_Label.setText(strBuilder.toString());
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
        fileTree_TreeView.setRoot(loadTreeNode(root));
    }

    /**
     * 展示文件夹和文件图标
     * */
    public void showDocumentIcon(SparrowDirectory sparrowDirectory){
        List<Document> directoryData = sparrowDirectory.getData();
        setPasteMenuItem();
        //设置外边距
        document_FlowPane.setPadding(new Insets(10));
        //水平间距
        document_FlowPane.setHgap(20);
        document_FlowPane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //右键菜单
            if(event.getButton() == MouseButton.SECONDARY && !fileContextMenu.isShowing()){
                rightClickContextMenu.show(document_FlowPane,event.getScreenX(),event.getScreenY());
            }else{
                rightClickContextMenu.hide();
            }
        });

        for (int i = 0; i < directoryData.size(); i++) {
            if(directoryData.get(i) instanceof SparrowDirectory){
                //如果是目录则显示目录标签
                Label dirsLabel = loadDirsIconLabel(directoryData.get(i));
                document_FlowPane.getChildren().add(dirsLabel);
                setAttributeMenuItem(directoryData.get(i));
            }else if (directoryData.get(i) instanceof SparrowFile){
                //如果不是目录,则
                if(directoryData.get(i).getFileCatalog().getExtensionName() == FileTypeEnum.EXE_FILE.getCode()){
                    //如果是可执行文件则显示exe标签
                    Label dirsLabel = loadDocIconLabel(directoryData.get(i),"resource/icon/exe.png");
                    document_FlowPane.getChildren().add(dirsLabel);
                    setAttributeMenuItem(directoryData.get(i));
                }else if(directoryData.get(i).getFileCatalog().getExtensionName() == FileTypeEnum.TXT_FILE.getCode()){
                    //如果是文本文件则显示文本标签
                    Label dirsLabel = loadDocIconLabel(directoryData.get(i),"resource/icon/txt.png");
                    document_FlowPane.getChildren().add(dirsLabel);
                    setAttributeMenuItem(directoryData.get(i));
                }else{
                    //如果是未知文件则显示未知标签
                    Label dirsLabel = loadDocIconLabel(directoryData.get(i),"resource/icon/unknownDoc.png");
                    document_FlowPane.getChildren().add(dirsLabel);
                    setAttributeMenuItem(directoryData.get(i));
                }
            }
        }
    }

    /**
     * 装载文件图标信息
     * */
    private Label loadDocIconLabel(Document document,String imageUrl){
        String fileName = FileTool.getEndFileName(document.getFileCatalog().getCatalogName());
        ImageView dirsImage  = new ImageView(imageUrl);
        dirsImage.setFitHeight(100);
        dirsImage.setFitWidth(100);
        Label dirsLabel = new Label(fileName,dirsImage);
        dirsLabel.setOnMouseClicked(event -> {
            if (event.getButton()==MouseButton.SECONDARY ){//右键单击
                fileContextMenu.show(dirsLabel,event.getScreenX(),event.getScreenY());
                //设置该选项的属性菜单项
                setAttributeMenuItem(document);
                //设置该选项的复制菜单项
                setCopyMenuItem(document);
                //设置该选项的粘贴菜单项
                failedPasteMenuItem();
                //设置该选项的删除菜单项
                setDeleteMenuItem(document);
                //设置该选项的重命名菜单项
                setRenameMenuItem(document);
            }else if(event.getButton() == MouseButton.PRIMARY){
                if (document.getFileCatalog().getExtensionName()==FileTypeEnum.TXT_FILE.getCode()){
                    showTXTDocument((SparrowFile) document);
                }else if(document.getFileCatalog().getExtensionName()==FileTypeEnum.EXE_FILE.getCode()){
                    showTipWindow(document.getFileCatalog().getCatalogName(),"程序已运行");
                }else {
                    showTipWindow(document.getFileCatalog().getCatalogName(),"未找到可打开该文件程序");
                }
            }
        });
        dirsLabel.setContentDisplay(ContentDisplay.TOP);//让它上下布局显示
        dirsLabel.setOnMouseEntered(event -> {
            dirsLabel.setStyle("-fx-background-color: #808080");
            setFileTooltip(document);
            dirsLabel.setTooltip(file_Tooltip);
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
        String directoryName = FileTool.getEndFileName(directory.getFileCatalog().getCatalogName());
        ImageView dirsImage  = new ImageView("resource/icon/directory.png");
        dirsImage.setFitHeight(100);
        dirsImage.setFitWidth(100);
        Label dirsLabel = new Label(directoryName,dirsImage);
        dirsLabel.setContentDisplay(ContentDisplay.TOP);//让它上下布局显示
        final Document document_i = directory;
        dirsLabel.setOnMouseClicked(event-> {
            //左键单击
            if (event.getButton() == MouseButton.PRIMARY){
                CurrentDirCatalog.setCurrentDir((SparrowDirectory)document_i);
                System.out.println(document_i.getFileCatalog().getCatalogName());
                refreshPath();
                document_FlowPane.getChildren().clear();
                showDocumentIcon((SparrowDirectory)document_i);
            }else if (event.getButton()==MouseButton.SECONDARY ){
                //右键单击
                fileContextMenu.show(dirsLabel,event.getScreenX(),event.getScreenY());
                //设置该选项的属性菜单项
                setAttributeMenuItem(directory);
                //设置该选项的复制菜单项
                setCopyMenuItem(directory);
                //设置该选项的粘贴菜单项
                setPasteIntoFileMenuItem((SparrowDirectory) directory);
                //设置该选项的删除菜单项
                setDeleteMenuItem(directory);
                //设置该选项的重命名菜单项
                setRenameMenuItem(directory);
            }else{
                fileContextMenu.hide();
            }
        });
        dirsLabel.setOnMouseEntered(event -> {
            dirsLabel.setStyle("-fx-background-color: #808080");
            setFileTooltip(directory);
            dirsLabel.setTooltip(file_Tooltip);
        });
        dirsLabel.setOnMouseExited(event -> {
            dirsLabel.setStyle("-fx-background-color: transparent");
        });
        return dirsLabel;
    }

    /**
     * 装载属性菜单项
     * */
    private void setAttributeMenuItem(Document doc){
        detailMenuItem.setOnAction(event -> {
            attribute_TextField.setFocusTraversable(false);
            showAttributeWindow(doc);
        });
    }

    /**
     * 装载复制菜单项
     * */
    private void setCopyMenuItem(Document doc){
        copyMenuItem.setOnAction(event -> {
            clipboard = doc;
            System.out.println(clipboard.toString()+"已被复制到剪贴板");
        });
    }

    /**
     * 装载粘贴菜单项
     * */
    private void setPasteIntoFileMenuItem(SparrowDirectory doc){
        pasteIntoFileMenuItem.setOnAction(event -> {
            if (clipboard==null){
                System.out.println("剪贴板为空");
                return;
            }

            //TODO 把文件粘贴到该目录下 √
            CopyFile copyFile = new CopyFile();
            Document newDocument = copyFile.copy(clipboard);
            FileCatalog newDocumentFileCatalog = newDocument.getFileCatalog();
            String curCatalog = CurrentDirCatalog.getCurrentDir().getFileCatalog().getCatalogName();
            newDocumentFileCatalog.setCatalogName(curCatalog+FileTool.getNewName(newDocumentFileCatalog.getCatalogName(),false));
            newDocument.setFileCatalog(newDocumentFileCatalog);

            List<Document> data = doc.getData();
            data.add(newDocument);
            doc.setData(data);
            copyFile.pasteDir2Dir(doc);
            //TODO 把这个文件装进硬盘 √
            System.out.println(clipboard.toString()+"完成粘贴");
            refreshFileTree();
            refreshDiskBlockTable();
        });
    }
    private void failedPasteMenuItem(){
        pasteIntoFileMenuItem.setOnAction(event -> {
            System.out.println("别调皮!");
        });
    }

    /**
     * 装载重命名菜单项
     * */
    private void setRenameMenuItem(Document document){
        renameMenuItem.setOnAction(event -> {
            attribute_TextField.setEditable(true);
            attribute_TextField.setFocusTraversable(true);
            attribute_TextField.setOnKeyPressed(event1 -> {
                if (event1.getCode()== KeyCode.ENTER){
                    String newFileName = attribute_TextField.getText();
                    String curCatalog = CurrentDirCatalog.getCurrentDir().getFileCatalog().getCatalogName();
                    System.out.println(document.getFileCatalog().getCatalogName()+"的文件名已改为："+newFileName);
                    document.getFileCatalog().setCatalogName(curCatalog+"/"+newFileName);
                    attribute_TextField.setText(newFileName);

                    //TODO 重命名后保存至硬盘,并刷新显示 √
                    FileTool.renameFile(document);
                    document_FlowPane.getChildren().clear();
                    showDocumentIcon(CurrentDirCatalog.getCurrentDir());//刷新了一下
                    //TODO 重命名后保存至硬盘,并刷新显示
                    refreshFlowPaneDisplay();
                    refreshFileTree();
                }
            });
            showAttributeWindow(document);
        });
    }

    /**
     * 装载删除菜单项
     * */
    private void setDeleteMenuItem(Document doc){
        deleteMenuItem.setOnAction(event -> {
            //TODO 删除doc文件并刷新 √
            new DeleteFile().deleteFile(doc);
            //刷新
            System.out.println("已成功删除"+doc.toString());
            refreshFlowPaneDisplay();
            refreshFileTree();
            refreshDiskBlockTable();
            /*document_FlowPane.getChildren().clear();
            showDocumentIcon(CurrentDirCatalog.getCurrentDir());*/
        });
    }

    /**
     * 装载粘贴菜单项
     * */
    private void setPasteMenuItem(){
        pasteMenuItem.setOnAction(event -> {
            if (clipboard==null){
                System.out.println("剪贴板为空");
                return;
            }
            SparrowDirectory currentDir = CurrentDirCatalog.getCurrentDir();
            List<Document> data = currentDir.getData();
            //TODO 接收clipboard.data,返回一个可用的Document(接口的意思)然后才放下去 √
            CopyFile copyFile = new CopyFile();
            Document newDocument = copyFile.copy(clipboard);
            FileCatalog newDocumentFileCatalog = newDocument.getFileCatalog();
            String curCatalog = CurrentDirCatalog.getCurrentDir().getFileCatalog().getCatalogName();
            newDocumentFileCatalog.setCatalogName(curCatalog+FileTool.getNewName(newDocumentFileCatalog.getCatalogName(),false));
            newDocument.setFileCatalog(newDocumentFileCatalog);

            data.add(newDocument);
            currentDir.setData(data);
            //TODO 装载回去,即将currentDir写回硬盘 √
            copyFile.pasteDir2Dir(currentDir);

            System.out.println(clipboard+"粘贴成功");
            refreshFlowPaneDisplay();
            refreshDiskBlockTable();
            refreshFileTree();
        });
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
            leaf = new TreeItem<>(FileTool.getEndFileName(doc.getFileCatalog().getCatalogName()));
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

    /**
     * 展示属性窗口
     * */
    private void showAttributeWindow(Document doc){
        AnchorPane attribute_AnchorPane = new AnchorPane();
        attribute_AnchorPane = loadAttribute(attribute_AnchorPane,doc);
        Stage attribute_Stage = new Stage();
        attribute_Stage.setTitle("属性");
        attribute_Stage.setAlwaysOnTop(true);
        Scene attributeScene = new Scene(attribute_AnchorPane);
        attribute_Stage.setScene(attributeScene);
        attribute_Stage.show();
    }


    /**
     * 装载属性面板
     * */
    private AnchorPane loadAttribute(AnchorPane attribute_AnchorPane,Document document){
        /**
         * 初始化控件
         */
        ImageView attribute_ImageView = new ImageView();
        attribute_ImageView.setFitHeight(50);
        attribute_ImageView.setFitWidth(50);
        attribute_ImageView.setLayoutX(27);
        attribute_ImageView.setLayoutY(28);

        attribute_TextField.setLayoutX(99);
        attribute_TextField.setLayoutY(38);


        Label position_text_Label = new Label();
        position_text_Label.setText("位置:");
        position_text_Label.setStyle("-fx-background-color: transparent");
        position_text_Label.setPrefHeight(30);
        position_text_Label.setPrefWidth(36);
        position_text_Label.setLayoutX(51);
        position_text_Label.setLayoutY(129);

        Label size_text_Label = new Label();
        size_text_Label.setText("大小:");
        size_text_Label.setStyle("-fx-background-color: transparent");
        size_text_Label.setPrefHeight(30);
        size_text_Label.setPrefWidth(36);
        size_text_Label.setLayoutX(51);
        size_text_Label.setLayoutY(189);

        Label type_text_Label = new Label();
        type_text_Label.setText("类型:");
        type_text_Label.setStyle("-fx-background-color: transparent");
        type_text_Label.setPrefHeight(30);
        type_text_Label.setPrefWidth(36);
        type_text_Label.setLayoutX(51);
        type_text_Label.setLayoutY(243);

        Label privilege_text_Label = new Label();
        privilege_text_Label.setText("权限:");
        privilege_text_Label.setStyle("-fx-background-color: transparent");
        privilege_text_Label.setPrefHeight(30);
        privilege_text_Label.setPrefWidth(36);
        privilege_text_Label.setLayoutX(51);
        privilege_text_Label.setLayoutY(320);

        Label position_Label = new Label();
        position_Label.setPrefHeight(30);
        position_Label.setPrefWidth(202);
        position_Label.setStyle("-fx-background-color: transparent");
        position_Label.setLayoutX(99);
        position_Label.setLayoutY(129);

        Label size_Label = new Label();
        size_Label.setPrefHeight(30);
        size_Label.setPrefWidth(202);
        size_Label.setStyle("-fx-background-color: transparent");
        size_Label.setLayoutX(99);
        size_Label.setLayoutY(189);

        Label type_Label = new Label();
        type_Label.setPrefHeight(30);
        type_Label.setPrefWidth(202);
        type_Label.setStyle("-fx-background-color: transparent");
        type_Label.setLayoutX(99);
        type_Label.setLayoutY(243);

        Line line0 = new Line();
        line0.setLayoutX(121);
        line0.setLayoutY(91);
        line0.setStartX(-100);
        line0.setStartY(0);
        line0.setEndX(202);
        line0.setEndY(0);
        line0.setStyle("-fx-stroke: #9f9f9f");

        Line line2 = new Line();
        line2.setLayoutX(122);
        line2.setLayoutY(296);
        line2.setStartX(-100);
        line2.setStartY(0);
        line2.setEndX(202);
        line2.setEndY(0);
        line2.setStyle("-fx-stroke: #9f9f9f");


        /**
         * 装载图片
         * */
        Image image;
        if (document instanceof SparrowDirectory){
            image = new Image("resource/icon/directory.png");
        }else{
            if (document.getFileCatalog().getExtensionName()== FileTypeEnum.TXT_FILE.getCode())
                image = new Image("resource/icon/txt.png");
            else if (document.getFileCatalog().getExtensionName()== FileTypeEnum.EXE_FILE.getCode())
                image = new Image("resource/icon/exe.png");
            else
                image = new Image("resource/icon/unknownDoc.png");
        }
        attribute_ImageView.setImage(image);

        String[] strings = document.getFileCatalog().getCatalogName().split("/");
        String fileName = strings[strings.length-1];
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < strings.length-1; i++) {
            stringBuilder.append(strings[i]);
        }
        String position = stringBuilder.toString();
        /**
         * 装载文件名
         * */
        attribute_TextField.setText(fileName);
        /**
         * 装载文件位置
         * */
        position_Label.setText(position);
        /**
         * 装载文件大小
         * */
        size_Label.setText(String.valueOf(document.getFileCatalog().getFileLength())+"B");
        /**
         * 装载文件类型
         * */

        if (document.getFileCatalog().getExtensionName()==FileTypeEnum.TXT_FILE.getCode()){
            type_Label.setText(".txt");
        }else if(document.getFileCatalog().getExtensionName()==FileTypeEnum.EXE_FILE.getCode()){
            type_Label.setText(".exe");
        }else if (document.getFileCatalog().getExtensionName()==FileTypeEnum.DIR_LABEL.getCode()){
            type_Label.setText("文件夹");
        }else{
            type_Label.setText("未知文件");
        }

        RadioButton radioButton = new RadioButton();
        radioButton.setPrefWidth(59);
        radioButton.setPrefHeight(20);
        radioButton.setLayoutX(114);
        radioButton.setLayoutY(325);
        radioButton.setText("只读");
        if(document.getFileCatalog().getFileAttribute()[7]==1){
            radioButton.setSelected(true);
        }

        radioButton.setToggleGroup(readOnlyGroup);

        readOnlyGroup.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle,
                                                            Toggle new_toggle) -> {
            if (readOnlyGroup.getSelectedToggle() != null) {
                FileCatalog catalog = document.getFileCatalog();
                catalog.setFileAttribute(catalog.getFileAttribute()[4]*1000+1);
                document.setFileCatalog(catalog);

                //TODO 修改只读属性
                FileTool.renameFile(document);
            }
        });


        if(document.getFileCatalog().getFileAttribute()!=null&&document.getFileCatalog().getFileAttribute()[7]==1){
            radioButton.setSelected(true);
        }

        attribute_AnchorPane.getChildren().addAll(attribute_ImageView,attribute_TextField,position_text_Label,size_text_Label,type_text_Label,privilege_text_Label,position_Label,size_Label,type_Label,line0,line2,radioButton);
        attribute_AnchorPane.setPrefWidth(347);
        attribute_AnchorPane.setPrefHeight(408);

        return attribute_AnchorPane;
    }

    /**
     * 展示文本编辑器
     * */
    private void showTXTDocument(SparrowFile sparrowFile){
        /**
         * 初始化控件
         */
        AnchorPane txt_AnchorPane = new AnchorPane();
        txt_AnchorPane.setPrefHeight(400);
        txt_AnchorPane.setPrefWidth(600);


        textArea.setLayoutY(29);
        textArea.setPrefHeight(359);
        textArea.setPrefWidth(600);
        textArea.setStyle("-fx-background-color: transparent, white, transparent, white");

        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("文件");
        Menu editMenu = new Menu("编辑");
        Menu helpMenu = new Menu("帮助");

        setDeleteTxtMenuItem();
        MenuItem aboutMenuItem = new MenuItem("关于");


        fileMenu.getItems().addAll(saveMenuItem);
        editMenu.getItems().addAll(deleteTxtMenuItem);
        helpMenu.getItems().addAll(aboutMenuItem);
        menuBar.getMenus().addAll(fileMenu,editMenu,helpMenu);
        menuBar.setPrefHeight(32);
        menuBar.setPrefWidth(600);

        setSaveMenuItem(sparrowFile);

        Label txtLengthLabel = new Label();
        txtLengthLabel.setLayoutX(524);
        txtLengthLabel.setLayoutY(386);
        txtLengthLabel.setPrefHeight(20);
        txtLengthLabel.setPrefWidth(76);

        String data;
        if (sparrowFile.getData()!=null){
            data = sparrowFile.getData();
        }else {
            data = "";
        }
        textArea.setText(data);
        txtLengthLabel.setText(String.valueOf(data.length()));

        txt_AnchorPane.getChildren().addAll(textArea,menuBar,txtLengthLabel);
        Stage txt_Stage = new Stage();
        txt_Stage.setTitle(sparrowFile.getFileCatalog().getCatalogName());
        txt_Stage.setAlwaysOnTop(true);
        Scene txtScene = new Scene(txt_AnchorPane);
        txt_Stage.setScene(txtScene);
        txt_Stage.show();

    }

    /**
     * 设置保存菜单项
     * */
    private void setSaveMenuItem(SparrowFile txtFile){
        saveMenuItem.setOnAction(event -> {
            //TODO 保存 √
<<<<<<< Updated upstream
            if (txtFile.getFileCatalog().getFileAttribute()[7]==1){
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(textArea.getText());
            txtFile.setData(stringBuilder.toString());
            FileCatalog fileCatalog =txtFile.getFileCatalog();
            fileCatalog.setFileLength(stringBuilder.toString().length());
            txtFile.setFileCatalog(fileCatalog);
            //TODO 保存至硬盘 √
            WriteFile writeFile = new WriteFile();
            writeFile.writeFile(txtFile);

            String fileName = FileTool.getEndFileName(txtFile.getFileCatalog().getCatalogName());
            System.out.println("已保存："+txtFile.getData()+"至"+fileName);

=======
            if (txtFile.getFileCatalog().getFileAttribute()[7]==1){//该文件为只读文件
                return;
            }else{
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(textArea.getText());
                txtFile.setData(stringBuilder.toString());
                FileCatalog fileCatalog =txtFile.getFileCatalog();
                fileCatalog.setFileLength(stringBuilder.toString().length());
                txtFile.setFileCatalog(fileCatalog);
                //TODO 保存至硬盘 √
                WriteFile writeFile = new WriteFile();
                writeFile.writeFile(txtFile);

                String fileName = FileTool.getEndFileName(txtFile.getFileCatalog().getCatalogName());
                System.out.println("已保存："+txtFile.getData()+"至"+fileName);
            }
>>>>>>> Stashed changes
        });
    }

    /**
     * 设置文本编辑器的清空菜单项
     * */
    private void setDeleteTxtMenuItem(){
        deleteTxtMenuItem.setOnAction(event -> {
            textArea.setText("");
        });
    }

    private void showTipWindow(String title,String tip){

        Alert information = new Alert(Alert.AlertType.INFORMATION,tip);
        information.setTitle(title);         //设置标题，不设置默认标题为本地语言的information
        information.setHeaderText(title);    //设置头标题，默认标题为本地语言的information
        information.show();
    }

    private void loadDiskTableView(){

        /**
         * 声明表格的列控件
         * */
        //每个Table的列
        TableColumn numCol = new TableColumn("盘块号");
        // 设置宽度
        numCol.setMinWidth(35);
        // 设置分箱的类下面的属性名
        numCol.setCellValueFactory(
                new PropertyValueFactory<>("num"));

        TableColumn isAvailableCol = new TableColumn("是否可用");
        isAvailableCol.setMinWidth(50);
        isAvailableCol.setCellValueFactory(
                new PropertyValueFactory<>("isAvailable"));

        TableColumn occupiedSizeCol = new TableColumn("占用大小");
        occupiedSizeCol.setMinWidth(50);
        occupiedSizeCol.setCellValueFactory(
                new PropertyValueFactory<>("occupiedSize"));

        TableColumn nextIndexCol = new TableColumn("后续盘块号");
        nextIndexCol.setMinWidth(60);
        nextIndexCol.setCellValueFactory(
                new PropertyValueFactory<>("nextIndex"));


        ObservableList<DiskBlockVO> targetData =
                        FXCollections.observableArrayList();

        /**
         * 组装数据源
         * */
        Disk disk = Disk.getDisk();
        int[] fileAllocateTable = disk.getFileAllocateTable();
        List<DiskBlock> diskBlockList = disk.getDiskBlockList();
        for(int i=0;i<diskBlockList.size();i++){
            DiskBlockVO diskBlockVO = new DiskBlockVO(i+1,diskBlockList.get(i).isAvailable(),diskBlockList.get(i).getOccupiedSize(),fileAllocateTable[i]);
            targetData.add(diskBlockVO);
        }

        /**
         * 装载数据
         * */
        disk_TableView.getItems().clear();
        disk_TableView.setItems(targetData);
        /**
         * 装载列
         * */
        disk_TableView.getColumns().addAll(numCol,isAvailableCol,nextIndexCol);
    }

    /**
     * 设置提示控件内容
     * */
    private void setFileTooltip(Document document){
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("文件类型:"+FileTool.getExtensionName(document));
        strBuilder.append("\n大小:"+document.getFileCatalog().getFileLength()+"B");
        strBuilder.append("\n所占盘块号："+FileTool.getOccupiedDiskBlockNum(document));
        file_Tooltip.setText(strBuilder.toString());
    }

    /**
     * 刷新当前面板
     * */
    private void refreshFlowPaneDisplay(){
        document_FlowPane.getChildren().clear();
        showDocumentIcon(CurrentDirCatalog.getCurrentDir());
    }

    /**
     * 刷新当前目录树
     * */
    private void refreshFileTree(){
        rootNode.getChildren().clear();
        showTree();
    }

    /**
     * 刷新磁盘块显示表
     * */
    private void refreshDiskBlockTable(){
        loadDiskTableView();
    }



}
