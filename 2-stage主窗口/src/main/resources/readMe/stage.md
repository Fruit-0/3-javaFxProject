# 1 认识【stage】 #

*stage*:

中文意思是舞台，在javaFx中，指的值最上层的面板,在 javaFx中，所有的构件都需要在其之下


# 2 stage的常用属性 #
+ _【taitle 标题名称】_

  `stage.setTitle("设置的窗口名称");`


+ _【设置图标】_

  `stage.getIcons().add(new Image("file:/image/logo.png"));`


+ _【resiziable】_

  是否允许调整大小(false-不允许；true-允许)
  `stage.setResizable(false);`


+ _【StageStyle  舞台样式】_

  （所谓平台装饰就是最小化，最大化，X 这些）
  + StageStyle.DECORATED - 具有纯白色背景和平台装饰的舞台。（一般用这个）
  + StageStyle.UNDECORATED - 纯白色背景且无装饰的舞台。
  + StageStyle.TRANSPARENT - 具有透明背景且没有装饰的舞台。
  + StageStyle.UTILITY - 具有纯白色背景和最小平台装饰的舞台（只有一个 X ）

   `stage.initStyle(StageStyle.DECORATED);`


+ _【Modality 窗口模态】_
  + Modality.NONE - 不阻塞任何其他窗口的阶段。（不限制）
  + Modality.WINDOW_MODAL - 阻止输入事件从其所有者（父）传递到其根的所有窗口的阶段。 它的根是最近的没有所有者的祖先窗口。（同级窗口可以，父级不行）
  + Modality.APPLICATION_MODAL - 阻止输入事件从同一应用程序传递到所有窗口的阶段，除了来自其子层次结构的那些。（人话就是除了当前窗口，其余窗口都不能被选中）

  `stage.initModality(Modality.APPLICATION_MODAL);`


+ event 设置点击 窗口的关闭按钮，的事件

  `        Platform.setImplicitExit(false);
          stage.setOnCloseRequest(event ->{
              event.consume();
              //通过弹窗来认为控制程序的关闭与否
              Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
              alert.setTitle("消息提示的抬头");
              alert.setHeaderText(null);
              alert.setContentText("是否退出程序？");
              Optional<ButtonType> buttonType = alert.showAndWait();
              if (buttonType.get() == ButtonType.OK){
                  Platform.exit();
              }
          });`

