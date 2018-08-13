通用android基础框架
## Android公共库 `android-common`

### 介绍
- 统一的联网请求接口
- 统一的图片请求接口
- 统一的数据库操作接口
- 统一BaseActiviy、BaseFragment等UI处理
- 统一列表下拉刷新、上拉加载更多的处理

## 初始化
~~~bash
$ git submodule init
$ git submodule update
~~~
或者
~~~bash
$ git clone --recursive [url]
~~~

## 分支管理
~~~bash
$ master        // 主干分支
$ develop       // 开发分支
$ feature/xxx   // 新功能分支
$ hotfix/xxx    // 解BUG分支
$ release/xxx   // 准备量产分支
~~~

## Git常用命令
~~~bash
$ git log -n        // 打印LOG, n表示最近的次数
$ git branch -a     // 获取所以分支
$ git remote -v     // 查看远程库信息
$ git checkout -b develop origin/develop    // clone远程develop分支到本地创建的develop分支
$ git tag -a v1.0.1 -m "version 1.0.1 release" 3628164  // 打TAG
$ git push origin --tags    // 推送本地全部TAG到远程
$ git remote set-url --add origin [url] // 添加一个远程分支
$ git push origin master // 推送本地到远程分支,多个远程分支可以一起推送
$ git remote add [shortname] [url] // 关联多个远程分支
$ git reset --hard origin/master   // 同步到服务器master分支的版本
~~~

## 数据库调试
~~~bash
D/DebugDB: Open http://10.0.3.15:8080 in your browser
~~~

## Windowns 命令行
打开terminal命令窗输入 `jenkins-build.bat` 回车执行命令即可进行sonar检查并且上传commonLib到远程仓库。
