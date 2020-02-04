| 字段           | 字段说明                                                                              | store表字段   | 字段说明 | store_group表字段 | 字段说明 | storea_area表字段 | 字段说明         |
| :------------- | :------------------------------------------------------------------------------------ | :------------ | :------- | :---------------- | :------- | :---------------- | :--------------- |
| siteId         | 自增量编号                                                                            | id            | id       | id                | id       | id                | id               |
| SiteKey        | 场所编号(唯一) 按规则自动生成                                                         | id            | id       | id                | id       | id                | id               |
| ParentId       | 父节点(递归节点) 根节点为 0                                                           | group_id      | 门店组id | parent_id         | 父级id   | storeId           | 门店id           |
| SiteAlias      | 客户场所编码(场所编号别名)                                                            | code          | 编号     | code              | 编号     | areaCode          | 店内区域编号别名 |
| SiteName       | 场所名称                                                                              | name          | 名称     | name              | 名称     | area_name         | 店内区域名称     |
| SiteType       | 场所类型 见参数表 SITE-TYPE                                                           | 'store'       | 固定值   | 'store_group'     | 固定值   | 'store_area'      | 固定值           |
| Adress         | 场所地址及位置                                                                        | address       | 地址     |                   | 没有     |
| Area           | 建筑面积 GRA (总面积)                                                                 | area          | 面积     |                   | 没有     |
| NetArea        | 使用面积 NLA (营业面积)                                                               | area          | 面积     |                   | 没有     |
| StaffNo        | 店员人数                                                                              |               |          |                   |
| SiteStatus     | 场所状态 见参数表 1-运行,2-暂 停,3-关闭，4-调铺中(店铺的状 态) 1 为启用，<>1 则为禁用 |
| OpenDate       | 开业/启用时间                                                                         |
| OpenDateStatus | 是否启用开业汇总                                                                      |               |          |                   |
| AuditEnable    | 数据审核使能                                                                          |               |          |                   |
| SmsEnable      | 短信通知使能                                                                          |               |          |                   |
| MailEnable     | 邮件通知使能                                                                          |               |          |                   |
| SiteAttrCode   | 场所属性卡编码                                                                        |               |          |                   |
| CityID         | 城市编号(与天气信息关联获取场 所所在地的天气信息)                                     |               |          |                   |
| SiteImage      | 平面图                                                                                |               |          |                   |
| VendorCode     | 厂商代码 可默认汇纳                                                                   |               |          |                   |
| IndexNo        | 排序编号                                                                              |               |          |                   |
| DomainName     | 域名                                                                                  |               |          |                   |
| CreateTime     | 创建时间                                                                              | create_time   |          | create_time       |
| ModifyTime     | 修改时间                                                                              | modified_time |          | modified_time     |
| DeleteTime     | 删除时间(不删除数据，删除置删 除时间) 为空为有效 设置删除时 间为无效                  |               |          |                   |
|                |                                                                                       | enterpriseId  | 企业id   | enterpriseId      | 企业id   | enterpriseId      | 企业id           |





/**
 * 100  全国（集团）
 * 200  区域
 * 201  经销商
 * 202  事业部
 * 203  品牌
 * 205  省份
 * 206  城市
 * 300  店铺
 * 400  店铺下区域
 * 401  预买区
 * 500  楼层
 * 700  通道
 * 800  工位
 */

//视图包含的表 以及SiteType的值对应的关系
store 300
store_group 200
store_area 400
