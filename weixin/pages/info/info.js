// pages/info/info.js
const user = getApp()
Page({
  /**
   * 页面的初始数据
   */

  data: {
    userInfo: user.user,
  },

  reLogin() {
    wx.showModal({
      title: '确认是否退出',
      content: '确认将需要重新登录',
      complete: (res) => {
        if (res.cancel) {
          return
        }
        if (res.confirm) {
          wx.reLaunch({
            url: '/pages/login/login',
          })
          wx.removeStorage({
            key: 'key',
          })
        }
      }
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
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