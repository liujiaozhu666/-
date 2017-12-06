var app = getApp()
Page({
  data: {
    orderdetail:{},
    globalurl: app.globalData.background_URL,
    orderFood:[],
    arrivetime:'',
    orderDate:'',
    userInfo: '',
    userAddress:{},
    orderStatus: 1,   // 0未付款 1已接单 2派送中 3已完成
    markers: [
      {
        iconPath: "../../imgs/orderDetail/shop-active.png",
        id: 0,
        name: '店家地址',
        latitude: 34.1017663010,
        longitude: 108.8846987486,
        width: 30,
        height: 30,
        callout: {
          content: '店家地址',
          display: 'ALWAYS',
          borderRadius: 2,
          bgColor: '#ffe400',
          padding: 10
        },
      }
    ]
  },  
  //获取当前位置
  onLoad: function (f) {  
    this.getThisOrderAndAddress(f);
  },
  onShow:function(e){
    if (typeof (e) !="undefined"){
      this.getThisOrderAndAddress(e);
    }
  },
  // 根据用户id和地址id获取该订单中的地址
  // 获取该订单相关信息
  getThisOrderAndAddress:function(e){
    var that = this;
    var wxid = e.wxid;
    var addressid = e.addressid;
    wx.request({
      url: that.data.globalurl + 'getAddressByOpenidAndAddressid.do',
      data: {
        'wxid': wxid,
        'addressid': addressid
      },
      method: "GET",
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      success: function (res) {
        var address = res.data;
        that.setData({
          userAddress: address
        })
      }
    })
    if (JSON.stringify(that.data.orderdetail) == "{}"){
      wx.getStorage({
        key: 'orderdetail',
        success: function (res) {
          var orderarrivetime = new Date(res.data.ordertime);
          var ordertime = new Date(res.data.ordertime);
          orderarrivetime.setTime(orderarrivetime.setMinutes(orderarrivetime.getMinutes() + 30));
          orderarrivetime = orderarrivetime.getHours() + ':' + orderarrivetime.getMinutes();
          ordertime = ordertime.getFullYear() + '年' + ordertime.getMonth() + '月' + ordertime.getDate() + '日' + ordertime.getHours() + ':' + ordertime.getMinutes();
          that.setData({
            orderdetail: res.data,
            arrivetime: orderarrivetime,
            orderDate: ordertime,
            orderFood: JSON.parse(res.data.orderdetailcontent)
          })
        },
      })
    }
  },
  cancelorder: function () {
    var that = this;
    var wxid = that.data.orderdetail.wxid;
    var orderdetailid = that.data.orderdetail.orderdetailid;
    var addressid = that.data.orderdetail.addressid;
    var jsonParam = {
      "wxid": wxid,
      "addressid": addressid
    };
    wx.request({
      url: that.data.globalurl + 'cancelOrder.do',
      data: {
        "wxid": wxid,
        "orderdetailid": orderdetailid
      },
      method: "GET",
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      success: function (res) {
        var neworderdetail = that.data.orderdetail;
        neworderdetail.orderdetailstate = "订单已取消";
        that.setData({
          orderdetail: neworderdetail
        })
        that.onShow(jsonParam);
      }
    })
  },
  // 打电话
  makecall: function (e) {
    var telnumber = e.currentTarget.dataset.tel;
    wx.makePhoneCall({
      phoneNumber: telnumber
    })
  },
  /*订单明细 
  未支付的订单再次发起支付   */
  pay: function (e) {
    var payparam = e.currentTarget.dataset.payid + '';
    var that = this;
    wx.getStorage({
      key: payparam,
      success: function (res) {
        var param = res.data;
        wx.requestPayment({
          timeStamp: param.timeStamp,
          nonceStr: param.nonceStr,
          package: param.package,
          signType: param.signType,
          paySign: param.paySign,
          success: function (res) {
            wx.removeStorage({
              key: payparam,
              success: function (res) { },
            })
            //付款成功时提交订单信息
            that.saveOrderDetail('payok');
            // success   
            // wx.navigateBack({
            //   delta: 1, // 回退前 delta(默认为1) 页面   
            //   success: function (res) {
            //     wx.showToast({
            //       title: '支付成功',
            //       icon: 'success',
            //       duration: 500
            //     })
            //   },
            //   fail: function () {
            //   },
            //   complete: function () {
            //   }
            // })
          },
          fail: function (res) {
            console.log(res)
          },
          complete: function (res) {
            console.log(res)
            // wx.showModal({
            //   title: '您已取消支付',
            //   showCancel: false,
            //   confirmColor: '#FEB70F',
            //   success: function (res) {
            //     if (res.confirm) {
            //       //用户取消时提交订单信息
            //       that.saveOrderDetail('paycancel');
            //       wx.navigateTo({
            //         url: '/page/myorder/myorder',
            //       })
            //     }
            //   }
            // })
          }
        })
      }
    })
  },
  regionchange(e) {
    console.log(e.type)
  },
  markertap(e) {
    console.log(e.markerId)
  },
  controltap(e) {
    console.log(e.controlId)
  },
  toApply: function() {
    wx.navigateTo({
      url: '../applyRefund/applyRefund',
    })
  },
  logToTrue: function() {
    this.setData({
      logistics: true
    })
  },
  logToFalse: function() {
    this.setData({
      logistics: false
    })
  },
  toEvaluate: function() {
    wx.navigateTo({
      url: '../evaluate/evaluate',
    })
  }
})