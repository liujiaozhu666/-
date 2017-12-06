var app = getApp();
var qqmapsdk;
Page({
  data: {
    id: '先生',
    city: '点击选择',
    name: '',
    phone: '',
    cityDetail: '',
    address: '',   //当前定位的城市
    nothing: false,
    globalurl: app.globalData.background_URL,
    thisAddress:{},
    modifyAddress:{},
    city:"",
    cityDetail:"",
    validateInfo:true,
    warnMessage:""
  },
  onLoad:function(){
     var that = this;
     wx.getStorage({
        key: 'modifyAddress',
        success: function(res) {
           console.log(res)
           var hasPro = false;
         //利用遍历来判断res中的data是否为{}
         //以便区分是执行修改地址方法还是新增地址方法
           for(var pro in res.data){
              hasPro = true;
           }
           if(hasPro){
              var arr = res.data.addressname.split(" ");
              console.log(res)
              that.setData({
                 modifyAddress: res.data,
                 city: arr[0],
                 cityDetail: arr[1]
              })
           }else{
              that.setData({
                 modifyAddress: {}
              })
           }
        },
     })
  },
  getAddress: function () {
    var that = this;
    wx.chooseLocation({
      success: function (res) {
      //   if (res.address.length > 10) {
      //     res.address = res.address.substr(0, 10) + '...'
      //   }
        that.setData({
          city: res.address
        })
      },
    })
  },
  setName: function (e) {
    this.setData({
      name: e.detail.value
    })
  },
  setPhone: function (e) {
    this.setData({
      phone: e.detail.value
    })
  },
  setCityDetail: function(e) {
    this.setData({
      cityDetail: e.detail.value
    })
  },
  // saveAddress: function() {
  //   console.log(this.data)
  //   wx.navigateBack({
  //     url: '../address/address',
  //   })   
  //   wx.request({
  //     url: '',
  //     data: this.data,
  //     success: function(res) {
  //       console.log(res)
  //     }
  //   })
  // },
  formSubmit: function (e) {
    var that = this;
    var phoneReg = /^1[34578]\d{9}$/;
    if ('' == e.detail.value.customername){
      that.setData({
        validateInfo:false,
        warnMessage:'请填写收货人姓名'
      })
    }
    else if (!(phoneReg.test(e.detail.value.phone))){
      that.setData({
        validateInfo: false,
        warnMessage: '请正确填写手机号'
      })
    } else if ('' == e.detail.value.city){
      that.setData({
        validateInfo: false,
        warnMessage: '请正确填写收货地址'
      })
    } else {//验证收货信息无误提交订单
      console.log('form发生了submit事件，携带数据为：', e.detail.value)
      var city = e.detail.value.city;
      var cityDetail = e.detail.value.cityDetail;
      var allAddressname = city + " " + cityDetail;
      var thiswxid = app.globalData.openid;
      that.setData({
        thisAddress: {
          customername: e.detail.value.customername,
          sex: e.detail.value.sex,
          phone: e.detail.value.phone,
          addressname: allAddressname,
          wxid: thiswxid
        }
      })
      wx.request({
        url: that.data.globalurl + 'addAddress.do',
        method: 'GET',
        header: {
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        data: {
          'addAddress': that.data.thisAddress
        },
        success: function (res) {
          console.log(res.data)
          wx.navigateBack({
            url: '../address/address'
          })
        }
      })
      // wx.navigateBack({
      //   url: '../address/address'
      // })
    }
  },
  formReset: function () {
    console.log('form发生了reset事件')
  }

})