# 答题前必读

笔试题一共包含三个考试项目，都已经在本项目中以 TODO 的方式标出。试题描叙在Interview1Tests的注释里。

项目所需要的jar包均已包含在mvn依赖中，如无特殊情况一般不需要新增依赖项。

测试数据路径在src/main/resource/data下，请勿移动位置。

验证结果可以直接运行Interview1Tests下的单元测试用例，请在提交前确保该用例里方法均可执行。（执行结果用stdout输出或者Assert验证均可)

概念解释:
sku(最小颗粒商品，例如[铅笔-蓝色-15cm]或[笔记本-A4-200页])
商品(一系列sku的组合，例如[铅笔]包含[铅笔-蓝色]，[铅笔-红色])
渠道库存(为各个销售渠道分别准备的库存数。例如给天猫备货10件铅笔，给X东供货5件铅笔，互不包含)
