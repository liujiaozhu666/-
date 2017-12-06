var app = getApp()
Page({
  data: {
    globalurl:app.globalData.background_URL,
    num: 0,
    noAddress: false,
    allAddress: [],
    defaultaddress: {},
    modifyAddress:{}
  },
  onLoad:function(){
    this.getAllAddress();
  },
  onShow:function(){
     this.getAllAddress();
  },
  onReady:function(){
    this.getAllAddress();
  },
  onPullDownRefresh: function () {
    var that = this;
    that.onLoad();
    wx.stopPullDownRefresh();
  },
  getAllAddress:function(){
     var that = this;
     var wxid = app.globalData.openid;
     wx.request({
        url: that.data.globalurl +'getAllAddress.do',
        data: {'wxid': wxid},
        method:'GET',
        header:{
          'Content-Type':'application/x-www-form-urlencoded'
        },
        success:function(res){
          var totalAddress = res.data;
          if (0 == totalAddress.length || null == totalAddress){
            that.setData({
              noAddress:true
            })
          }else{
            that.setData({
              noAddress: false,
              allAddress: totalAddress
            });
          }
        },
     })
  },
  chooseAddress: function(e) {
    var that = this; 
    var type = e.currentTarget.dataset.id;
    this.setData({
       num: type
    })
    var dftaddress = this.data.allAddress[type];
    wx.request({
       url: that.data.globalurl + 'updateDefaultAddress.do',
       method: 'GET',
       header: {
          'Content-Type': 'application/x-www-form-urlencoded'
       },
       data: {
          'dftaddress': that.data.allAddress[type]
       },
       success: function (res) {
          console.log(res.data)
       }
    })
    wx.navigateBack({
       url: '/page/order/order',
    })
  },
  addAddress: function() {
   var that = this;
    wx.setStorage({
       key: 'modifyAddress',
       data: {},
    })
    wx.navigateTo({
      url: '../addAddress/addAddress'
    })
  },
  toEdit: function(e) {
    var that = this;
    var type = e.target.dataset.id;
    wx.setStorage({
       key: 'modifyAddress',
       data: that.data.allAddress[type],
    })
    wx.navigateTo({
      url: '../updateAddress/updateAddress',
    })
  },
  delAddress:function(e){
    var that = this;
    var type = e.target.dataset.id;
    var address = that.data.allAddress[type];
    var uniqueId = address.id;
    wx.request({
      url: that.data.globalurl + 'deleteSingleAddress/' + uniqueId+'.do',
      method:'DELETE',
      header:{
        'Content-Type':'application/json'
      },
      success:function(res){
        that.onShow();
      }
    })
  }
})