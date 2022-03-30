# swagger2


<a name="overview"></a>
## 概览
Restful API 接口


### 版本信息
*版本* : 1.0


### URI scheme
*域名* : localhost:8080  
*基础路径* : /


### 标签

* user-login-controller : User Login Controller




<a name="paths"></a>
## 资源

<a name="user-login-controller_resource"></a>
### User-login-controller
User Login Controller


<a name="phoneloginusingpost"></a>
#### 手机号用户登录
```
POST /login/phoneLogin
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**password**  <br>*可选*|password|string|
|**Query**|**userName**  <br>*可选*|userName|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[RespondData«Map»](#64b6608c437a6dcb1d3c1c277b728d5a)|
|**201**|Created|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/login/phoneLogin
```


###### 请求 query
```
json :
{
  "password" : "string",
  "userName" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "data" : "object",
  "message" : "string",
  "status" : 0,
  "timestamp" : 0
}
```


<a name="tokentestusingpost"></a>
#### tokenTest
```
POST /login/tokenTest
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Body**|**newToken**  <br>*可选*|newToken|string|
|**Body**|**userId**  <br>*可选*|userId|integer (int32)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|string|
|**201**|Created|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/login/tokenTest
```


###### 请求 body
```
json :
{ }
```


##### HTTP响应示例

###### 响应 200
```
json :
"string"
```


<a name="wechatloginusingpost"></a>
#### 微信用户登录接口
```
POST /login/weChatLogin
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Body**|**weChatUserInfo**  <br>*必填*|weChatUserInfo|[WeChatUserInfo](#wechatuserinfo)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[RespondData«Map»](#64b6608c437a6dcb1d3c1c277b728d5a)|
|**201**|Created|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/login/weChatLogin
```


###### 请求 body
```
json :
{
  "code" : "string",
  "iv" : "string",
  "rawData" : "string",
  "signature" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "data" : "object",
  "message" : "string",
  "status" : 0,
  "timestamp" : 0
}
```




<a name="definitions"></a>
## 定义

<a name="64b6608c437a6dcb1d3c1c277b728d5a"></a>
### RespondData«Map»

|名称|说明|类型|
|---|---|---|
|**data**  <br>*可选*|**样例** : `"object"`|object|
|**message**  <br>*可选*|**样例** : `"string"`|string|
|**status**  <br>*可选*|**样例** : `0`|integer (int32)|
|**timestamp**  <br>*可选*|**样例** : `0`|integer (int64)|


<a name="wechatuserinfo"></a>
### WeChatUserInfo

|名称|说明|类型|
|---|---|---|
|**code**  <br>*可选*|**样例** : `"string"`|string|
|**iv**  <br>*可选*|**样例** : `"string"`|string|
|**rawData**  <br>*可选*|**样例** : `"string"`|string|
|**signature**  <br>*可选*|**样例** : `"string"`|string|





