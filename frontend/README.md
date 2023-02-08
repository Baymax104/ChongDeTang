# Android端说明

## 项目结构

APP使用MVVM架构

- entity文件夹：存放实体类
- view文件夹：存放Activity和Fragment，主页面包含多个子页面时，创建子文件夹存放
- viewModel文件夹：存放viewModel类，Fragment之间共享Activity的ViewModel，Activity之间不共享ViewModel，每个包含数据的Activity都需要创建一个ViewModel，各个ViewModel在Repository层共享数据
- Repository文件夹：存放XXXRepository类，用于从远程或者SQLite获取数据，viewModel从Repository类中取数据

![img](https://typora-images-1309988842.cos.ap-beijing.myqcloud.com/img/10465727-847b38ef891ad908.png)

## MVVM主要类的使用

- ViewModel和LiveData

  - 创建

    创建ViewModel类需要继承ViewModel抽象类，其中使用MutableLiveData存储数据，使用泛型指定存储的类型，在无参构造中使用new创建MutableLiveData，ViewModel必须存在无参构造

    在Activity或Fragment中声明ViewModel变量

    ``` java
    // 在Activity的onCreate中创建
    vm = new ViewModelProvider(this).get(viewModel.class); // viewModel是vm的类型
    // 在Fragment的onViewCreated中创建
    vm = new ViewModelProvider(requireActivity()).get(viewModel.class)
    ```
  - 使用

    MutableLiveData是LiveData类的子类，通过调用getValue和setValue触发视图更新

    每个MutableLiveData都需要提供get方法，返回MutableLiveData类型，用于在DataBinding中绑定

    viewModel中可以添加操作数据的接口方法

    ``` java
    public class MyViewModel extends ViewModel {
    
    	// 集合类型
        private MutableLiveData<List<Collection>> collections;
    
    	// 单个变量
        private MutableLiveData<String> label;
    
        public MyViewModel() {
            collections = new MutableLiveData<>();
    		List<Collection> list = new ArrayList<>();
    
            // 向list中填充数据...
    
            collections.setValue(list);  // 设置初值
    
            label = new MutableLiveData();
            label.setValue("初值");
    
        }
    	// getter
        public MutableLiveData<List<Collection>> getCollections() {
            // 必须返回MutableLiveData类型才能在DataBinding视图中同步数据更新
            return collections;
        }
        // update data
        public void addCollection(Collection collection) {
            List<Collection> value = collections.getValue(); // 获取LiveData中的值
            if (value != null && 其他的条件) {
                value.add(collection);
                // 更新数据后需要调用setValue触发视图更新
                collections.setValue(value);
            }
            // 调用Repository进行数据层操作
        }
    }
    ```
- DataBinding

  - 布局改变

    在原来布局的根布局上按alt+enter，选择Convert to data binding layout转换为DataBinding布局
  - 双向绑定

    - 视图绑定数据

      在转换为DataBinding布局后，会自动生成对应的Binding类，在Activity或Fragment中声明Binding对象，通过`binding.id`即可通过控件id调用对应的控件
    - 数据绑定视图

      在DataBinding的data标签中使用`<variable>`声明需要使用的变量和变量类型，`<import>`标签可以引入类，便于声明变量和使用类的静态字段，比如引入View后，可以使用View.VISIBLE等常量

      在控件的属性上使用`@{}`表达式，可以使用data标签中声明的变量，大括号中写入使用的变量

      布局表达式详见：[布局和绑定表达式  | Android 开发者  | Android Developers (google.cn)](https://developer.android.google.cn/topic/libraries/data-binding/expressions?hl=zh-cn)
    - 使用

      数据绑定视图

      通常在data标签中声明对应的viewModel或者实体类

      ```xml
      <data>
          <variable
                  name="viewModel"
                  type="com.cdtde.chongdetang.viewModel.XXXViewModel" />
          <!--类型是全类名，也可以import引入类型后，使用引入的类型-->
      </data>
      ```

      在`@{}`表达式中使用`viewModel.`调用字段，在视图中获取字段时，可以获取的字段以类中存在的get方法和is方法为准

      ```xml
      <TextView
      	android:text="@{viewModel.name}"/>
      <!--viewModel类中必须存在getName方法-->
      ```

      视图绑定数据

      在Activity或Fragment中声明binding对象并创建

      ``` java
      // Activity在onCreate中创建binding
      binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
      binding.setLifecycleOwner(this);
      setContentView(binding.getRoot());
      
      // Fragment在onCreateView中创建binding
      public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
          binding = FragmentIndexBinding.inflate(inflater, container, false);
          return binding.getRoot();
      }
      // Fragment在onViewCreated中设置binding的生命周期
      binding.setLifecycleOwner(this);
      ```

      设置视图data标签中的数据

      ``` java
      binding.setViewModel(vm);
      ``
- LiveData与DataBinding联动
  - LiveData和DataBinding联动可以让视图同步更新
  - 原理：LiveData使用了观察者模式来实现同步更新，即设置了一个观察者对象来观察某个变量，当变量的值改变时，观察者就做出反应
    - 在ViewModel中声明LiveData变量时使用的是MutableLiveData类型，MutableLiveData就是观察者对象，其中包含了我们需要的变量，当我们需要修改变量值时，调用setValue或者postValue，就可以修改变量值，同时我们是通过MutableLiveData对象来调用setValue方法的，那么MutableLiveData这个观察者对象也就知道了变量值已经改变，需要作出反应
    - 我们可以设置MutableLiveData作出什么反应，调用MutableLiveData对象的observe方法，传入一个LifecycleOwner和一个Observer接口的实现类，LifecycleOwner就是一个具有生命周期的对象，比如Activity和Fragment，假如传入一个Activity对象，那么就是将MutableLiveData观察者的生命周期设置成与Activity一致。此处Observer接口的实现类可以简化为Lambda表达式，可以理解成一段方法体，方法体的内容就是这个MutableLiveData对象在变量值改变后要作出的反应。在变量值改变后，观察者就执行这一段方法体。
    ``` java
    // 通常在activity中设置，因此传this
    mutableLiveData.observe(this, value -> {
	// 变量改变后我们需要更新视图，可以在此处通过binding对象调用控件来设置属性
	binding.xxx.setXXX(value);
    });
    ```
   - LiveData和DataBinding联动
     DataBinding提供了一种更加便捷的方式将观察者的行为与对应的控件绑定起来，从而代替observe方法
     我们可以将MutableLiveData对象传入DataBinding布局中，直接设置到对应控件属性上
     ``` xml
     <variable
		name="liveData"
		type="MutableLiveData"/>
     <XXXView
		attribute="@{liveData}"/>
     ```
     比如给TextView设置text属性，text属性需要一个String类型的值，可以直接将`MutableLiveData<String>`类型的对象通过布局表达式赋值到text属性上
     此时DataBinding会在内部自动调用MutableLiveData的getValue方法取出String类型的变量值给text属性赋值，同时将该MutableLiveData对象与text属性绑定起来，当调用setValue修改变量值时，DataBinding也能知道变量值被修改，从而同步修改控件属性
     最后需要调用binding.setLifecycleOwner设置一个LifecycleOwner，可以传入Activity或fragment，该方法本质上与observe方法中传入的LifecycleOwner是一样的，同样是为观察者设置一个依赖的生命周期
- 与ViewModel的配合
  在上述情境下可以加入ViewModel，ViewModel的特点就是可以感知Activity的生命周期，在同一个Activity反复销毁创建时，ViewModel能够一直存在，适用于保存数据
  因此可以将上述情境中的MutableLiveData放在ViewModel中并提供get方法，返回MutableLiveData类型。由于ViewModel可能会存放非常多的数据，如果手动调用get方法获取MutableLiveData，然后传入binding.set方法中会非常麻烦，通常在DataBinding布局中声明对应的ViewModel，直接将ViewModel传入DataBinding布局，在DataBinding布局中调用ViewModel的get方法
  ``` java
  class XXXViewModel extends ViewModel {
      private MutableLiveData<XXX> value;
	public MutableLiveData<XXX> getValue() {
		return value;
	}
  }
  ```
  ``` xml
  <variable
	    name="viewModel"
	    type="XXXViewModel"/>
  <XXXView
	   attribute="@{viewModel.value}"/>
  <!--在DataBinding中调用的get方法会自动解析成属性，以属性形式调用-->
  ```

## 关于命名

所有命名使用英文

java类使用驼峰命名，类名和接口名使用大驼峰(MyClass)，包名、变量名和函数名使用小驼峰(myFunction)

xml文件使用下划线+小写字符命名

- fragment：使用fragment前缀，fragment_...
- activity：使用activity前缀，activity_...
- 列表项或重复的标签项：使用item前缀，item_...
- dialog：使用dialog前缀，dialog_...

drawable资源命名：一到两个主要单词即可，下划线连接，不需要表示从属的页面

xml布局id命名：范围在当前布局内唯一即可，binding对象自带范围，不需要表示从属的页面，跳转入口尽量使用entry结尾，尽量不要使用控件的名称来命名，如btn,tv等

## 关于drawable资源格式

复杂图片使用.jpg格式

icon图标使用矢量图，可以使用Android自带图标库或阿里图标库

icon图标svg创建方法

1. 在阿里巴巴图标库下载svg图片，注意选择颜色==
2. 右键drawable文件夹，选择new->Vector Asset，选择local file，选择下载的svg文件，重新命名后点击next->finish

## 字体使用

APP使用两种字体：江西拙楷(regular.ttf)和方正楷体(text_regular.ttf)

在ToolBar标题、页面模块的小标题、点击入口的标签使用regular.ttf

一般文本、提示文字、文章使用text_regular.ttf

在TextView的android:fontFamily属性设置字体

在themes.xml中定义了一些常用的字体样式，可以直接使用style属性设置

## BaseAdapter使用

为了便于DataBinding使用，封装了RecyclerViewAdapter并已经绑定了相关属性

创建RecyclerView的Adapter类时，只需要继承BaseAdapter，泛型指定展示的数据类型

例：IndexCollectionAdapter，Index页面藏品推荐部分的adapter，可作为模板

```java
public class IndexCollectionAdapter extends BaseAdapter<Collection> {

    // BaseAdapter内含有data的List集合和set方法，不需要声明
    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 创建布局并创建ViewHolder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_index_collection, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        // 通过binding对象可以设置item布局data标签内的实体类，在xml中使用实体类字段
	XXXBinding binding = ((ViewHolder) holder).binding;
        Collection collection = data.get(position);
        binding.setCollection(collection);
        // 也可以通过binding对象binding.id获取控件，手动设置
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    // ViewHolder继承BaseViewHolder，不需要写控件字段
    public static class ViewHolder extends BaseViewHolder {
        // 当列表数据量大时，会出现View must have a tag错误信息
        // 因此将binding对象放在ViewHolder内创建，onBindViewHolder方法中通过holder.binding获取binding对象，holder注意转型
        XXBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = XXBinding.bind(itemView);
        }
    }
}
```

### 使用Adapter

将data集合放在ViewModel中，xml布局的data标签中声明viewModel和adapter

```xml
<data>
    <variable
            name="collectionAdapter"
            type="com.cdtde.chongdetang.util.adapter.IndexCollectionAdapter" />
    <variable
            name="viewModel"
            type="com.cdtde.chongdetang.viewModel.index.IndexViewModel" />
</data>
```

在RecyclerView控件中通过recycler_adapter和recycler_data属性绑定

``` xml
<androidx.recyclerview.widget.RecyclerView
    ...
    tools:listitem="@layout/item_index_collection"
    app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
    android:orientation="horizontal"
    recycler_adapter="@{collectionAdapter}"
    recycler_data="@{viewModel.collections}"/>
<!--
	listitem属性传入item布局可以看到预览的效果
	layoutManager传入LayoutManager，省去代码设置
	orientation设置LayoutManager的排列属性
	recycler_adapter传入声明的adapter
	recycler_data传入viewModel中的数据集合
-->
```

最后在Fragment或Activity中设置data标签中的数据

``` java
binding.setCollectionAdapter(new IndexCollectionAdapter());
binding.setViewModel(vm);
```

## WebView修改HTML实现

webView修改HTML通过注入javascript代码实现

``` java
public static void configure(WebView view, boolean isListPage) {
    view.getSettings().setJavaScriptEnabled(true);
    view.setWebChromeClient(new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress >= 40) {
                view.loadUrl("javascript:(function() {" +
                        "document.getElementsByClassName('nav')[0].remove();" +
                        "document.getElementsByClassName('logo')[0].remove();" +
                        "document.getElementById('bottom').remove();" +
                        (isListPage ? "document.getElementsByClassName('flc')[0].remove();" : "") +
                        "})()");
            }
            super.onProgressChanged(view, newProgress);
        }
    });
}
```

-   `setJavaScriptEnabled`：使WebView支持javascript
-   `setWebChromeClient`：处理HTML页面，简单需求上`setWebViewClient`可以实现一样的效果

一般使用WebViewClient，重写onPageFinished，为了效果更好，在页面加载的过程中处理页面

重写onProgressChanged方法，在页面加载时调用loadUrl注入javascript代码

``` javascript
(function() {
    document.getElementsByClassName('nav')[0].remove(); // 删除页面中class=nav的第一个元素
    document.getElementsByClassName('logo')[0].remove(); // 删除class=logo的第一个元素
    document.getElementById('bottom').remove(); // 删除bottom
    document.getElementsByClassName('flc')[0].remove(); // 在列表页面删除分类列表元素
})()
```

## 项目依赖的UI组件

- FlowLayout：流式布局，可以实现搜索页面的内容标签效果

  [hongyangAndroid/FlowLayout: [不再维护\]Android流式布局，支持单选、多选等，适合用于产品标签等。 (github.com)](https://github.com/hongyangAndroid/FlowLayout)
- Xpopup：对话框，可以实现各种效果的对话框

  [li-xiaojun/XPopup: 🔥XPopup2.0版本重磅来袭，2倍以上性能提升，带来可观的动画性能优化和交互细节的提升！！！功能强大，交互优雅，动画丝滑的通用弹窗！可以替代Dialog，PopupWindow，PopupMenu，BottomSheet，DrawerLayout，Spinner等组件，自带十几种效果良好的动画， 支持完全的UI和动画自定义！(Powerful and Beautiful Popup for Android，can absolutely replace Dialog，PopupWindow，PopupMenu，BottomSheet，DrawerLayout，Spinner. With built-in animators , very easy to custom popup view.) (github.com)](https://github.com/li-xiaojun/XPopup)
- FloatingActionButton：浮动按钮，样式高度自定义

  [Clans/FloatingActionButton: Android Floating Action Button based on Material Design specification (github.com)](https://github.com/Clans/FloatingActionButton)
- banner：轮播控件，可以实现各种轮播效果，使用友好

  [youth5201314/banner: 🔥🔥🔥Banner 2.0 来了！Android广告图片轮播控件，内部基于ViewPager2实现，Indicator和UI都可以自定义。 (github.com)](https://github.com/youth5201314/banner)
- DslTabLayout：TabLayou，与ViewPager关联一行代码搞定，指示器样式高度自定义，效果多

  [angcyo/DslTabLayout: Android界最万能的TabLayout(不仅仅是TabLayout), 支持任意类型的item, 支持Drawable类型的指示器,智能开启滚动,支持横竖向布局等 (github.com)](https://github.com/angcyo/DslTabLayout)
- RoundImageView：圆形圆角ImageView，设置简单

  [vinc3m1/RoundedImageView: A fast ImageView that supports rounded corners, ovals, and circles. (github.com)](https://github.com/vinc3m1/RoundedImageView)
- LiveDataBus：消息总线，可以替代Intent、BroadCast
  
  [JeremyLiao/LiveEventBus: EventBus for Android，消息总线，基于LiveData，具有生命周期感知能力，支持Sticky，支持AndroidX，支持跨进程，支持跨APP (github.com)](https://github.com/JeremyLiao/LiveEventBus)
  
- AndroidUtilCode：Android工具类

  [Blankj/AndroidUtilCode: Android developers should collect the following utils(updating). (github.com)](https://github.com/Blankj/AndroidUtilCode)

- Android-PickerView：选择器控件

  [Bigkoo/Android-PickerView: This is a picker view for android , support linkage effect, timepicker and optionspicker.（时间选择器、省市区三级联动） (github.com)](https://github.com/Bigkoo/Android-PickerView)

- UCrop：裁剪功能库

  [Yalantis/uCrop: Image Cropping Library for Android (github.com)](https://github.com/Yalantis/uCrop)
