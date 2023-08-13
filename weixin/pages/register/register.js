// pages/register/register.js
Page({

  /**
   * 页面的初始数据
   */
  data: {

  },
  registerSubmit:function(options) {
    wx.request({
      url: 'http://localhost:9090/user',
      method: 'POST',
      data: {
        nickname: options.detail.value.nickname,
        userUsername: options.detail.value.userUsername,
        userPassword: options.detail.value.userPassword,
        userPhone: options.detail.value.userPhone
      },
      success:function(res){
        if (!options.detail.value.nickname || !options.detail.value.userUsername || !options.detail.value.userPassword || !options.detail.value.userPhone) {
          wx.showToast({
            title: '请输入完整信息',
            icon: 'none'
          });
        } else {
          if(res.data.code === '200') {
            console.log(res);
            wx.showToast({
              title: '注册成功',
            });
            wx.reLaunch({
              url: '../login/login',
            })
          } else {
            wx.showToast({
              title: '注册失败',
              icon: 'none'
            })
          }
        }
      }
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