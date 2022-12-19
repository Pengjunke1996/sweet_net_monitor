package com.sweet.sign;

public abstract class ProgressCallback {

  //用于进度的回调
  public abstract void onLoading(long total, long progress);
}