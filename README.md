# Sokoban 1.2.1

# Bugs:
  把警衛逼近死角遊戲會crash\
  遊戲地圖有多餘hardWall, 以及有缺漏hardWall的地圖導致穿牆可穿至地圖外

# 提案或建議:
感覺穿牆發動後可以加一個顯示剩餘時間的提示，比較不會緊張(?\
人物可以轉動方向

# 尚未開發完成項目:
警衛\
主選單\
難度選擇\
關卡開發\
音效選擇

# Bugs Archive:
讀不到圖問題:修改資料夾層級\
傳送門之人物蓋住貨物神奇bug:改成上面有貨不給傳\
子彈可以穿過地圖:改成不能穿\
穿牆穿出地圖

# Version History
(重大更新：改最前面數字。中等更新：改中間數字。微幅更動：改後面數字)\
v1.0.0 - NULL\
v1.0.1 - 修正貨物可推至hardWall及穿牆可多次使用的bug\
v1.1.0 - 警衛版一更改 增加警衛class 在board裡增加checkBagCollisionforPolice method\
v1.1.1 - 圖片更改；圖片封裝優先度更改，警衛的圖片會顯示在較上層\
v1.2.0 - 新增地圖，更改路徑。\
v1.2.1 - 將警衛改為不可穿牆。為debug將穿牆功能暫時改為按一次即可永久穿牆，再按一次恢復，交替運作

# 程式內變數解釋
 forbottom:為了防止當sokoban 裡的timer refresh repaint 時和點按鍵時的repaint 重複
