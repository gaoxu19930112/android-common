通用Android基础框架
## Android公共库 `android-common`
***
###介绍
* 统一的联网请求接口
* 统一的图片请求接口
* 统一的数据库操作接口
* 统一BaseActiviy、BaseFragment等UI处理
* 统一列表下拉刷新、上拉加载更多的处理
* 支持Glide使用
* 支持lambda表达式
* 支持greedao
* 支持Gson
* 支持通用Adapter BaseRecyclerViewAdapterHelper
* 支持Rxjava2
* 内置大部分常用工具类

###使用方式
```
repositories {
    maven { url 'https://dl.bintray.com/gaoxu930112/maven/'}
}
dependencies {
    api 'com.gaoxu:android-common:1.1.0'
}
```