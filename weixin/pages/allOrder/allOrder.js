// pages/allOrder/allOrder.js
const user = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    orderDatas: {}
  },


  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    var that = this
    wx.request({
      url: 'http://localhost:9090/order/allOrder',
      data: {
        userPhone: user.user.userPhone,
      },
      success:function(res){        
        that.setData({
          orderDatas: res.data.data
        })
        console.log(res.data.data)
      }
    })

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {
    this.onLoad();
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  }
})