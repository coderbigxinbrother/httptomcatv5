本版本解决问题:
    session:  解决http的无状态问题
    
    前提条件： 服务器必须要能返回cookie, 因为  session的sessionid是以cookie形式保存在客户端，同时服务器中保存了session键值对。
    每次请求都会将sessionid传到服务器. 