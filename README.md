# 大三專題(APP)

## Overview
**指導教授**：王宗一
**專題內容**：集合一些基礎小遊戲、小工具，用 android studio 開發手機 app 

# app內容
```
> 遊戲
> 工具
```

#### 上學期
##### Greedy Snake
* 了解各種 layout 的差別
* 在各個畫面之間傳遞 variables
* 學習在 layout 中建立動畫與著色
##### Bottle
* rotate 動畫的應用
#### 下學期
##### Brick
* 在基本玩法上做一些改變
* 把接球板的移動改用==三軸穩定器==控制
##### Memory (flip cards)
* 翻牌動畫，在兩個卡片不同時，需恢復成原本的樣子
* 搭配 android 的 toast 互動

---
# Android Studio
\ Deal with the sceen pixel problem in different cellphone /
* add the program below into the " AndroidManifest.xml "
```xml=
<supports-screens
        android:largeScreens="true"
        android:normalScreens="true"
        android:smallScreens="true"
        android:anyDensity="true"
        />
```
* Change the units in  ' .kt '  files. From 'dp' to 'dip'
    * but it's not work (01/05)
* Noticed the symbol of the margin
    * ([referenced link](https://ithelp.ithome.com.tw/articles/10208704))
        ![](https://i.imgur.com/MONOI64.png)
    * after changing the symbol
        ![](https://i.imgur.com/qTCLbsU.png)
        * successful !!(01/07)

### ***~~Bug~~***
```
1. Build apk,two icons were created
```
* Find out in the file " AndroidManifest " ( in the ' manifests ' folder )
* Inside the activity label if there's an " intent-filter " label.Then, it'll turn out to be an icon after installing the apk
* Usually, there will be only one " intent-filter " label which is in the MainActivity

```
2.When using the View object from Widget, we need to connect the object in xml with the view.kt file
```
* change the label of the view object in xml like below
```xml=
<com.example.practice.snake.snakeView
        android:id="@+id/game"
    ......
        tools:visibility="visible" />
```
**or**
* use the convert function
![](https://i.imgur.com/CcsNg3S.png)
* paste the address of the view.kt file at the input box
![](https://i.imgur.com/3fNp2p8.png)

## 專題內容

### Greedy Snake

[示範圖片](https://drive.google.com/file/d/130b2njOTiR4o4IbooZ3M-Nf-5QvmFSnW/view?usp=share_link)

[示範影片](https://drive.google.com/file/d/1AD1tIVoL3XQMH9TAFtGtnjSa1fbKJGeO/view?usp=drivesdk)

#### Improve?
```
1. when cancel is pressed,the screen stay unchanged
```
* Solved !!
add `finish()` to go back to the previous layout
```
2. whether the head of the snake can be painted into different color from the body
```

```
3. the game over messange have no score on it
```
![](https://i.imgur.com/Gymk8Za.jpg)
* Solved ! (01/07)
```
4. to use the snake to write out the name which the user input
```
---
### Bottle

[示範圖片](https://drive.google.com/file/d/13-SxPhCnw952voHt8GA2XoNiLxUmcZfK/view?usp=share_link)

[示範影片](https://drive.google.com/file/d/12zmg7rve29h1JxSCJDPRyJbQISJl9qIk/view?usp=share_link)

#### Improve?

---
### Brick

[示範圖片1](https://drive.google.com/file/d/12opK7JJgwjeUgIlLd_2q04qt88K4LhNE/view?usp=share_link)

[示範圖片2](https://drive.google.com/file/d/12oAfxzIJW8sot8Mc78gdjD9RGzGJG87p/view?usp=share_link)

[示範影片](https://drive.google.com/file/d/12_h6_K80p3mRMROugasSexI7qcXBoeeg/view?usp=share_link)

#### Improve?

```
1. Use Triaxial accelerometer to control the catching paddle
```
![](https://i.imgur.com/2mTfw2o.png)

![](https://i.imgur.com/dMv4fQ7.png)

* Solved!
---
### Memory

[示範圖片](https://drive.google.com/file/d/12Y_WoL78k13UQO27PPF0PYxeuBZWrSJH/view?usp=share_link)

[示範影片](https://drive.google.com/file/d/12ULImFfWnEzquWX1rzyWv0ixPp_z60p7/view?usp=share_link)

#### Improve?
```
1. toast do not appear right away
```
