// app.js
App({
  onLaunch() {
    this.user = wx.getStorageSync('key')
    console.log(this.user)
  },
  globalData: {
    user: {}
  }
})
