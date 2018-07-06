
# Innooz Android Sample App Project

Hi, 很高興您即將成為我們的夥伴

首先，有正確的開啟這份 [Markdown](https://en.wikipedia.org/wiki/Markdown) 文件嗎？ 
沒有的話可以來這邊[查查](https://stackoverflow.com/questions/9843609/view-markdown-files-offline)

在正式的專案指派之前，我們希望你可以先完成以下的簡易 app 來暖暖身，同時也讓我們了解你的 Coding Style，讓我們未來的合作更加愉快 :)

## Sample App

這個 app 使用2個Activity，

1. 登入用的Activity，讓使用者可以選擇Google帳號或Facebook帳號登入。[示意圖](https://drive.google.com/file/d/1caWtjtbQeEqrIJRZNE1qtwrudDSUa8v3/view?usp=sharing)
2. 如果登入成功，進入MainActivity的主畫面，

主畫面使用

- **ViewPager**
- **TabLayout** 

來建構 App。
[示意圖](https://drive.google.com/file/d/1NXSsxI4hbDr237e094Fnn_SDvbxzAKbE/view?usp=sharing)

### Tab0
a 或 b 方案二選一，由於政府提供的api常常會有問題，請選擇當時可以使用的。

----------
a.
請以[Data.Taipei臺北市政府資料開放平台](http://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a) 來建立動物園區簡介列表，
這個列表需要在左邊顯示圖片(E\_Pic\_URL)以及右邊顯示名稱(E_Name)，
[示意圖](https://drive.google.com/file/d/13J92Xks7J9BuNYDaOJlx_6Psf5iRbO3l/view?usp=sharing)

b.
請以[政府資料開放平台 展演空間 API](https://cloud.culture.tw/frontsite/trans/emapOpenDataAction.do?method=exportEmapJsonByMainType&mainType=10) 來建立展演空間列表，
這個列表需要在左邊顯示圖片(representImage)以及右邊顯示簡介(intro)，

----------

此外還需要以
- **RecyclerView** 

來建立列表，點擊任一個RecyclerView的item則開啟WebView顯示連結(E\_URL 或 website)
以及加上你覺得其他可以讓這個 app 更好使用的功能。
附註：請以 OkHttp 或 Volley 或 Retrofit 來處理網路資料。

### Tab1
包含2個Button，
先以Firebase後台建立一個資料庫，並隨意新增資料。Android端的fragment
畫面顯示目前資料庫中的資料(不限方式)，2個Button的功能分別為新增一筆
隨機資料、刪除最後一筆資料，按下Button後，畫面須即時更新為最新狀態。
[示意圖](https://drive.google.com/file/d/1tEdgvGrRlvWwuBqh7kJdJ-GbkeRB3ll8/view?usp=sharing)

### Tab2
請建立一個影片播放器，播放這個影片串流檔案。
rtsp://184.72.239.149/vod/mp4:BigBuckBunny_175k.mov
[示意圖](https://drive.google.com/file/d/1t_O2jbgzhUpnbFXPkLadNVidHzNyP_sN/view?usp=sharing)

### Tab3
請建立一個 WebView 放入以下網站 
[Real-time Air Quality Index](http://aqicn.org/city/taiwan/gutin/)
並建立一個Switch，
打開Switch可以觸發網站右下角齒輪按鈕的選單，關閉則讓選單消失。
[示意圖](https://drive.google.com/file/d/1zdz3KseEkzx_W2xiRHKsvJ8rDOzgi-CA/view?usp=sharing)
[示意圖](https://drive.google.com/file/d/1RGSj1nqpxtH3-2JjOzLF46R0g1Ywew5t/view?usp=sharing)

### Tab4
使用 ConstraintLayout 設計一個計算機的畫面，先不需要功能。
[示意圖](https://www.google.com.tw/search?q=calculator+layout&source=lnms&tbm=isch&sa=X&ved=0ahUKEwjj05K47c_ZAhVIi7wKHSpBCdsQ_AUICigB)

---
---

## 使用工具
- [Android Studio](https://developer.android.com/studio/index.html)

## 撰寫語言
- Java
- [Kotlin](https://developer.android.com/kotlin/index.html)

## 版本控制
- [git](https://try.github.io/levels/1/challenges/1)
- [bitbucket](https://bitbucket.org/)
- [SourceTree](https://www.sourcetreeapp.com/)

## 外部資源
- [Stack Overflow](https://stackoverflow.com/)
- [Material Design](https://material.io/guidelines/)