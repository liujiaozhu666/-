var app = getApp();
Page({
	data: {
      thisorderfoods:[],
      thisordercart:{},
      thisordershop:{},
      globalurl:app.globalData.background_URL,
      defaultaddress:{},
      orderDetail:{},
      noDefaultAddress:false,
      clock: ''
   },
	onLoad: function () {
      var wxid = app.globalData.openid;
      var that = this;
      that.getDefaultAddress();
      wx.getStorage({
         key: 'order',
         success: function(res) {
            that.setData({
               thisorderfoods: res.data.ordergoods,
               thisordercart: res.data.ordercart,
               thisordershop: res.data.ordershop,
               orderDetail:{
                 'wxid': wxid,
                 'orderdetailsum': res.data.ordercart.total,
                 'orderdetailshopname': res.data.ordershop.name,
                 'orderdetailshoptel':res.data.ordershop.tel,
                 'thisorderfoods': res.data.ordergoods,
                 'thisordercart': res.data.ordercart
               }
            })
         },
      })
   },
	onShow: function() {
    console.log("进入onShow")
      this.getDefaultAddress();
   },
  getDefaultAddress: function () {
    var that = this;
    var wxid = app.globalData.openid;
    wx.request({
      url: that.data.globalurl + 'getDefaultAddress.do',
      data: { 'wxid': wxid },
      method: 'GET',
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        console.log('默认地址')
        console.log(res.data.addressname)
        console.log("" != res.data || '' != res.data)
        if ("" != res.data || '' != res.data){
          var defaultaddress = res.data;
          that.setData({
            defaultaddress: defaultaddress,
            noDefaultAddress:true
          });
        }else{
          that.setData({
            defaultaddress: {},
            noDefaultAddress: false
          });
        }
      },
    })
  },

   // showtotal:function(){
   //    var that = this;
      
   // },
  saveOrderDetail: function (payflag, orderdetailid){
     var that = this;
     var orderDetail = that.data.orderDetail;
     var addressid = that.data.defaultaddress.addressid;
     
     wx.request({
       url: that.data.globalurl +"saveOrderDetail.do",
       data: {
         'orderdetailid': orderdetailid,
         'orderDetail': orderDetail,
         "addressid": addressid,
         "payflag": payflag
         },
       method:'GET',
       header:{
         'Content-Type':'application/x-www-form-urlencoded'
       },
       success:function(){
       }
     })
   },
   wxpay: function () {
      var that = this
      
      //登陆获取code   
      wx.login({
         success: function (res) {
            //获取openid   
            that.getOpenId(res.code)
         }
      });
   },
   getOpenId: function (code) {
      var that = this;
      wx.request({
         url: that.data.globalurl +"GetOpenId.do",
         data: { 'code': code },
         method: 'GET',
         header: {
            'content-type': 'application/x-www-form-urlencoded'
         },
         success: function (res) {
            that.generateOrder(res.data.openid)
         },
         fail: function () {
            // fail   
         },
         complete: function () {
            // complete   
         }
      })
   },
   /**生成商户订单 */
   generateOrder: function (openid) {
      var that = this;
      var total_fee = that.data.thisordercart.total*100;
      //统一支付   
      wx.request({
         url: that.data.globalurl +'pay.do',
         method: 'GET',
         header: {
            'content-type': 'application/x-www-form-urlencoded'
         },
         data: {
            "total_fee": total_fee,
            "body": '秦岭圣麦/'+that.data.thisordershop.name+'-消费',
            "openid":openid
         },
         success: function (res) {
           console.log('后台pay.do获取到的数据')
           console.log(res)
            var pay = res.data;
            //发起支付   
            var timeStamp = pay[0].timeStamp;
            console.log("timeStamp:" + timeStamp)
            var packages = pay[0].package;
            console.log("package:" + packages)
            var paySign = pay[0].paySign;
            console.log("paySign:" + paySign)
            var nonceStr = pay[0].nonceStr;
            console.log("nonceStr:" + nonceStr)
            var param = { "timeStamp": timeStamp, "package": packages, "paySign": paySign, "signType": "MD5", "nonceStr": nonceStr };
            that.pay(param)
         },
      })
   },
   /* 支付   */
   pay: function (param) {
     var orderdetailid = new Date().getTime();//毫秒值作为订单ID,由前端生成
     var that = this;
     //var totalSecond = 10 * 1000;
     //var orderLastTime = orderdetailid+'second';//设置缓存倒计时的key
    //  wx.setStorage({
    //    key: orderLastTime,
    //    data: totalSecond
    //  })
      wx.requestPayment({
         timeStamp: param.timeStamp,
         nonceStr: param.nonceStr,
         package: param.package,
         signType: param.signType,
         paySign: param.paySign,
         success: function (res) {
            //付款成功,即向后台发送'payok'标识
            that.saveOrderDetail('payok', orderdetailid);
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
         },
         complete: function (res) {
           console.log(res)
           wx.showModal({
             title: '您已取消支付,请在30分钟内在我的订单页付款',
             showCancel:false,
             confirmColor: '#FEB70F',
             success:function(res){
                if(res.confirm){
                  // that.countDown(orderLastTime);//取消付款触发订单倒计时
                  wx.setStorage({
                    key: orderdetailid + '',
                    data: {
                      'param':param
                    }
                  })
                  //用户取消时提交订单信息
                  that.saveOrderDetail('paycancel', orderdetailid);
                  wx.redirectTo({
                    url: '/page/myorder/myorder',
                  })
                }
             }
           })
         }
      })
   },
   toMyAddress:function(){
      wx.navigateTo({
         url: '/page/address/address',
      })
   },
   //以下是订单倒计时
   
  //  countDown: function (storageKey) {
  //    console.log('进入倒计时----------------------------------------------------')
  //    var orderLastTime = storageKey;
  //    var that = this;
  //    var total_micro_second = 0;
  //    wx.getStorage({
  //      key: orderLastTime,
  //      success: function(res) {
  //        console.log(res)
  //        total_micro_second = res.data;
  //      },
  //    })
  //    console.log(total_micro_second);
  //    if (total_micro_second <= 0) {
  //      console.log('该去后台关闭订单了-----------------------------------------')
  //      wx.removeStorage({//订单倒计时结束，清楚对应订单的计时缓存
  //        key: 'orderLastTime',
  //        success: function(res) {}
  //      })
  //      clearTimeout(t);//清除计时器
  //      return;
  //    }
  //    wx.setStorage({
  //      key: orderLastTime,
  //      data: total_micro_second
  //    })
    //  var t = setTimeout(function () {
    //    // 放在最后--
    //    total_micro_second -= 1000;
    //    that.countDown(orderLastTime);
    //  }
    //    , 1000);
  //  }
})

