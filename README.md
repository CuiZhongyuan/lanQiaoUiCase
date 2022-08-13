# 使用技术栈
- springboot
- testng
- selenium
- poi ：数据驱动-excel
- PaddleOCR : 验证码图片识别



# 基于po模型开发

包目录说明
- base包：负责存放公用的操作方法
- page包：负责用于元素的定位和数据的维护
- handle包:获取元素后的操作
- business包:负责业务逻辑用例
- listener包:报告生成监听器
- utils包:工具类

# 简单case使用说明
- 测试入口：com.iwebui.buiness 包下是测试入口包含：
    - admin登录页
    - 首页注册、登录
    - excel数据驱动
