var app = getApp();

Page({
	data: {
    userInfo:{},
    canIUse:wx.canIUse('button.open-type.getUserInfo')
  },
	onLoad: function () {
    if(app.globalData.userInfo){
      console.log('app中有userInfo')
      this.setData({
        userInfo:app.globalData.userInfo
      })
    }else if(this.data.canIUse){
      
      //由于getUserInfo是网络请求,可能会在Page.onload之后才返回
      //所以此处加入callback以防止这种情况
      app.userInfoReadyCallback = res =>{
        this.setData({
          userInfo:res.userInfo
        })
      }
    }else{
      
      //在没有open-type=getUserInfo版本的兼容处理
      wx.getUserInfo({
        success:res =>{
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo:res.userInfo
          })
        }
      })
    }
	},
	// onShow: function () {
	// 	this.setData({
	// 		userInfo: app.globalData.userInfo
	// 	});
	// },
  getUserInfo: function (e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo
    })
  },
  toMyOrder:function(){
    console.log('跳到我的订单列表页面')
    wx.navigateTo({
      url:"/page/myorder/myorder"
    })
  },
  toMyAddress: function () {
    wx.navigateTo({
      url: '/page/address/address',
    })
  },
  makecall:function(){
    wx.makePhoneCall({
      phoneNumber: '18629505590'
    })
  }
});

