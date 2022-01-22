package com.example;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class MenuSample1 extends Application {

    final PageData[] pages = new PageData[] {
            new PageData("Apple",
                    "The apple is the pomaceous fruit of the apple tree, species Malus "
                            +"domestica in the rose family (Rosaceae). It is one of the most "
                            +"widely cultivated tree fruits, and the most widely known of "
                            +"the many members of genus Malus that are used by humans. "
                            +"The tree originated in Western Asia, where its wild ancestor, "
                            +"the Alma, is still found today.",
                    "Malus domestica"),
            new PageData("Hawthorn",
                    "The hawthorn is a large genus of shrubs and trees in the rose "
                            + "family, Rosaceae, native to temperate regions of the Northern "
                            + "Hemisphere in Europe, Asia and North America. "
                            + "The name hawthorn was "
                            + "originally applied to the species native to northern Europe, "
                            + "especially the Common Hawthorn C. monogyna, and the unmodified "
                            + "name is often so used in Britain and Ireland.",
                    "Crataegus monogyna"),
            new PageData("Ivy",
                    "The ivy is a flowering plant in the grape family (Vitaceae) native"
                            +" to eastern Asia in Japan, Korea, and northern and eastern China."
                            +" It is a deciduous woody vine growing to 30 m tall or more given "
                            +"suitable support,  attaching itself by means of numerous small "
                            +"branched tendrils tipped with sticky disks.",
                    "Parthenocissus tricuspidata"),
            new PageData("Quince",
                    "The quince is the sole member of the genus Cydonia and is native"
                            +" to warm-temperate southwest Asia in the Caucasus region. The "
                            +"immature fruit is green with dense grey-white pubescence, most "
                            +"of which rubs off before maturity in late autumn when the fruit "
                            +"changes color to yellow with hard, strongly perfumed flesh.",
                    "Cydonia oblonga")
    };

    final String[] viewOptions = new String[] {
            "Title",
            "Binomial name",
            "Picture",
            "Description"
    };

    //
    final Entry<String, Effect>[] effects = new Entry[] {
            new SimpleEntry<>("Sepia Tone", new SepiaTone()),
            new SimpleEntry<>("Glow", new Glow()),
            new SimpleEntry<>("Shadow", new DropShadow())
    };

    final ImageView pic = new ImageView();
    final Label name = new Label();
    final Label binName = new Label();
    final Label description = new Label();
    private int currentIndex = -1;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        
        stage.setTitle("Menu Sample");
        //设置垂直布局
        Scene scene = new Scene(new VBox(), 400, 350);
        //设置背景填充色
        scene.setFill(Color.OLDLACE);



        //为页面详细信息的不同部分（标题，正文等）设置格式
        name.setFont(new Font("Verdana Bold", 22));
        binName.setFont(new Font("Arial Italic", 10));
        description.setWrapText(true);
        description.setTextAlignment(TextAlignment.JUSTIFY);
        //设置图片的格式
        pic.setFitHeight(150);
        //锁定宽高比
        pic.setPreserveRatio(true);


        //随机案例，点击按钮以后，页面的属性随机发生变化
        shuffle();


        //创建菜单栏(MenuBar)和菜单(Menu)，添加菜单项(MenuItem)，将菜单分组，创建子菜单(Submenu)，以及设置上下文菜单(Context Menu)。
        //新建菜单栏对象
        MenuBar menuBar = new MenuBar();

        //设置垂直布局
        final VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(0, 10, 0, 10));
        vbox.getChildren().addAll(name, binName, pic, description);

        // 新建菜单对象 File
        Menu menuFile = new Menu("File");
        //创建菜单项(MenuItem)
        MenuItem add = new MenuItem("Shuffle",
                new ImageView(new Image("file:image/new.png")));
        add.setOnAction((ActionEvent t) -> {
            shuffle();
            //visible：可见
            vbox.setVisible(true);
        });

        //创建菜单项(MenuItem)
        MenuItem clear = new MenuItem("Clear");
        clear.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
        clear.setOnAction((ActionEvent t) -> {
            vbox.setVisible(false);
        });

        //创建菜单项(MenuItem)
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction((ActionEvent t) -> {
            System.exit(0);
        });


        //向菜单File中添加 菜单项 MenuItem
        menuFile.getItems().addAll(add, clear, new SeparatorMenuItem(), exit);


        // --- Menu Edit
        Menu menuEdit = new Menu("Edit");
        Menu menuEffect = new Menu("Picture Effect");
        final ToggleGroup groupEffect = new ToggleGroup();
        //将map中的组件添加到 菜单项中
        for (Entry<String, Effect> effect : effects) {
            RadioMenuItem itemEffect = new RadioMenuItem(effect.getKey());
            itemEffect.setUserData(effect.getValue());
            itemEffect.setToggleGroup(groupEffect);
            menuEffect.getItems().add(itemEffect);
        }
        final MenuItem noEffects = new MenuItem("No Effects");
        noEffects.setDisable(true);
        noEffects.setOnAction((ActionEvent t) -> {
            pic.setEffect(null);
            groupEffect.getSelectedToggle().setSelected(false);
            noEffects.setDisable(true);
        });

        groupEffect.selectedToggleProperty().addListener(
                (ObservableValue<? extends Toggle> ov, Toggle old_toggle,
                 Toggle new_toggle) -> {
                    if (groupEffect.getSelectedToggle() != null) {
                        Effect effect =
                                (Effect) groupEffect.getSelectedToggle().getUserData();
                        pic.setEffect(effect);
                        noEffects.setDisable(false);
                    } else {
                        noEffects.setDisable(true);
                    }
                });

        menuEdit.getItems().addAll(menuEffect, noEffects);



        // --- Menu View
        Menu menuView = new Menu("View");

        //在start方法中创建四个Check Menu Item     createMenuItem方法可以实现根据是否勾选,让组件是否显示
        CheckMenuItem titleView = createMenuItem("Title", name);
        CheckMenuItem binNameView = createMenuItem("Binomial name", binName);
        CheckMenuItem picView = createMenuItem("Picture", pic);
        CheckMenuItem descriptionView = createMenuItem("Description", description);

        menuView.getItems().addAll(titleView, binNameView, picView, descriptionView);

        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
        ((VBox) scene.getRoot()).getChildren().addAll(menuBar, vbox);



        //右击可以选中组件进行操作
        final ContextMenu cm = new ContextMenu();
        MenuItem cmItem1 = new MenuItem("Copy Image");
        cmItem1.setOnAction((ActionEvent e) -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putImage(pic.getImage());
            clipboard.setContent(content);
        });

        cm.getItems().add(cmItem1);
        pic.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                cm.show(pic, e.getScreenX(), e.getScreenY());
            }
        });


        stage.setScene(scene);
        stage.show();
    }

    private void shuffle() {
        int i = currentIndex;
        while (i == currentIndex) {
            i = (int) (Math.random() * pages.length);
        }
        pic.setImage(pages[i].image);
        name.setText(pages[i].name);
        binName.setText("(" + pages[i].binNames + ")");
        description.setText(pages[i].description);
        currentIndex = i;
    }


    private class PageData {
        public String name;
        public String description;
        public String binNames;
        public Image image;
        public PageData(String name, String description, String binNames) {
            this.name = name;
            this.description = description;
            this.binNames = binNames;
            image = new Image(getClass().getResourceAsStream("/image/"+name + ".png"));
        }
    }

    //createMenuItem方法
    private static CheckMenuItem createMenuItem (String title, final Node node){
        CheckMenuItem cmi = new CheckMenuItem(title);
        cmi.setSelected(true);
        cmi.selectedProperty().addListener(
                (ObservableValue<? extends Boolean> ov, Boolean old_val,
                 Boolean new_val) -> {
                    node.setVisible(new_val);
                });
        return cmi;
    }
}