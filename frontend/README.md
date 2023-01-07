# Android端说明

## 项目结构

APP使用MVVM架构

-   model文件夹：存放实体类
-   view文件夹：存放Activity和Fragment，主页面包含多个子页面时，创建子文件夹存放
-   viewModel文件夹：存放viewModel类，尽量一个主页面使用一个viewModel，当子页面的数据过于复杂时，可以创建子页面的viewModel
-   Repository文件夹：存放XXXRepository类，用于从远程或者SQLite获取数据，viewModel从Repository类中取数据

![img](https://typora-images-1309988842.cos.ap-beijing.myqcloud.com/img/10465727-847b38ef891ad908.png)

## MVVM主要类的使用

-   ViewModel和LiveData

    -   创建

        创建ViewModel类需要继承ViewModel抽象类，其中使用MutableLiveData存储数据，使用泛型指定存储的类型，在无参构造中使用new创建MutableLiveData

        在Activity或Fragment中声明ViewModel变量

        ``` java
        // 在Activity的onCreate中创建
        vm = new ViewModelProvider(this).get(viewModel.class); // viewModel是vm的类型
        // 在Fragment的onViewCreated中创建
        vm = new ViewModelProvider(requireActivity()).get(viewModel.class)
        ```

    -   使用

        MutableLiveData是LiveData类的子类，通过调用getValue和setValue触发视图更新

        每个MutableLiveData都需要提供get方法，返回MutableLiveData类型，用于在DataBinding中绑定

        viewModel中可以添加操作数据的接口方法

        ```java
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

-   DataBinding

    -   布局改变

        在原来布局的根布局上按alt+enter，选择Convert to data binding layout转换为DataBinding布局

    -   双向绑定

        -   视图绑定数据

            在==转换为DataBinding布局后==，会自动生成对应的Binding类，在Activity或Fragment中声明Binding对象，通过`binding.id`即可通过控件id调用对应的控件

        -   数据绑定视图

            在DataBinding的data标签中使用`<variable>`声明需要使用的变量和变量类型，`<import>`标签可以引入类，便于声明变量和使用类的静态字段，比如引入View后，可以使用View.VISIBLE等常量

            在控件的属性上使用`@{}`表达式，可以使用data标签中声明的变量，大括号中写入使用的变量

            布局表达式详见：[布局和绑定表达式  | Android 开发者  | Android Developers (google.cn)](https://developer.android.google.cn/topic/libraries/data-binding/expressions?hl=zh-cn)

        -   使用

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
            ```

    -   @BindingAdapter注解

        之后再更新

## 关于命名

所有命名使用英文

java类使用驼峰命名，类名和接口名使用大驼峰(MyClass)，包名、变量名和函数名使用小驼峰(myFunction)

xml文件使用下划线+小写字符命名

-   fragment：使用fragment前缀，fragment_...
-   activity：使用activity前缀，activity_...
-   列表项或重复的标签项：使用item前缀，item_...

drawable资源命名：一到两个主要单词即可，下划线连接，不需要表示从属的页面

## 关于drawable资源格式

复杂图片使用.jpg格式

icon图标使用矢量图，可以使用Android自带图标库或阿里图标库

icon图标svg创建方法

1.   在阿里巴巴图标库下载svg图片，==注意选择颜色==
2.   右键drawable文件夹，选择new->Vector Asset，选择local file，选择下载的svg文件，重新命名后点击next->finish

