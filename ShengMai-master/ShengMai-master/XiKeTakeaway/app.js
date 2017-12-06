//app.js
App({
  onLaunch: function () {
    var that = this;
     var url = this.globalData.background_URL+'getAllShop.do';
     this.getShops(url);
     // 展示本地存储能力
     var logs = wx.getStorageSync('logs') || []
     logs.unshift(Date.now())
     wx.setStorageSync('logs', logs)

   // 登录
    wx.login({
      success: function(res){
        console.log('app中的code')
        console.log(res)
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
        that.getOpenId(res.code)
      }
    })
    // 获取用户信息
    wx.getSetting({
      success: res => {
        // if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
          wx.getUserInfo({
            success: res => {
              // 可以将 res 发送给后台解码出 unionId
              that.globalData.userInfo = res.userInfo

              // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
              // 所以此处加入 callback 以防止这种情况
              if (that.userInfoReadyCallback) {
                that.userInfoReadyCallback(res)
              }
            }
          })
        // }
      }
    })
  },
  getOpenId:function(code){
    var that = this;
    wx.request({
      url: that.globalData.background_URL+'GetOpenId.do',
      data:{'code':code},
      method:'GET',
      header:{
        'Content-Type':'application/x-wwww-form-urlencoded'
      },
      success:function(res){
        console.log('app登陆后的openid')
        console.log(res.data.openid)
        that.globalData.openid = res.data.openid
      }
    })
  },
  getUserInfo: function (cb) {
     var that = this
     if (that.globalData.userInfo) {
       typeof cb == "function" && cb(that.globalData.userInfo)
     } else {
        //调用登录接口
        wx.login({
           success: function () {
              wx.getUserInfo({
                 success: function (res) {
                    that.globalData.userInfo = res.userInfo
                    typeof cb == "function" && cb(that.globalData.userInfo)
                 }
              })
           }
        })
     }
  },
//获取商铺方法
  getShops:function(address){
     var that = this;//获取this对象
     wx.request({
        url:address,//请求后台获取商铺列表
        header:{
           "Content-Type":"application/json"
        },
        method:"GET",//get方式提交
        success:function(res){
           console.log('进入app的js成功回调，查看返回数据');
           console.log(res);
           that.globalData.shops = res.data;
        },
     })
  },
  globalData: {
     openid:'',
     total: 0,
     userInfo: null,
    //  background_URL:'http://localhost:8080/Maven_Project/'
     background_URL: 'https://xikeruanjianwx.com/Maven_Project/'
  }
})