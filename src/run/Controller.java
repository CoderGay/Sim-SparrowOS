package run;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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

public class Controller {

    @FXML
    private AnchorPane parent;

    @FXML
    private ImageView desktop_wallpaper;

    @FXML
    private Pane desktop_pane;

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

}
