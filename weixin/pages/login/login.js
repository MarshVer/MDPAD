// pages/login/login.js
Page({

  /**
   * 页面的初始数据
   */
  data: {

  },
  login:function(options) {
    wx.request({
      url: 'http://localhost:9090/user/login',
      data: {
        userUsername: options.detail.value.userUsername,
        userPassword: options.detail.value.userPassword
      },
      success:function(res){
        if (!options.detail.value.userUsername || !options.detail.value.userPassword) {
          wx.showToast({
            title: '请输入用户名和密码',
            icon: 'none'
          });
        } else {
          if(res.data.code === '200') {
            wx.showToast({
              title: '登陆成功',
            });
            wx.switchTab({
              url: '../allOrder/allOrder',
            });
            wx.setStorageSync('key', res.data.data)
          } else {
            wx.showToast({
              title: '用户名或密码错误',
              icon: 'none'
            })
          }
        }
      },
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {

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