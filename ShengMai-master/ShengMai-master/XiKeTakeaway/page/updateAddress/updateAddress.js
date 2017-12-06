var app = getApp();
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
    modifyAddress: {},
    city: "",
    cityDetail: ""
  },
  onLoad: function () {
    var that = this;
    wx.getStorage({
      key: 'modifyAddress',
      success: function (res) {
        console.log(res)
        var hasPro = false;
        //利用遍历来判断res中的data是否为{}
        //以便区分是执行修改地址方法还是新增地址方法
        for (var pro in res.data) {
          hasPro = true;
        }
        if (hasPro) {
          var arr = res.data.addressname.split(" ");
          console.log(res)
          that.setData({
            modifyAddress: res.data,
            city: arr[0],
            cityDetail: arr[1]
          })
        } else {
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
  chooseId: function (e) {
    var type = e.currentTarget.dataset.id;
    this.setData({
      id: type
    })
  },
  toEditAddress: function () {
    this.setData({
      pageType: 2
    })
  },
  noChoose: function () {
    this.setData({
      pageType: 1,
      city: '点击选择',
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
  setCityDetail: function (e) {
    this.setData({
      cityDetail: e.detail.value
    })
  },
  formSubmit: function (e) {
    var that = this;
    //modifyAddress赋值给updateAddress
    var updateAddress = that.data.modifyAddress;
    console.log('form发生了submit事件，携带数据为：', e.detail.value)
    //获取表单提交的值城市、明细，合并为一个值
    var city = e.detail.value.city;
    var cityDetail = e.detail.value.cityDetail;
    var allAddressname = city + " " + cityDetail;
    //改变updateAddress对象中的属性值
    updateAddress.customername = e.detail.value.customername;
    updateAddress.sex = e.detail.value.sex;
    updateAddress.phone = e.detail.value.phone;
    updateAddress.addressname = allAddressname;
    //向后台发送请求数据，更新地址
    wx.request({
      url: that.data.globalurl + 'updateAddress.do',
      method: 'get',
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      data: {
        'updateAddress': updateAddress
      },
      success: function (res) {
        console.log(res.data)
      }
    })
    wx.navigateBack({
      url: '/page/address/address',
    })
  },
  formReset: function () {
    console.log('form发生了reset事件')
  }
})