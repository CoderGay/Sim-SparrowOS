package run;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;

public class Controller {

    @FXML
    private AnchorPane parent;

    @FXML
    private ImageView desktop_wallpaper;

    @FXML
    private Pane desktop_pane;

    @FXML
    private ImageView fileManager_ImageView;

    @FXML
    private ImageView close_ImageView;

    @FXML
    private ImageView command_ImageView;

    @FXML
    void desktop_right_click(ContextMenuEvent event) {
        System.out.println("你刚刚右键点了一下");
    }

    @FXML
    void changeDesktopWallpaper(ActionEvent event) {
        try {
            /**
             * 弹出一个窗口选择路径
             * */
            FileChooser directoryChooser=new FileChooser();
            File file = directoryChooser.showOpenDialog(new Stage());
            String desktopWallpaperPath = file.getPath();//选择的文件夹路径
            System.out.println(desktopWallpaperPath);

            Image desktopWallpaperImage = new Image(desktopWallpaperPath);

            desktop_wallpaper.setImage(desktopWallpaperImage);


        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void openFileManager(MouseEvent event) throws IOException {
        if(event.getClickCount()==2){
            Parent fileManagerRoot = FXMLLoader.load(getClass().getResource("FileManager.fxml"));
            Stage fileManager_Stage = new Stage();
            fileManager_Stage.setTitle("文件资源管理器");
            fileManager_Stage.setScene(new Scene(fileManagerRoot));
            fileManager_Stage.show();
        }
    }

    @FXML
    void openCommand(MouseEvent event) throws IOException {
        AnchorPane fileManager_AnchorPane = FXMLLoader.load(getClass().getResource("Command.fxml"));
        Stage fileManager_Stage = new Stage();
        fileManager_Stage.setTitle("命令提示符");
        fileManager_Stage.setScene(new Scene(fileManager_AnchorPane));
        fileManager_Stage.show();
    }

    @FXML
    void close_desktop(MouseEvent event) {
        System.exit(0);
    }



}
