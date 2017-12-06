var app = getApp();
Page({
	data: {
    goods:[],
    goodsList:[],
    globalurl:app.globalData.background_URL,
		cart: {
			count: 0,
			total: 0,
			list: {}
		},
		showCartDetail: false,
    orderOrBusiness: 'order',
	},
	onLoad: function (options) {
    var shopId = options.id;
    var urlgood = this.data.globalurl + 'selectGoodsOfShopByShopid/' + shopId+'.do';
    var urlgoodtype = this.data.globalurl +'selectAllGoodType.do';
    this.getGoods(urlgood);
    this.getGoodType(urlgoodtype);
		for (var i = 0; i < app.globalData.shops.length; ++i) {
			if (app.globalData.shops[i].id == shopId) {
				this.setData({
					shop: app.globalData.shops[i]
				});
				break;
			}
		}
	},
  //获取菜品的方法
  getGoods: function (address) {
    var that = this;//获取this对象
    wx.request({
      url: address,//请求后台获取商铺列表
      header: {
        "Content-Type": "application/json"
      },
      method: "GET",//get方式提交
      success: function (res) {
        console.log('菜品--------------')
        console.log(res)
        that.setData({
          goods: res.data.goodsList
        });

      },
    })
  },
  // 打电话
  makecall:function(e){
    var telnumber = e.currentTarget.dataset.id;
    wx.makePhoneCall({
      phoneNumber: telnumber
    })
  },
  // 点菜和商家切换
  tabChange: function (e) {
    var type = e.currentTarget.dataset.id;
    this.setData({
      orderOrBusiness: type
    })
  },
   //获取菜品全部类型的方法
   getGoodType: function (address) {
      var that = this;//获取this对象
      wx.request({
         url: address,//请求后台获取商铺列表
         header: {
            "Content-Type": "application/json"
         },
         method: "GET",//get方式提交
         success: function (res) {
            that.setData({
               goodsList: res.data
            });

         },
      })
   },
	tapAddCart: function (e) {
		this.addCart(e.target.dataset.id);
	},
	tapReduceCart: function (e) {
		this.reduceCart(e.target.dataset.id);
	},
	addCart: function (id) {
		var num = this.data.cart.list[id] || 0;
		this.data.cart.list[id] = num + 1;
		this.countCart();
	},
	reduceCart: function (id) {
		var num = this.data.cart.list[id] || 0;
		if (num <= 1) {
			delete this.data.cart.list[id];
		} else {
			this.data.cart.list[id] = num - 1;
		}
		this.countCart();
	},
	countCart: function () {
		var count = 0,
			total = this.data.shop.expressprice;
		for (var id in this.data.cart.list) {
			var goods = this.data.goods[id];
			count += this.data.cart.list[id];
			total += goods.price * this.data.cart.list[id];
		}
		this.data.cart.count = count;
		this.data.cart.total = total;
		/*设置全局结算总价格变量*/
		app.globalData.total = total;
		this.setData({
			cart: this.data.cart
		});
	},
   go_order: function(){
      var ordercart = this.data.cart;
      var ordergoods = this.data.goods;
      var ordershop = this.data.shop;
      if (this.data.cart.count){
         wx.setStorage({
            key: 'order',
            data:{
               ordercart,
               ordergoods,
               ordershop
            },
         })
         wx.navigateTo({
            url: '/page/order/order'
         })
      }
   },
	follow: function () {
		this.setData({
			followed: !this.data.followed
		});
	},
	onGoodsScroll: function (e) {
		if (e.detail.scrollTop > 10 && !this.data.scrollDown) {
			this.setData({
				scrollDown: true
			});
		} else if (e.detail.scrollTop < 10 && this.data.scrollDown) {
			this.setData({
				scrollDown: false
			});
		}

		var scale = e.detail.scrollWidth / 570,
			scrollTop = e.detail.scrollTop / scale,
			h = 0,
			classifySeleted,
			len = this.data.goodsList.length;
		this.data.goodsList.forEach(function (classify, i) {
			var _h = 70 + classify.goods.length * (46 * 3 + 20 * 2);
			if (scrollTop >= h - 100 / scale) {
            classifySeleted = classify.goodtypeid;
			}
			h += _h;
		});
		this.setData({
			classifySeleted: classifySeleted
		});
	},
	tapClassify: function (e) {
		var id = e.target.dataset.id;
		this.setData({
			classifyViewed: id
		});
		var self = this;
		setTimeout(function () {
			self.setData({
				classifySeleted: id
			});
		}, 100);
	},
	showCartDetail: function () {
		this.setData({
			showCartDetail: !this.data.showCartDetail
		});
	},
	hideCartDetail: function () {
		this.setData({
			showCartDetail: false
		});
	},
	submit: function (e) {
		server.sendTemplate(e.detail.formId, null, function (res) {
			if (res.data.errorcode == 0) {
				wx.showModal({
					showCancel: false,
					title: '恭喜',
					content: '订单发送成功！下订单过程顺利完成，本例不再进行后续订单相关的功能。',
					success: function(res) {
						if (res.confirm) {
							wx.navigateBack();
						}
					}
				})
			}
		}, function (res) {
			console.log(res)
		});
	}
});

