var app = getApp();
var total_micro_second = 1800 * 1000;
Page({
	data: {
		filterId: 1,
    globalurl: app.globalData.background_URL,
    shops: [],
    banners:[
      { img:'https://xikeruanjianwx.com/imgs/index/banner_1.jpg'},
      { img:'https://xikeruanjianwx.com/imgs/index/banner_2.jpg'}
    ],
    clock: ''
	},
   onLoad: function(){
      this.getShops();
   },
   //获取商铺方法
   getShops: function () {
      var that = this;//获取this对象
      wx.request({
        url: this.data.globalurl +'getAllShop.do',//请求后台获取商铺列表
         header: {
           "Content-Type": "application/x-www-form-urlencoded"
         },
         method: "GET",//get方式提交
         success: function (res) {
            that.setData({
              shops:res.data
            })
         },
      })
   }
  //  countDown: function (that){
  //    // 渲染倒计时时钟
  //    that.setData({
  //      clock: that.dateFormat(total_micro_second)
  //    });

  //    if (total_micro_second <= 0) {
  //      that.setData({
  //        clock: "订单已失效"
  //      });
  //      // timeout则跳出递归
  //      return;
  //    }
  //    setTimeout(function () {
  //      // 放在最后--
  //      total_micro_second -= 10;
  //      that.countDown(that);
  //    }
  //      , 10)
  //  },
  //  dateFormat: function (micro_second){
  //    var that = this;
  //    // 秒数
  //    var second = Math.floor(micro_second / 1000);
  //    // 小时位
  //    var hr = Math.floor(second / 3600);
  //    // 分钟位
  //    var min = that.fillZero(Math.floor((second - hr * 3600) / 60));
  //    // 秒位
  //    var sec = that.fillZero((second - hr * 3600 - min * 60));// equal to => var sec = second % 60;
  //    // 毫秒位，保留2位
  //    var micro_sec = that.fillZero(Math.floor((micro_second % 1000) / 10));

  //    return min + ":" + sec;
  //  },
  //  fillZero: function (num){
  //    return num < 10 ? "0" + num : num;
  //  }
});

