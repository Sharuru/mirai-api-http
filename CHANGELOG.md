# 更新日志

## \[2.3.1\] - 2021-09-12

### 修复

+ 修复文件上传后没有发布消息提示, #468
+ 减少上传资源时出现内存泄露的可能



## \[2.3.0\] - 2021-09-09

### 修复

+ 修复上报时提示的资源泄漏
+ 修复群文件上传报错
+ 修复消息撤回事件中时间的错误, #442
+ 修复 ws 创建连接时, 认证失败的返回数据格式不正确, #446

### 变更

+ 更新 core 依赖版本到 `2.7.0`
+ `groupConfig` 移除群通知参数, 无法获取也无法发布, 下个版本会专门开放群通知接口
+ 语音上报的**返回结果**不再携带 `url` 参数, 语音消息不受影响
+ 明确群文件上传 `path` 参数意义为**父级目录**, 上传后的文件名取自 `multipart` 参数中的文件名

### 优化

### 新增

+ 新增修改群员管理员权限接口 [接口定义](./docs/api/API.md#修改群员管理员)
+ 支持好友语音
+ 语音追加 `length` 返回语音时间长度, 单位为秒
+ `BotJoinGroupEvent`, `MemberJoinEvent` 两个入群时间追加邀请人 `invitor` 参数
+ `BotLeaveEvent` Bot 离群事件, 在 Bot 被踢出时可通过 `operator` 获取执行操作的管理员信息
+ 群文件相关接口全增加 `path` 参数用于模糊定位(群文件相同目录可重名), 优先级高于 `id`, 精准定位请使用 `id`
+ 丰富群文件信息的 `downloadInfo` 参数的内容，包括修改时间、上传时间、上传者、下载次数


## \[2.2.0\] - 2021-08-09

### 修复

+ 部分接口中的大小写匹配
+ 状态码序列化异常
+ 语音失真
+ 文件上传时可能出现的内存泄漏

### 优化

+ `peekMessage` 接口拼写错误, 原 `peakMessage` 接口保留一段时间兼容
+ 提升 `webhook` 的一点点性能
+ 补充文档, `戳一戳事件`、明确环境中 `path` 参数的含义

### 新增

+ MiraiCode 消息类型支持，可将 MiraiCode 作为一种消息类型 [消息格式](https://github.com/project-mirai/mirai-api-http/blob/master/docs/api/MessageType.md#miraicode) [MiraiCode的使用](https://github.com/mamoe/mirai/blob/dev/docs/Messages.md#%E6%B6%88%E6%81%AF%E5%85%83%E7%B4%A0)
+ `Webhook Adapter` 对请求头 `qq`, `bot` 追加可反代的 `X-header` 格式
+ 配置文件中智能的 host 解析, 默认解析到 `http://` scheme
+ 群文件请求 `/file/list`, `/file/info` 可携带 `withDownloadInfo` 返回额外的下载信息 [查看文件列表](https://github.com/project-mirai/mirai-api-http/blob/master/docs/api/API.md#查看文件列表)
+ 群文件请求 `/file/list` 追加分页参数, `offset`, `size`



## \[2.1.0\] - 2021-07-19

### 修复

+ 群文件相关接口字段错误 `isDictionary` -> `idDirectory`, `isDictionary` 保留一段时间兼容性, 涉及接口
 + http 文件上传 `/file/upload` 返回值(已兼容)
 + 创建群文件夹 `/file/mkdir` 请求参数(**不兼容**)
 + 查看群文件列表 `/file/list` 返回值(已兼容)
 + 获取文件信息 `/file/info` 返回值(已兼容)
+ websocket 部分异常没有返回正确的格式 #383
+ websocket 无法进行引用回复 #401
+ 若干文档说明，更正 反向ws adapter 的使用文档

### 新增
+ 获取 session 信息 #386 `[GET] /sessionInfo`, websocket 命令字 `sessionInfo`

## \[2.0.2\] - 2021-06-14

### 修复

+ 管理员处理入群请求时，拒绝消息丢失
+ 可能存在着的未读消息序列化错误



## \[2.0.1\] - 2021-06-12

### 修复

+ 修复 `http adapter` 对于 `[GET]` 请求参数序列化错误

### 变更

+ 由于 `http adapter` 中 `[GET]` 请求无法针对**文件 id**根目录传递空字符串, 将为该 id 值提供默认值参数, 默认值为空字符串, 即默认操作根目录



## \[2.0.0\] - 2021-06-12

### 修复

+ 修复 `session` 释放接口错误
+ 修复 `websocket` 中 `session` 相互干扰
+ 修复请求参数序列化内部异常
+ 修复多 `adaptor` 协作时 `session` 类型不匹配

 

## \[2.0-RC2\] - 2021-05-26

### 变更

+ `Mirai core` 版本更新到 `2.6.4`
+ `ForwardMessage` 字段修改为与 1.x 相同: 
    + 类型type: `Forward` -> `ForwardMessage`
    + 节点: `nodes` -> `nodeList`
    + 发送人: `sender` -> `senderId`, `name` -> `senderName`

### 修复

+ `about` 接口修复, #351
+ session 生成异常, #345
+ websocket adapter 异常导致断连
+ 配置序列化导致 webhook 等初始化异常

### 新增

+ 追加 debug 模式开启 debug 信息
+ 群文件支持
+ 其他客户端消息(`OtherClientMessage`)接收支持, #331 (受 core 限制, 暂不支持发送)
+ mirai console 命令 API

> 该版本为预览版本, 功能未经过充分测试, 提前发布以适应接口变更
> 请酌情使用



## \[2.0-RC1\] - 2021-05-10

### 新增

+ 支持新消息类型: `MusicShare`, `Dice`, `ForwardMessage`, ``
+ 支持新消息事件: `好友输入状态改变`, `好友昵称改变`, `群荣誉改变(龙王)`
+ 支持新操作: `设置精华`, `删除好友`, `查询资料片`， `戳一戳`
+ 群成员返回 `最后发言事件`, `入群时间` 等字段
+ 多媒体上传支持 base64 格式
+ 支持反向 websocket, 上报支持回调

### 变更

+ `群名片变更`, `群头衔变更`, `群权限变更`, `群匿名开启变更` 等事件 `new` 字段正式废除
+ 认证流程变更, 且支持从请求头认证
+ http 部分接口返回格式变更
+ 多媒体上传不再进行缓存

详见[迁移文档](docs/misc/Migration2.md)

### 优化

+ adapter 拆分
+ 解决已发现的内存泄漏

### 正式发布前待解决

+ 恢复 `console` 命令相关接口
+ 恢复群文件相关接口
+ 恢复 API TESTER 工具

### 版本依赖

+ mirai core: 2.6.2

> 该版本为预览版本, 功能未经过充分测试, 提前发布以适应接口变更
> 请酌情使用
