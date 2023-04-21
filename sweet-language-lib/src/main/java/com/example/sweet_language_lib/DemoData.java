package com.example.sweet_language_lib;


import com.alibaba.excel.annotation.ExcelProperty;
import com.android.annotations.Nullable;

public class DemoData {
  @ExcelProperty("key-android")
  @Nullable
  public String key;
  @ExcelProperty("en")
  @Nullable
  public String en;
  @ExcelProperty("ar")
  @Nullable
  public String ar;
  @ExcelProperty("Chinese")
  @Nullable
  public String zh;
  @ExcelProperty("tr")
  @Nullable
  public String tr;

  @Nullable
  public String getTr() {
    return tr;
  }

  public void setTr(@Nullable String tr) {
    this.tr = tr;
  }

  public String getZh() {
    return zh;
  }

  public void setZh(String zh) {
    this.zh = zh;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getEn() {
    return en;
  }

  public void setEn(String en) {
    this.en = en;
  }

  public String getAr() {
    return ar;
  }

  public void setAr(String ar) {
    this.ar = ar;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    DemoData demoData = (DemoData) o;

    if (key != null ? !key.equals(demoData.key) : demoData.key != null) return false;
    if (en != null ? !en.equals(demoData.en) : demoData.en != null) return false;
    return ar != null ? ar.equals(demoData.ar) : demoData.ar == null;
  }

  @Override
  public int hashCode() {
    int result = key != null ? key.hashCode() : 0;
    result = 31 * result + (en != null ? en.hashCode() : 0);
    result = 31 * result + (ar != null ? ar.hashCode() : 0);
    return result;
  }
}
