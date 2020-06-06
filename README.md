﻿# Sokoban 1.4.3

# Bugs:
把警衛逼近死角遊戲會crash\
邊按上下左右邊射子彈，子彈的軌跡會怪怪的

  
# 提案或建議:
感覺穿牆發動後可以加一個顯示剩餘時間的提示，比較不會緊張(?\
要不要警衛被逼進死較就把他free掉(指向null)?省得麻煩\
角色選擇決定功能物品配置\
(有空再做)地獄模式特殊結局:達成所有貨物後，不結束遊戲，而是生出6隻警衛，並在地圖另一頭開一個口，目標變成逃出房間

# 尚未開發完成項目:
關卡開發\
音效選擇\
選擇不同小偷配置不同於額的傳送門跟彈藥?\
第一關不開放槍\
第二關開放槍\
第三關出3個警衛(有空再做)

# Bugs Archive:
讀不到圖問題:修改資料夾層級\
傳送門之人物蓋住貨物神奇bug:改成上面有貨不給傳\
子彈可以穿過地圖:改成不能穿\
穿牆穿出地圖\
人物正面碰到警衛後僅把visible設false，遊戲未關閉\
程式碼出現不雅字眼\
關卡complete會當掉

# Version History
v1.0.0 - NULL\
v1.0.1 - 修正貨物可推至hardWall及穿牆可多次使用的bug\
v1.1.0 - 警衛版一更改 增加警衛class 在board裡增加checkBagCollisionforPolice method\
v1.1.1 - 圖片更改；圖片封裝優先度更改，警衛的圖片會顯示在較上層\
v1.2.0 - 新增地圖，更改路徑。\
v1.2.1 - 將警衛改為不可穿牆。為debug將穿牆功能暫時改為按一次即可永久穿牆，再按一次恢復，交替運作\
v1.2.2 - 配合eclipse修改圖片載入路徑\
v1.3.0 - 人物會依據移動方向改變圖片方向；將穿牆功能改為X鍵、傳送點功能改為Z鍵，更改package\
v1.3.1 - 警衛碰到子彈就消失，人物正面碰到警衛遊戲結束\
v1.3.2 - 修改警衛消失後的bug,修改成警衛子彈不同調\
v1.4.0 - 子彈設定成無法連射，難度決定警衛跑速，增加主選單\
v1.4.1 - 新增圖片；精簡部分程式碼；新增一張地圖(地獄)；增加解說；警衛改成每走兩步換行進方向，減少混亂感\
v1.4.2 - 現在關卡完成後遊戲會正確關閉了\
v1.4.3 - 對齊貨物至格子中間，削減部分程式碼，新增一首BGM(尚未能撥放)，暫時新增二號玩家
v1.4.4 - 警衛2.0 
# 程式內變數解釋
 forbottom:為了防止當sokoban 裡的timer refresh repaint 時和點按鍵時的repaint 重複
