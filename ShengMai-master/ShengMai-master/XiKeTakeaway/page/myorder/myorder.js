//myorder.js
var app = getApp();
Page({
  data: {
    // order
    orderOk: false,
    globalurl: app.globalData.background_URL,
    orderdetail:[],
    ordergoods:[],
  },
  onLoad: function () {
    this.getMyAllOrders();
  },
  onShow:function(){
    this.getMyAllOrders();
  },
  onPullDownRefresh:function(){
    var that = this;
    that.onLoad();
    wx.stopPullDownRefresh();
  },
  // 根据用户id获取全部订单
  getMyAllOrders:function(){
    var wxid = app.globalData.openid;
    var that = this;
    wx.request({
      url: that.data.globalurl + 'getAllOrderDetailByWxid.do',
      data: {
        "wxid": wxid
      },
      method: "GET",
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        that.setData({
          orderdetail: res.data//设置订单数据集
        })
        var arr = new Array();
        for (var i = 0; i < res.data.length; i++) {
          arr[i] = JSON.parse(res.data[i].orderdetailcontent);
        }
        that.setData({
          ordergoods: arr
        })
      }
    })
  },
  // order
  orderOk: function () {
    this.setData({
      orderOk: true
    })
  },
  okCancel: function () {
    this.setData({
      orderOk: false
    })
  },
  okOk: function () {
    this.setData({
      orderOk: false
    })
  },

  toDetail: function (e) {
    var that = this;
    console.log('跳转至订单详情页面')
    console.log(e)
    var wxid = e.currentTarget.dataset.orderinfo.wxid;
    var addressid = e.currentTarget.dataset.orderinfo.addressid;
    wx.setStorage({
      key: 'orderdetail',
      data: e.currentTarget.dataset.orderinfo,
    })
    wx.navigateTo({
      url: '/page/orderDetail/orderDetail?wxid='+wxid+'&addressid='+addressid
    })
  },
  cancelorder:function(e){
    var that = this;
    var wxid = e.currentTarget.dataset.cancelinfo.wxid;
    var orderdetailid = e.currentTarget.dataset.cancelinfo.orderdetailid;
    wx.request({
      url: that.data.globalurl +'cancelOrder.do',
      data:{
        "wxid":wxid,
        "orderdetailid":orderdetailid
      },
      method:"GET",
      header:{
        "Content-Type":"application/x-www-form-urlencoded"
      },
      success:function(res){
        that.onShow();
        console.log(res)
      }
    })
  },
  //等待付款的订单，付款成功，改变订单状态
  changeOrderSuccess: function (e) {//传入一个订单对象作为参数
    var that = this;
    var wxid = e.wxid;
    var orderdetailid = e.orderdetailid;
    wx.request({
      url: that.data.globalurl +'successOrder.do',
      data:{
        "wxid":wxid,
        "orderdetailid":orderdetailid
      },
      method:'GET',
      header:{
        "Content-Type":"application/x-www-form-urlencoded"
      },
      success:function(res){
        that.onShow();
      }
    })
  },
  //等待付款的订单，取消支付，改变订单状态为已取消
  changeOrderInvalid: function (e) {//传入一个订单对象作为参数
    var that = this;
    var wxid = e.wxid;
    var orderdetailid = e.orderdetailid;
    wx.request({
      url: that.data.globalurl + 'invalidOrder.do',
      data: {
        "wxid": wxid,
        "orderdetailid": orderdetailid
      },
      method: 'GET',
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      success: function (res) {
        that.onShow();
      }
    })
  },
  /*订单列表 
  未支付的订单再次发起支付   */
  pay: function (e) {
    var paramOrder = e.currentTarget.dataset.orderdetail;
    var paykey = e.currentTarget.dataset.orderdetail.orderdetailid+'';
    var that = this;
    wx.getStorage({
      key: paykey,
      success: function(res) {
        var param = res.data.param;
        wx.requestPayment({
          timeStamp: param.timeStamp,
          nonceStr: param.nonceStr,
          package: param.package,
          signType: param.signType,
          paySign: param.paySign,
          success: function (res) {
            wx.removeStorage({
              key: paykey,
              success: function(res) {},
            })
            //付款成功时提交订单信息
            that.changeOrderSuccess(paramOrder);
            // success   
            wx.navigateBack({
              delta: 1, // 回退前 delta(默认为1) 页面   
              success: function (res) {
                wx.showToast({
                  title: '支付成功',
                  icon: 'success',
                  duration: 500
                })
              },
              fail: function () {
              },
              complete: function () {
              }
            })
          },
          fail: function (res) {
            console.log(res)
          },
          complete: function (res) {
            wx.showModal({
              title: '您已取消订单',
              showCancel: false,
              confirmColor: '#FEB70F',
              success: function (res) {
                if (res.confirm) {
                  that.changeOrderInvalid(paramOrder);
                  //用户取消时提交订单信息
                  // that.saveOrderDetail('paycancel');
                  // wx.navigateTo({
                  //   url: '/page/myorder/myorder',
                  // })
                }
              }
            })
          }
        })
      }
    })
  }
})
