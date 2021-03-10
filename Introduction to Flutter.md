# Introduction to Flutter

### 一、Flutter是什么？

 - Flutter是谷歌的移动UI框架，可以快速在iOS和Android上构建高质量的原生用户界面。 Flutter可以与现有的代码一起工作。在全世界，Flutter正在被越来越多的开发者和组织使用，并且Flutter是完全免费、开源的。
 - Flutter 主要是针对跨平台：Android and iOS 的解决方案，它性能优越、并且高效。
 ---
 ### 二、Flutter的优点
 
 - **热重载**：页面每次改动,不需要手动去刷新,可自动刷新。即支持开发过程中热重载。
 - **统一而美观的UI**：Flutter 提供丰富的内置 UI 组件——Material Design（针对 Android）和  Cupertino（适用于 iOS ），不需要担心在众多设备上会有什么不同。
 - **快速**：
	 - Flutter 的UI 渲染性能很好，在生产环境下，Flutter将代码编译成机器码执行、并充分利用GPU的图形加速能力，因此使用Flutter开发的移动应用即使在低配手机上也能实现每秒60帧的UI渲染速度。
	 - flutter 引擎使用C++编写、包括高效的Skia 2D渲染引擎，Dart运行时和文本渲染库。 这个引擎使得Flutter 框架可以自由、灵活、高效地绘制UI组件，而应用开发者则可以用Flutter框架来轻松实现各种设计语言和动画效果。
---
### 三、Flutter的绘制原理

#### 1. Flutter 渲染本质
##### 问题一：一个图像到底是如何显示在屏幕上的？

首先，我们在屏幕上可以看到的所有内容都是计算机绘制出来的图像，无论是视频还是GIF图片，还是操作系统给我们看到的图像化界面都是图像。

##### 问题二：我们为什么能看到类似于动画的效果呢？

这是因为播放的速度非常的快，研究表明：

-   当图片连续播放的频率超过16帧（16张图片），人眼就会感到非常流畅，当少于16帧时，会感到非常卡顿。
-   所以我们平时看到的电影，通常都是24帧或者30帧（李安导演之前拍摄过120帧的电影，目的就是让图片间隔更小，画面更加流畅）

##### 电脑、手机屏幕上的显示

显示器就是以固定的频率显示图像，比如iphone的60Hz，ipad pro的120Hz。一帧图像绘制完毕后，准备绘制下一帧时，显示器会发出一个垂直信号。所以60Hz的频率就会一秒发出60次这样的信号。在计算机系统中，CPU、GPU、和显示器以一种特定的方式协作：

-   CPU将计算机好的显示内容提交给GPU
-   GPU渲染后放入帧缓冲区
-   视频控制器按照VSync信号从帧缓冲区取帧数据传递给显示器
##### Flutter 跟 React Native的本质区别？

-   React Native 之类的框架，只是通过JavaScript虚拟机拓展调用系统组件，由Android 和 iOS系统进行组件的渲染。
-   Flutter是自己完成了组件渲染的闭环。
- --
### 四、Dart语言的优势？

#### Flutter 为什么选择Dart语言作为开发语言？

-   Dart是AOT（Ahead of Time）编译的，编译成快速、可预测的本地代码，使Flutter几乎都可以使用Dart编写，这不仅使Flutetr变得更快，而且几乎所有的东西（包括所有的小组件）都可以定制。
-   Dart也可以JIT（Just in Time）编译，开发周期异常快，工作流颠覆常规（包括Flutter流行的亚秒级有状态热重载）。
-   Dart可以更轻松地构建以60fps运行的流畅动画和转场，Dart可以在没有锁的情况下进行对象分配和垃圾回收，就像JavaScript一样，Dart避免了抢占式调度和共享内存（因而也不许需要锁），由于Flutter应用程序被编译为本地代码，因此它们不需要在领域之间建立缓慢的桥接（例如JavaScript到本地代码)，它的启动速度也快的多。
-   Dart使Flutter不需要单独的声明式布局语言，如JSX或XML，或单独的可视化界面构建器，因为Dart的声明式编译布局易于阅读和可视化，所有的布局使用一种语言，聚集在一处，Flutter很容易提供高级工具，使布局更简单。
-   开发人员发发现Dart特别容易学习，因为它具有静态和动态语言用户都熟悉的特性，并非所有这些功能都是Dart独有的，但它们的组合却恰大好处，使Dart在实现Flutter方面独一无二，因为没有Dart，很难想象Flutter像这样强大。
- --
### 五、渲染引擎Skia

-   Skia 全名Skia Graphics Library（SGL）是一个由C++编写的开源图形库，能在低端设备如手机上呈现高质量的2D图形，最初由Skia公司开发，后被Google收购，应用于Android、Google、Chrome、Chrome OS等等当中。
-   目前，Skia已然是Android官网的图像渲染引擎了，因此Flutter Android SDK无需内嵌Skia引擎就可以获得天然的Skia支持。
-   而对于iOS平台来说，由于Skia是跨平台的，因此它作为Flutter iOS渲染引擎嵌入到Flutter的iOS SDK中，替代了iOS闭源的Core Craphics/ Core Animation/ Core Text，这也正是Flutter iOS SDK 打包的App包体积比Android要大一些的原因。
-   底层渲染引擎统一了，上层开发接口和功能体验也就随即统一了，开发者再也不用操心平台相关的渲染特性了，也就是说，Skia保证了同一套代码调用在Android 和 iOS平台上的渲染效果就是完全一致的。
---
### 六、如何学习Flutter
#### 资源

-   **官网**：阅读Flutter官网的资源是快速入门的最佳方式，同时官网也是了解最新Flutter发展动态的地方，由于目前Flutter仍然处于快速发展阶段，所以建议读者还是时不时的去官网看看有没有新的动态。
    
-   **源码及注释**：源码注释应作为学习Flutter的第一文档，Flutter SDK的源码是开源的，并且注释非常详细，也有很多示例，实际上，Flutter官方的SDK文档就是通过注释生成的。源码结合注释可以帮我们解决大多数问题。
    
-   **Github**：如果遇到的问题在StackOverflow上也没有找到答案，可以去github flutter 项目下提issue。
    
-   **Gallery源码**：Gallery是Flutter官方示例APP，里面有丰富的示例，读者可以在网上下载安装。Gallery的源码在Flutter源码“examples”目录下。
    

#### 社区

-   **StackOverflow**：这是目前全球最大的程序员问答社区，现在也是活跃度最高的Flutter问答社区。StackOverflow上面除了世界各地的Flutter使用者会在上面交流之外，Flutter开发团队的成员也经常会在上面回答问题。
-   **Flutter中文网社区**：Flutter中文网(https://flutterchina.club)，目前也是最大的中文资源社区，上面提供了Flutter官网的文档翻译、开源项目、及案例，还有申请加入组织的入口。
